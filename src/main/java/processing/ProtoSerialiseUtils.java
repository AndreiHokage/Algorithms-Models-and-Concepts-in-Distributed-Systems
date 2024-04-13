package processing;

import abstractizations.MetaInfoMessage;
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

}
