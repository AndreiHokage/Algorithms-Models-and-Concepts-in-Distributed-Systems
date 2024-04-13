package processing;

import model.DistributedSystem;
import model.Hub;
import model.MyselfNode;
import model.ProcessNode;

import java.util.Locale;

public class ProtoDeserialiseUtils {

    public static ProcessNode getSenderOfNetworkMessage(networking.NetworkMessage networkMessage){
        String host = networkMessage.getSenderHost();
        String port = Integer.valueOf(networkMessage.getSenderListeningPort()).toString();

        Hub hub = DistributedSystem.createNewInstance().getHub();
        if(host.equals(hub.getHost()) && port.equals(hub.getPort().toString())) {
            return hub;
        }

        ProcessNode sender = DistributedSystem.createNewInstance().searchForProcessNode(host, port);
        return sender;
    }

    public static ProcessNode convertProcessIdToProcessNode(networking.ProcessId processId){
        String host = processId.getHost();
        String port = Integer.valueOf(processId.getPort()).toString();

        Hub hub = DistributedSystem.createNewInstance().getHub();
        if(host.equals(hub.getHost()) && port.equals(hub.getPort().toString())){
            String owner =  processId.getOwner();
            Integer index = processId.getIndex();
            Integer rank = processId.getRank();
            ProcessNode processNodeHub = new ProcessNode(host, Integer.valueOf(port), owner, index, rank);
            return processNodeHub;
        }

        ProcessNode processNode = DistributedSystem.createNewInstance().searchForProcessNode(host, port);

        return processNode;
    }

    public static ProcessNode createProcessNodeFromProcessId(networking.ProcessId processId){
        String host = processId.getHost();
        Integer port = Integer.valueOf(processId.getPort());
        String owner =  processId.getOwner();
        Integer index = processId.getIndex();
        Integer rank = processId.getRank();

        ProcessNode processNode = new ProcessNode(host, port, owner, index, rank);
        return processNode;
    }
}
