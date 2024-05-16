package abstractizations;

import model.DistributedSystem;
import model.ProcessNode;
import networking.Message;
import processing.ProtoDeserialiseUtils;
import processing.ProtoSerialiseUtils;
import utils.IntfConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class EPAbstraction implements Abstraction{

    private Integer indexEPepoch; // is the esame with eps and is found here ep[eps]

    private EPState epState = null;

    private Integer tmpVal = null;

    private List<EPState> statesList = new ArrayList<>();

    private Integer accepted = 0;

    private Integer N = IntfConstants.NUM_PROC;

    private Boolean halt = false;

    public EPAbstraction(String indexEPepoch){
        this.indexEPepoch = Integer.valueOf(indexEPepoch);
        this.epState = new EPState();
        this.tmpVal = Integer.MIN_VALUE;
        this.statesList = new ArrayList<>();
        this.accepted = 0;
    }

    public EPAbstraction(EPState state, String indexEPepoch) {
        this.indexEPepoch = Integer.valueOf(indexEPepoch);
        this.epState = state;
        this.tmpVal = Integer.MIN_VALUE;
        this.statesList = new ArrayList<>();
        this.accepted = 0;
    }

    private Boolean isExecutionHalt(){
        return halt;
    }

    private networking.Message handleAcceptFromMajority(){
        if(accepted > Math.ceil(N /(double) 2)){
            accepted = 0;
            String generatedUUID = UUID.randomUUID().toString();
            String currentAbstraction = IntfConstants.EP_ABS;
            MetaInfoMessage metaInfoMessageEpInternalDecided = new MetaInfoMessage(Message.Type.EP_INTERNAL_DECIDED, generatedUUID,
                    null, currentAbstraction, DistributedSystem.createNewInstance().getSystemId());
            networking.Message epInternalDecidedMessage = ProtoSerialiseUtils.createEpInternalDecided(tmpVal, metaInfoMessageEpInternalDecided);

            String toAbstractionId = currentAbstraction + "." + IntfConstants.BEB_ABS;
            MetaInfoMessage metaInfoMessageBebBroadcast = new MetaInfoMessage(Message.Type.BEB_BROADCAST, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message bebBroadcastMessage = ProtoSerialiseUtils.createBebBroadcastMessage(epInternalDecidedMessage, metaInfoMessageBebBroadcast);

            return bebBroadcastMessage;
        }

        return null;
    }

    private networking.Message handleReadFromMajority(){
        if(statesList.size() > Math.ceil(N /(double) 2)){
            EPState maximumEPState = statesList.get(0);
            for(EPState epState: statesList){
                if(epState.getEpochTimestamp().compareTo(maximumEPState.getEpochTimestamp()) > 0){
                    maximumEPState = epState;
                }
            }

            if(!maximumEPState.getValueEpoch().equals(Integer.MIN_VALUE)){
                tmpVal = maximumEPState.getValueEpoch();
            }

            statesList = new ArrayList<>();

            String generatedUUID = UUID.randomUUID().toString();
            String currentAbstraction = IntfConstants.EP_ABS;
            MetaInfoMessage metaInfoMessageEpInternalWrite = new MetaInfoMessage(Message.Type.EP_INTERNAL_WRITE, generatedUUID,
                    null, currentAbstraction, DistributedSystem.createNewInstance().getSystemId());
            networking.Message epInternalWriteMessage = ProtoSerialiseUtils.createEpInternalWrite(tmpVal, metaInfoMessageEpInternalWrite);

            String toAbstractionId = currentAbstraction + "." + IntfConstants.BEB_ABS;
            MetaInfoMessage metaInfoMessageBebBroadcast = new MetaInfoMessage(Message.Type.BEB_BROADCAST, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message bebBroadcastMessage = ProtoSerialiseUtils.createBebBroadcastMessage(epInternalWriteMessage, metaInfoMessageBebBroadcast);

            return bebBroadcastMessage;
        }

        return null;
    }

    public networking.Message handlePropose(networking.EpPropose epPropose, MetaInfoMessage metaInfoMessage){ // only leader
        if(isExecutionHalt()){
            return null;
        }

        Integer value = epPropose.getValue().getV();
        tmpVal = value;

        String generatedUUID = UUID.randomUUID().toString();
        String currentAbstraction = metaInfoMessage.getToAbstractionId();
        MetaInfoMessage metaInfoMessageEpInternalRead = new MetaInfoMessage(Message.Type.EP_INTERNAL_READ, generatedUUID,
                null, currentAbstraction, DistributedSystem.createNewInstance().getSystemId());
        networking.Message epInternalReadMessage = ProtoSerialiseUtils.createEpInternalRead(metaInfoMessageEpInternalRead);

        String toAbstractionId = currentAbstraction + "." + IntfConstants.BEB_ABS;
        MetaInfoMessage metaInfoMessageBebBroadcast = new MetaInfoMessage(Message.Type.BEB_BROADCAST, generatedUUID,
                null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
        networking.Message bebBroadcastMessage = ProtoSerialiseUtils.createBebBroadcastMessage(epInternalReadMessage, metaInfoMessageBebBroadcast);

        return bebBroadcastMessage;
    }

    public networking.Message handleBebDeliver(networking.BebDeliver message, MetaInfoMessage metaInfoMessage){
        if(isExecutionHalt()){
            return null;
        }

        ProcessNode sender = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getSender());
        networking.Message content = message.getMessage();
        Message.Type typeContent = content.getType();

        if(typeContent.equals(Message.Type.EP_INTERNAL_READ)){
            String generatedUUID = UUID.randomUUID().toString();
            String currentAbstraction = metaInfoMessage.getFromAbstractionId(); // it comes from lower levels
            MetaInfoMessage metaInfoMessageEpInternalState = new MetaInfoMessage(Message.Type.EP_INTERNAL_STATE, generatedUUID,
                    null, currentAbstraction, DistributedSystem.createNewInstance().getSystemId());
            networking.Message epInternalStateMessage = ProtoSerialiseUtils.createEpInternalState(epState.getEpochTimestamp(),
                    epState.getValueEpoch(), metaInfoMessageEpInternalState);

            String toAbstractionId = currentAbstraction + "." + IntfConstants.PL_ABS;
            MetaInfoMessage metaInfoMessagePlSend = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(epInternalStateMessage, metaInfoMessagePlSend, sender);

            return plSendMessage;
        }

        if(typeContent.equals(Message.Type.EP_INTERNAL_WRITE)){
            networking.EpInternalWrite epInternalWrite = content.getEpInternalWrite();
            Integer valueEpoch = epInternalWrite.getValue().getV();
            epState = new EPState(indexEPepoch, valueEpoch); // REPLACE THE STATE: CAREFULLY how to init epochCreationTimeStamp

            String generatedUUID = UUID.randomUUID().toString();
            String currentAbstraction = metaInfoMessage.getFromAbstractionId();
            MetaInfoMessage metaInfoMessageEpInternalAccept = new MetaInfoMessage(Message.Type.EP_INTERNAL_ACCEPT, generatedUUID,
                    null, currentAbstraction, DistributedSystem.createNewInstance().getSystemId());
            networking.Message epInternalAcceptMessage = ProtoSerialiseUtils.createEpInternalAccept(metaInfoMessageEpInternalAccept);

            String toAbstractionId = currentAbstraction + "." + IntfConstants.PL_ABS;
            MetaInfoMessage metaInfoMessagePlSend = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(epInternalAcceptMessage, metaInfoMessagePlSend, sender);

            return plSendMessage;
        }

        if(typeContent.equals(Message.Type.EP_INTERNAL_DECIDED)){
            networking.EpInternalDecided epInternalDecided = content.getEpInternalDecided();
            Integer valueDecided = epInternalDecided.getValue().getV();

            String generatedUUID = UUID.randomUUID().toString();
            String fromAbstractionId = IntfConstants.EC_ABS; // send ABOVE!!!
            MetaInfoMessage metaInfoMessageFinalEPDiceded = new MetaInfoMessage(Message.Type.EP_DECIDE, generatedUUID,
                    fromAbstractionId, null, DistributedSystem.createNewInstance().getSystemId());
            networking.Message epDecideMessage = ProtoSerialiseUtils.createEpDecide(indexEPepoch, valueDecided, metaInfoMessageFinalEPDiceded);

            return epDecideMessage;
        }

        return null;
    }

    public networking.Message handlingPlDeliver(networking.PlDeliver message, MetaInfoMessage metaInfoMessage){
        if(isExecutionHalt()){
            return null;
        }

        networking.Message contentMessage = message.getMessage();
        Message.Type type = contentMessage.getType();

        if(type.equals(Message.Type.EP_INTERNAL_STATE)){ // only leader
            networking.EpInternalState epInternalStateMessage = contentMessage.getEpInternalState();
            Integer valueTimestamp = epInternalStateMessage.getValueTimestamp();
            Integer valueEpoch = epInternalStateMessage.getValue().getV();

            statesList.add(new EPState(valueTimestamp, valueEpoch));
            networking.Message ifMajorityBebBroadcastMessage = handleReadFromMajority();

            return ifMajorityBebBroadcastMessage;
        }

        if(type.equals(Message.Type.EP_INTERNAL_ACCEPT)){ // only the leader
            accepted = accepted + 1;
            networking.Message ifAcceptFromMajority = handleAcceptFromMajority();

            return ifAcceptFromMajority;
        }

        return null;
    }

    public networking.Message handlingAbort(networking.EpAbort message, MetaInfoMessage metaInfoMessage) {
        String generatedUUID = UUID.randomUUID().toString();
        String fromAbstractionId = IntfConstants.EC_ABS; // send ABOVE!!!
        MetaInfoMessage metaInfoMessageEpAborted = new MetaInfoMessage(Message.Type.EP_ABORTED, generatedUUID,
                fromAbstractionId, null, DistributedSystem.createNewInstance().getSystemId());
        networking.Message epAbortedMessage = ProtoSerialiseUtils.createEpAborted(indexEPepoch, epState.getEpochTimestamp(),
                epState.getValueEpoch(), metaInfoMessageEpAborted);

        halt = true; // all upcoming messages for the ec[indexEPepoch] will be ignored (this kind of halting manner): return null
        return epAbortedMessage;
    }
}
