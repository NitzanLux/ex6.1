package scopePackage;

import variblePackage.Variable;
import variblePackage.VariableException;

import java.util.HashMap;
import java.util.LinkedList;

public class MethodFactory {

    private static MethodFactory instance;

    private String line;

    private Method method;

    public static MethodFactory getInstance() {
        return instance;
    }

    private MethodFactory() throws VariableException {
        this.method = new Method(getVariables(), getName());
    }

    public void setLine(String line){
        this.line = line;
    }

    private String[] sliceLine() {
        String[] sline = new String[2];
        String s = line.split("\\)")[0];
        String[] spl = s.split("\\(");
        sline[0] = spl[0].split(" ")[1];
        sline[1] = spl[1];
        return sline;
    }

    private String getName(){
        return sliceLine()[0];
    }

    private HashMap getVariables() throws VariableException {
        String varLine = sliceLine()[1];
        String[] splitVars = varLine.split(",");
        LinkedList<String[]> strings = new LinkedList<String[]>();
        for(String s: splitVars){
            strings.add(s.split(" "));
        }
        HashMap<String, Variable> variables = new HashMap<>(strings.size());
        for(String[] s: strings){
            boolean isFinal = false;
            int index = 0;
            if(s.length == 3){
                isFinal = true;
                index = 1;
            }
            Variable v = new Variable(s[index], s[index+1], null, isFinal);
            variables.put(v.getName(), v);
        }
        return variables;
    }

    public static void main(String[] args) throws VariableException {
        String s = "static void make_cake(int milk, String eggs, boolean chocolate){";
    }
}
