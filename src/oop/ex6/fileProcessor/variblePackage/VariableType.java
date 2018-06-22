package oop.ex6.fileProcessor.variblePackage;

public enum VariableType {
    INTEGER(Constants.INT) {
        boolean isFitValue(String value) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    },
    DOUBLE(Constants.DOUBLE1) {
        boolean isFitValue(String value) {
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }
    },
    STRING(Constants.STRING1) {
        boolean isFitValue(String value) {
            return value.startsWith("\"")&&value.endsWith("\"");
        }
    },
    CHAR(Constants.CHAR1) {
        boolean isFitValue(String value) {
            return value.length() == 3 && value.startsWith("'") && value.endsWith("'");
        }
    },
    BOOLEAN(Constants.BOOLEAN1) {
        boolean isFitValue(String value) {
            return value.equals(Constants.TRUE) || value.equals(Constants.FALSE)||
                    VariableType.INTEGER.isFitValue(value);
        }
    };

    private String typeKey;
    VariableType(String typeName){
        this.typeKey = typeName;
    }
    abstract boolean isFitValue(String value);

    @Override
    public String toString() {
        return typeKey;
    }


    public static VariableType parseType(String typeKey) throws VariableException.TypeNotFoundException {
        for (VariableType currentType : VariableType.values()) {
            if (typeKey.equals(currentType.toString())) {
                return currentType;
            }
        }
        throw new VariableException.TypeNotFoundException();
    }

    private static class Constants {
        private static final String DOUBLE1 = "double";
        private static final String INT = "int";
        private static final String STRING1 = "String";
        private static final String CHAR1 = "char";
        private static final String BOOLEAN1 = "boolean";
        private static final String TRUE = "true";
        private static final String FALSE = "false";
    }
    public static boolean isValueOfType(String typeKey){
        for (VariableType variableType:VariableType.values()) {
            if(variableType.isFitValue(typeKey)){
                return true;
            }
        }
        return false;
    }
    static boolean isTypeMatch(String variableAssignedType,Variable variableAsgining){
        VariableType assognedType=getVariableType(variableAssignedType);
        if (assognedType==variableAsgining.getVariableType()){
            return true;
        }
        if (assognedType==null){
            return false;
        }
        if (assognedType==VariableType.DOUBLE){
            return variableAsgining.getVariableType() == VariableType.INTEGER;
        }else if (assognedType==VariableType.BOOLEAN){
            return variableAsgining.getVariableType() == VariableType.INTEGER||variableAsgining.getVariableType()
                    == VariableType.DOUBLE;
        }
        return false;
    }
    private static VariableType getVariableType(String typeKey){
        for (VariableType currentType:VariableType.values()){
            if (currentType.typeKey.equals(typeKey)){
                return currentType;
            }
        }
        return null;
    }
}
