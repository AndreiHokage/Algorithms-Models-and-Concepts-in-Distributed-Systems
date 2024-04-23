package abstractizations;

import model.DistributedSystem;
import model.Hub;
import model.MyselfNode;
import model.ProcessNode;
import networking.AppRead;
import networking.AppWrite;
import networking.Message;
import networking.Value;
import org.apache.log4j.Logger;
import processing.CatalogueAbstractions;
import processing.ProtoDeserialiseUtils;
import processing.ProtoSerialiseUtils;
import utils.IntfConstants;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AppAbstraction implements Abstraction{

    private static Logger logger = Logger.getLogger(AppAbstraction.class.getName());

    private static AppAbstraction appAbstraction = null;

    private AppAbstraction(){

    }

    public static AppAbstraction createNewInstance(){
        if(appAbstraction == null){
            appAbstraction = new AppAbstraction();
        }

        return appAbstraction;
    }

    public networking.Message handlingPLDeliver(networking.PlDeliver message, MetaInfoMessage metaInfoMessage){
        // get the content
        Message content = message.getMessage();
        String orgToAbstractionId = metaInfoMessage.getFromAbstractionId();
        if(content.getType().equals(Message.Type.APP_BROADCAST)){
            networking.AppBroadcast appBroadcast = content.getAppBroadcast();
            networking.Value value = appBroadcast.getValue();

            String generatedUUID = UUID.randomUUID().toString();
            MetaInfoMessage metaInfoMessageAppValue = new MetaInfoMessage(Message.Type.APP_VALUE, generatedUUID,
                    null, orgToAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message appValueMessageBroadcasting = ProtoSerialiseUtils.createAppValueMessage(value, metaInfoMessageAppValue);

            String toAbstractionId = orgToAbstractionId + "." + IntfConstants.BEB_ABS;
            MetaInfoMessage metaInfoMessageBebBroadcast = new MetaInfoMessage(Message.Type.BEB_BROADCAST, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message bebBroadcastMessage = ProtoSerialiseUtils.createBebBroadcastMessage(appValueMessageBroadcasting, metaInfoMessageBebBroadcast);

            return bebBroadcastMessage;
        }

        if(content.getType().equals(Message.Type.BEB_DELIVER)){
            networking.AppValue appValue = content.getAppValue();
            networking.Value value = appValue.getValue();

            String generatedUUID = UUID.randomUUID().toString();
            String toAbstractionId = orgToAbstractionId + "." + IntfConstants.PL_ABS;
            MetaInfoMessage metaInfoMessagePLSend = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());

            networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(content, metaInfoMessagePLSend, MyselfNode.createNewInstance());
            return plSendMessage;
        }

        if(content.getType().equals(Message.Type.PROC_INITIALIZE_SYSTEM)){
            networking.ProcInitializeSystem procInitializeSystemMessage = content.getProcInitializeSystem();
            List<networking.ProcessId> processesIdList = procInitializeSystemMessage.getProcessesList();
            List<ProcessNode> processNodes = processesIdList.stream()
                    .map(processId -> {
                        return ProtoDeserialiseUtils.createProcessNodeFromProcessId(processId);
                    })
                    .collect(Collectors.toList());
            DistributedSystem.createNewInstance().setProcesses(processNodes);
            logger.info(MyselfNode.createNewInstance().getNameNode() + ": Starting system " + DistributedSystem.createNewInstance().getSystemId());
        }

        if(content.getType().equals(Message.Type.APP_WRITE)){
            AppWrite appWriteMessage = content.getAppWrite();
            String register = appWriteMessage.getRegister();
            Value appValue = appWriteMessage.getValue();

            // create a register `register` if it didn't exist yet and lock it
            CatalogueAbstractions.getNnarAbstraction(register);

            if(!CatalogueAbstractions.getNnarAbstraction(register).isLocked()){
                CatalogueAbstractions.getNnarAbstraction(register).lockRegister();
            } else {
                return null; // need to finish the operation and after that will be released
            }

            String generatedUUID = UUID.randomUUID().toString();
            String toAbstractionId = "app" + "." + IntfConstants.NNAR_ABS + "[" + register + "]";
            MetaInfoMessage metaInfoMessageNnarWriteMessage = new MetaInfoMessage(Message.Type.NNAR_WRITE, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message nnarWriteMessage = ProtoSerialiseUtils.createNnarWriteMessage(appValue, metaInfoMessageNnarWriteMessage);

            return nnarWriteMessage;
        }

        if(content.getType().equals(Message.Type.APP_READ)){
            AppRead appReadMessage = content.getAppRead();
            String register = appReadMessage.getRegister();

            // create a register `register` if it didn't exist yet and lock it
            CatalogueAbstractions.getNnarAbstraction(register);
            if(!CatalogueAbstractions.getNnarAbstraction(register).isLocked()){
                CatalogueAbstractions.getNnarAbstraction(register).lockRegister();
            } else {
                return null; // need to finish the operation and after that will be released
            }

            String generatedUUID = UUID.randomUUID().toString();
            String toAbstractionId = "app" + "." + IntfConstants.NNAR_ABS + "[" + register + "]";
            MetaInfoMessage metaInfoMessageNnarReadMessage = new MetaInfoMessage(Message.Type.NNAR_READ, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message nnarReadMessage = ProtoSerialiseUtils.createNnarReadMessage(metaInfoMessageNnarReadMessage);

            return nnarReadMessage;
        }

        return null;
    }

    public networking.Message handlingBebDeliver(networking.BebDeliver message, MetaInfoMessage metaInfoMessage){
        // get the content
        Message content = message.getMessage();
        String orgToAbstractionId = metaInfoMessage.getFromAbstractionId();

        networking.AppValue appValue = content.getAppValue();
        networking.Value value = appValue.getValue();

        String generatedUUID = UUID.randomUUID().toString();
        String toAbstractionId = "app";
        MetaInfoMessage metaInfoMessageAppValueMessage = new MetaInfoMessage(Message.Type.APP_VALUE, generatedUUID,
                null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
        networking.Message appValueMessage = ProtoSerialiseUtils.createAppValueMessage(value, metaInfoMessageAppValueMessage);

        String toAbstractionIdPLSend = toAbstractionId + "." + IntfConstants.PL_ABS;
        MetaInfoMessage metaInfoMessagePLSend = new MetaInfoMessage(Message.Type.PL_SEND, metaInfoMessageAppValueMessage.getMessageUuid(),
                null, toAbstractionIdPLSend, DistributedSystem.createNewInstance().getSystemId());

        ProcessNode processNodeHub = DistributedSystem.createNewInstance().getHub();
        networking.Message plSendMessageConfirmationBeb = ProtoSerialiseUtils.createPLSendMessage(appValueMessage, metaInfoMessagePLSend, processNodeHub);

        return plSendMessageConfirmationBeb;
    }

    public networking.Message handlingNnarWriteReturn(networking.NnarWriteReturn message, MetaInfoMessage metaInfoMessage){
        String releasedIdRegister = MyselfNode.createNewInstance().getReleasedLockRegisters().get(metaInfoMessage.getMessageUuid());
        String toAbstractionId = "app" + "." + IntfConstants.PL_ABS;
        String generatedUUID = UUID.randomUUID().toString();

        MetaInfoMessage metaInfoMessageAppWriteReturn = new MetaInfoMessage(Message.Type.APP_WRITE_RETURN, generatedUUID,
                null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
        networking.Message appWriteReturnMessage = ProtoSerialiseUtils.createAppWriteReturnMessage(releasedIdRegister, metaInfoMessageAppWriteReturn);

        MetaInfoMessage metaInfoMessagePLSend = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
        networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(appWriteReturnMessage, metaInfoMessagePLSend,
                DistributedSystem.createNewInstance().getHub());
        MyselfNode.createNewInstance().removeRegistrationReleaseLockRegister(metaInfoMessage.getMessageUuid());
        CatalogueAbstractions.getNnarAbstraction(releasedIdRegister).releaseLockRegister();

        return plSendMessage;
    }

    public networking.Message handlingNnarReadReturn(networking.NnarReadReturn message, MetaInfoMessage metaInfoMessage){
        String releasedIdRegister = MyselfNode.createNewInstance().getReleasedLockRegisters().get(metaInfoMessage.getMessageUuid());
        String toAbstractionId = "app" + "." + IntfConstants.PL_ABS;
        String generatedUUID = UUID.randomUUID().toString();

        MetaInfoMessage metaInfoMessage1AppReadReturn = new MetaInfoMessage(Message.Type.APP_READ_RETURN, generatedUUID,
                null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
        networking.Value readVal = ProtoSerialiseUtils.createValueMessage(message.getValue().getV());
        networking.Message appReadReturnMessage = ProtoSerialiseUtils.createAppReadReturnMessage(readVal, releasedIdRegister, metaInfoMessage1AppReadReturn);

        MetaInfoMessage metaInfoMessagePLSend = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
        networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(appReadReturnMessage, metaInfoMessagePLSend,
                DistributedSystem.createNewInstance().getHub());
        MyselfNode.createNewInstance().removeRegistrationReleaseLockRegister(metaInfoMessage.getMessageUuid());
        CatalogueAbstractions.getNnarAbstraction(releasedIdRegister).releaseLockRegister();

        return plSendMessage;
    }

    public networking.Message handlingProcRegistration(){
        String generatedUUID = UUID.randomUUID().toString();
        String toAbstractionId = "app";
        MetaInfoMessage metaInfoMessageProcReg = new MetaInfoMessage(Message.Type.PROC_REGISTRATION, generatedUUID,
                null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
        networking.Message procRegistrationMessage = ProtoSerialiseUtils.createProcRegistrationRequest(metaInfoMessageProcReg);

        String toAbstractionIdPLSend = toAbstractionId + "." + IntfConstants.PL_ABS;
        MetaInfoMessage metaInfoMessagePL = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                null, toAbstractionIdPLSend, DistributedSystem.createNewInstance().getSystemId());

        ProcessNode processNodeHub = DistributedSystem.createNewInstance().getHub();
        networking.Message plSendProcRegistrationMessage = ProtoSerialiseUtils.createPLSendMessage(procRegistrationMessage, metaInfoMessagePL, processNodeHub);

        return plSendProcRegistrationMessage;
    }
}
