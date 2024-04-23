package model;

import java.util.HashMap;
import java.util.UUID;

public class MyselfNode extends ProcessNode {

    private static MyselfNode myselfNode = null;

    private HashMap<String, String> releasedLockRegisters = new HashMap<>();

    private MyselfNode(String host, Integer port, String owner, Integer index, Integer rank) {
        super(host, port, owner, index, rank);
    }

    public static MyselfNode createNewInstance(){
        if(myselfNode == null) {
            return null;
        }
        return myselfNode;
    }

    public static MyselfNode createNewInstance(String host, Integer port, String owner, Integer index, Integer rank){
        if(myselfNode == null){
            myselfNode = new MyselfNode(host, port, owner, index, rank);
        }
        return myselfNode;
    }

    public void registerReleaseLockRegister(String uuid, String idAbstraction){
        releasedLockRegisters.put(uuid, idAbstraction);
    }

    public void removeRegistrationReleaseLockRegister(String uuid){
        releasedLockRegisters.remove(uuid);
    }

    public HashMap<String, String> getReleasedLockRegisters() {
        return releasedLockRegisters;
    }

    public void setReleasedLockRegisters(HashMap<String, String> releasedLockRegisters) {
        this.releasedLockRegisters = releasedLockRegisters;
    }
}

