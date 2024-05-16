package processing.factoryAbstractizations;

import abstractizations.MetaInfoMessage;
import networking.Message;
import org.apache.log4j.Logger;
import processing.CatalogueAbstractions;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class ManageEventEPFDFactory extends ManageEventAbstractFactory{

    private static final Logger logger = Logger.getLogger(ManageEventEPFDFactory.class.getName());

    private static ManageEventEPFDFactory manageEventEPFDFactory = null;

    private ManageEventEPFDFactory(){

    }

    public static ManageEventEPFDFactory createNewInstance(){
        if(manageEventEPFDFactory == null){
            manageEventEPFDFactory = new ManageEventEPFDFactory();
        }

        return manageEventEPFDFactory;
    }

    @Override
    protected List<networking.Message> processEvent(Message message) {
        List<networking.Message> messageList = new ArrayList<>();
        MetaInfoMessage metaInfoMessage = MessageUtils.extractMetaDataFromMessage(message);
        Message.Type typeMessage = message.getType();

        logger.info("Deliver the message of type: " + typeMessage + " to EPFD");

        if(typeMessage.equals(Message.Type.PL_DELIVER)){
            networking.Message outcome = CatalogueAbstractions.getEpfdAbstraction().handlingPLDeliver(message.getPlDeliver(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        return messageList;
    }
}
