package abstractizations;

import model.DistributedSystem;
import model.MyselfNode;
import model.ProcessNode;
import networking.Message;
import processing.CatalogueAbstractions;
import processing.ProtoDeserialiseUtils;
import processing.ProtoSerialiseUtils;
import utils.IntfConstants;
import utils.MessageUtils;

import java.util.UUID;

public class UCAbstraction implements Abstraction {

    private String idAbstraction;

    private Integer val;

    private Boolean proposed;

    private Boolean decided;

    private Integer ets; // indicate the time coordinate of the current epoch

    private ProcessNode l; // indicate the leader coordinate of the current epoch

    private Integer newts; // indicate the time coordinate of the upcoming epoch

    private ProcessNode newl; // indicate the leader coordinate of the upcoming epoch

    public UCAbstraction(){
        this.val = Integer.MIN_VALUE;
        this.proposed = false;
        this.decided = false;

        this.ets = 0;
        this.l = getMaxRankProcessNode(); //BALANICI???
        CatalogueAbstractions.getAndCreateEpAbstraction(ets.toString(), new EPState());

        // It doesn't make sense to kick off the epoch now due to the val is still undefined
        // It is not possible as we had already responded to UC.Propose before the UC to be created

        this.newts = 0;
        this.newl = null;
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

    private networking.Message handlingKickOffInitEPEpoch(){
        if(l.equals(MyselfNode.createNewInstance()) && proposed == false && !val.equals(Integer.MIN_VALUE)){
            proposed = true;

            String generatedUUID = UUID.randomUUID().toString();
            String toAbstractionId = IntfConstants.EC_ABS + "." + IntfConstants.EP_ABS;
            MetaInfoMessage metaInfoMessageEPPropose = new MetaInfoMessage(Message.Type.EP_PROPOSE, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message epProposeMessage = ProtoSerialiseUtils.createEPPropose(val, metaInfoMessageEPPropose);

            return epProposeMessage;
        }

        return null;
    }

    public networking.Message handlingPropose(networking.UcPropose message, MetaInfoMessage metaInfoMessage){
        Integer proposedValue = message.getValue().getV();
        this.val = proposedValue;

        networking.Message kickOffEPEpochMessage = handlingKickOffInitEPEpoch(); // val is defined now

        return kickOffEPEpochMessage;
    }

    public networking.Message handlingStartEpoch(networking.EcStartEpoch message, MetaInfoMessage metaInfoMessage){
        Integer fwTimestamp = message.getNewTimestamp();
        ProcessNode fwLeader = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getNewLeader());

        newts = fwTimestamp;
        newl = fwLeader;

        String generatedUUID = UUID.randomUUID().toString();
        String toAbstractionId = IntfConstants.EC_ABS + "." + IntfConstants.EP_ABS;
        MetaInfoMessage metaInfoMessageEPAbort = new MetaInfoMessage(Message.Type.EP_ABORT, generatedUUID,
                null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
        networking.Message epAbortMessage = ProtoSerialiseUtils.createEpAbort(metaInfoMessageEPAbort);

        return epAbortMessage;
    }

    public networking.Message handlingAborted(networking.EpAborted message, MetaInfoMessage metaInfoMessage){
        Integer indexEPepoch = message.getEts();

        if(!ets.equals(indexEPepoch)){ // the aborted message is not corresponding for the epoch that I am running now. Maybe it is a delayed EPAborted message from a former EPEpoch
            return null;
        }

        // we are in the epoch that the aborting is referring to. We close the epoch and start another one
        EPState wellFormedEpState = new EPState(message.getValueTimestamp(), message.getValue().getV());
        ets = newts;
        l = newl;
        proposed = false;
        // wellFormedEpState may have an epochTimestamp different from ets
        CatalogueAbstractions.getAndCreateEpAbstraction(ets.toString(), wellFormedEpState); // create a new EP epoch starting from ets ut with the state wellFormedEpState

        networking.Message kickOffEPEpochMessage = handlingKickOffInitEPEpoch(); // only the leader will succeed

        return kickOffEPEpochMessage;
    }

    public networking.Message handlingDecide(networking.EpDecide message, MetaInfoMessage metaInfoMessage){
        Integer epDecidedValue = message.getValue().getV();
        Integer indexEPepoch = message.getEts();

        if(!ets.equals(indexEPepoch)){ // the decided message is not corresponding fo the epoch that I am running now. Maybe it is a delayed EPDecided message and a new epoch had already been started
            return null;
        }

        if(decided == true){
            return null;
        }

        decided = true;
        String generatedUUID = UUID.randomUUID().toString();
        String fromAbstractionId = IntfConstants.APP_ABS; // I marked the abstraction to which I delegate the processing responsability
        MetaInfoMessage metaInfoMessageUCDecide = new MetaInfoMessage(Message.Type.UC_DECIDE, generatedUUID,
                fromAbstractionId, null, DistributedSystem.createNewInstance().getSystemId());
        networking.Message ucDecidedMessage = ProtoSerialiseUtils.createUCDecide(epDecidedValue, metaInfoMessageUCDecide);

        return ucDecidedMessage;
    }
}
