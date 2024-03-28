import model.Hub;
import model.ProcessNode;
import processing.ProcessServicesRPC;

public class Main {

    public static void main(String[] args) {
        String hub_host = args[0];
        Integer hub_port = Integer.valueOf(args[1]);
        String host = args[2];
        Integer port = Integer.valueOf(args[3]);
        String owner = args[4];
        Integer index = Integer.valueOf(args[5]);

        Hub hub = new Hub(hub_host, hub_port);
        ProcessNode processNode = new ProcessNode(host, port, owner, index);

        ProcessServicesRPC processServicesRPC = new ProcessServicesRPC(processNode, hub);
        System.out.println(processNode.getNameNode() + ": listening on " + processNode.getHost() + ":" + processNode.getPort().toString());
        processServicesRPC.registrateToTheHUB();
        while(true){

        }
    }

}
