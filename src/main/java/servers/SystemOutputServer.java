package servers;

import model.Hub;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Level;

public class SystemOutputServer {

    private static final Logger logger = Logger.getLogger(SystemOutputServer.class.getName());

    private Hub hub;

    private Socket outputConnection;

    private OutputStream outputStreamConnection;

    public SystemOutputServer(Hub hub){
        this.hub = hub;
    }

    private OutputStream createOutputConnection(String host, int port){
        try{
            outputConnection = new Socket(host, port);
            outputStreamConnection = outputConnection.getOutputStream();
            outputStreamConnection.flush();

            return outputStreamConnection;
        } catch (Exception ex){
            logger.error("Error in creating output socket connection: " + ex.getMessage());
            return null;
        }
    }

    private void closeOutputConnection(){
        try{
            outputStreamConnection.close();
            outputConnection.close();
            logger.trace("OutputStream connection has been closed!");
        } catch (IOException e) {
            logger.error("Error in closing the outputStream connection: " + e.getMessage() );
        }
    }

    public void sendRequest(networking.Message request, String host, int port){
        try{
            OutputStream outputStreamLocal = createOutputConnection(host, port);

            if(outputStreamLocal == null){
                return;
            }

            logger.info("Sending request ... \n" + request);
            logger.info("Size of the sending message: " + Integer.valueOf(request.getSerializedSize()).toString());

            byte[] sizeMessageToByes = ByteBuffer.allocate(4).putInt(request.getSerializedSize()).array();
            byte[] contentMessageToBytes = request.toByteArray();
            byte[] requestToBytes = new byte[sizeMessageToByes.length + contentMessageToBytes.length];

            ByteBuffer buffer = ByteBuffer.wrap(requestToBytes);
            buffer.put(sizeMessageToByes);
            buffer.put(contentMessageToBytes);
            requestToBytes = buffer.array();

            outputStreamConnection.write(requestToBytes);
            outputStreamConnection.flush();

            logger.info("Request has been sent!");
            closeOutputConnection();
        } catch (IOException e) {
            logger.error( "Error in sending the message through socket stream: " + e.getMessage());
        }
    }






}
