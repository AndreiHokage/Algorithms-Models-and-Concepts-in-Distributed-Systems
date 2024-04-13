package processing;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import model.Hub;
import model.ProcessNode;

import java.util.logging.*;

public class ProcessServicesRPC {

    private static final Logger logger = Logger.getLogger(ProcessServicesRPC.class.getName());

    private ProcessNode processNode;

    private Hub hub;

    private InputStream input;

    private OutputStream output;

    private Socket connection;

    private Socket outputConnection;

    private OutputStream outputStreamConnection;

    private Socket inputConnection;

    private InputStream inputStreamConnection;

    private BlockingQueue<networking.Message> qResponses;

    private volatile boolean finished;

    public ProcessServicesRPC(ProcessNode processNode,  Hub hub){
        this.processNode = processNode;
        this.hub = hub;
        this.qResponses = new LinkedBlockingDeque<networking.Message>();
    }

    public void registrateToTheHUB(){
        //networking.Message requestRegistrationMessage = ProtoSerialiseUtils.createProcRegistrationRequest(processNode);
        //sendRequest(requestRegistrationMessage);
        //networking.Message response = readResponse();
        //System.out.println("Receive response ... \n" + response);
    }

    private void initializeConnection() {
        try{
            this.connection = new Socket(hub.getHost(), hub.getPort().intValue());
            output = connection.getOutputStream();
            output.flush();
            input = connection.getInputStream();
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private networking.Message readResponse(){
        networking.Message response = null;

        try {
            response = qResponses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }

    private OutputStream createOutputConnection(){
        try {
            outputConnection = new Socket(hub.getHost(), hub.getPort().intValue());
            outputStreamConnection = outputConnection.getOutputStream();
            outputStreamConnection.flush();

            return outputStreamConnection;
        } catch (Exception ex){
            logger.log (Level.SEVERE,"Error in creating output socket connection: " + ex.getMessage());
            return null;
        }
    }

    private void closeOutputConnection(){
        try{
            outputStreamConnection.close();
            outputConnection.close();
            logger.info("OutputStream connection has been closed!");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in closing the outputStream connection: " + e.getMessage() );
        }
    }

    private void sendRequest(networking.Message request){
        try{
            OutputStream outputStreamConnection = createOutputConnection();

            if(outputStreamConnection == null){
                return;
            }

            logger.info("Sending request ... \n" + request);
            logger.info("Size of the sending message: " + Integer.valueOf(request.getSerializedSize()).toString());

            byte[] sizeMessageToBytes = ByteBuffer.allocate(4).putInt(request.getSerializedSize()).array();
            byte[] contentMessageToBytes = request.toByteArray();

            byte[] requestToBytes = new byte[sizeMessageToBytes.length + contentMessageToBytes.length];

            ByteBuffer buffer = ByteBuffer.wrap(requestToBytes);
            buffer.put(sizeMessageToBytes);
            buffer.put(contentMessageToBytes);
            requestToBytes = buffer.array();

            outputStreamConnection.write(requestToBytes);
            outputStreamConnection.flush();

            logger.info("Request has been sent!");
            closeOutputConnection();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in sending the message through socket stream: " + e.getMessage());
        }
    }

    private InputStream createInputConnection(){
        try{
            inputConnection = new Socket(hub.getHost(), hub.getPort().intValue());
            inputStreamConnection = inputConnection.getInputStream();

            return inputStreamConnection;
        } catch (Exception ex) {
            logger.log (Level.SEVERE,"Error in creating output socket connection: " + ex.getMessage());
            return null;
        }
    }

    private networking.Message receiveReply(){
        InputStream inputStreamConnection = createInputConnection();

        if(inputStreamConnection == null){
            return null;
        }

        logger.info("Reading reply... ");

        return null;
    }

    private void startReader(){
        Thread readerThread = new Thread(new ReaderThread());
        readerThread.start();
    }

    private class ReaderThread implements Runnable {

        @Override
        public void run() {
            while(!finished){
                try {
                    networking.Message response = networking.Message.parseDelimitedFrom(input);
                    qResponses.put(response);
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (InterruptedException e) {
                    System.out.println("Error at pushing a message to the queue "+e);
                }
            }
        }
    }

}
