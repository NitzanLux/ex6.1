package variblePackage;

public class Variable {
    private String value;
    private String variableName;
    private Type variableType;

    public Variable(String variableName, String value, String type)
            throws VaribleException.TypeNotFoundException,
            VaribleException.NoVariableNameException {
        this.value = value;
        this.variableType = Type.parseType(type);
        this.variableName = variableName;
        if (variableName == null) {
            throw new VaribleException.NoVariableNameException();
        }
    }
    private void NameChecker(){
        //todo chack if name is valid
    }


}


