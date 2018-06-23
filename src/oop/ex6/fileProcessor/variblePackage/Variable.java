package oop.ex6.fileProcessor.variblePackage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variable {
    private boolean isValueAssigned;
    private String variableName;
    private boolean isFinal = false;
    private VariableType variableType;
    private static final Pattern namePattern=Pattern.compile("(?:^(?:[A-Za-z]+|(?:[_]+\\w*[A-Za-z]))\\w*\\b)*$");

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

    public String getName(){
        return variableName;
    }

    public void assignVariable(String value) throws VariableException {
        if (isFinal&&isValueAssigned){
            throw new  VariableException.FinalException.FinalAssignedAlradyException();
        }
        if(variableType.isFitValue(value)){
            isValueAssigned = true;
        }else{
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

    void setValueAssigned(){
        isValueAssigned = true;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public boolean isFinal() {
        return isFinal;
    }
}


