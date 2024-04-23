package model;

import org.apache.log4j.Logger;
import servers.SystemInputServer;
import servers.SystemOutputServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Works like a controller
 */
public class DistributedSystem {

    private static Logger logger = Logger.getLogger(DistributedSystem.class.getName());

    private static DistributedSystem distributedSystem = null;

    private static Integer systemVersion = 0;

    private String systemId;

    private List<ProcessNode> processes;

    private Map<String, ProcessNode> processNodesMap = new HashMap<>();

    private SystemInputServer systemInputServer;

    private SystemOutputServer systemOutputServer;

    private EventQueue eventQueue;

    private Hub hub;

    private DistributedSystem(List<ProcessNode> processes, String systemId){
        this.processes = processes;
        for(ProcessNode processNode: processes){
            if(processNode.getUUIDProcessNode().equals(MyselfNode.createNewInstance().getUUIDProcessNode())){
                MyselfNode.createNewInstance().setRank(processNode.getRank());
            }
            this.processNodesMap.put(processNode.getUUIDProcessNode(), processNode);
        }
        this.systemId = systemId;
    }

    private DistributedSystem(String systemId){
        this.processes = null;
        this.systemId = systemId;
    }

    public static DistributedSystem createNewInstance(){
        if(distributedSystem == null){
            systemVersion++;
            String systemIdentification = "sys-" + systemVersion;
            logger.trace("Initialise the system " + systemIdentification + " with the processes: null");
            distributedSystem = new DistributedSystem(systemIdentification);
        }
        return distributedSystem;
    }

    public static DistributedSystem createNewInstance(List<ProcessNode> processes){
        if(distributedSystem == null){
            systemVersion++;
            String systemIdentification = "sys-" + systemVersion;
            logger.trace("Initialise the system " + systemIdentification + " with the processes: " + processes);
            distributedSystem = new DistributedSystem(processes,  systemIdentification);
        }
        return distributedSystem;
    }

    public ProcessNode searchForProcessNode(String host, String port){
        ProcessNode searchedProcessNode = processNodesMap.get(host + "-" + port);
        return searchedProcessNode;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public List<ProcessNode> getProcesses() {
        return processes;
    }

    public void setProcesses(List<ProcessNode> processes) {
        for(ProcessNode processNode: processes) {
            if (processNode.getUUIDProcessNode().equals(MyselfNode.createNewInstance().getUUIDProcessNode())) {
                logger.info("Found my own process: " + processNode + " and initialise the rank with " + processNode.getRank());
                MyselfNode.createNewInstance().setRank(processNode.getRank());
            }
            this.processNodesMap.put(processNode.getUUIDProcessNode(), processNode);
        }

        logger.info("The list of processes received is: ");
        for(ProcessNode processNode: processes) {
            logger.info(processNode);
        }
        logger.info("----------------------END  list of processes for initialising system----------------------------");
        this.processes = processes;
    }

    public SystemInputServer getSystemInputServer() {
        return systemInputServer;
    }

    public SystemOutputServer getSystemOutputServer() {
        return systemOutputServer;
    }

    public EventQueue getEventQueue() {
        return eventQueue;
    }

    public void setSystemInputServer(SystemInputServer systemInputServer) {
        this.systemInputServer = systemInputServer;
    }

    public void setSystemOutputServer(SystemOutputServer systemOutputServer) {
        this.systemOutputServer = systemOutputServer;
    }

    public void setEventQueue(EventQueue eventQueue) {
        this.eventQueue = eventQueue;
    }

    public Hub getHub() {
        return hub;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
    }

    public Integer getNumberProcessesSystem(){
        return DistributedSystem.createNewInstance().processNodesMap.size();
    }
}
