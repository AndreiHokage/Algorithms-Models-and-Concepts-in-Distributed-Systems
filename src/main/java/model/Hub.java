package model;

import utils.IntfConstants;

public class Hub extends ProcessNode{

    private static final Integer HUB_INDEX = -1;

    private static final Integer HUB_RANK = -1;

    public Hub(String host, Integer port) {
        super(host, port, IntfConstants.HUB_OWNER, HUB_INDEX, HUB_RANK);
    }

    public Hub(String host, Integer port, String owner, Integer index) {
        super(host, port, owner, index, HUB_RANK);
    }
}
