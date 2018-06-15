package variblePackage;
import scopePackage.Scope;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableFactory {

    private static VariableFactory instance = new VariableFactory();

    private static LinkedList<Scope> currentStack;

    public static VariableFactory getInstance() {
        return instance;
    }

    private VariableFactory(){

    }

    public void setCurrentStack(LinkedList<Scope> stack){
        currentStack = stack;
    }

    /**
     * variable creator
     * @param line a code line
     * @return a variable object fits the line
     * @throws VariableException if
     */
    public Variable createVariable(String line) throws VariableException {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(line);
        List<String> foundMatches = new ArrayList<>();
        while(matcher.find()){
            foundMatches.add(matcher.group());
        }
        return getVariableFromList(foundMatches);
    }

    /**
     *
     * @param foundMatches is
     * @return item
     * @throws VariableException if
     */
    private Variable getVariableFromList(List<String> foundMatches) throws VariableException {
        boolean isFinal = false;
        int valStart;
        String vType, vName, vValue = null;
        List<String> varsValues;
        if(foundMatches.get(0).equals("final")){
            isFinal = true;
            vType = foundMatches.get(1);
            valStart = 2;
        }
        else {
            vType = foundMatches.get(0);
            valStart = 1;
        }
        varsValues = foundMatches.subList(valStart, foundMatches.size());
        vName = varsValues.get(0);
        if(varsValues.size() == 2){
            vValue = varsValues.get(1);
        }
        return new Variable(vType, vName, vValue, isFinal);
    }

    private Variable[] multiAssignment(String line){
        //TODO: OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
        return null;
    }

    public static void main(String[] args) {
//        Variable v = createVariable("ab=BDNbbdj  ddsd  dvd0");
        System.out.println("\\d");
    }
}
