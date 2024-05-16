package processing;

import abstractizations.MetaInfoMessage;
import abstractizations.NnarDefinedValue;
import model.MyselfNode;
import model.ProcessNode;
import networking.*;

import java.util.UUID;

public class ProtoSerialiseUtils {

    private static networking.Message.Builder createWrapperGeneralMessage(MetaInfoMessage metaInfoMessage){
        networking.Message.Builder wrapperGeneralMessage = networking.Message.newBuilder()
                .setType(metaInfoMessage.getType());

        if(metaInfoMessage.getMessageUuid() != null){
            wrapperGeneralMessage.setMessageUuid(metaInfoMessage.getMessageUuid());
        }

        if(metaInfoMessage.getFromAbstractionId() != null){
            wrapperGeneralMessage.setFromAbstractionId(metaInfoMessage.getFromAbstractionId());
        }

        if(metaInfoMessage.getToAbstractionId() != null){
            wrapperGeneralMessage.setToAbstractionId(metaInfoMessage.getToAbstractionId());
        }

        if(metaInfoMessage.getSystemId() != null) {
            wrapperGeneralMessage.setSystemId(metaInfoMessage.getSystemId());
        }

        return wrapperGeneralMessage;
    }

    public static networking.Message createSizeMessageRequest(networking.Message message){
        networking.Value sizeValue = Value.newBuilder().setV(message.getSerializedSize()).build();
        networking.AppValue appValue = AppValue.newBuilder().setValue(sizeValue).build();
        networking.Message requestSizeMessage = networking.Message.newBuilder()
                .setType(Message.Type.APP_VALUE)
                .setMessageUuid(UUID.randomUUID().toString())
                .setAppValue(appValue)
                .build();
        return requestSizeMessage;
    }

    public static networking.ProcessId convertProcessNodeToProcessId(ProcessNode processNode){
        networking.ProcessId.Builder builder = networking.ProcessId.newBuilder()
                .setHost(processNode.getHost())
                .setPort(processNode.getPort())
                .setOwner(processNode.getOwner())
                .setIndex(processNode.getIndex());

        if(processNode.getRank() != null){
            builder.setRank(processNode.getRank());
        }

        networking.ProcessId processId = builder.build();

        return processId;
    }

    public static networking.Value createValueUndefinedMessage(){
        return Value.newBuilder().build();
    }

    public static networking.Value createValueMessage(Integer v){
        return Value.newBuilder().setV(v).setDefined(true).build();
    }

    public static networking.Message createNetworkMessage(Message message, MetaInfoMessage metaInfoMessage){
        networking.NetworkMessage networkingMessage = networking.NetworkMessage.newBuilder()
                .setSenderHost(MyselfNode.createNewInstance().getHost())
                .setSenderListeningPort(MyselfNode.createNewInstance().getPort())
                .setMessage(message)
                .build();

        networking.Message wireMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setNetworkMessage(networkingMessage)
                .build();

        return wireMessage;
    }

    public static networking.Message createProcRegistrationRequest(MetaInfoMessage metaInfoMessage){
        networking.ProcRegistration procRegistration = networking.ProcRegistration.newBuilder()
                .setOwner(MyselfNode.createNewInstance().getOwner())
                .setIndex(MyselfNode.createNewInstance().getIndex())
                .build();

        networking.Message wrapperProcRegistration = createWrapperGeneralMessage(metaInfoMessage)
                .setProcRegistration(procRegistration)
                .build();

        return wrapperProcRegistration;
    }

    public static networking.Message createPlDeliverMessage(Message deliveredMessage, MetaInfoMessage metaInfoMessage, ProcessNode processNode){
        networking.ProcessId processId = convertProcessNodeToProcessId(processNode);

        networking.PlDeliver plDeliverMessage = networking.PlDeliver.newBuilder()
                .setMessage(deliveredMessage)
                .setSender(processId)
                .build();

        networking.Message wrappedPlDeliveredMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setPlDeliver(plDeliverMessage)
                .build();

        return wrappedPlDeliveredMessage;
    }

    public static networking.Message createPLSendMessage(Message sendingMessage, MetaInfoMessage metaInfoMessage, ProcessNode destinationProcessNode) {
        networking.ProcessId processId = convertProcessNodeToProcessId(destinationProcessNode);
        networking.PlSend plSendMessage = networking.PlSend.newBuilder()
                .setMessage(sendingMessage)
                .setDestination(processId)
                .build();

        networking.Message wrapperPLSendMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setPlSend(plSendMessage)
                .build();

        return wrapperPLSendMessage;
    }

    public static networking.Message createBebDeliverMessage(Message message, MetaInfoMessage metaInfoMessage, ProcessNode processNode){
        networking.ProcessId processId = convertProcessNodeToProcessId(processNode);

        networking.BebDeliver bebDeliverMessage = networking.BebDeliver.newBuilder()
                .setMessage(message)
                .setSender(processId)
                .build();

        networking.Message wrappedBebDeliverMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setBebDeliver(bebDeliverMessage)
                .build();

        return wrappedBebDeliverMessage;

    }

    public static networking.Message createBebBroadcastMessage(Message message, MetaInfoMessage metaInfoMessage){
        networking.BebBroadcast bebBroadcastMessage = networking.BebBroadcast.newBuilder()
                .setMessage(message)
                .build();

        networking.Message wrappedBebBroadcastMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setBebBroadcast(bebBroadcastMessage)
                .build();

        return wrappedBebBroadcastMessage;
    }

    public static networking.Message createAppValueMessage(networking.Value value, MetaInfoMessage metaInfoMessage){
        networking.AppValue appValueMessage = networking.AppValue.newBuilder()
                .setValue(value)
                .build();

        networking.Message wrappedAppValueMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setAppValue(appValueMessage)
                .build();

        return wrappedAppValueMessage;
    }

    public static networking.Message createNnarWriteMessage(networking.Value value, MetaInfoMessage metaInfoMessage){
        networking.NnarWrite nnarWriteMessage = networking.NnarWrite.newBuilder()
                .setValue(value)
                .build();

        networking.Message wrappedNnarWriteMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setNnarWrite(nnarWriteMessage)
                .build();

        return wrappedNnarWriteMessage;
    }

    public static networking.Message createNnarInternalReadMessage(Integer readId, MetaInfoMessage metaInfoMessage){
        networking.NnarInternalRead nnarInternalReadMessage = networking.NnarInternalRead.newBuilder()
                .setReadId(readId)
                .build();

        networking.Message wrappedNnarInternalReadMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setNnarInternalRead(nnarInternalReadMessage)
                .build();

        return wrappedNnarInternalReadMessage;
    }

    public static networking.Message createNnarInternalValueMessage(Integer rid, NnarDefinedValue nnarDefinedValue, MetaInfoMessage metaInfoMessage){
        networking.NnarInternalValue nnarInternalValueMessage = networking.NnarInternalValue.newBuilder()
                .setReadId(rid)
                .setTimestamp(nnarDefinedValue.getTs())
                .setWriterRank(nnarDefinedValue.getWr())
                .setValue(createValueMessage(nnarDefinedValue.getVal()))
                .build();

        networking.Message wrappedNnarInternalValueMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setNnarInternalValue(nnarInternalValueMessage)
                .build();

        return wrappedNnarInternalValueMessage;
    }

    public static networking.Message createNnarInternalWriteMessage(Integer rid, NnarDefinedValue nnarDefinedValue, MetaInfoMessage metaInfoMessage){
        networking.NnarInternalWrite nnarInternalWriteMessage = networking.NnarInternalWrite.newBuilder()
                .setReadId(rid)
                .setTimestamp(nnarDefinedValue.getTs())
                .setWriterRank(nnarDefinedValue.getWr())
                .setValue(createValueMessage(nnarDefinedValue.getVal()))
                .build();

        networking.Message wrappedNnarInternalWriteMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setNnarInternalWrite(nnarInternalWriteMessage)
                .build();

        return wrappedNnarInternalWriteMessage;
    }

    public static networking.Message createNnarInternalAckMessage(Integer readId, MetaInfoMessage metaInfoMessage){
        networking.NnarInternalAck nnarInternalAckMessage = networking.NnarInternalAck.newBuilder()
                .setReadId(readId)
                .build();

        networking.Message wrappedNnarInternalAckMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setNnarInternalAck(nnarInternalAckMessage)
                .build();

        return wrappedNnarInternalAckMessage;
    }

    public static networking.Message createNnarReadReturnMessage(networking.Value value, MetaInfoMessage metaInfoMessage){
        networking.NnarReadReturn nnarReadReturnMessage = networking.NnarReadReturn.newBuilder()
                .setValue(value)
                .build();

        networking.Message wrappedNnarReadReturnMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setNnarReadReturn(nnarReadReturnMessage)
                .build();

        return wrappedNnarReadReturnMessage;
    }

    public static networking.Message createNnarWriteReturnMessage(MetaInfoMessage metaInfoMessage){
        networking.NnarWriteReturn nnarWriteReturnMessage = networking.NnarWriteReturn.newBuilder().build();

        networking.Message wrappedNnarWriteReturnMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setNnarWriteReturn(nnarWriteReturnMessage)
                .build();

        return wrappedNnarWriteReturnMessage;
    }

    public static networking.Message createAppWriteReturnMessage(String register, MetaInfoMessage metaInfoMessage){
        networking.AppWriteReturn appWriteReturnMessage = networking.AppWriteReturn.newBuilder()
                .setRegister(register)
                .build();

        networking.Message wrappedAppWriteReturnMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setAppWriteReturn(appWriteReturnMessage)
                .build();

        return wrappedAppWriteReturnMessage;
    }

    public static networking.Message createNnarReadMessage(MetaInfoMessage metaInfoMessage){
        networking.Message wrappedNnarReadMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setNnarRead(networking.NnarRead.newBuilder().build())
                .build();

        return wrappedNnarReadMessage;
    }

    public static networking.Message createAppReadReturnMessage(networking.Value value, String register, MetaInfoMessage metaInfoMessage){
        networking.AppReadReturn appReadReturnMessage = networking.AppReadReturn.newBuilder()
                .setValue(value)
                .setRegister(register)
                .build();

        networking.Message wrappedAppReadReturnMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setAppReadReturn(appReadReturnMessage)
                .build();

        return wrappedAppReadReturnMessage;
    }

    public static networking.Message createEpfdSuspectMessage(MetaInfoMessage metaInfoMessage, ProcessNode suspectedProcessNode){
        networking.ProcessId processId = convertProcessNodeToProcessId(suspectedProcessNode);
        networking.EpfdSuspect epfdSuspectMessage = networking.EpfdSuspect.newBuilder()
                .setProcess(processId)
                .build();

        networking.Message wrapperEpfdSuspectMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpfdSuspect(epfdSuspectMessage)
                .build();

        return wrapperEpfdSuspectMessage;
    }

    public static networking.Message createEpfdRestoreMessage(MetaInfoMessage metaInfoMessage, ProcessNode restoringProcessNode){
        networking.ProcessId processId = convertProcessNodeToProcessId(restoringProcessNode);
        networking.EpfdRestore epfdRestoreMessage = networking.EpfdRestore.newBuilder()
                .setProcess(processId)
                .build();

        networking.Message wrapperEpfdRestoreMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpfdRestore(epfdRestoreMessage)
                .build();

        return wrapperEpfdRestoreMessage;
    }

    public static networking.Message createEpfdInternalHeartbeatRequestMessage(MetaInfoMessage metaInfoMessage){
        networking.EpfdInternalHeartbeatRequest epfdInternalHeartbeatRequestMessage = networking.EpfdInternalHeartbeatRequest.newBuilder()
                .build();

        networking.Message wrapperEpfdInternalHeartbeatRequestMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpfdInternalHeartbeatRequest(epfdInternalHeartbeatRequestMessage)
                .build();

        return wrapperEpfdInternalHeartbeatRequestMessage;
    }

    public static networking.Message createEpfdInternalHeartbeatReplyMessage(MetaInfoMessage metaInfoMessage){
        networking.EpfdInternalHeartbeatReply epfdInternalHeartbeatReplyMessage = networking.EpfdInternalHeartbeatReply.newBuilder()
                .build();

        networking.Message wrapperEpfdInternalHeartbeatReplyMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpfdInternalHeartbeatReply(epfdInternalHeartbeatReplyMessage)
                .build();

        return wrapperEpfdInternalHeartbeatReplyMessage;
    }

    public static networking.Message createEldTrustMessage(MetaInfoMessage metaInfoMessage, ProcessNode trustProcess){
        networking.ProcessId processId = convertProcessNodeToProcessId(trustProcess);
        networking.EldTrust eldTrustMessage = networking.EldTrust.newBuilder()
                .setProcess(processId)
                .build();

        networking.Message wrapperEldTrustMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEldTrust(eldTrustMessage)
                .build();

        return wrapperEldTrustMessage;
    }

    public static networking.Message createEcInternalNewEpochMessage(Integer proposedStartEpochTs, MetaInfoMessage metaInfoMessage){
        networking.EcInternalNewEpoch ecInternalNewEpochMessage = networking.EcInternalNewEpoch.newBuilder()
                .setTimestamp(proposedStartEpochTs)
                .build();

        networking.Message wrapperEcInternalNewEpochMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEcInternalNewEpoch(ecInternalNewEpochMessage)
                .build();

        return wrapperEcInternalNewEpochMessage;
    }

    public static networking.Message createEcStartEpochMessage(Integer newTimestamp, ProcessNode newLeader, MetaInfoMessage metaInfoMessage){
        networking.ProcessId processId = convertProcessNodeToProcessId(newLeader);
        networking.EcStartEpoch ecStartEpochMessage = networking.EcStartEpoch.newBuilder()
                .setNewLeader(processId)
                .setNewTimestamp(newTimestamp)
                .build();

        networking.Message wrapperEcStartEpochMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEcStartEpoch(ecStartEpochMessage)
                .build();

        return wrapperEcStartEpochMessage;
    }

    public static networking.Message createEcInternalNack(MetaInfoMessage metaInfoMessage){
        networking.EcInternalNack ecInternalNackMessage = networking.EcInternalNack.newBuilder().build();

        networking.Message messageEcInternalNackMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEcInternalNack(ecInternalNackMessage)
                .build();

        return messageEcInternalNackMessage;
    }

    public static networking.Message createEpInternalRead(MetaInfoMessage metaInfoMessage){
        networking.EpInternalRead epInternalReadMessage = networking.EpInternalRead.newBuilder().build();

        networking.Message wrapperEpInternalReadMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpInternalRead(epInternalReadMessage)
                .build();

        return wrapperEpInternalReadMessage;
    }

    public static networking.Message createEpInternalState(Integer valueTimestamp, Integer valueEpoch, MetaInfoMessage metaInfoMessage){
        networking.EpInternalState epInternalStateMessage = networking.EpInternalState.newBuilder()
                .setValueTimestamp(valueTimestamp)
                .setValue(createValueMessage(valueEpoch))
                .build();

        networking.Message wrapperEpInternalReadMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpInternalState(epInternalStateMessage)
                .build();

        return wrapperEpInternalReadMessage;
    }

    public static networking.Message createEpInternalWrite(Integer valueEpoch, MetaInfoMessage metaInfoMessage){
        networking.EpInternalWrite epInternalWriteMessage = networking.EpInternalWrite.newBuilder()
                .setValue(createValueMessage(valueEpoch))
                .build();

        networking.Message wrapperEpInternalWriteMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpInternalWrite(epInternalWriteMessage)
                .build();

        return wrapperEpInternalWriteMessage;
    }

    public static networking.Message createEpInternalAccept(MetaInfoMessage metaInfoMessage){
        networking.EpInternalAccept epInternalAcceptMessage = networking.EpInternalAccept.newBuilder().build();

        networking.Message wrapperEpInternalAcceptMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpInternalAccept(epInternalAcceptMessage)
                .build();

        return wrapperEpInternalAcceptMessage;
    }

    public static networking.Message createEpInternalDecided(Integer decidedValue, MetaInfoMessage metaInfoMessage){
        networking.EpInternalDecided epInternalDecidedMessage = networking.EpInternalDecided.newBuilder()
                .setValue(createValueMessage(decidedValue))
                .build();

        networking.Message wrapperEpInternalDecidedMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpInternalDecided(epInternalDecidedMessage)
                .build();

        return wrapperEpInternalDecidedMessage;
    }

    public static networking.Message createEpDecide(Integer epochCreationTimeStamp, Integer valueDecidedEpoch, MetaInfoMessage metaInfoMessage){
        networking.EpDecide epDecideMessage = networking.EpDecide.newBuilder()
                .setEts(epochCreationTimeStamp)
                .setValue(createValueMessage(valueDecidedEpoch))
                .build();

        networking.Message wrapperEpDecideMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpDecide(epDecideMessage)
                .build();

        return wrapperEpDecideMessage;
    }

    public static networking.Message createEpAborted(Integer epochCreationTimeStamp, Integer valueTimestamp, Integer valueDecidedEpoch, MetaInfoMessage metaInfoMessage){
        networking.EpAborted epAbortedMessage = networking.EpAborted.newBuilder()
                .setEts(epochCreationTimeStamp)
                .setValueTimestamp(valueTimestamp)
                .setValue(createValueMessage(valueDecidedEpoch))
                .build();

        networking.Message wrapperEpAbortedMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpAborted(epAbortedMessage)
                .build();

        return wrapperEpAbortedMessage;
    }

    public static networking.Message createEpAbort(MetaInfoMessage metaInfoMessage){
        networking.EpAbort epAbortMessage = networking.EpAbort.newBuilder().build();

        networking.Message wrapperEpAbortMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpAbort(epAbortMessage)
                .build();

        return wrapperEpAbortMessage;
    }

    public static networking.Message createUCDecide(Integer decidedValue, MetaInfoMessage metaInfoMessage){
        networking.UcDecide ucDecideMessage = networking.UcDecide.newBuilder()
                .setValue(createValueMessage(decidedValue))
                .build();

        networking.Message wrapperUCDecideMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setUcDecide(ucDecideMessage)
                .build();

        return wrapperUCDecideMessage;
    }

    public static networking.Message createEPPropose(Integer proposedValue, MetaInfoMessage metaInfoMessage){
        networking.EpPropose epProposedMessage = networking.EpPropose.newBuilder()
                .setValue(createValueMessage(proposedValue))
                .build();

        networking.Message wrapperEpProposeMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setEpPropose(epProposedMessage)
                .build();

        return wrapperEpProposeMessage;
    }

    public static networking.Message createUCPropose(Integer proposedValue, MetaInfoMessage metaInfoMessage){
        networking.UcPropose ucProposeMessage = networking.UcPropose.newBuilder()
                .setValue(createValueMessage(proposedValue))
                .build();

        networking.Message wrapperUCProposeMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setUcPropose(ucProposeMessage)
                .build();

        return wrapperUCProposeMessage;
    }

    public static networking.Message createAppDecide(Integer decidedValue, MetaInfoMessage metaInfoMessage){
        networking.AppDecide appDecideMessage = networking.AppDecide.newBuilder()
                .setValue(createValueMessage(decidedValue))
                .build();

        networking.Message wrapperAppDecideMessage = createWrapperGeneralMessage(metaInfoMessage)
                .setAppDecide(appDecideMessage)
                .build();

        return wrapperAppDecideMessage;
    }

}
