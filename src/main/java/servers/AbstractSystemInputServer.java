package servers;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerError;

public abstract class AbstractSystemInputServer {

    private static final Logger logger = Logger.getLogger(AbstractSystemInputServer.class.getName());

    private int port;

    private ServerSocket server = null;

    public AbstractSystemInputServer(int port){
        this.port = port;
    }

    public void start(){
        try{
            server = new ServerSocket(port);
            while (true){
                //logger.trace("Waiting for clients ...");
                Socket client = server.accept();
                //logger.info("Client connected ...");
                processRequest(client);
            }
        } catch (IOException e) {
            logger.error("Starting SystemInputServer error: " + e.getMessage());
        }
    }

    protected abstract void processRequest(Socket client);

    public void stop(){
        try{
            server.close();
        } catch (IOException e) {
            logger.error("Closing SystemInputServer error: " + e.getMessage());
        }
    }





}
