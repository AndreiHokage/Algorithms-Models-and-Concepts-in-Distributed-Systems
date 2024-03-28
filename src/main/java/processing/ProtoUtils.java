package processing;

import model.ProcessNode;
import networking.*;

import java.util.UUID;

public class ProtoUtils {

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

    public static networking.Message createProcRegistrationRequest(ProcessNode processNode){
        networking.ProcRegistration procRegistration = networking.ProcRegistration.newBuilder()
                .setOwner(processNode.getOwner())
                .setIndex(processNode.getIndex())
                .build();

        networking.Message wrapperProcRegistration = networking.Message.newBuilder()
                .setType(Message.Type.PROC_REGISTRATION)
                .setMessageUuid(UUID.randomUUID().toString())
                .setProcRegistration(procRegistration)
                .build();

        networking.NetworkMessage networkMessage = NetworkMessage.newBuilder()
                .setSenderHost(processNode.getHost())
                .setSenderListeningPort(processNode.getPort().intValue())
                .setMessage(wrapperProcRegistration)
                .build();

        networking.Message requestRegistrationMessage = networking.Message.newBuilder()
                .setType(Message.Type.NETWORK_MESSAGE)
                .setMessageUuid(UUID.randomUUID().toString())
                .setNetworkMessage(networkMessage)
                .build();

        return requestRegistrationMessage;
    }

}
