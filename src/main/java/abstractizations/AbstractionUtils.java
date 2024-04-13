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
}
