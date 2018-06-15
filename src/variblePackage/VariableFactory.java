package variblePackage;

import com.sun.org.apache.xpath.internal.operations.VariableSafeAbsRef;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableFactory {

    private static VariableFactory instance = new VariableFactory();

    public static VariableFactory getInstance() {
        return instance;
    }

    private VariableFactory(){

    }

    /**
     * variable creator
     * @param line a code line
     * @return a variable object fits the line
     * @throws VariableException.NoVariableNameException if the var name illegal
     * @throws VariableException.TypeNotFoundException if the var type illegal
     */
    public Variable createVariable(String line) throws VariableException.NoVariableNameException,
            VariableException.TypeNotFoundException {
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
     * @param foundMatches
     * @return
     * @throws VariableException.NoVariableNameException
     * @throws VariableException.TypeNotFoundException
     */
    private Variable getVariableFromList(List<String> foundMatches)
            throws VariableException.NoVariableNameException, VariableException.TypeNotFoundException {
        boolean isFinal = false, isValue = false;
        int valStart;
        String vType, vName;
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
            isValue = true;
        }
        return new Variable(vType, vName, isValue, isFinal);
    }

    public static void main(String[] args) {
//        Variable v = createVariable("ab=BDNbbdj  ddsd  dvd0");
        System.out.println("\\d");
    }
}
