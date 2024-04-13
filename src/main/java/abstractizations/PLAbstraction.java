package abstractizations;

import model.DistributedSystem;
import model.MyselfNode;
import model.ProcessNode;
import networking.Message;
import networking.NetworkMessage;
import org.apache.log4j.Logger;
import processing.ProtoDeserialiseUtils;
import processing.ProtoSerialiseUtils;
import servers.SystemOutputServer;

import java.util.UUID;

public class PLAbstraction implements Abstraction {

    private static Logger logger = Logger.getLogger(PLAbstraction.class.getName());

    private static PLAbstraction plAbstraction = null;

    private SystemOutputServer systemOutputServer = null;

    private PLAbstraction(SystemOutputServer systemOutputServer){
        this.systemOutputServer = systemOutputServer;
    }

    public static PLAbstraction createNewInstance(SystemOutputServer systemOutputServer){
        if(plAbstraction == null){
            plAbstraction = new PLAbstraction(systemOutputServer);
        }

        return plAbstraction;
    }

    /**
     * A process intercepts a message sent through links
     * @param message - a dequeued message of type NETWORK_MESSAGE received through links
     * @param metaInfoMessage - meta data about the delivering message (the message that wraps the `message`)
     * @return a message of type PL_DELIVER to be enqueued
     */
    public networking.Message handlingSLDeliver(NetworkMessage message, MetaInfoMessage metaInfoMessage){
        // get the content of the message
        networking.Message m = message.getMessage();
        // get the sender
        ProcessNode p = ProtoDeserialiseUtils.getSenderOfNetworkMessage(message);

        // the abstraction received from the network is found on `toAbstractionId` field from the sender perspective
        // swap `toAbstractionId` to `fromAbstractionId`  from the receiver perspective
        String fromAbstractionId = metaInfoMessage.getToAbstractionId();
        String alteredFromAbstractionId = AbstractionUtils.removeCurrentAbstraction(fromAbstractionId); // remove the last token: "pl" from `fromAbstractionID`

        logger.trace("Deliver the message: " + m + " with the metadata: " + metaInfoMessage + " sent by " + p + " to the abstraction " + alteredFromAbstractionId);

        MetaInfoMessage metaInfoMessagePLDeliver = new MetaInfoMessage(Message.Type.PL_DELIVER, metaInfoMessage.getMessageUuid(),
                alteredFromAbstractionId, null, DistributedSystem.createNewInstance().getSystemId());
        Message plDeliverMessage = ProtoSerialiseUtils.createPlDeliverMessage(m, metaInfoMessagePLDeliver, p);

        return plDeliverMessage;
    }

    /**
     * A process sends a message through the links
     * @param message - a message of type PL_SEND that will be sent through the links
     * @param metaInfoMessage - meta data about the sending message (the message that wraps the `message`)
     */
    public void handlingPLSend(networking.PlSend message, MetaInfoMessage metaInfoMessage){
        // get the content
        networking.Message m = message.getMessage();
        // get the destination
        ProcessNode destinationProcessNode = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getDestination());

        // keep the same Message ID, that was given on app abstraction level due to metaInfoMessage defines  the wrapper of PLSend
        MetaInfoMessage metaInfoMessagePLSend = new MetaInfoMessage(Message.Type.NETWORK_MESSAGE, metaInfoMessage.getMessageUuid(),
                metaInfoMessage.getFromAbstractionId(), metaInfoMessage.getToAbstractionId(), metaInfoMessage.getSystemId());

        networking.Message sentMessageToWire = ProtoSerialiseUtils.createNetworkMessage(m, metaInfoMessagePLSend);
        logger.trace("Send message " + m + " to the abstraction " + metaInfoMessage.getToAbstractionId() + " by PL send");

        systemOutputServer.sendRequest(sentMessageToWire, destinationProcessNode.getHost(), destinationProcessNode.getPort().intValue());
    }










}
