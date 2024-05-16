package processing.factoryAbstractizations;

import abstractizations.MetaInfoMessage;
import abstractizations.PLAbstraction;
import networking.Message;
import processing.CatalogueAbstractions;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class ManageEventPLFactory extends ManageEventAbstractFactory{

    private static ManageEventPLFactory manageEventPLFactory = null;

    private ManageEventPLFactory(){

    }

    // It is accessed by both ProcessEventQueueThread and processHeartBeatQueueThread
    public synchronized static ManageEventPLFactory createNewInstance(){
        if(manageEventPLFactory == null){
            manageEventPLFactory = new ManageEventPLFactory();
        }

        return manageEventPLFactory;
    }

    @Override
    protected List<networking.Message> processEvent(networking.Message message) {
        List<networking.Message> messageList = new ArrayList<>();
        MetaInfoMessage metaInfoMessage = MessageUtils.extractMetaDataFromMessage(message);
        Message.Type typeMessage = message.getType();

        if(typeMessage.equals(Message.Type.PL_SEND)){
            CatalogueAbstractions.getPlAbstraction().handlingPLSend(message.getPlSend(), metaInfoMessage);
        }

        if(typeMessage.equals(Message.Type.NETWORK_MESSAGE)){ // <sl, Deliver>
            networking.Message outcome = CatalogueAbstractions.getPlAbstraction().handlingSLDeliver(message.getNetworkMessage(), metaInfoMessage);
            if(outcome != null) {
                messageList.add(outcome);
            }
        }

        return messageList;
    }
}
