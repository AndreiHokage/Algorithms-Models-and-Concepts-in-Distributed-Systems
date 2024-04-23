package abstractizations;

import model.DistributedSystem;
import model.MyselfNode;
import model.ProcessNode;
import networking.Message;
import processing.ProtoDeserialiseUtils;
import processing.ProtoSerialiseUtils;
import utils.IntfConstants;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * An abstraction can contain multiple sessions. Each invocation of a writing or a reading operation on the same process
 * for the same register leads to a new session
 */
public class NnarAbstraction implements Abstraction{

    private String idAbstraction;

    private Integer rid;

    // It is possible as the register to be called for 2 different operations on the same time.
    // To differentiate between both operations and the internal state (Acks, readList, reading)
    // we create different session for THE SAME REGISTER -> session assigned for different processes, not for the same process
    // On process level, each node is assigned to only one NNarAbstraction_NnarSession during the operation execution
    // When a new operation is invoked another session is created;
    private HashMap<Integer, NnarSession> sessions = new HashMap<>();

    private NnarDefinedValue nnarDefinedValue;

    private Boolean isLocked;

    public NnarAbstraction(String idAbstraction){
        this.idAbstraction = idAbstraction;
        this.nnarDefinedValue = new NnarDefinedValue();
        this.rid = 0;
        this.isLocked = false;
    }

    public void createReadSession(){
        rid = rid + 1;
        sessions.put(rid, new NnarSession(0, null, new ArrayList<>(), null, true));
    }

    public void createWriteSession(Integer value){
        rid = rid + 1;
        sessions.put(rid, new NnarSession(0, value, new ArrayList<>(), null, false));
    }

    public void lockRegister() {
         this.isLocked = true;
    }

    public void releaseLockRegister() {
        this.isLocked = false;
    }

    public Boolean isLocked(){
        return this.isLocked;
    }

    public NnarSession getCurrentSession(){
        return sessions.get(rid);
    }

    public String getIdAbstraction() {
        return idAbstraction;
    }

    public void setIdAbstraction(String idAbstraction) {
        this.idAbstraction = idAbstraction;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public HashMap<Integer, NnarSession> getSession() {
        return sessions;
    }

    public void setSession(HashMap<Integer, NnarSession> sessions) {
        this.sessions = sessions;
    }

    public NnarDefinedValue getNnarDefinedValue() {
        return nnarDefinedValue;
    }

    public void setNnarDefinedValue(NnarDefinedValue nnarDefinedValue) {
        this.nnarDefinedValue = nnarDefinedValue;
    }

    /*----------------------------- All the below methods are written on instance level. They are working on data on the current register abstraction ---------------------------*/

    /**
     *
     * @param message - a message of type NnarWrite that triggers a broadcast with a NnarInternalRead
     * @param metaInfoMessage - meta data for the message NnarWrite
     * @return a BebBroadcast message to be enqueued
     */
    public networking.Message handlingNnarWrite(networking.NnarWrite message, MetaInfoMessage metaInfoMessage){
        networking.Value valueMessage = message.getValue();
        Integer value = valueMessage.getV();

        this.createWriteSession(value);

        String currentHandingAbstraction = metaInfoMessage.getToAbstractionId(); // comes from app level (up -> down)
        String toAbstractionID = metaInfoMessage.getToAbstractionId() + "." + IntfConstants.BEB_ABS;
        MetaInfoMessage metaInfoMessageNnarInternalRead = new MetaInfoMessage(Message.Type.NNAR_INTERNAL_READ, metaInfoMessage.getMessageUuid(),
                null, currentHandingAbstraction, DistributedSystem.createNewInstance().getSystemId()); // think as the process ref-1 unwrappes the messag and look to the toAbstractonId in order to see where to send further the message. Now is beb !!!
        networking.Message nnarInternalReadMessage = ProtoSerialiseUtils.createNnarInternalReadMessage(this.rid, metaInfoMessageNnarInternalRead);

        MetaInfoMessage metaInfoMessageBebBroadcast = new MetaInfoMessage(Message.Type.BEB_BROADCAST, metaInfoMessage.getMessageUuid(),
                null, toAbstractionID, DistributedSystem.createNewInstance().getSystemId());
        networking.Message bebBroadcastMessage = ProtoSerialiseUtils.createBebBroadcastMessage(nnarInternalReadMessage, metaInfoMessageBebBroadcast); // the message has the abstraction toAbstraction in any other node perspective

        return bebBroadcastMessage;
    }

    public networking.Message handlingNnarRead(networking.NnarRead message, MetaInfoMessage metaInfoMessage){
        this.createReadSession();

        String currentHandlingAbstraction = metaInfoMessage.getToAbstractionId(); // comes from app level (up -> down)
        String toAbstractionID = metaInfoMessage.getToAbstractionId() + "." + IntfConstants.BEB_ABS;
        MetaInfoMessage metaInfoMessageNnarInternalRead = new MetaInfoMessage(Message.Type.NNAR_INTERNAL_READ, metaInfoMessage.getMessageUuid(),
                null, currentHandlingAbstraction, DistributedSystem.createNewInstance().getSystemId());
        networking.Message nnarInternalReadMessage = ProtoSerialiseUtils.createNnarInternalReadMessage(this.rid, metaInfoMessageNnarInternalRead);

        MetaInfoMessage metaInfoMessageBebBroadcast = new MetaInfoMessage(Message.Type.BEB_BROADCAST, metaInfoMessage.getMessageUuid(),
                null, toAbstractionID, DistributedSystem.createNewInstance().getSystemId());
        networking.Message bebBroadcastMessage = ProtoSerialiseUtils.createBebBroadcastMessage(nnarInternalReadMessage, metaInfoMessageBebBroadcast); // the message has the abstraction toAbstraction in any other node perspective

        return bebBroadcastMessage;
    }

    public networking.Message handlingBebDeliver(networking.BebDeliver message, MetaInfoMessage metaInfoMessage){
        ProcessNode sender = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getSender());
        networking.Message content = message.getMessage();
        Message.Type typeContent = content.getType();

        if(typeContent.equals(Message.Type.NNAR_INTERNAL_READ)){
            String currentHandlingAbstraction = metaInfoMessage.getFromAbstractionId();
            String toAbstractionID = metaInfoMessage.getFromAbstractionId() + "." + IntfConstants.PL_ABS;
            networking.NnarInternalRead nnarInternalReadMessage = content.getNnarInternalRead();

            Integer deliveredRID = nnarInternalReadMessage.getReadId();
            NnarDefinedValue registeredValueOnCurrentSessionPerNode = this.nnarDefinedValue; // take the current value owned by the register

            String generatedUUID = UUID.randomUUID().toString();
            MetaInfoMessage metaInfoMessageNnarInternalValue = new MetaInfoMessage(Message.Type.NNAR_INTERNAL_VALUE, generatedUUID,
                    null, currentHandlingAbstraction, DistributedSystem.createNewInstance().getSystemId());
            networking.Message nnarInternalValueMessage = ProtoSerialiseUtils.createNnarInternalValueMessage(deliveredRID, registeredValueOnCurrentSessionPerNode,
                    metaInfoMessageNnarInternalValue);

            MetaInfoMessage metaInfoMessagePLSend = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                    null, toAbstractionID, DistributedSystem.createNewInstance().getSystemId());
            networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(nnarInternalValueMessage, metaInfoMessagePLSend, sender);

            return plSendMessage;
        }

        if(typeContent.equals(Message.Type.NNAR_INTERNAL_WRITE)){
            String currentHandlingAbstraction = metaInfoMessage.getFromAbstractionId();
            String toAbstractionId = metaInfoMessage.getFromAbstractionId() + "." + IntfConstants.PL_ABS;
            networking.NnarInternalWrite nnarInternalWriteMessage = content.getNnarInternalWrite();

            Integer r = nnarInternalWriteMessage.getReadId();
            Integer ts_p = nnarInternalWriteMessage.getTimestamp();
            Integer wr_p = nnarInternalWriteMessage.getWriterRank();
            Integer v_p = nnarInternalWriteMessage.getValue().getV();

            NnarDefinedValue nnarComputedDefinedValue = new NnarDefinedValue(ts_p, wr_p, v_p);
            if(nnarComputedDefinedValue.compareTo(this.nnarDefinedValue) > 0){ // the new value received is greater than the current one
                this.nnarDefinedValue = nnarComputedDefinedValue;
            }

            String generatedUUID = UUID.randomUUID().toString();
            MetaInfoMessage metaInfoMessageNnarInternalAck = new MetaInfoMessage(Message.Type.NNAR_INTERNAL_ACK, generatedUUID,
                    null, currentHandlingAbstraction, DistributedSystem.createNewInstance().getSystemId());
            networking.Message nnarInternalAckMessage = ProtoSerialiseUtils.createNnarInternalAckMessage(r, metaInfoMessageNnarInternalAck);

            MetaInfoMessage metaInfoMessagePLSend = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(nnarInternalAckMessage, metaInfoMessagePLSend, sender);

            return plSendMessage;
        }

        return null;
    }

    /**
     * Collect all the reads/acks from all other processes
     * @param message
     * @param metaInfoMessage
     * @return
     */
    public networking.Message handlingPLDeliver(networking.PlDeliver message, MetaInfoMessage metaInfoMessage){
        networking.Message contentMessage = message.getMessage();
        Message.Type type = contentMessage.getType();

        if(type.equals(Message.Type.NNAR_INTERNAL_VALUE)) {
            networking.NnarInternalValue nnarInternalValueMessage = contentMessage.getNnarInternalValue();
            String generatedUUID = UUID.randomUUID().toString();
            String currentHandlingAbstraction = metaInfoMessage.getFromAbstractionId();
            String toAbstractionID = metaInfoMessage.getFromAbstractionId() + "." + IntfConstants.BEB_ABS;

            Integer r = nnarInternalValueMessage.getReadId();
            Integer ts_p = nnarInternalValueMessage.getTimestamp();
            Integer wr_p = nnarInternalValueMessage.getWriterRank();
            Integer v_p = nnarInternalValueMessage.getValue().getV();

            this.sessions.get(r).getReadList().add(new NnarDefinedValue(ts_p, wr_p, v_p));
            if(this.sessions.get(r).getReadList().size() > Math.ceil(IntfConstants.NUM_PROC /(double) 2)){ // IN FAVOUR
                NnarDefinedValue biggest = this.getSession().get(r).getBiggestValueFromAllReadings(); // `such that` is applied
                this.getSession().get(r).clearReadList(); // clear the readList of the queried session
                this.getSession().get(r).setReadVal(biggest.getVal());
                if(this.getSession().get(r).getReading().equals(Boolean.TRUE)){
                    MetaInfoMessage metaInfoMessageNnarInternalWrite = new MetaInfoMessage(Message.Type.NNAR_INTERNAL_WRITE, generatedUUID,
                            null, currentHandlingAbstraction, DistributedSystem.createNewInstance().getSystemId());

                    networking.Message nnarInternalWriteMessage = ProtoSerialiseUtils.createNnarInternalWriteMessage(r, biggest,
                            metaInfoMessageNnarInternalWrite);

                    MetaInfoMessage metaInfoMessageBebBroadcast = new MetaInfoMessage(Message.Type.BEB_BROADCAST, generatedUUID,
                            null, toAbstractionID, DistributedSystem.createNewInstance().getSystemId());

                    networking.Message bebBroadcastMessage = ProtoSerialiseUtils.createBebBroadcastMessage(nnarInternalWriteMessage, metaInfoMessageBebBroadcast);

                    return bebBroadcastMessage;
                } else { // WRITE Operation
                    MetaInfoMessage metaInfoMessageNnarInternalWrite = new MetaInfoMessage(Message.Type.NNAR_INTERNAL_WRITE, generatedUUID,
                            null, currentHandlingAbstraction, DistributedSystem.createNewInstance().getSystemId());

                    NnarDefinedValue modifiedBiggest = biggest;
                    modifiedBiggest.setTs(modifiedBiggest.getTs() + 1);
                    modifiedBiggest.setWr(MyselfNode.createNewInstance().getRank());
                    modifiedBiggest.setVal(this.sessions.get(r).getWriteval());
                    networking.Message nnarInternalWriteMessage = ProtoSerialiseUtils.createNnarInternalWriteMessage(r, modifiedBiggest, metaInfoMessageNnarInternalWrite);

                    MetaInfoMessage metaInfoMessageBebBroadcast = new MetaInfoMessage(Message.Type.BEB_BROADCAST, generatedUUID,
                            null, toAbstractionID, DistributedSystem.createNewInstance().getSystemId());

                    networking.Message bebBroadcastMessage = ProtoSerialiseUtils.createBebBroadcastMessage(nnarInternalWriteMessage, metaInfoMessageBebBroadcast);

                    return bebBroadcastMessage;
                }
            }
        }

        if(type.equals(Message.Type.NNAR_INTERNAL_ACK)){
            networking.NnarInternalAck nnarInternalAck = contentMessage.getNnarInternalAck();
            String fromAbstractionID = AbstractionUtils.removeCurrentAbstraction(metaInfoMessage.getFromAbstractionId()); // !!! go up. `app`

            Integer r = nnarInternalAck.getReadId();
            this.sessions.get(r).setAcks(this.sessions.get(r).getAcks() + 1);

            if(this.sessions.get(r).getAcks() > Math.ceil(IntfConstants.NUM_PROC /(double) 2)){ // IN FAVOUR
                this.sessions.get(r).setAcks(0); // any later on message will stop here !!!!!!!!
                if(this.getSession().get(r).getReading().equals(Boolean.TRUE)){
                    this.getSession().get(r).setReading(Boolean.FALSE);

                    MetaInfoMessage metaInfoMessageNnarReadReturn = new MetaInfoMessage(Message.Type.NNAR_READ_RETURN, metaInfoMessage.getMessageUuid(),
                            fromAbstractionID, null, DistributedSystem.createNewInstance().getSystemId());
                    networking.Message nnarReadReturnMessage = ProtoSerialiseUtils.createNnarReadReturnMessage(
                            ProtoSerialiseUtils.createValueMessage(this.sessions.get(r).getReadVal()),  metaInfoMessageNnarReadReturn);

                    MyselfNode.createNewInstance().registerReleaseLockRegister(metaInfoMessageNnarReadReturn.getMessageUuid(), this.idAbstraction);
                    return nnarReadReturnMessage;
                } else { // WRITE
                    MetaInfoMessage metaInfoMessageNnarWriteReturn = new MetaInfoMessage(Message.Type.NNAR_WRITE_RETURN, metaInfoMessage.getMessageUuid(),
                            fromAbstractionID, null, DistributedSystem.createNewInstance().getSystemId());
                    networking.Message nnarWriteReturnMessage = ProtoSerialiseUtils.createNnarWriteReturnMessage(metaInfoMessageNnarWriteReturn);

                    MyselfNode.createNewInstance().registerReleaseLockRegister(metaInfoMessageNnarWriteReturn.getMessageUuid(), this.idAbstraction);
                    return nnarWriteReturnMessage;
                }
            }
        }

        return null;
    }

}
