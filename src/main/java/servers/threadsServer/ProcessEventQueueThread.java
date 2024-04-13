package servers.threadsServer;

import abstractizations.*;
import model.EventQueue;
import org.apache.log4j.Logger;
import processing.factoryAbstractizations.ManageEventAPPFactory;
import processing.factoryAbstractizations.ManageEventAbstractFactory;
import processing.factoryAbstractizations.ManageEventBEBFactory;
import processing.factoryAbstractizations.ManageEventPLFactory;
import utils.IntfConstants;

import java.util.List;

public class ProcessEventQueueThread extends Thread{

    private static final Logger logger = Logger.getLogger(ProcessEventQueueThread.class.getName());

    private EventQueue eventQueue = null;

    public ProcessEventQueueThread(EventQueue eventQueue){
        this.eventQueue = eventQueue;
    }

    private ManageEventAbstractFactory createManageEventAbstractFactory(String abstractionAlgorithm){
        if(abstractionAlgorithm.equals(IntfConstants.PL_ABS)){
            return ManageEventPLFactory.createNewInstance();
        }

        if(abstractionAlgorithm.equals(IntfConstants.BEB_ABS)){
            return ManageEventBEBFactory.createNewInstance();
        }

        if(abstractionAlgorithm.equals(IntfConstants.APP_ABS)){
            return ManageEventAPPFactory.createNewInstance();
        }

        return null;
    }

    private void consume(networking.Message message) throws InterruptedException {
        String abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getToAbstractionId());
        // The message is not triggered by us, it means that we have received it
        if(abstractionAlgorithm == null){
            abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getFromAbstractionId());
        }

        ManageEventAbstractFactory manageEventAbstractFactory = createManageEventAbstractFactory(abstractionAlgorithm);
        List<networking.Message> messageList = manageEventAbstractFactory.handleEvent(message);

        for(networking.Message outcome: messageList) {
            eventQueue.getEventsQueue().put(outcome);
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
