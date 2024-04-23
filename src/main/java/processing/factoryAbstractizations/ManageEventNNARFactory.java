package processing.factoryAbstractizations;

import abstractizations.AbstractionUtils;
import abstractizations.MetaInfoMessage;
import networking.Message;
import processing.CatalogueAbstractions;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class ManageEventNNARFactory extends ManageEventAbstractFactory{

    private static ManageEventNNARFactory manageEventNNARFactory = null;

    private ManageEventNNARFactory(){

    }

    public static ManageEventNNARFactory createNewInstance(){
        if(manageEventNNARFactory == null){
            manageEventNNARFactory = new ManageEventNNARFactory();
        }

        return manageEventNNARFactory;
    }

    private String getRegistrationName(Message message){
        String abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getToAbstractionId());
        // The message is not triggered by us, it means that we have received it
        if(abstractionAlgorithm == null){
            abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getFromAbstractionId());
        }

        String nnarRegister = AbstractionUtils.extractRegisterIDFromNNAR(abstractionAlgorithm);
        return nnarRegister;
    }

    @Override
    protected List<Message> processEvent(Message message) {
        String nnarRegister = getRegistrationName(message);
        List<networking.Message> messageList = new ArrayList<>();
        MetaInfoMessage metaInfoMessage = MessageUtils.extractMetaDataFromMessage(message);
        Message.Type typeMessage = message.getType();

        if(typeMessage.equals(Message.Type.NNAR_WRITE)){
            networking.Message outcome = CatalogueAbstractions.getNnarAbstraction(nnarRegister).handlingNnarWrite(message.getNnarWrite(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.NNAR_READ)){
            networking.Message outcome = CatalogueAbstractions.getNnarAbstraction(nnarRegister).handlingNnarRead(message.getNnarRead(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.BEB_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getNnarAbstraction(nnarRegister).handlingBebDeliver(message.getBebDeliver(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.PL_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getNnarAbstraction(nnarRegister).handlingPLDeliver(message.getPlDeliver(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        return messageList;
    }






}
