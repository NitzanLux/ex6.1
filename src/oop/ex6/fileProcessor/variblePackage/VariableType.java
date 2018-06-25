package oop.ex6.fileProcessor.variblePackage;

/**enumerate class for variable types*/
public enum VariableType {

    /**type int - a whole number*/
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

    /**type double - a float number*/
    DOUBLE(Constants.DOUBLE1) {
        boolean isFitValue(String value) {
            return value.matches ("-?[0-9]+(?:\\.[0-9]+)?");
        }
    },

    /**type String - a string of characters*/
    STRING(Constants.STRING1) {
        boolean isFitValue(String value) {
            return value.startsWith("\"")&&value.endsWith("\"");
        }
    },

    /**type char - a single character*/
    CHAR(Constants.CHAR1) {
        boolean isFitValue(String value) {
            return value.length() == 3 && value.startsWith("'") && value.endsWith("'");
        }
    },

    /**type boolean - true, false or any number (int or double)*/
    BOOLEAN(Constants.BOOLEAN1) {
        boolean isFitValue(String value) {
            return value.equals(Constants.TRUE) || value.equals(Constants.FALSE)||
                    VariableType.DOUBLE.isFitValue(value);
        }
    };

    //--data member--//
    private String typeKey;

    /**setter for typeKey member
     * @param typeName data member typeKey
     */
    VariableType(String typeName){
        this.typeKey = typeName;
    }

    /**abstract method to check if value string fits type
     * @param value the value to assign
     * @return true if it fits the value, false else
     */
    abstract boolean isFitValue(String value);

    /**
     * @return a string representation for typeKey (name of type)
     */
    @Override
    public String toString() {
        return typeKey;
    }

    /**
     * returns the type that recognized from the string typeKey
     * @param typeKey the type from string code
     * @return the type that it fits
     * @throws VariableException.TypeNotFoundException if it does not fit any of them
     */
    public static VariableType parseType(String typeKey) throws VariableException.TypeNotFoundException {
        for (VariableType currentType : VariableType.values()) {
            if (typeKey.equals(currentType.toString())) {
                return currentType;
            }
        }
        throw new VariableException.TypeNotFoundException();
    }

    //--constants--//
    private static class Constants {
        private static final String DOUBLE1 = "double";
        private static final String INT = "int";
        private static final String STRING1 = "String";
        private static final String CHAR1 = "char";
        private static final String BOOLEAN1 = "boolean";
        private static final String TRUE = "true";
        private static final String FALSE = "false";
    }

    /**
     * check if value is fit type
     * @param value value to check
     * @return true if it legal
     */
    public static boolean isValueOfType(String value){
      return getVariableType(value)!=null;
    }

    /**
     * check if type of assigned type fits variable assigning in type
     * @param variableAssignedType type of variable that assigned
     * @param variableAssigningType type of variable that we are assigning to
     * @return true if type fits false otherwhise
     * @throws VariableException.TypeNotFoundException if type is not parsable
     */
    public static boolean isTypeMatchForAssignment(String variableAssignedType,
           VariableType variableAssigningType) throws VariableException.TypeNotFoundException {
        VariableType assignedType=parseType(variableAssignedType);
        return isTypeIsParsable(assignedType,variableAssigningType);
    }

    /**
     * matches type to value
     * @param value value to match
     * @return the type that fit the value, null if none fits
     */
   public static VariableType getVariableType(String value){
       for (VariableType variableType:VariableType.values()) {
           if (variableType.isFitValue(value)){
               return variableType;
           }
       }
       return null;
    }

    /**
     * check if type is parsable to parser
     * @param parser the type we want to check with
     * @param toParse the type that we want to know if it fit
     * @return true if we can parse to parser
     */
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
