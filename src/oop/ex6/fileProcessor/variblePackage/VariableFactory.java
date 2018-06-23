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
    public Variable[] getVariables(LinkedList<String> strings, boolean isAlreadyAssigned)
            throws VariableException {
        Variable[] variables = new Variable[strings.size()];
        int counter = 0;
        String variableType = null;
        HashSet<String> currentNames = new HashSet<>();
        for (String variable : strings) {
            int var_index = 0;
            String variableName;
            boolean isAssigned = false;
            if (variable.contains("=")) {
                isAssigned = true;
            }
            variable = variable.trim();
            String[] variableData = variable.split("\\s+(?:\\=\\s*)?");
            String value = null;
            if (variableData.length < File.MIN_SCOPE_SIZE) {
                throw new VariableException.IllegalVariableNameException();
            }
            if (variableData.length >= 2) {
                var_index = 1;
            }
            if (isAssigned) {
                value = variableData[variableData.length - 1];
            }
            boolean isItFinal = false;
            if (variableData[0].equals("final")) {
                isItFinal = true;
                var_index++;
            }
            if (!currentNames.contains(variableData[var_index])) {//in case of double assigment name in the same line.
                currentNames.add(variableData[var_index]);
            } else {
                throw new VariableException.FinalException.AssigmentOfTheSameVariableException();
            }
            if (!((variableData.length == 2 && isAssigned) || (variableData.length == 1 && !isAssigned))) {//if thers molty assigment with the same type( boolean a,a,a
                variableType = variableData[var_index - 1];
                variableName = variableData[var_index];
            } else if (!isAlreadyAssigned && variableType == null) {
                throw new VariableException.FinalException.AssertionTypeIncompatibleException();//if variable type does not exists.
            } else {
                variableName = variableData[FIRST_POSITION];
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
                //TODO oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                //VariableType valType = VariableType.getVariableType (value);
                //TODO oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
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
        //Variable[] variables = getVariables(strings, true);
        line = line.replace(";", "");
        line = line.trim();
        String[] variables = line.split("[ \\t]*\\,[ \\t]*");
        for (String variable : variables) {
            String[] currentVariable = variable.split("[ \\t]*\\=[ \\t]*");
            if (currentVariable.length == 2 && isLegalReAssignment(currentVariable[0], currentVariable[1])) {//todo duplicate code
                file.getVariable(currentVariable[0]).setValueAssigned();
            } else {
                throw new VariableException.NoVariableNameException();
            }

        }
    }

    /**
     * checks if variable assign is legal
     *
     * @param
     * @return
     */
    private boolean isLegalReAssignment(String variableName, String value) {
        Variable variable = file.getVariable(variableName);
        Variable assigningVariable = file.getVariable(value);
        VariableType assigningType;
        if (assigningVariable == null) {
            assigningType = VariableType.getVariableType(value);
        } else {
            if (assigningVariable.isValueAssigned()) {
                assigningType = assigningVariable.getVariableType();
            } else {
                return false;
            }
        }
        if (variable != null && !variable.isFinal()) {
            return VariableType.isTypeIsParsable(assigningType, variable.getVariableType());
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
