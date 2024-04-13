package processing;

import abstractizations.AppAbstraction;
import abstractizations.BebAbstraction;
import abstractizations.PLAbstraction;
import model.DistributedSystem;
import servers.SystemOutputServer;

public class CatalogueAbstractions {

    private static PLAbstraction plAbstraction = null;

    private static BebAbstraction bebAbstraction = null;

    private static AppAbstraction appAbstraction = null;

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
}
