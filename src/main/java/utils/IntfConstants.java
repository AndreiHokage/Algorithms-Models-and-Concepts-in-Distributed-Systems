package utils;

import model.DistributedSystem;

public interface IntfConstants {

    public static final String HUB_OWNER = "hub";

    public static final String PL_ABS = "pl";

    public static final String BEB_ABS = "beb";

    public static final String NNAR_ABS = "nnar";

    public static final String APP_ABS = "app";

    public static final String SYSTEM_IN = "IN";

    public static final String SYSTEM_OUT = "OUT";

    public static final Integer NUM_PROC = DistributedSystem.createNewInstance().getNumberProcessesSystem(); // 5 + 1 = 6 (N, N)
}
