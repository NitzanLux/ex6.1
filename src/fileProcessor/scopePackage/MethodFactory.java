package fileProcessor.scopePackage;

import fileProcessor.variblePackage.Variable;
import fileProcessor.variblePackage.VariableException;

import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedList;

public class MethodFactory {

    private File file;
    private boolean isSecondTime = false;

    public MethodFactory(File file){
        this.file = file;
    }

    public void createMethod(String line) throws ScopeException.NoParentException, VariableException {
        String methodName = getName(line);
        HashMap<String, Variable> variables = getVariables(line);
        Method method=new Method(variables,methodName);
        if(!isSecondTime){
            this.file.addMethod(method);
        }
        else{
            this.file.addScope(method);
        }
    }
    public void methodCall(String line){
        //////////
            ///
            ///
            ///
            /// todo
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

    boolean methodExistInHash(String methodName){
        return this.file.getMethods().containsKey(methodName);
    }

    public void setSecondTime() {
        isSecondTime = true;
    }

}
