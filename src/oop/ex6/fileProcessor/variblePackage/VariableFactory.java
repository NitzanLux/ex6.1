package oop.ex6.fileProcessor.variblePackage;

import oop.ex6.fileProcessor.scopePackage.File;
import oop.ex6.fileProcessor.scopePackage.Scope;
import oop.ex6.fileProcessor.scopePackage.ScopeException;

import java.util.HashSet;
import java.util.LinkedList;

public class VariableFactory {

    private static final int FIRST_POSITION = 0;
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
        Variable[] vars = getVariables(strings, false);
        for (Variable var : vars) {
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
            strings.addLast(variable);
        }
        return strings;
    }

    /**
     * gets the variables from assignment line
     *
     * @param strings the strings after parsing the line
     * @return variables
     * @throws VariableException if variables are not okay
     */
    public Variable[] getVariables(LinkedList<String> strings, boolean isAlreadyAssigned) throws VariableException {
        Variable[] variables = new Variable[strings.size()];
        int counter = 0;
        String variableType = null;
        HashSet<String> currentNames = new HashSet<>();
        for (String variable : strings) {
            int firstIndex = 0;
            String variableName;
            boolean isAssigned = false;
            if (variable.contains("=")) {
                isAssigned = true;
            }
            variable = variable.trim();
            String[] variableData = variable.split("\\s+(?:\\=\\s*)?");
            String value = null;
            if (variableData.length < File.MIN_SCOPE_SIZE + 1) {
                throw new VariableException.IllegalVariableNameException();
            }
            if (isAssigned) {
                value = variableData[variableData.length - 1];
            }
            boolean isItFinal = false;
            if (variableData[0].equals("final")) {
                isItFinal = true;
                firstIndex++;
            }
            if (!currentNames.contains(variableData[1 + firstIndex])) {//in case of double assigment name in the same line.
                currentNames.add(variableData[1 + firstIndex]);
            } else {
                throw new VariableException.FinalException.AssigmentOfTheSameVariableException();
            }
            if (!((variableData.length==2&&isAssigned)||(variableData.length==1&&!isAssigned))) {//if thers molty assigment with the same type( boolean a,a,a
                variableType=variableData[firstIndex];
                variableName=   variableData[1 + firstIndex];
            }else if (variableType==null){
                throw new VariableException.FinalException.AssertionTypeIncompatibleException();//if variable type does not exists.
            }else {
                variableName=variableData[FIRST_POSITION];
            }
            if (isAssigned && !VariableType.isValueOfType(value)) {//if variable is assigned by reference.
                if (variableToVaribleAssignmentLeagel(variableType, value)) {//chack if type are fit.
                    Variable variableRefernce = getVariable(value);
                    if (!variableRefernce.isValueAssigned()) {
                        throw new VariableException.FinalException.AssertionTypeIncompatibleException();
                    }
                    variables[counter] = new Variable(variableType, variableName,
                            variableRefernce.isValueAssigned(), isItFinal);
                } else {
                    throw new VariableException.FinalException.AssertionTypeIncompatibleException();
                }

            } else if (!isAlreadyAssigned) {
                variables[counter] = new Variable(variableType, variableName,//create variable from reference.
                        value, isItFinal);
            } else {
                variables[counter] = new Variable(variableType, variableName,
                        true, isItFinal);
            }
            counter++;
        }
        return variables;
    }


    /**
     * reassigns Variables
     *
     * @param line code line
     * @throws VariableException if theres problem with variabales
     */
    public void reAssignment(String line) throws VariableException {
        LinkedList<String> strings = getListedVariables(line);
        Variable[] variables = getVariables(strings, false);
        for (Variable variable : variables) {
            if (!isLegalReAssignment(variable)) {
                throw new VariableException.NoVariableNameException();
            }
            file.getVariable(variable.getName()).setValueAssigned();

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

    private boolean variableToVaribleAssignmentLeagel(String variableAssignedType, String variableAssigningKey)
            throws VariableException.TypeNotFoundException {
        Variable variableAssigning = getVariable(variableAssigningKey);
        return variableAssigning != null && VariableType.isTypeMatchForAssignment(variableAssignedType,
                variableAssigning.getVariableType());
    }


}
