package model;

public class ProcessNode {

    private String host;

    private Integer port;

    private String owner;

    private Integer index;

    private Integer rank;

    public ProcessNode(String host, Integer port) {
        this.host = host;
        this.port = port;
        this.owner = null;
        this.index = null;
        this.rank = null;
    }

    public ProcessNode(String host, Integer port, String owner, Integer index) {
        this.host = host;
        this.port = port;
        this.owner = owner;
        this.index = index;
        this.rank = null;
    }

    public ProcessNode(String host, Integer port, String owner, Integer index, Integer rank) {
        this.host = host;
        this.port = port;
        this.owner = owner;
        this.index = index;
        this.rank = rank;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getNameNode(){
        return owner + "-" + index.toString();
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * A PROCESS IS UNIQUELY IDENTIFIED BY ITS IP AND PORT
     * @return
     */
    public String getUUIDProcessNode(){
        return host + "-" + port;
    }

    @Override
    public String toString() {
        return "ProcessNode{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", owner='" + owner + '\'' +
                ", index=" + index +
                ", rank=" + rank +
                '}';
    }
}
