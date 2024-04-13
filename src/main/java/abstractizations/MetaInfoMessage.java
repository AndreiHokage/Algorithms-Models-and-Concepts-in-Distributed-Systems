package abstractizations;

import networking.Message;

/**
 * Each type of message is encapsulated into a general message that precises general details such as:
 *  1) the type of the embedded message
 *  2) a unique id that characterises the wrapped message
 *  3) the abstraction ID from which the message is delivered from
 *  4) the abstraction ID to that the message is sent to
 *  5) the system ID on which the message is defined
 *  6) the EMBEDDED message
 *
 *  This object will be sent as an attachment to every type of message that is EMBEDDED into it.
 */
public class MetaInfoMessage {

    private Message.Type type;

    private String messageUuid;

    private String fromAbstractionId;

    private String toAbstractionId;

    private String systemId;

    public MetaInfoMessage(Message.Type type, String messageUuid, String fromAbstractionId, String toAbstractionId, String systemId) {
        this.type = type;
        this.messageUuid = messageUuid;
        this.fromAbstractionId = fromAbstractionId;
        this.toAbstractionId = toAbstractionId;
        this.systemId = systemId;
    }

    public Message.Type getType() {
        return type;
    }

    public void setType(Message.Type type) {
        this.type = type;
    }

    public String getMessageUuid() {
        return messageUuid;
    }

    public void setMessageUuid(String messageUuid) {
        this.messageUuid = messageUuid;
    }

    public String getFromAbstractionId() {
        return fromAbstractionId;
    }

    public void setFromAbstractionId(String fromAbstractionId) {
        this.fromAbstractionId = fromAbstractionId;
    }

    public String getToAbstractionId() {
        return toAbstractionId;
    }

    public void setToAbstractionId(String toAbstractionId) {
        this.toAbstractionId = toAbstractionId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String toString() {
        return "MetaInfoMessage{" +
                "type=" + type +
                ", messageUuid='" + messageUuid + '\'' +
                ", fromAbstractionId='" + fromAbstractionId + '\'' +
                ", toAbstractionId='" + toAbstractionId + '\'' +
                ", systemId='" + systemId + '\'' +
                '}';
    }
}
