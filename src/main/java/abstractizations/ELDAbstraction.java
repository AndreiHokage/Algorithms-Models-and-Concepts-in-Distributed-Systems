package abstractizations;

import model.DistributedSystem;
import model.ProcessNode;
import networking.Message;
import processing.ProtoDeserialiseUtils;
import processing.ProtoSerialiseUtils;
import utils.IntfConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ELDAbstraction implements Abstraction{

    private List<ProcessNode> suspected = new ArrayList<>();

    private ProcessNode leader = null;

    public ELDAbstraction(){

    }

    private void pullOutFromSuspectedList(ProcessNode processNode){
        int idx = 0;
        for(ProcessNode processNodeSuspected: suspected){
            if(processNodeSuspected.equals(processNode)){
                break;
            }
            idx = idx + 1;
        }

        suspected.remove(idx);
    }

    private boolean isContainedIntoList(ProcessNode processNode, List<ProcessNode> analysedList){
        for(ProcessNode processNodeList: analysedList){
            if(processNode.equals(processNodeList)){
                return true;
            }
        }

        return false;
    }

    private networking.Message checkCurrentLeader(MetaInfoMessage metaInfoMessage){
        Integer maxRank = null;
        ProcessNode localLeader = null;
        for(ProcessNode processNode: DistributedSystem.createNewInstance().getProcesses()){
            if(isContainedIntoList(processNode, suspected) == false) { // PI \ suspected
                if(maxRank == null || processNode.getRank().compareTo(maxRank) > 0){
                    maxRank = processNode.getRank();
                    localLeader = processNode;
                }
            }
        }

        // it is possible as localLeader to have a lower rank. All we want to know is if the maxRank occurs again or not
        if(leader == null || !localLeader.getRank().equals(leader.getRank())){
            String generatedUUID = UUID.randomUUID().toString();
            String fromAbstractionId = IntfConstants.EC_ABS; // we go up now; it creates a message for itself and send tit to above abstraction levels
            MetaInfoMessage metaInfoMessageEldTrust = new MetaInfoMessage(Message.Type.ELD_TRUST, generatedUUID,
                    fromAbstractionId, null, DistributedSystem.createNewInstance().getSystemId());
            networking.Message eldTrustMessage = ProtoSerialiseUtils.createEldTrustMessage(metaInfoMessageEldTrust, localLeader);

            return eldTrustMessage;
        }

        return null;
    }

    public networking.Message handlingSuspect(networking.EpfdSuspect message, MetaInfoMessage metaInfoMessage){
        ProcessNode suspectedProcess = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getProcess());
        suspected.add(suspectedProcess);

        return checkCurrentLeader(metaInfoMessage);
    }

    public networking.Message handlingRestore(networking.EpfdRestore message, MetaInfoMessage metaInfoMessage){
        ProcessNode restoredProcess = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getProcess());
        pullOutFromSuspectedList(restoredProcess);

        return checkCurrentLeader(metaInfoMessage);
    }
}
