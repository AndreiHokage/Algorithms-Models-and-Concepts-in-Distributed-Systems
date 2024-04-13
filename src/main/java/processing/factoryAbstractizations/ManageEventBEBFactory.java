package processing.factoryAbstractizations;

import abstractizations.BebAbstraction;
import abstractizations.MetaInfoMessage;
import networking.Message;
import processing.CatalogueAbstractions;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class ManageEventBEBFactory extends ManageEventAbstractFactory{

    private static ManageEventBEBFactory manageEventBEBFactory = null;

    private ManageEventBEBFactory(){

    }

    public static ManageEventBEBFactory createNewInstance(){
        if(manageEventBEBFactory == null){
            manageEventBEBFactory = new ManageEventBEBFactory();
        }

        return manageEventBEBFactory;
    }

    @Override
    protected List<networking.Message> processEvent(networking.Message message) {
        List<networking.Message> messageList = new ArrayList<>();
        MetaInfoMessage metaInfoMessage = MessageUtils.extractMetaDataFromMessage(message);
        Message.Type typeMessage = message.getType();

        if(typeMessage.equals(Message.Type.PL_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getBebAbstraction().handlingPLDeliver(message.getPlDeliver(), metaInfoMessage);
            if(outcome != null) {
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.BEB_BROADCAST)){
            List<networking.Message> plMessagesList =  CatalogueAbstractions.getBebAbstraction().handlingBebBroadcast(message.getBebBroadcast(), metaInfoMessage);
            for(networking.Message plSendMessage: plMessagesList){
                if(plSendMessage != null) {
                    messageList.add(plSendMessage);
                }
            }
        }

        return messageList;
    }
}
