package abstractizations;

import model.DistributedSystem;
import model.ProcessNode;
import networking.Message;
import networking.PlSend;
import org.apache.log4j.Logger;
import processing.ProtoDeserialiseUtils;
import processing.ProtoSerialiseUtils;
import utils.IntfConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BebAbstraction implements Abstraction {

    private static Logger logger = Logger.getLogger(BebAbstraction.class.getName());

    private static BebAbstraction bebAbstraction = null;

    private BebAbstraction(){

    }

    public static BebAbstraction createNewInstance() {
        if(bebAbstraction == null){
            bebAbstraction = new BebAbstraction();
        }

        return bebAbstraction;
    }

    /**
     * A process intercepts a message sent through broadcast
     * @param message - a dequeued message of type PL_DELIVER received through broadcast
     * @param metaInfoMessage - meta data for the message (the message that wraps the `message`)
     * @return a message of type BEB_DELIVER to be enqueued
     */
    public networking.Message handlingPLDeliver(networking.PlDeliver message, MetaInfoMessage metaInfoMessage){
        // get the content of the message
        networking.Message m = message.getMessage();
        // get the sender
        ProcessNode p = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getSender());

        String fromAbstractionId = metaInfoMessage.getFromAbstractionId();
        // remove the last token: "beb" from `fromAbstractionID`. The Message has reached to me !!!!!
        String alteredFromAbstractionId = AbstractionUtils.removeCurrentAbstraction(fromAbstractionId);

        logger.trace("Deliver the message: " + m + " sent by " + p + " to the abstraction " + alteredFromAbstractionId);

        MetaInfoMessage metaInfoMessageBebDeliver = new MetaInfoMessage(Message.Type.BEB_DELIVER, metaInfoMessage.getMessageUuid(),
                alteredFromAbstractionId, null, DistributedSystem.createNewInstance().getSystemId());
        Message bebDeliverMessage = ProtoSerialiseUtils.createBebDeliverMessage(m, metaInfoMessageBebDeliver, p);

        return bebDeliverMessage;
    }

    /**
     *
     * @param message - a message of type BebBroadcast that triggers a broadcast with the message it contains
     * @param metaInfoMessage - meta data for the message BebBroadcast (the message that wraps the `message`)
     * @return a list of messages of type PL_SEND to be enqueued (TRIGGERS EVENTS!!)
     */
    public List<networking.Message> handlingBebBroadcast(networking.BebBroadcast message, MetaInfoMessage metaInfoMessage) {
        List<networking.Message> plSends = new ArrayList<>();
        // get the content
        networking.Message m = message.getMessage();

        String toAbstractionId = metaInfoMessage.getToAbstractionId() + "." + IntfConstants.PL_ABS; // we send to a deeper level abstraction
        for(ProcessNode destinationProcessNode: DistributedSystem.createNewInstance().getProcesses()){

            // a new UUID for each message due to we broadcast it
            MetaInfoMessage metaInfoMessagePerProcess = new MetaInfoMessage(Message.Type.PL_SEND, UUID.randomUUID().toString(),
                    null, toAbstractionId, metaInfoMessage.getSystemId());

            networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(m, metaInfoMessagePerProcess, destinationProcessNode);
            plSends.add(plSendMessage);
        }

        return plSends;
    }






}
