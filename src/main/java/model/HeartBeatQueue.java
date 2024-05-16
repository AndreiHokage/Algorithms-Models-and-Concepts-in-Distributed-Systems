package model;

import networking.Message;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class HeartBeatQueue extends EventQueue {

    private static Logger logger = Logger.getLogger(EventQueue.class.getName());

    // We need a thread-safe queue because multiple threads will use the queue
    // An event queue / thread
//    private BlockingQueue<Message> heartBeatsQueue = null;

    public HeartBeatQueue(){
        super(false);
        //heartBeatsQueue = new LinkedBlockingDeque<Message>();
    }

//    @Override
//    public BlockingQueue<Message> getEventsQueue() {
//        return heartBeatsQueue;
//    }
}
