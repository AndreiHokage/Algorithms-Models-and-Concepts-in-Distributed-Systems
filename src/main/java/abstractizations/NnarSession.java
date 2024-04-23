package abstractizations;

import java.util.ArrayList;
import java.util.List;

public class NnarSession {

    private Integer acks;

    private Integer writeval;

    private List<NnarDefinedValue> readList;

    private Integer readVal;

    private Boolean reading;

    public NnarSession(){
        acks = 0;
        writeval = Integer.MIN_VALUE;
        readList = new ArrayList<>();
        readVal = Integer.MIN_VALUE;
        reading = false;
    }

    public NnarSession(Integer acks, Integer writeval, List<NnarDefinedValue> readList, Integer readVal, Boolean reading) {
        this.acks = acks;
        this.writeval = writeval;
        this.readList = readList;
        this.readVal = readVal;
        this.reading = reading;
    }

    public Integer getAcks() {
        return acks;
    }

    public void setAcks(Integer acks) {
        this.acks = acks;
    }

    public Integer getWriteval() {
        return writeval;
    }

    public void setWriteval(Integer writeval) {
        this.writeval = writeval;
    }

    public List<NnarDefinedValue> getReadList() {
        return readList;
    }

    public void setReadList(List<NnarDefinedValue> readList) {
        this.readList = readList;
    }

    public Integer getReadVal() {
        return readVal;
    }

    public void setReadVal(Integer readVal) {
        this.readVal = readVal;
    }

    public Boolean getReading() {
        return reading;
    }

    public void setReading(Boolean reading) {
        this.reading = reading;
    }

    public Boolean isWriteValueDefined(){
        return !writeval.equals(Integer.MIN_VALUE);
    }

    public Boolean isReadValueDefined(){
        return !readVal.equals(Integer.MIN_VALUE);
    }

    public NnarDefinedValue getBiggestValueFromAllReadings(){
        NnarDefinedValue answer = readList.get(0);
        for(NnarDefinedValue nnarDefinedValue: readList){
            if(answer.compareTo(nnarDefinedValue) <= 0){ // answer <= nnarDefinedValue
                answer = nnarDefinedValue;
            }
        }

        return answer;
    }

    public void clearReadList(){
        this.readList.clear();
    }

    @Override
    public String toString() {
        return "NnarSession{" +
                "acks=" + acks +
                ", writeval=" + writeval +
                ", readList=" + readList +
                ", readVal=" + readVal +
                ", reading=" + reading +
                '}';
    }
}
