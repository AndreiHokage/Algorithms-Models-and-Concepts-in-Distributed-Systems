package processing;

import abstractizations.*;
import model.DistributedSystem;
import model.MyselfNode;
import org.apache.log4j.Logger;
import servers.SystemOutputServer;
import utils.IntfConstants;

import java.util.HashMap;

public class CatalogueAbstractions {

    private static final Logger logger = Logger.getLogger(CatalogueAbstractions.class.getName());

    private static PLAbstraction plAbstraction = null;

    private static BebAbstraction bebAbstraction = null;

    private static AppAbstraction appAbstraction = null;

    private static EPFDAbstraction epfdAbstraction = null;

    private static ELDAbstraction eldAbstraction = null;

    private static ECAbstraction ecAbstraction = null;

    private static HashMap<String, NnarAbstraction> nnarAbstractions = new HashMap<>(); // store instances

    private static HashMap<String, EPAbstraction> epAbstractions = new HashMap<>(); // store instances

    private static HashMap<String, UCAbstraction> ucAbstractions = new HashMap<>();

    // It is accessed by both ProcessEventQueueThread and processHeartBeatQueueThread
    public synchronized static PLAbstraction getPlAbstraction() {
        if(plAbstraction == null){
            plAbstraction = PLAbstraction.createNewInstance(DistributedSystem.createNewInstance().getSystemOutputServer());
        }
        return plAbstraction;
    }

    // It is accessed by both ProcessEventQueueThread and processHeartBeatQueueThread
    public synchronized static BebAbstraction getBebAbstraction() {
        if(bebAbstraction == null){
            bebAbstraction = BebAbstraction.createNewInstance();
        }
        return bebAbstraction;
    }

    public static AppAbstraction getAppAbstraction() {
        if(appAbstraction == null){
            appAbstraction = AppAbstraction.createNewInstance();
        }
        return appAbstraction;
    }

    public static NnarAbstraction getNnarAbstraction(String register){
        if(nnarAbstractions.get(register) == null) {
            logger.info(DistributedSystem.createNewInstance().getSystemId() + "/" + MyselfNode.createNewInstance().getNameNode() +
                    ": Registering unknown abstraction " + register);
            NnarAbstraction nnarAbstraction = new NnarAbstraction(register);
            nnarAbstractions.put(register, nnarAbstraction);
        }
        return nnarAbstractions.get(register);
    }

    public static EPFDAbstraction getEpfdAbstraction(){
        if(epfdAbstraction == null){
            logger.info("EpfdAbstraction registered with delta: " + IntfConstants.DELTA_EPFD);
            epfdAbstraction = new EPFDAbstraction(IntfConstants.DELTA_EPFD);
        }

        return epfdAbstraction;
    }

    public static ELDAbstraction getEldAbstraction(){
        if(eldAbstraction == null){
            eldAbstraction = new ELDAbstraction();
        }

        return eldAbstraction;
    }

    public static ECAbstraction getEcAbstraction(){
        if(ecAbstraction == null){
            ecAbstraction = new ECAbstraction();
        }

        return ecAbstraction;
    }

    public static EPAbstraction getEpAbstraction(String epIndexEPEpoch){
        if(epAbstractions.get(epIndexEPEpoch) == null) {
            logger.info(DistributedSystem.createNewInstance().getSystemId() + "/" + MyselfNode.createNewInstance().getNameNode() +
                    ": Create new EP Epoch abstraction for node " + epIndexEPEpoch); // can be created by both leader and nod-leader node
            EPAbstraction epAbstraction = new EPAbstraction(epIndexEPEpoch);
            epAbstractions.put(epIndexEPEpoch, epAbstraction);
        }
        return epAbstractions.get(epIndexEPEpoch);
    }

    public static EPAbstraction getAndCreateEpAbstraction(String epIndexEPEpoch, EPState epState){
        if(epAbstractions.get(epIndexEPEpoch) == null) {
            logger.info(DistributedSystem.createNewInstance().getSystemId() + "/" + MyselfNode.createNewInstance().getNameNode() +
                    ": Create new EP Epoch abstraction for the leader node " + epIndexEPEpoch); // can be created by both leader and nod-leader node
            EPAbstraction epAbstraction = new EPAbstraction(epState, epIndexEPEpoch);
            epAbstractions.put(epIndexEPEpoch, epAbstraction);
        } /*else {
            assert false; // shouldn t exist
        }*/
        return epAbstractions.get(epIndexEPEpoch);
    }

    public static UCAbstraction getUcAbstraction(String topicConsensus){
        if(ucAbstractions.get(topicConsensus) == null){
            UCAbstraction ucAbstraction = new UCAbstraction();
            ucAbstractions.put(topicConsensus, ucAbstraction);
        }

        return ucAbstractions.get(topicConsensus);
    }
}
