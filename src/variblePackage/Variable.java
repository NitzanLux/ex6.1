package variblePackage;

public class Variable {
    private String value;
    private String variableName;
    private boolean isFinal = false;
    private VariableType variableType;

    public Variable(String variableName, String value, String type, boolean isFinal)
            throws VariableException.TypeNotFoundException,
            VariableException.NoVariableNameException {
        this.value = value;
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
    private void NameChecker(){
        //todo check if name is valid
    }


}


