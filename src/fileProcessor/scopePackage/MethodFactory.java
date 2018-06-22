package fileProcessor.scopePackage;

import fileProcessor.variblePackage.Variable;
import fileProcessor.variblePackage.VariableException;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MethodFactory {

    private static final int METHOD_PLACE_IN_SCOPE = 1;
    private File file;
    private Method currentMethod;
    private ArrayList<String> methodsCalls=new ArrayList<>();
    /**
     * constructor
     * @param file
     */
    public MethodFactory(File file){
        this.file = file;
    }

    /**
     * this method is called when we want to create new method
     * @param line
     * @throws VariableException
     */
    public void createMethod(String line) throws VariableException {
        String methodName = getName(line);
        HashMap<String, Variable> variables = getVariables(line);
        Method method=new Method(variables, methodName);
            this.file.addMethod(method);
            this.currentMethod=method;
            this.file.addScope(this.currentMethod);
    }

    /**
     * this method called when we call a method
     * @param line
     * @throws ScopeException
     */
    public void methodCall(String line) throws ScopeException {
        String[] sline = sliceLine(line);
        if(file.getMethods().containsKey(sline[0])){
            String[] vars = sline[1].split(",");
            for(int i = 0; i < vars.length; i++){
                vars[i] = vars[i].replaceAll("\\s+", "");
            }
            for(String var: vars){
                if(!file.isVariableAssigned(var)){
                    throw new ScopeException("variable " + var + " is not assigned!");
                }
            }
        }
        else{
            throw new ScopeException("Method " + sline[0] + " is undeclared");
        }
    }

    /**
     * slices line and returns array size 2:
     * one with method name, and one with variables
     * @param line
     * @return
     */
    private String[] sliceLine(String line) {
        String[] sline = new String[2];
        String s = line.split("\\)")[0];
        String[] spl = s.split("\\(");
        sline[0] = spl[0].split("\\s+")[1];
        sline[1] = spl[1];
        return sline;
    }

    /**
     * returns name of the method
     * @param line
     * @return
     */
    private String getName(String line){
        return sliceLine(line)[0];
    }

    /**
     * returns hashmap with variables in method scope
     * @param line
     * @return
     * @throws VariableException
     */
    private HashMap<String, Variable> getVariables(String line) throws VariableException {
        String varLine = sliceLine(line)[1];
        String[] splitVars = varLine.split(",");
        LinkedList<String[]> strings = new LinkedList<String[]>();
        for(String s: splitVars){
            strings.add(s.split("\\s+"));
        }
        HashMap<String, Variable> variables = new HashMap<>(strings.size());
        for(String[] s: strings){
            boolean isFinal = false;
            int index = 0;
            if(s.length == 3){
                isFinal = true;
                index = 1;
            }
            Variable v = new Variable(s[index], s[index+1], (String) null, isFinal);
            variables.put(v.getName(), v);
        }
        return variables;
    }

    boolean methodExistInHash(String methodName){
        return this.file.getMethods().containsKey(methodName);
    }

    public void methodReturn(){
        if (file.getScopes().size()== METHOD_PLACE_IN_SCOPE+1&&currentMethod!=null){
            currentMethod.setReturn();
        }
    }
    public boolean cheakMethodCalls(){
        for (String line:methodsCalls) {
            if (!methodExistInHash(line)){
                return false;
            }
        }
        return true;
    }
    public void addMethodCall(String line){
        methodsCalls.add(line);
    }
//    boolean methodExistInHash(String methodName){
//        return this.file.getMethods().containsKey(methodName);
//    }
//
//    public void setSecondTime() {
//        isSecondTime = true;
//    }

}
