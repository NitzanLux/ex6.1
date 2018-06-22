package fileProcessor.variblePackage;
import fileProcessor.scopePackage.File;
import fileProcessor.scopePackage.Scope;
import fileProcessor.scopePackage.ScopeException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class VariableFactory {

    private File file;

    private static final String COMMA = ",", EQUAL = "=", SEMICOL = ";", FINAL = "final";
    private static final char SPACE = ' ';

//    private Scope currentScope = file.getCurrentScope ();

    public VariableFactory(File file){
        this.file=file;
    }

    /**
     * @param line code line
     * @throws VariableException or scope exception in cases assignment is illegal
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
            file.getCurrentScope().addVariable(var);
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
     * gets the variables from assignment line
     * @param isFinal if is final
     * @param strings the strings after parsing the line
     * @return variables
     * @throws VariableException if variables are not okay
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

    /**
     * reassigns Variables
     * @param line code line
     * @throws VariableException if theres problem with variabales
     */
    public void reAssignment(String line) throws VariableException {
        LinkedList<String> strings = getListedVariables(line);
        Variable[] variables = getVariables(false, strings);
        for(Variable variable: variables){
            if(!isLegalReAssignment(variable)){
                throw new VariableException.NoVariableNameException();
            }
        }
    }

    /**
     * checks if variable assign is legal
     * @param variable
     * @return
     */
    private boolean isLegalReAssignment(Variable variable) {
        String name = variable.getName();
        for(Scope current: file.getScopes()){
            if (current.getVariables().containsKey(name)){
                Variable originalVariable = current.getVariables().get(name);
                if (originalVariable.getVariableType().equals(variable.getVariableType())
                        && !originalVariable.isFinal()){
                    originalVariable.setValueAssigned();
                    return true;
                }
                return false;
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
