package variblePackage;

public class Variable {
    private boolean isValueAssigned;
    private String variableName;
    private boolean isFinal = false;
    private VariableType variableType;

    Variable(String type, String variableName, Boolean isValueAssigned, boolean isFinal)
            throws VariableException.TypeNotFoundException,
            VariableException.NoVariableNameException {
        this.isValueAssigned = isValueAssigned;
        this.variableType = VariableType.parseType(type);
        this.variableName = variableName;
        this.isFinal = isFinal;

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

    void NameChecker(){
        //todo check if name is valid
    }


}


