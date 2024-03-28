package model;

public class ProcessNode {

    private String host;

    private Integer port;

    private String owner;

    private Integer index;

    public ProcessNode(String host, Integer port, String owner, Integer index) {
        this.host = host;
        this.port = port;
        this.owner = owner;
        this.index = index;
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
}
