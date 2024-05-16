import abstractizations.PLAbstraction;
import model.*;
import processing.CatalogueAbstractions;
import processing.ProcessServicesRPC;
import servers.SystemInputServer;
import servers.SystemOutputServer;
import servers.threadsServer.ProcessEventQueueThread;
import servers.threadsServer.ProcessHeartBeatQueueThread;
import servers.threadsServer.SystemInputServerThread;

public class Main {

    public static void main(String[] args){
        String hub_host = args[0];
        Integer hub_port = Integer.valueOf(args[1]);
        String host = args[2];
        Integer port = Integer.valueOf(args[3]);
        String owner = args[4];
        Integer index = Integer.valueOf(args[5]);

        // Initialise the hub
        Hub hub = new Hub(hub_host, hub_port, owner, index);
        DistributedSystem.createNewInstance().setHub(hub);

        // Initialise the self identification of the process
        MyselfNode.createNewInstance(host, port, owner, index, null);

        // Initialise the distributed system with empty list of processes
        DistributedSystem.createNewInstance();

        // Initialize the event queue that stores all the events for a specific process / per process
        EventQueue eventQueue = new EventQueue();

        // Initialize the event queue that stores all the heartbeats requests and replies for ensuring the liveness of the system in real time
        HeartBeatQueue heartBeatQueue = new HeartBeatQueue();

        // Creates the input server that listens for incoming messages and stores them in queue
        SystemInputServer systemInputServer = new SystemInputServer(MyselfNode.createNewInstance().getPort(), eventQueue, heartBeatQueue);

        // Creates the output server that send message to the HUB
        SystemOutputServer systemOutputServer = new SystemOutputServer(hub);

        // Initialise the distributed system process with all the components
        DistributedSystem.createNewInstance().setSystemInputServer(systemInputServer);
        DistributedSystem.createNewInstance().setSystemOutputServer(systemOutputServer);
        DistributedSystem.createNewInstance().setEventQueue(eventQueue);
        DistributedSystem.createNewInstance().setHeartBeatQueue(heartBeatQueue);

        Thread systemInputServerThread = new SystemInputServerThread(systemInputServer);
        systemInputServerThread.setName("Thread-systemInputServer");
        Thread processEventQueueThread = new ProcessEventQueueThread(eventQueue);
        processEventQueueThread.setName("Thread-processEventQueue");
        Thread processHeartBeatQueueThread = new ProcessHeartBeatQueueThread(heartBeatQueue); // will extract rhe message from heartBeatQueue
        processHeartBeatQueueThread.setName("Thread-processHeartBeatQueue");

        Thread[] threads = new Thread[3];
        threads[0] = systemInputServerThread;
        threads[1] = processEventQueueThread;
        threads[2] = processHeartBeatQueueThread;

        for(Thread thread: threads){
            thread.start();
        }

        CatalogueAbstractions.getEpfdAbstraction(); // start the epfd from here

        // ProcessNode processNode = new ProcessNode(host, port, owner, index);
        // ProcessServicesRPC processServicesRPC = new ProcessServicesRPC(processNode, hub);
        System.out.println(MyselfNode.createNewInstance().getNameNode() + ": listening on " + MyselfNode.createNewInstance().getHost() +
                ":" + MyselfNode.createNewInstance().getPort().toString());
        //processServicesRPC.registrateToTheHUB();
        while(true){

        }
    }

}
