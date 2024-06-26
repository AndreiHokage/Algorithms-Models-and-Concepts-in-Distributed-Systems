package servers;

import model.EventQueue;
import model.HeartBeatQueue;
import org.apache.log4j.Logger;
import processing.ProcessingInputRequestWorker;

import java.net.Socket;

/**
 * Run the input server on a separate thread because it listens for requests.
 */
public class SystemInputServer extends ConcurrentSystemInputServer{

    private static final Logger logger = Logger.getLogger(SystemInputServer.class.getName());

    private EventQueue eventQueue;

    private EventQueue heartBeatQueue;

    public SystemInputServer(int port, EventQueue eventQueue, EventQueue heartBeatQueue){
        super(port);
        this.eventQueue = eventQueue;
        this.heartBeatQueue = heartBeatQueue;
        logger.info("SystemInputServer has started!");
    }

    /**
     * Create a separate thread for each served request because there can be blocking operations when processing a message
     * that may lead to blocking of the server thread
     * @param client
     * @return
     */
    @Override
    protected Thread createWorker(Socket client) {
        Thread worker = new Thread(new ProcessingInputRequestWorker(eventQueue, heartBeatQueue, client));
        return worker;
    }

}
