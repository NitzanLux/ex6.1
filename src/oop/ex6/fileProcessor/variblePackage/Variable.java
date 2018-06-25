package oop.ex6.fileProcessor.variblePackage;

import oop.ex6.fileProcessor.scopePackage.Scope;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**class representing variable object*/
public class Variable {

    //--data members--//
    private boolean isValueAssigned;
    private String variableName;
    private boolean isFinal = false;
    private VariableType variableType;

    //--constant regex pattern for variable--//
    private static final Pattern NAME_PATTERN = Pattern.compile
            ("(?:^(?:[A-Za-z]+|(?:[_]+\\w*[A-Za-z]))\\w*\\b)*$");

    /**
     * a cloning constructor
     * @param variable the variable we want to clone
     */
    public Variable(Variable variable){
        this.isValueAssigned=variable.isValueAssigned;
        this.isFinal = variable.isFinal;
        this.variableName = variable.getName();
        this.variableType = variable.getVariableType();
    }

    /**
     * value assigning constructor
     * @param type type of variable
     * @param variableName name of variable
     * @param value we assign to variable
     * @param isFinal true if variable is final
     * @throws VariableException in case we try to assign to final var, or there is no type declare
     */
    Variable(String type, String variableName, String value, boolean isFinal)
            throws VariableException{
        setVariable(type,variableName,isFinal);
        if(value != null){
            assignVariable(value);
        }
        if (isFinal&&value==null){
            throw new VariableException.FinalException.FinalNotAssignedException(variableName);
        }
        if (variableType == null) {
            throw new VariableException.TypeNotFoundException();
        }

    }

    /**
     * default constructor
     * @param type type of variable
     * @param variableName name of variable
     * @param isValueAssigned true if value is assigned within variable
     * @param isFinal true if variable is final
     * @throws VariableException in case we try to assign to final var, or there is no type declare
     */
    Variable(String type, String variableName, boolean isValueAssigned, boolean isFinal)
            throws VariableException{
        setVariable(type,variableName,isFinal);
        this.isValueAssigned=isValueAssigned;
        if (isFinal&&!isValueAssigned){
            throw new VariableException.FinalException.FinalNotAssignedException(variableName);
        }
        if (variableType == null) {
            throw new VariableException.TypeNotFoundException();
        }
    }

    /**
     * @return true if value is assigned
     */
    public boolean isValueAssigned() {
        return isValueAssigned;
    }

    /**
     * sets variable type, name and final
     * @param type type we want to change to
     * @param variableName name we want to change to
     * @param isFinal if we want it to be final
     * @throws VariableException in case type illegal
     */
    private void setVariable(String type, String variableName, boolean isFinal) throws VariableException {
        this.isFinal = isFinal;
        this.variableType = VariableType.parseType(type);
        setVariableName(variableName);
    }

    /**
     * adds variable to scopes hashmap
     * @param scope the scope we are in
     */
    public void assignedVariable(Scope scope) {
        scope.reAssignVariable(this,!isValueAssigned);
        this.isValueAssigned=true;
    }

    /**
     * restores variable to be unassigned
     */
    public void reStoreVariable(){
        this.isValueAssigned=false;
    }

    /**
     * @return variable name
     */
    public String getName(){
        return variableName;
    }

    /**
     * sets variable to be assigned
     * @param value value we assign
     * @throws VariableException if we cannot assign the value
     */
    private void assignVariable(String value) throws VariableException {
        if(variableType.isFitValue(value)){
            isValueAssigned = true;
        }else{
            if (isFinal){
                throw new  VariableException.FinalException.FinalAssignedAlradyException();
            }
            throw new VariableException.ValueNotMatchingTypeException();
        }

    }

    /**
     * sets variable name
     * @param variableName the new name
     * @throws VariableException if variable name illegal
     */
    private void setVariableName(String variableName) throws VariableException {
        Matcher matcher=Variable.NAME_PATTERN.matcher(variableName);
        if (matcher.matches()){
            this.variableName=variableName;
        }else {
            throw new VariableException.NoVariableNameException();
        }

    }

    /**
     * @return the variable type
     */
    public VariableType getVariableType() {
        return variableType;
    }

    /**
     * @return true if var is final
     */
    public boolean isFinal() {
        return isFinal;
    }
}


