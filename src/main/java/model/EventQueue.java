package model;

import abstractizations.AppAbstraction;
import networking.Message;
import org.apache.log4j.Logger;
import processing.CatalogueAbstractions;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class EventQueue {

    private static Logger logger = Logger.getLogger(EventQueue.class.getName());

    // We need a thread-safe queue because multiple threads will use the queue
    // An event queue / thread
    private BlockingQueue<networking.Message> eventsQueue = null;

    public EventQueue(){
        eventsQueue = new LinkedBlockingDeque<networking.Message>();
        try {
            eventsQueue.put(CatalogueAbstractions.getAppAbstraction().handlingProcRegistration());
        } catch (InterruptedException e) {
            logger.error("Couldn't insert the porc registration message on init of enetQueue: " + e.getMessage());
        }
    }

    public BlockingQueue<Message> getEventsQueue() {
        return eventsQueue;
    }
}
