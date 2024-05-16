package processing.factoryAbstractizations;

import abstractizations.MetaInfoMessage;
import networking.Message;
import processing.CatalogueAbstractions;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class ManageEventECFactory extends ManageEventAbstractFactory {

    private static ManageEventECFactory manageEventECFactory = null;

    private ManageEventECFactory(){

    }

    public static ManageEventECFactory createNewInstance(){
        if(manageEventECFactory == null){
            manageEventECFactory = new ManageEventECFactory();
        }

        return manageEventECFactory;
    }

    @Override
    protected List<Message> processEvent(Message message) {
        List<networking.Message> messageList = new ArrayList<>();
        MetaInfoMessage metaInfoMessage = MessageUtils.extractMetaDataFromMessage(message);
        Message.Type typeMessage = message.getType();

        if(typeMessage.equals(Message.Type.ELD_TRUST)){
            networking.Message outcome = CatalogueAbstractions.getEcAbstraction().handleTrust(message.getEldTrust(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.BEB_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getEcAbstraction().handlingBebDeliver(message.getBebDeliver(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.PL_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getEcAbstraction().handlingPlDeliver(message.getPlDeliver(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        return messageList;
    }
}
