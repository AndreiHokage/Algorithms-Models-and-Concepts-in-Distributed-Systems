package abstractizations;

import java.util.ArrayList;
import java.util.List;

public class EPSession {

    private EPState epState = null;

    private Integer tmpVal = null;

    private List<EPState> statesList = new ArrayList<>();

    private Integer accepted = 0;

    public EPSession(){
        this.epState = new EPState();
        this.tmpVal = Integer.MIN_VALUE;
        this.statesList = new ArrayList<>();
        this.accepted = 0;
    }

    public EPSession(EPState state){
        this.epState = state;
        this.tmpVal = Integer.MIN_VALUE;
        this.statesList = new ArrayList<>();
        this.accepted = 0;
    }

}
