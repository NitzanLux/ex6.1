package fileProcessor.variblePackage;

public class Variable {
    private boolean isValueAssigned;
    private String variableName;
    private boolean isFinal = false;
    private VariableType variableType;

    public Variable(String type, String variableName, String value, boolean isFinal)
            throws VariableException{
        this.variableType = VariableType.parseType(type);
        this.variableName = variableName;
        this.isFinal = isFinal;
        if(value != null){
            assignVariable(value);
        }
        if (isFinal&&value==null){
            throw new VariableException.FinalException.FinalNotAssignedException();
        }
        if (variableType == null) {
            throw new VariableException.TypeNotFoundException();
        }
        if (variableName == null) {
            throw new VariableException.NoVariableNameException();
        }
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
        }
        else{
            throw new VariableException.ValueNotMatchingTypeException();
        }
    }

    void NameChecker(){
        //todo check if name is valid
        //(?:^[A-Za-z_]\w*[A-Za-z]{1,}\w*\b)*

    }


    public VariableType getVariableType() {
        return variableType;
    }

    public boolean isFinal() {
        return isFinal;
    }
}


