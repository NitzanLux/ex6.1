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
                    VariableType.DOUBLE.isFitValue(value);
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
    public static boolean  isValueOfType(String typeKey){
      return getVariableType(typeKey)!=null;
    }

    public static boolean isTypeMatchForAssignment(String variableAssignedType, VariableType variableAsginingType){
        VariableType assognedType=getVariableType(variableAssignedType);
        return isTypeIsParsable(assognedType,variableAsginingType);
    }
   public static VariableType getVariableType(String typeKey){
       for (VariableType variableType:VariableType.values()) {
           if (variableType.isFitValue(typeKey)){
               return variableType;
           }
       }
       return null;
    }
    public static boolean isTypeIsParsable(VariableType parser,VariableType toParse){
        if (parser==toParse){
            return true;
        }
        if (parser==null){
            return false;
        }
        if (parser==VariableType.DOUBLE){
            return toParse == VariableType.INTEGER;
        }else if (parser==VariableType.BOOLEAN){
            return toParse == VariableType.INTEGER || toParse == VariableType.DOUBLE;
        }
        return false;
    }
}
