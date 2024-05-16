package processing.factoryAbstractizations;

import abstractizations.AbstractionUtils;
import abstractizations.MetaInfoMessage;
import networking.Message;
import processing.CatalogueAbstractions;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class ManageEventUCFactory extends ManageEventAbstractFactory{

    private static ManageEventUCFactory manageEventUCFactory = null;

    private ManageEventUCFactory(){

    }

    public static ManageEventUCFactory createNewInstance(){
        if(manageEventUCFactory == null){
            manageEventUCFactory = new ManageEventUCFactory();
        }

        return manageEventUCFactory;
    }

    private String getTopicConsensus(Message message){
        String abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getToAbstractionId());
        // The message is not triggered by us, it means that we have received it
        if(abstractionAlgorithm == null){
            abstractionAlgorithm = AbstractionUtils.getCurrentAbstraction(message.getFromAbstractionId());
        }

        String topicConsensus = AbstractionUtils.extractTopicConsensusFromUC(abstractionAlgorithm);
        return topicConsensus;
    }

    @Override
    protected List<Message> processEvent(Message message) {
        String topicConsensus = getTopicConsensus(message);
        List<networking.Message> messageList = new ArrayList<>();
        MetaInfoMessage metaInfoMessage = MessageUtils.extractMetaDataFromMessage(message);
        Message.Type typeMessage = message.getType();

        if(typeMessage.equals(Message.Type.UC_PROPOSE)){
            networking.Message outcome = CatalogueAbstractions.getUcAbstraction(topicConsensus).handlingPropose(message.getUcPropose(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.EC_START_EPOCH)){
            networking.Message outcome = CatalogueAbstractions.getUcAbstraction(topicConsensus).handlingStartEpoch(message.getEcStartEpoch(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.EP_ABORTED)){
            networking.Message outcome = CatalogueAbstractions.getUcAbstraction(topicConsensus).handlingAborted(message.getEpAborted(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.EP_DECIDE)){
            networking.Message outcome = CatalogueAbstractions.getUcAbstraction(topicConsensus).handlingDecide(message.getEpDecide(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        return messageList;
    }
}
