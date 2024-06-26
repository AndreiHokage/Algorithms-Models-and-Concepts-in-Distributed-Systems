package processing;

import com.google.protobuf.InvalidProtocolBufferException;
import model.EventQueue;
import model.HeartBeatQueue;
import networking.Message;
import org.apache.log4j.Logger;
import utils.MessageUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ProcessingInputRequestWorker implements Runnable {

    private static final Logger logger = Logger.getLogger(ProcessingInputRequestWorker.class.getName());

    private EventQueue eventQueue;

    private EventQueue heartBeatQueue;

    private Socket connection;

    private InputStream inputStream;

    public ProcessingInputRequestWorker(EventQueue eventQueue, EventQueue heartBeatQueue, Socket connection) {
        this.eventQueue = eventQueue;
        this.heartBeatQueue = heartBeatQueue;
        this.connection = connection;

        try {
            this.inputStream = connection.getInputStream(); // ASSOCIATED WITH THE CONNECTIONS
            // Reading from an input stream blocks until input data is available, the end of the stream is detected, or an exception is thrown
            // It is possible as not all the bytes to be available from the first time, so we need to retry
        } catch (IOException e) {
            logger.error("Error in getting the input stream for a new connection: " + e.getMessage());
        }
    }

    /**
     * This method blocks (The thread is blocked; th reason why we wanted a separate thread from the input system server thread)
     * @param predefinedLength
     * @return
     */
    private byte[] readPredefinedBytesFromInputStream(int predefinedLength){
        byte[] byteContentArray = new byte[predefinedLength];
        int lengthIndex = 0;
        int amountBytesForLength = predefinedLength;
        while(amountBytesForLength > 0){
            try{
                int countReadBytes = inputStream.read(byteContentArray, lengthIndex, amountBytesForLength);
                if(countReadBytes != -1){
                    lengthIndex += countReadBytes;
                    amountBytesForLength -= countReadBytes;
                }
            } catch (IOException e) {
                logger.error("Error reading form the input stream: " + e.getMessage());
            }
        }

        return byteContentArray;
    }

    private int readLengthMessage(){
        byte[] lengthAsBytes = readPredefinedBytesFromInputStream(4);
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES); // 4 bytes
        buffer.put(lengthAsBytes);
        buffer.rewind();

        int lengthMsg = buffer.getInt();
        return lengthMsg;
    }

    private networking.Message readContentMessage(int lengthMessage){
        byte[] contentAsBytes = readPredefinedBytesFromInputStream(lengthMessage);
        try {
            networking.Message message = networking.Message.parseFrom(contentAsBytes);
            return message;
        } catch (InvalidProtocolBufferException e) {
            logger.error("Error deserializing the message: " + e.getMessage());
            return null;
        }
    }

    private void closeInputConnection(){
        try{
            this.inputStream.close();
            this.connection.close();
        } catch (IOException e) {
            logger.error("Error closing the input stream connection: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        int lengthMessage = readLengthMessage();
        networking.Message message = readContentMessage(lengthMessage);
        try {
            if(MessageUtils.checkHeartBeatOriginMessages(message) == false) { // the message is not related to the liveness status of the system
                eventQueue.getEventsQueue().put(message); // read the message and close the connection. Not wait after completion of handilng the message => asynchronous
            } else {
                heartBeatQueue.getEventsQueue().put(message);
            }
        } catch (InterruptedException e) {
            logger.error("Error putting the message into eventQueue: " + e.getMessage());
        } finally {
            closeInputConnection();
        }
    }

}
