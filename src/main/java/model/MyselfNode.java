package model;

public class MyselfNode extends ProcessNode {

    private static MyselfNode myselfNode = null;

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
}

