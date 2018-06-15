package variblePackage;

public class Variable {
    private boolean isValueAssigned;
    private String variableName;
    private boolean isFinal = false;
    private VariableType variableType;

    Variable(String type, String variableName, String value, boolean isFinal)
            throws VariableException{
        this.variableType = VariableType.parseType(type);
        this.variableName = variableName;
        this.isFinal = isFinal;
        if(value != null){
            assignVariable(value);
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

    private void assignVariable(String value) throws VariableException {
        if(variableType.isFitValue(value)){
            isValueAssigned = true;
        }
        else{
            throw new VariableException.ValueNotMatchingTypeException();
        }
    }

    void NameChecker(){
        //todo check if name is valid
    }


}


