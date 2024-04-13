package processing.factoryAbstractizations;

import abstractizations.AppAbstraction;
import abstractizations.MetaInfoMessage;
import networking.Message;
import processing.CatalogueAbstractions;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class ManageEventAPPFactory extends ManageEventAbstractFactory {

    private static ManageEventAPPFactory manageEventAPPFactory = null;

    private ManageEventAPPFactory(){

    }

    public static ManageEventAPPFactory createNewInstance(){
        if(manageEventAPPFactory == null){
            manageEventAPPFactory = new ManageEventAPPFactory();
        }

        return manageEventAPPFactory;
    }

    @Override
    protected List<networking.Message> processEvent(networking.Message message) {
        List<networking.Message> messageList = new ArrayList<>();
        MetaInfoMessage metaInfoMessage = MessageUtils.extractMetaDataFromMessage(message);
        Message.Type typeMessage = message.getType();

        if(typeMessage.equals(Message.Type.PL_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getAppAbstraction().handlingPLDeliver(message.getPlDeliver(), metaInfoMessage);
            if(outcome != null) {
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.BEB_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getAppAbstraction().handlingBebDeliver(message.getBebDeliver(), metaInfoMessage);
            if(outcome != null) {
                messageList.add(outcome);
            }
        }

        return messageList;
    }
}
