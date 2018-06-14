public class Variable {
    private String value;
    private String variableName;
    private Type variableType;
    public Variable(String variableName,String value,String type){
        this.value=value;

    }
    private Type getVariableTypeFromString(String type){
        for (Type currentType:Type.values()) {
            if (type.equals(currentType.toString())){
                return currentType;
            }
        }
        return null;
    }
    private enum Type{INTEGER, DOUBLE, STRING, CHAR, BOOLEAN;
        private


    }
}
