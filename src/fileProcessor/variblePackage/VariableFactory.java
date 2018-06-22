package fileProcessor.variblePackage;

import fileProcessor.scopePackage.File;
import fileProcessor.scopePackage.Scope;
import fileProcessor.scopePackage.ScopeException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class VariableFactory {

    private File file;

    private static final String COMMA = ",", EQUAL = "=", SEMICOLON = ";", FINAL = "final";
    private static final char SPACE = ' ';

//    private Scope currentScope = file.getCurrentScope ();

    public VariableFactory(File file) {
        this.file = file;
    }

    /**
     * @param line code line
     * @throws VariableException or scope exception in cases assignment is illegal
     */
    public void makeAssignment(String line) throws VariableException, ScopeException {
        LinkedList<String> strings = getListedVariables(line);
//        if (strings.get(0).equals(FINAL)) {
//            // if final declaration.
//            isFinal = true;
//            strings.remove(0);
//        }
        Variable[] vars = getVariables(strings);
        for (Variable var : vars) {
            if (var == null) {
                System.out.printf("");
            }
            file.getCurrentScope().addVariable(var);
        }
    }

    private LinkedList<String> getListedVariables(String line) {
        if (line.contains("(")) {
            line = line.substring(line.indexOf("("), line.indexOf(")"));
        }
        String[] variables = line.split("\\,|\\(|\\)|\\;");
        LinkedList<String> strings = new LinkedList<>();
        for (String variable : variables) {
            strings.addFirst(variable);
        }
        //todo we can send array and finnish with it
//        int start = 0, end;
//        boolean b = false;
//        char[] charray = line.toCharArray();
//        for(int i = 0; i < charray.length; i++){
//            if(charray[i] != SPACE && !b){
//                start = i;
//                b = true;
//            }
//            else if (charray[i] == SPACE && b){
//                end = i;
//                b = false;
//                strings.add(String.copyValueOf(Arrays.copyOfRange(charray, start, end)));
//            }
//        }
        return strings;
    }

    /**
     * gets the variables from assignment line
     *
     * @param strings the strings after parsing the line
     * @return variables
     * @throws VariableException if variables are not okay
     */
    private Variable[] getVariables(LinkedList<String> strings) throws VariableException {
        Variable[] variables = new Variable[strings.size()];
        int counter = 0;
        for (String variable : strings) {
            int firstIndex = 0;
            boolean isAssigned = false;
            if (variable.contains("=")) {
                isAssigned = true;
            }
            String[] variableData = variable.split("\\s+\\=?\\s*");
            String value = null;
            if (isAssigned) {
                value = variableData[variableData.length - 1];
            }
            boolean isItFinal = false;
            if (variableData[0].equals("final")) {
                isItFinal = true;
                firstIndex++;
            }
            if (isAssigned&&!VariableType.isType(value)) {
                if (variableToVaribleAssignmentLeagel(variableData[0 + firstIndex], value)) {
                    Variable variableRefernce = getVariable(value);
                    variables[counter] = new Variable(variableData[firstIndex], variableData[1 + firstIndex], variableRefernce, isItFinal);
                } else {
                    //todo throw exception.
                }

            } else {
                variables[counter] = new Variable(variableData[firstIndex], variableData[1 + firstIndex], value, isItFinal);
            }
            counter++;
        }
        return variables;
    }
//            throws VariableException {
//        String vType = strings.get(0);
//        strings.remove(0);
//        String conStr = "";
//        for (String s: strings){
//            conStr = conStr.concat(s);
//        }
//        String[] splitLine = conStr.split(COMMA);
//        //splitting the line by commas
//        LinkedList<String> names = new LinkedList<>();
//        LinkedList<String> values = new LinkedList<>();
//        for (String s: splitLine){
//            if(s.lastIndexOf(EQUAL) != s.indexOf(EQUAL)){
//                throw new VariableException.IllegalVariableNameException();
//            }
//            String[] s1 = s.split(EQUAL);
//            names.add(s1[0]);
//            if(s1.length == 2){
//                values.add(s1[1]);
//            }
//            else{
//                values.add(null);
//            }
//        }
//        Variable[] variables = new Variable[names.size()];
//        for(int i = 0; i < variables.length; i++){
//            variables[i] = new Variable(vType, names.get(i), values.get(i), isFinal);
//        }
//        return variables;


    /**
     * reassigns Variables
     *
     * @param line code line
     * @throws VariableException if theres problem with variabales
     */
    public void reAssignment(String line) throws VariableException {
        LinkedList<String> strings = getListedVariables(line);
        Variable[] variables = getVariables(strings);
        for (Variable variable : variables) {
            if (!isLegalReAssignment(variable)) {
                throw new VariableException.NoVariableNameException();
            }
        }
    }

    /**
     * checks if variable assign is legal
     *
     * @param variable
     * @return
     */
    private boolean isLegalReAssignment(Variable variable) {
        String name = variable.getName();
        for (Scope current : file.getScopes()) {
            if (current.getVariables().containsKey(name)) {
                Variable originalVariable = current.getVariables().get(name);
                if (originalVariable.getVariableType().equals(variable.getVariableType())
                        && !originalVariable.isFinal()) {
                    originalVariable.setValueAssigned();
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private Variable getVariable(String variable) {
        for (Scope current : file.getScopes()) {
            if (current.getVariables().containsKey(variable)) {
                return current.getVariables().get(variable);
            }
        }
        return null;
    }

    private boolean variableToVaribleAssignmentLeagel(String variableAssignedType, String variableAssigningKey) {
        Variable variableAssigning = getVariable(variableAssigningKey);
        return variableAssigning != null && VariableType.isTypeMatch(variableAssignedType, variableAssigning);
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
