package oop.ex6.fileProcessor.variblePackage;

import oop.ex6.fileProcessor.scopePackage.File;
import oop.ex6.fileProcessor.scopePackage.Scope;
import oop.ex6.fileProcessor.scopePackage.ScopeException;

import java.util.HashSet;
import java.util.LinkedList;

public class VariableFactory {

    private static final int FIRST_POSITION = 0;
    private static final int VARIABLE_PLACE_IN_ARRAY = 0;
    private static final int VALUE_PLACE_IN_ARRAY = 1;
    private File file;

    private static final String EQUAL = "=", SEMICOLON = ";", FINAL = "final", SPACES = "[,();]";
    private static final int TYPE_PLACE = 0,
    NAME_PLACE = 1;

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
        Variable[] vars = getVariables(strings);
        for (Variable var : vars) {
            file.getCurrentScope().addVariable(var);

        }
    }

    /**
     * generates a linked list containing all variables as strings
     * @param line code line we want to generate to variables
     * @return a linked list of variables as strings
     */
    private LinkedList<String> getListedVariables(String line) {
        if (line.contains("(")) {
            line = line.substring(line.indexOf("("), line.indexOf(")"));
        }
        String[] variables = line.split(SPACES);
        LinkedList<String> strings = new LinkedList<>();
        for (String variable : variables) {
            strings.addLast(variable);
        }
        return strings;
    }

    /**
     * gets the variables from assignment line
     * @param strings the strings after parsing the line
     * @return array contains variables
     * @throws VariableException if variables are not okay
     */
    public Variable[] getVariables(LinkedList<String> strings)
            throws VariableException {
        Variable[] variables = new Variable[strings.size()];
        int counter = 0;
        String variableType = null;
        HashSet<String> currentNames = new HashSet<>();
        boolean isItFinal = false;
        for (String variable : strings) {
            int firstIndex = 0;
            boolean isAssigned = (variable.contains(EQUAL));
            String [] variableData = getVariableData (variable);
            String value = getValue (variableData, isAssigned);
            if (variableData[0].equals(FINAL)) {
                isItFinal = true;
                firstIndex++;
            }
            String[] typeName = typeAndName (variableType, variableData, firstIndex, isAssigned);
            variableType = typeName[TYPE_PLACE];
            String variableName = typeName[NAME_PLACE];
            currentNames = updateCurrentNames (currentNames, variableName);
            variables[counter] = generateVariable (variableType, variableName, value, isAssigned, isItFinal);
            counter++;
        }
        return variables;
    }


    /**
     * creates a new String array with 2 members: variable name and variable type
     * @param variableType variable type (could be null)
     * @param variableData array that contains data about the variable
     * @param firstIndex the index in the data where the type of variable appears in
     * @param isAssigned if variable is assigned a value
     * @return String array with both variable name and variable type
     * @throws VariableException in case we try to assert illegal type
     */
    private String[] typeAndName(String variableType, String[] variableData,
                                 int firstIndex, boolean isAssigned) throws VariableException {
        String variableName = null;
        if (!((variableData.length==2&&isAssigned)||(variableData.length==1))) {
            //in case there is a multi assignment of variables from the same type
            if (variableType==null){
                variableType = variableData[firstIndex];
                variableName = variableData[1 + firstIndex];
            }
        }else {
            variableName=variableData[firstIndex];
        }
        if (variableType==null||variableName==null) {
            throw new VariableException.FinalException.AssertionTypeIncompatibleException();
            //in case variable type does not exists.
        }
        return new String[]{variableType, variableName};
    }

    /**
     * creates variable from data:
     * @param variableType .
     * @param variableName .
     * @param value .
     * @param isAssigned .
     * @param isItFinal .
     * @return the new variable
     * @throws VariableException in case of incompatible assertion type
     */
    private Variable generateVariable(String variableType, String variableName, String value,
                                            boolean isAssigned, boolean isItFinal) throws VariableException {
        if (isAssigned && !VariableType.isValueOfType(value)) {//if variable is assigned by reference.
            Variable variableReference=file.getVariable(value);
            if (variableReference!=null&&VariableType.isTypeMatchForAssignment(variableType,
                    variableReference.getVariableType())) {//check if types fit.
                if (!variableReference.isValueAssigned()) {
                    throw new VariableException.FinalException.AssertionTypeIncompatibleException();
                }
                return new Variable(variableType, variableName, true, isItFinal);
            } else {
                throw new VariableException.FinalException.AssertionTypeIncompatibleException();
            }

        } else {
            return new Variable(variableType, variableName, value, isItFinal);
        }
    }

    /**
     * updates currentNames
     * @param currentNames a hashmap of all the used variables in certain assignment
     * @param variableName name we want to add
     * @return updated hashmap with name
     * @throws VariableException in case we try to assign an already existing name
     */
    private HashSet<String> updateCurrentNames(HashSet<String> currentNames, String variableName)
            throws VariableException {
        if (!currentNames.contains(variableName)) {
            //in case of double assignment name in the same line.
            currentNames.add(variableName);
        } else {
            throw new VariableException.FinalException.AssigmentOfTheSameVariableException();
        }
        return currentNames;
    }

    /**
     * generates data about the variable from string
     * @param variable string representing the assignment
     * @return array with data about the variable
     * @throws VariableException in case of illegal name
     */
    private String[] getVariableData(String variable) throws VariableException {
        variable = variable.trim();
        String[] variableData = variable.split("[ \\t]*(?:[ \\t]++|(?:=[ \\t]*+))");
        if (variableData.length==0) {
            throw new VariableException.IllegalVariableNameException();
        }
        return variableData;
    }

    /**
     * gets value to assign
     * @param variableData data about variable
     * @param isAssigned boolean variable to know if variable was assigned
     * @return the value or null if not assigned
     */
    private String getValue(String[] variableData, boolean isAssigned){
        if (isAssigned) {
            return variableData[variableData.length - 1];
        }
        return null;
    }


    /**
     * reassigns Variables
     * @param line code line
     * @throws VariableException if theres problem with variabales
     */
    public void reAssignment(String line) throws VariableException {
        line = line.replace(SEMICOLON, "");
        line = line.trim();
        String[] variables = line.split("[ \\t]*+,[ \\t]*+");
        for (String variable : variables) {
            String[] currentVariable = variable.split("[ \\t]*+=[ \\t]*+");
            if (currentVariable.length == 2 && isLegalReAssignment(currentVariable[VARIABLE_PLACE_IN_ARRAY],
                    currentVariable[VALUE_PLACE_IN_ARRAY])) {
                file.getVariable(currentVariable[VARIABLE_PLACE_IN_ARRAY]).assignedVariable
                        (file.getCurrentScope());
            } else {
                throw new VariableException.NoVariableNameException();
            }

        }
    }

    /**
     * checks if variable assign is legal
     * @param variableName name of variable
     * @param value value we want to reassign in it
     * @return true if assignment is legal, false otherwise
     */
    private boolean isLegalReAssignment(String variableName, String value) {
        Variable variable = file.getVariable (variableName);
        Variable assigningVariable = file.getVariable (value);
        VariableType assigningType;
        if (assigningVariable == null) {
            assigningType = VariableType.getVariableType (value);
        } else {
            if (assigningVariable.isValueAssigned ()) {
                assigningType = assigningVariable.getVariableType ();
            } else {
                return false;
            }
        }
        return assigningType != null && variable != null && !variable.isFinal ()
                && VariableType.isTypeIsParsable (variable.getVariableType (), assigningType);
        //returns true only if value type is okay, variable exists, not final and the types match
    }

}
