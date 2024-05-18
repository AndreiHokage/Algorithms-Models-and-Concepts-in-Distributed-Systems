package utils;

import abstractizations.MetaInfoMessage;
import networking.Message;

public class MessageUtils {

    public static MetaInfoMessage extractMetaDataFromMessage(networking.Message message){
        Message.Type type = message.getType();
        String messageUUID = message.getMessageUuid();
        String fromAbstractionId = message.getFromAbstractionId();
        String toAbstractionId = message.getToAbstractionId();
        String systemId = message.getSystemId();

        MetaInfoMessage metaInfoMessage = new MetaInfoMessage(type, messageUUID, fromAbstractionId, toAbstractionId, systemId);
        return metaInfoMessage;
    }

    public static boolean checkHeartBeatOriginMessages(networking.Message message){
        // all the heartbeat messages regardless of their type (request| replies) are wrapped into NetworkMessages
        if(message.getType().equals(Message.Type.NETWORK_MESSAGE)) {
            networking.Message contentInsideNetwork = message.getNetworkMessage().getMessage();
            if(contentInsideNetwork.getType().equals(Message.Type.EPFD_INTERNAL_HEARTBEAT_REPLY) ||
                    contentInsideNetwork.getType().equals(Message.Type.EPFD_INTERNAL_HEARTBEAT_REQUEST)){
                return true;
            }
        }

        if(message.getType().equals(Message.Type.PL_SEND)) {
            networking.Message contentInsidePLSend = message.getPlSend().getMessage();
            if(contentInsidePLSend.getType().equals(Message.Type.EPFD_INTERNAL_HEARTBEAT_REPLY) ||
                    contentInsidePLSend.getType().equals(Message.Type.EPFD_INTERNAL_HEARTBEAT_REQUEST)){
                return true;
            }
        }

        return false;
    }

}
