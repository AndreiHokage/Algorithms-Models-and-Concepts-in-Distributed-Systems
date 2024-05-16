package abstractizations;

public class AbstractionUtils {

    public static String removeCurrentAbstraction(String abstraction){
        int index = abstraction.lastIndexOf('.');
        if(index == -1){
            System.out.println("Couldn't extract the next abstraction from " + abstraction);
            return null;
        }

        String nextAbstraction = abstraction.substring(0, index);
        return nextAbstraction;
    }

    public static String getCurrentAbstraction(String abstraction){
        if(abstraction == null || abstraction.isEmpty()){
            return null;
        }

        String[] parts = abstraction.split("\\.");
        String answer = parts[parts.length - 1];
        return answer;
    }

    public static String extractRegisterIDFromNNAR(String nnarAbstractisation){
        int index_left = nnarAbstractisation.indexOf('[');
        int index_right = nnarAbstractisation.indexOf(']');

        String nnarRegister = nnarAbstractisation.substring(index_left + 1, index_right);
        return nnarRegister;
    }

    public static String extractEpochIndexIDFromEp(String epAbstractisation){
        int index_left = epAbstractisation.indexOf('[');
        int index_right = epAbstractisation.indexOf(']');

        String epochIdx = epAbstractisation.substring(index_left + 1, index_right);
        return epochIdx;
    }

    public static String extractTopicConsensusFromUC(String ucAbstractisation){
        int index_left = ucAbstractisation.indexOf('[');
        int index_right = ucAbstractisation.indexOf(']');

        String topicConsensus = ucAbstractisation.substring(index_left + 1, index_right);
        return topicConsensus;
    }
}
