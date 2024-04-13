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

}
