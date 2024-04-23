package processing;

import abstractizations.AppAbstraction;
import abstractizations.BebAbstraction;
import abstractizations.NnarAbstraction;
import abstractizations.PLAbstraction;
import model.DistributedSystem;
import model.MyselfNode;
import org.apache.log4j.Logger;
import servers.SystemOutputServer;

import java.util.HashMap;

public class CatalogueAbstractions {

    private static final Logger logger = Logger.getLogger(CatalogueAbstractions.class.getName());

    private static PLAbstraction plAbstraction = null;

    private static BebAbstraction bebAbstraction = null;

    private static AppAbstraction appAbstraction = null;

    private static HashMap<String, NnarAbstraction> nnarAbstractions = new HashMap<>(); // store instances

    public static PLAbstraction getPlAbstraction() {
        if(plAbstraction == null){
            plAbstraction = PLAbstraction.createNewInstance(DistributedSystem.createNewInstance().getSystemOutputServer());
        }
        return plAbstraction;
    }

    public static BebAbstraction getBebAbstraction() {
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
}
