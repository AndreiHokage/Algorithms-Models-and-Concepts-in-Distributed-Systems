package processing.factoryAbstractizations;

import abstractizations.AbstractionUtils;
import abstractizations.MetaInfoMessage;
import networking.Message;
import org.apache.log4j.Logger;
import processing.CatalogueAbstractions;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class ManageEventEPFactory extends ManageEventAbstractFactory{

    private static final Logger logger = Logger.getLogger(ManageEventEPFactory.class.getName());

    private static ManageEventEPFactory manageEventEPFactory = null;

    public ManageEventEPFactory(){

    }

    public static ManageEventEPFactory createNewInstance(){
        if(manageEventEPFactory == null){
            manageEventEPFactory = new ManageEventEPFactory();
        }

        return manageEventEPFactory;
    }

    private String getEpochIndex(Message message){
        String abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getToAbstractionId());
        // The message is not triggered by us, it means that we have received it
        if(abstractionAlgorithm == null){
            abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getFromAbstractionId());
        }

        String nnarRegister = AbstractionUtils.extractEpochIndexIDFromEp(abstractionAlgorithm);
        return nnarRegister;
    }

    @Override
    protected List<Message> processEvent(Message message) {
        String indexEPepoch = getEpochIndex(message);
        List<networking.Message> messageList = new ArrayList<>();
        MetaInfoMessage metaInfoMessage = MessageUtils.extractMetaDataFromMessage(message);
        Message.Type typeMessage = message.getType();

        if(typeMessage.equals(Message.Type.EP_PROPOSE)){
            networking.Message outcome = CatalogueAbstractions.getEpAbstraction(indexEPepoch).handlePropose(message.getEpPropose(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.BEB_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getEpAbstraction(indexEPepoch).handleBebDeliver(message.getBebDeliver(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.PL_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getEpAbstraction(indexEPepoch).handlingPlDeliver(message.getPlDeliver(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.EP_ABORT)){
            networking.Message outcome = CatalogueAbstractions.getEpAbstraction(indexEPepoch).handlingAbort(message.getEpAbort(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        return messageList;
    }
}
