package scopePackage;

import variblePackage.Variable;
import variblePackage.VariableException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class MethodFactory {

    private static MethodFactory instance=new MethodFactory(null, null);
    private boolean isMethodCall;
    private Scope papa;
    private Method method;

    public static MethodFactory getInstance() {
        return instance;
    }

    private MethodFactory(Method method, Scope papa){
        this.method = method;
        this.papa = papa;
    }

    public void setPapa(Scope papa) {
        this.papa = papa;
    }

    private Method createMethod(String line) throws ScopeException.NoParentException, VariableException {
        String methodName = getName(line);
        HashMap<String, Variable> variables = getVariables(line);
        Scope father = this.papa;
        return new Method(variables, methodName, father);
    }


    private String[] sliceLine(String line) {
        String[] sline = new String[2];
        String s = line.split("\\)")[0];
        String[] spl = s.split("\\(");
        sline[0] = spl[0].split(" ")[1];
        sline[1] = spl[1];
        return sline;
    }

    private String getName(String line){
        return sliceLine(line)[0];
    }

    private HashMap<String, Variable> getVariables(String line) throws VariableException {
        String varLine = sliceLine(line)[1];
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

    public void setMethod(String line) throws VariableException, ScopeException.NoParentException {
        this.method = createMethod(line);
    }
}
