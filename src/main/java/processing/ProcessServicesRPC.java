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

public class ProcessServicesRPC {

    private ProcessNode processNode;

    private Hub hub;

    private InputStream input;

    private OutputStream output;

    private Socket connection;

    private BlockingQueue<networking.Message> qResponses;

    private volatile boolean finished;

    public ProcessServicesRPC(ProcessNode processNode,  Hub hub){
        this.processNode = processNode;
        this.hub = hub;
        this.qResponses = new LinkedBlockingDeque<networking.Message>();
    }

    public void registrateToTheHUB(){
        initializeConnection();
        networking.Message requestRegistrationMessage = ProtoUtils.createProcRegistrationRequest(processNode);
        //sendRequest(ProtoUtils.createSizeMessageRequest(requestRegistrationMessage));
        sendRequest(requestRegistrationMessage);
        // sendRequest(ProtoUtils.createProcRegistrationRequest(processNode));
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

    private void sendRequest(networking.Message request){
        try{
            System.out.println("Sending request ... \n" + request);
            System.out.println("MESSAGE SIZE: " + Integer.valueOf(request.getSerializedSize()).toString());

            byte[] sizeBytes = ByteBuffer.allocate(4).putInt(request.getSerializedSize()).array();
            byte[] messageBytes = request.toByteArray();

            byte[] combined = new byte[sizeBytes.length + messageBytes.length];

            ByteBuffer buffer = ByteBuffer.wrap(combined);
            buffer.put(sizeBytes);
            buffer.put(messageBytes);

            combined = buffer.array();

            //request.writeDelimitedTo(output);
            output.write(combined);
            output.flush();
            System.out.println("Request sent.");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
