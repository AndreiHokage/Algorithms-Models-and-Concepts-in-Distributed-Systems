package abstractizations;

public class EPState {

    private Integer epochTimestamp;

    private Integer valueEpoch;

    public EPState(){
        epochTimestamp = 0;
        valueEpoch = Integer.MIN_VALUE;
    }

    public EPState(Integer epochTimestamp, Integer valueEpoch) {
        this.epochTimestamp = epochTimestamp;
        this.valueEpoch = valueEpoch;
    }

    public Integer getEpochTimestamp() {
        return epochTimestamp;
    }

    public void setEpochTimestamp(Integer epochTimestamp) {
        this.epochTimestamp = epochTimestamp;
    }

    public Integer getValueEpoch() {
        return valueEpoch;
    }

    public void setValueEpoch(Integer valueEpoch) {
        this.valueEpoch = valueEpoch;
    }
}
