package servers.threadsServer;

import abstractizations.*;
import model.EventQueue;
import org.apache.log4j.Logger;
import processing.factoryAbstractizations.*;
import utils.IntfConstants;

import java.util.List;

public class ProcessEventQueueThread extends Thread{

    private static final Logger logger = Logger.getLogger(ProcessEventQueueThread.class.getName());

    private EventQueue eventQueue = null;

    public ProcessEventQueueThread(EventQueue eventQueue){
        this.eventQueue = eventQueue;
    }

    public EventQueue getEventQueue() {
        return eventQueue;
    }

    private ManageEventAbstractFactory createManageEventAbstractFactory(String abstractionAlgorithm){
        if(abstractionAlgorithm.equals(IntfConstants.PL_ABS)){
            return ManageEventPLFactory.createNewInstance();
        }

        if(abstractionAlgorithm.equals(IntfConstants.BEB_ABS)){
            return ManageEventBEBFactory.createNewInstance();
        }

        if(abstractionAlgorithm.startsWith(IntfConstants.NNAR_ABS)){
            return ManageEventNNARFactory.createNewInstance();
        }

        if(abstractionAlgorithm.equals(IntfConstants.APP_ABS)){
            return ManageEventAPPFactory.createNewInstance();
        }

        if(abstractionAlgorithm.equals(IntfConstants.EPFD_ABS)){
            return ManageEventEPFDFactory.createNewInstance();
        }

        if(abstractionAlgorithm.equals(IntfConstants.ELD_ABS)){
            return ManageEventELDFactory.createNewInstance();
        }

        if(abstractionAlgorithm.equals(IntfConstants.EC_ABS)){
            return ManageEventECFactory.createNewInstance();
        }

        if(abstractionAlgorithm.startsWith(IntfConstants.EP_ABS)){
            return ManageEventEPFactory.createNewInstance();
        }

        if(abstractionAlgorithm.startsWith(IntfConstants.UC_ABS)){
            return ManageEventUCFactory.createNewInstance();
        }

        return null;
    }

    protected void addMessageToTheQueue(networking.Message message) throws InterruptedException {
        eventQueue.getEventsQueue().put(message);
    }

    private void consume(networking.Message message) throws InterruptedException {
        String abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getToAbstractionId());
        // The message is not triggered by us, it means that we have received it
        if(abstractionAlgorithm == null){
            abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getFromAbstractionId());
        }

        // determine the abstraction factory based on abstractionAlgorithm attribute. Return just the abstraction that will handle the message
        ManageEventAbstractFactory manageEventAbstractFactory = createManageEventAbstractFactory(abstractionAlgorithm);
        // handle the event based on the type of the message. Provide the logic within the abstraction for dealing with that the of message
        if(manageEventAbstractFactory == null){
            logger.info("NULLABLE on message: " + message);
        }
        List<networking.Message> messageList = manageEventAbstractFactory.handleEvent(message);

        for(networking.Message outcome: messageList) {
            addMessageToTheQueue(outcome);
        }
    }

    @Override
    public void run() {
        while(true){
            try {
                networking.Message inputRequest = eventQueue.getEventsQueue().take();
                // All the input requests are processed in a sequential manner
                consume(inputRequest);
            } catch (InterruptedException e) {
                logger.error("Error in pulling a message from evenQueue: " + e.getMessage());
            }
        }
    }
}
