package processing.factoryAbstractizations;

import abstractizations.AbstractionUtils;
import networking.Message;
import utils.IntfConstants;

import java.util.List;

public abstract class ManageEventAbstractFactory {

    public List<Message> handleEvent(networking.Message message){
        return processEvent(message);
    }

    protected abstract List<networking.Message> processEvent(networking.Message message);
}
