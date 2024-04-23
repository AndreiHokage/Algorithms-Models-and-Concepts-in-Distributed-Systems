package abstractizations;

import model.MyselfNode;

public class NnarDefinedValue {

    private Integer ts;

    private Integer wr;

    private Integer val;

    public NnarDefinedValue() {
        ts = 0;
        // very important; Each time we create a REGISTER, NOT A SESSION, a new NnarDefinedValue is instantiated
        wr = MyselfNode.createNewInstance().getRank();
        val = Integer.MIN_VALUE;
    }

    public NnarDefinedValue(Integer ts, Integer wr, Integer val) {
        this.ts = ts;
        this.wr = wr;
        this.val = val;
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public Integer getWr() {
        return wr;
    }

    public void setWr(Integer wr) {
        this.wr = wr;
    }

    public Integer getVal() {
        return val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }

    public Boolean isDefined(){
        return !val.equals(Integer.MIN_VALUE);
    }

    public Integer compareTo(NnarDefinedValue other){
        if(this.getTs().compareTo(other.getTs()) > 0){
            return 1;
        }

        if(this.getWr().compareTo(other.getWr()) > 0){
            return 1;
        } // this is greater if it has the ts or the rank greater

        return -1;
    }

    @Override
    public String toString() {
        return "NnarDefinedValue{" +
                "ts=" + ts +
                ", wr=" + wr +
                ", val=" + val +
                '}';
    }
}
