package servers.threadsServer;

import model.DistributedSystem;
import model.EventQueue;
import networking.Message;
import utils.MessageUtils;

public class ProcessHeartBeatQueueThread extends ProcessEventQueueThread {

    public ProcessHeartBeatQueueThread(EventQueue eventQueue) {
        super(eventQueue);
    }

    @Override
    protected void addMessageToTheQueue(Message message) throws InterruptedException {
        // The only messages that are pushed into the queue are ones of the following types:
        //  1) HeartBeat request
        //  2) HearBeat reply
        //  3) Suspect/Restore
        // Are inserted by the ProcessHeartBeatQueueThread and ProcessHeartBeatQueueThread.LivenessSystemChecking scheduled thread
        if(MessageUtils.checkHeartBeatOriginMessages(message)){ // we want to store just the HeartBeat request/reply messages
            getEventQueue().getEventsQueue().put(message);
        } else { //Suspect/Restore
            DistributedSystem.createNewInstance().getEventQueue().getEventsQueue().put(message); // we are in the processing thread associated with the heartbeat queue
            // need to make references ot the classical queue
        }
    }
}
