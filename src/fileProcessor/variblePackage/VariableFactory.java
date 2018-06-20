package fileProcessor.variblePackage;
import fileProcessor.scopePackage.File;
import fileProcessor.scopePackage.Scope;
import fileProcessor.scopePackage.ScopeException;

import java.util.Arrays;
import java.util.LinkedList;

public class VariableFactory {

    private File file;

    private static final String COMMA = ",", EQUAL = "=", SEMICOL = ";", FINAL = "final";
    private static final char SPACE = ' ';

    private Scope currentScope = file.getCurrentScope();

//    public Variable[] getVariables(String line, boolean isReassignment) throws VariableException {
//       if(isReassignment){
//           return reAssignment(line);
//       }
//       return makeAssignment(line);
//    }

    private VariableFactory(){

    }

    public void setCurrentStack(LinkedList<Scope> stack){
        currentStack = stack;
    }

    /**
     * @param line code line
     * @return a variables array containing all variables in line.
     * @throws VariableException
     */
    public void makeAssignment(String line) throws VariableException, ScopeException {
        boolean isFinal = false;
        LinkedList<String> strings = getListedVariables(line);
        if(strings.get(0).equals(FINAL)){
            // if final declaration.
            isFinal = true;
            strings.remove(0);
        }
        Variable[] vars = getVariables(isFinal, strings);
        for(Variable var: vars){
            currentStack.getFirst().addVariable(var);
        }
    }

    private LinkedList<String> getListedVariables(String line){
        line = line.split(SEMICOL)[0];
        LinkedList<String> strings = new LinkedList<>();
        int start = 0, end;
        boolean b = false;
        char[] charray = line.toCharArray();
        for(int i = 0; i < charray.length; i++){
            if(charray[i] != SPACE && !b){
                start = i;
                b = true;
            }
            else if (charray[i] == SPACE && b){
                end = i;
                b = false;
                strings.add(String.copyValueOf(Arrays.copyOfRange(charray, start, end)));
            }
        }
        return strings;
    }

    /**
     *
     * @param isFinal
     * @param strings
     * @return
     * @throws VariableException
     */
    private Variable[] getVariables(boolean isFinal, LinkedList<String> strings)
            throws VariableException {
        String vType = strings.get(0);
        strings.remove(0);
        String conStr = "";
        for (String s: strings){
            conStr = conStr.concat(s);
        }
        String[] splitLine = conStr.split(COMMA);
        //splitting the line by commas
        LinkedList<String> names = new LinkedList<>();
        LinkedList<String> values = new LinkedList<>();
        for (String s: splitLine){
            if(s.lastIndexOf(EQUAL) != s.indexOf(EQUAL)){
                throw new VariableException.IllegalVariableNameException();
            }
            String[] s1 = s.split(EQUAL);
            names.add(s1[0]);
            if(s1.length == 2){
                values.add(s1[1]);
            }
            else{
                values.add(null);
            }
        }
        Variable[] variables = new Variable[names.size()];
        for(int i = 0; i < variables.length; i++){
            variables[i] = new Variable(vType, names.get(i), values.get(i), isFinal);
        }
        return variables;
    }

    private Variable[] reAssignment(String line) throws VariableException {
        LinkedList<String> strings = getListedVariables(line);
        Variable[] variables = getVariables(false, strings);
        for(Variable variable: variables){
            if(!isLegalReAssignment(variable)){
                throw new VariableException.NoVariableNameException();
            }
        }
        return variables;
    }

    /**
     * insert varibels to current scop hashmap
     */
    public void insertVariabels(String line){

    }
    // TODO: in case of reassignment loop running over all scopes in stack,
    // todo - checking for declaration of variable before, if its final, its type and if it exists
    private boolean isLegalReAssignment(Variable variable){
        String name = variable.getName();
        Scope current = currentStack.getFirst();
        while (current != null){
            if (current.getVariables().containsKey(name)){
                Variable originalVariable = current.getVariables().get(name);
                return (originalVariable.getVariableType().equals(variable.getVariableType())
                        && !originalVariable.isFinal());
            }
        }
        return false;
    }
//
//    public static void main(String[] args) throws VariableException {
//        String line = "int a = 98;";
//        Variable[] vs = makeAssignment(line);
//        for(Variable v: vs){
//            System.out.println(v.getName());
//        }
//    }
}
