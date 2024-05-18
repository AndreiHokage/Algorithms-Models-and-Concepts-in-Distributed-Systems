package abstractizations;

import model.DistributedSystem;
import model.MyselfNode;
import model.ProcessNode;
import networking.Message;
import processing.ProtoDeserialiseUtils;
import processing.ProtoSerialiseUtils;
import utils.IntfConstants;

import java.util.UUID;

public class ECAbstraction implements Abstraction{

    private ProcessNode trusted; // the leader of the epoch

    private Integer lastts; // the time when the epoch starts

    private Integer ts; // the time when a new epoch want to surpass the current one

    private Integer N;

    public ECAbstraction(){
        this.trusted = getMaxRankProcessNode();
        this.lastts = 0;
        this.ts = MyselfNode.createNewInstance().getRank();
        this.N = DistributedSystem.createNewInstance().getNumberProcessesSystem();
    }

    private ProcessNode getMaxRankProcessNode(){
        ProcessNode maxRankProcess = DistributedSystem.createNewInstance().getProcesses().get(0);
        for(ProcessNode processNode: DistributedSystem.createNewInstance().getProcesses()){
            if(maxRankProcess.getRank().compareTo(processNode.getRank()) < 0){
                maxRankProcess = processNode;
            }
        }

        return maxRankProcess;
    }

    private networking.Message imposeLeadership(){
        if(trusted.equals(MyselfNode.createNewInstance())){ // it has come from lower abstraction (up) && it is still the leader
            ts = ts + N;
            String generatedUUID = UUID.randomUUID().toString();
            String toAbstractionId = IntfConstants.EC_ABS; // we initialise here the message, so we put in the front only the abstraction that will handle this message
            MetaInfoMessage metaInfoMessageEcInternalNewEpoch = new MetaInfoMessage(Message.Type.EC_INTERNAL_NEW_EPOCH, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message EcInternalNewEpochMessage = ProtoSerialiseUtils.createEcInternalNewEpochMessage(ts, metaInfoMessageEcInternalNewEpoch);

            String toAbstractionIdBroadcast = toAbstractionId + "." + IntfConstants.BEB_ABS;
            MetaInfoMessage metaInfoMessageBroadcast = new MetaInfoMessage(Message.Type.BEB_BROADCAST, generatedUUID,
                    null, toAbstractionIdBroadcast, DistributedSystem.createNewInstance().getSystemId());
            networking.Message broadcastMessage = ProtoSerialiseUtils.createBebBroadcastMessage(EcInternalNewEpochMessage, metaInfoMessageBroadcast);

            return broadcastMessage;
        }

        return null;
    }

    public networking.Message handleTrust(networking.EldTrust message, MetaInfoMessage metaInfoMessage){
        ProcessNode trustedProcess = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getProcess());
        trusted = trustedProcess;

        networking.Message leadershipBroadcastMessage = imposeLeadership();
        return leadershipBroadcastMessage;
    }

    public networking.Message handlingBebDeliver(networking.BebDeliver bebDeliver, MetaInfoMessage metaInfoMessage){
        networking.Message contentMessage = bebDeliver.getMessage();
        networking.EcInternalNewEpoch ecInternalNewEpochMessage = contentMessage.getEcInternalNewEpoch();
        ProcessNode imposingLeader = ProtoDeserialiseUtils.convertProcessIdToProcessNode(bebDeliver.getSender());
        Integer newts = ecInternalNewEpochMessage.getTimestamp();

        if(AppAbstraction.currentTopicConsensus == null){ // we don't have any consensus topic for moment
            return null;
        }

        if(trusted.equals(imposingLeader) && newts > lastts){
            lastts = newts;

            String fromAbstractionId = IntfConstants.UC_ABS + "[" + AppAbstraction.currentTopicConsensus + "]"; // CREATE ONE HERE (INTERMEDIATE). DON'T CARE ABOUT LOWER LEVELS. Which consensusTopic?
            MetaInfoMessage metaInfoMessageStartEpoch = new MetaInfoMessage(Message.Type.EC_START_EPOCH, metaInfoMessage.getMessageUuid(),
                    fromAbstractionId, null, DistributedSystem.createNewInstance().getSystemId()); // go up; put the abstraction that will handle this
            networking.Message ecStartEpochMessage = ProtoSerialiseUtils.createEcStartEpochMessage(newts, imposingLeader, metaInfoMessageStartEpoch);

            return ecStartEpochMessage;
        } else {
            String uuidGeneratedMessage = UUID.randomUUID().toString();
            String toAbstractionId = IntfConstants.EC_ABS + "." + IntfConstants.PL_ABS;
            MetaInfoMessage metaInfoMessageEcInternalNack = new MetaInfoMessage(Message.Type.EC_INTERNAL_NACK, uuidGeneratedMessage,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message ecInternalNackMessage = ProtoSerialiseUtils.createEcInternalNack(metaInfoMessageEcInternalNack);

            return ecInternalNackMessage;
        }
    }

    public networking.Message handlingPlDeliver(networking.PlDeliver message, MetaInfoMessage metaInfoMessage){
        networking.Message contentMessage = message.getMessage();
        Message.Type type = contentMessage.getType();

        networking.Message leadershipBroadcastMessage = imposeLeadership();
        return leadershipBroadcastMessage;
    }

}
