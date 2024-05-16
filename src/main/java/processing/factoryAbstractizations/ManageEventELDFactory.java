package processing.factoryAbstractizations;

import abstractizations.MetaInfoMessage;
import networking.Message;
import processing.CatalogueAbstractions;
import utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class ManageEventELDFactory extends ManageEventAbstractFactory {

    private static ManageEventELDFactory manageEventELDFactory = null;

    private ManageEventELDFactory(){

    }

    public synchronized static ManageEventELDFactory createNewInstance(){
        if(manageEventELDFactory == null){
            manageEventELDFactory = new ManageEventELDFactory();
        }

        return manageEventELDFactory;
    }

    @Override
    protected List<networking.Message> processEvent(networking.Message message) {
        List<networking.Message> messageList = new ArrayList<>();
        MetaInfoMessage metaInfoMessage = MessageUtils.extractMetaDataFromMessage(message);
        Message.Type typeMessage = message.getType();

        if(typeMessage.equals(Message.Type.EPFD_SUSPECT)){
            networking.Message outcome = CatalogueAbstractions.getEldAbstraction().handlingSuspect(message.getEpfdSuspect(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        if(typeMessage.equals(Message.Type.EPFD_RESTORE)){
            networking.Message outcome = CatalogueAbstractions.getEldAbstraction().handlingRestore(message.getEpfdRestore(), metaInfoMessage);
            if(outcome != null){
                messageList.add(outcome);
            }
        }

        return messageList;
    }
}
