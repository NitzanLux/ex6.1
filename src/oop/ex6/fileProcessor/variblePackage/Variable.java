package oop.ex6.fileProcessor.variblePackage;

import oop.ex6.fileProcessor.scopePackage.Scope;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variable {
    private boolean isValueAssigned;
    private String variableName;
    private boolean isFinal = false;
    private VariableType variableType;
    private static final Pattern namePattern=Pattern.compile("(?:^(?:[A-Za-z]+|(?:[_]+\\w*[A-Za-z]))\\w*\\b)*$");

    public Variable(Variable variable){
        this.isValueAssigned=variable.isValueAssigned;
        this.isFinal = variable.isFinal;
        this.variableName = variable.getName();
        this.variableType = variable.getVariableType();
    }
    Variable(){}
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

    public boolean isValueAssigned() {
        return isValueAssigned;
    }

    private void setVariable(String type, String variableName, boolean isFinal) throws VariableException {
        this.isFinal = isFinal;
        this.variableType = VariableType.parseType(type);
        setVariableName(variableName);


    }
    public void assignedVariable(Scope scope) throws VariableException {
        scope.reAssignVariable(this,!isValueAssigned);
        this.isValueAssigned=true;
    }
    public void  reStoreVariable(){
        this.isValueAssigned=false;
    }
    public String getName(){
        return variableName;
    }

    public void assignVariable(String value) throws VariableException {
        if(variableType.isFitValue(value)){
            isValueAssigned = true;
        }else{
            if (isFinal){
                throw new  VariableException.FinalException.FinalAssignedAlradyException();
            }
            throw new VariableException.ValueNotMatchingTypeException();
        }

    }

    private void setVariableName(String variableName) throws VariableException.NoVariableNameException {
        Matcher matcher=Variable.namePattern.matcher(variableName);
        if (matcher.matches()){
            this.variableName=variableName;
        }else {
            throw new VariableException.NoVariableNameException();
        }

    }


    public VariableType getVariableType() {
        return variableType;
    }

    public boolean isFinal() {
        return isFinal;
    }
}


