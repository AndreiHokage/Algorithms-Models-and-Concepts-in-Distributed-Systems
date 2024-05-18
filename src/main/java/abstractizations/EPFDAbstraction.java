package abstractizations;

import model.DistributedSystem;
import model.MyselfNode;
import model.ProcessNode;
import networking.Message;
import networking.PlSend;
import org.apache.log4j.Logger;
import processing.CatalogueAbstractions;
import processing.ProtoDeserialiseUtils;
import processing.ProtoSerialiseUtils;
import utils.IntfConstants;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EPFDAbstraction implements Abstraction{

    public static final Logger logger = Logger.getLogger(EPFDAbstraction.class.getName());

    // To block the processing of heartbeatReplies when the Timeout event regarding
    // the checking of suspicious failures is performed. We are interested in the liveness
    // of the system after the delay has been elapsed completely
    private Lock aliveListLocked = new ReentrantLock();

    private ScheduledExecutorService failureDetectorCheckingTimeout = Executors.newScheduledThreadPool(1); // only one thread need to schedule the job
    // we want to avoid the situation when 2 checks are doing concurrently

    private List<ProcessNode> alive = new ArrayList<>();

    private List<ProcessNode> suspected = new ArrayList<>();

    private Long delay = null;

    private Long delta = null;

    public EPFDAbstraction(Long delta){
        this.alive = DistributedSystem.createNewInstance().getProcesses();
        this.delta = delta;
        this.delay = delta;

        scheduleTheNextTimeoutFromInit(); // BALANICI???
    }

    private void scheduleTheNextTimeoutFromInit(){
        // it is gonna to put the job in the queue due to we always launch the next scheduling within the current command
        failureDetectorCheckingTimeout.schedule(new LivenessSystemChecking(), 0, TimeUnit.MILLISECONDS);
        //failureDetectorCheckingTimeout.scheduleAtFixedRate(new LivenessSystemChecking(), 0, this.delay, TimeUnit.MILLISECONDS);
    }

    private void scheduleTheNextTimeout(){
        // it is gonna to put the job in the queue due to we always launch the next scheduling within the current command
        failureDetectorCheckingTimeout.schedule(new LivenessSystemChecking(), this.delay, TimeUnit.MILLISECONDS);
        //failureDetectorCheckingTimeout.scheduleAtFixedRate(new LivenessSystemChecking(), 0, this.delay, TimeUnit.MILLISECONDS);
    }

    public networking.Message handlingPLDeliver(networking.PlDeliver message, MetaInfoMessage metaInfoMessage){ // we receive from ourselves as well
        if(AppAbstraction.currentTopicConsensus == null){
            logger.info("No current topic abstraction that to be treated by epfd");
            return null;
        }

        networking.Message contentMessage = message.getMessage();
        Message.Type type = contentMessage.getType();

        if(type.equals(Message.Type.EPFD_INTERNAL_HEARTBEAT_REQUEST)) {
            String generatedUUID = UUID.randomUUID().toString();
            String toAbstractionIdHeartbeatReply = metaInfoMessage.getFromAbstractionId(); // comes from lower abstraction levels. Should be the EPFD abstraction
            MetaInfoMessage metaInfoMessageHeartBeatReply = new MetaInfoMessage(Message.Type.EPFD_INTERNAL_HEARTBEAT_REPLY, generatedUUID,
                    null, toAbstractionIdHeartbeatReply, DistributedSystem.createNewInstance().getSystemId());
            networking.Message heartbeatReplyMessage = ProtoSerialiseUtils.createEpfdInternalHeartbeatReplyMessage(metaInfoMessageHeartBeatReply);

            String toAbstractionId = toAbstractionIdHeartbeatReply + "." + IntfConstants.PL_ABS;
            // get the sender
            ProcessNode destinationProcessNode = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getSender());
            MetaInfoMessage metaInfoMessagePLSend = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                    null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
            networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(heartbeatReplyMessage, metaInfoMessagePLSend, destinationProcessNode);

            return plSendMessage;
        }

        if(type.equals(Message.Type.EPFD_INTERNAL_HEARTBEAT_REPLY)){
            // once the ProcessHeartBeatQueueThread blocks here, it means that the timeout has been elapsed and it is trying to
            // classify again the processes as right or suspicious ones. When the locking is released, the alive list will be empty
            aliveListLocked.lock();
            ProcessNode senderProcessNode = ProtoDeserialiseUtils.convertProcessIdToProcessNode(message.getSender());
            alive.add(senderProcessNode);
            aliveListLocked.lock();
        }

        return null;
    }

    private class LivenessSystemChecking implements Callable<List<networking.Message>> {

        private boolean isContainedIntoList(ProcessNode processNode, List<ProcessNode> analysedList){
            for(ProcessNode processNodeList: analysedList){
                if(processNode.equals(processNodeList)){
                    return true;
                }
            }

            return false;
        }

        private boolean hasRevivedProcesses(){
            for(ProcessNode aliveProcess: alive){ // a process that has replied back to the heartbeat request on this current session
                for(ProcessNode suspectedProcess: suspected){ // a process that is suspected to have been crashed due to hasn't replied back from a previous session until now
                    if(aliveProcess.equals(suspectedProcess)){
                        return true; // a process that we have made a wrong suspicion failure
                    }
                }
            }
            return false;
        }

        private void pullOurFromSuspectedList(ProcessNode processNode){
            int idx = 0;
            for(ProcessNode processNodeSuspected: suspected){
                if(processNodeSuspected.equals(processNode)){
                    break;
                }
                idx = idx + 1;
            }

            suspected.remove(idx);
        }

        @Override
        public List<Message> call() throws Exception {
            if(AppAbstraction.currentTopicConsensus == null){
                logger.info("No current topic abstraction that to be treated by epfd");
                // schedule the next timeout
                scheduleTheNextTimeout();
                return null;
            }

            aliveListLocked.lock();
            List<networking.Message> livenessMessagesList = new ArrayList<>();

            if(hasRevivedProcesses() == true){
                delay += delta;
            }

            for(ProcessNode processNode: DistributedSystem.createNewInstance().getProcesses()){
                if(!isContainedIntoList(processNode, alive) && !isContainedIntoList(processNode, suspected)){ // died now. `processNode` has possible died now
                    logger.trace(MyselfNode.createNewInstance().getUUIDProcessNode() + ": detected the process: " + processNode.getUUIDProcessNode() + " as suspected!");
                    suspected.add(processNode);

                    String generatedUUID = UUID.randomUUID().toString();
                    String fromAbstractionID = IntfConstants.ELD_ABS; // the abstraction that will handle the returned message
                    MetaInfoMessage metaInfoMessageEpfdSuspect = new MetaInfoMessage(Message.Type.EPFD_SUSPECT, generatedUUID,
                            fromAbstractionID, null, DistributedSystem.createNewInstance().getSystemId());
                    networking.Message epfdSuspectMessage = ProtoSerialiseUtils.createEpfdSuspectMessage(metaInfoMessageEpfdSuspect, processNode);

                    livenessMessagesList.add(epfdSuspectMessage);
                } else if(isContainedIntoList(processNode, alive) && isContainedIntoList(processNode, suspected)){ // revived now. `processNode` has revived now
                    logger.trace(MyselfNode.createNewInstance().getUUIDProcessNode() + ": detected the process: " + processNode.getUUIDProcessNode() + " as alive!");
                    pullOurFromSuspectedList(processNode);

                    String generatedUUID = UUID.randomUUID().toString();
                    String fromAbstractionID = IntfConstants.ELD_ABS; // the abstraction that will handle the returned message
                    MetaInfoMessage metaInfoMessageEpfdRestore = new MetaInfoMessage(Message.Type.EPFD_RESTORE, generatedUUID,
                            fromAbstractionID, null, DistributedSystem.createNewInstance().getSystemId());
                    networking.Message epfdRestoreMessage = ProtoSerialiseUtils.createEpfdRestoreMessage(metaInfoMessageEpfdRestore, processNode);

                    livenessMessagesList.add(epfdRestoreMessage);
                }

                String generatedUUID = UUID.randomUUID().toString();
                //"app.uc[x].ec[0].eld" + "." + IntfConstants.EPFD_ABS;
                String toAbstractionIdHeartbeatRequest = IntfConstants.APP_ABS + "." +
                        IntfConstants.UC_ABS + "[" + AppAbstraction.currentTopicConsensus + "]" + "." +
                        IntfConstants.EC_ABS + "[" + CatalogueAbstractions.getUcAbstraction(AppAbstraction.currentTopicConsensus).getEts() + "]" + "." +
                        IntfConstants.ELD_ABS + "." +
                        IntfConstants.EPFD_ABS; // indicate the abstraction that will handel the encapsulated message or from where comes form
                MetaInfoMessage metaInfoMessageHeartBeatRequest = new MetaInfoMessage(Message.Type.EPFD_INTERNAL_HEARTBEAT_REQUEST, generatedUUID,
                        null, toAbstractionIdHeartbeatRequest, DistributedSystem.createNewInstance().getSystemId());
                networking.Message heartbeatRequestMessage = ProtoSerialiseUtils.createEpfdInternalHeartbeatRequestMessage(metaInfoMessageHeartBeatRequest);

                String toAbstractionId = toAbstractionIdHeartbeatRequest + "." + IntfConstants.PL_ABS;
                MetaInfoMessage metaInfoMessagePLSend = new MetaInfoMessage(Message.Type.PL_SEND, generatedUUID,
                        null, toAbstractionId, DistributedSystem.createNewInstance().getSystemId());
                networking.Message plSendMessage = ProtoSerialiseUtils.createPLSendMessage(heartbeatRequestMessage, metaInfoMessagePLSend, processNode);

                livenessMessagesList.add(plSendMessage);
            }

            for(networking.Message message: livenessMessagesList){
                if(MessageUtils.checkHeartBeatOriginMessages(message)){ // we want to store just the HeartBeat request/reply messages
                    DistributedSystem.createNewInstance().getHeartBeatQueue().getEventsQueue().put(message);
                } else { //Suspect/Restore
                    DistributedSystem.createNewInstance().getEventQueue().getEventsQueue().put(message); // we are in the processing thread associated with the heartbeat queue
                    // need to make references ot the classical queue
                }
            }

            // reset the alive processes list to check again the status of liveness of the system;
            alive = new ArrayList<>();

            // schedule the next timeout
            scheduleTheNextTimeout();

            aliveListLocked.unlock();
            return livenessMessagesList;
        }
    }
}
