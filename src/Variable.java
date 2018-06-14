public class Variable {

    /**
     * Enum that...
     */
    private enum Type{
        INTEGER(TypeConstants.INT){ boolean isFitValue(String value){
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true; }},
        DOUBLE(TypeConstants.DOUBLE1){ boolean isFitValue(String value) {
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true; }},
        STRING(TypeConstants.STRING1) {boolean isFitValue(String value){
            return true; }},
        CHAR(TypeConstants.CHAR1){boolean isFitValue(String value){
            return value.length() == 1;
        }},
        BOOLEAN(TypeConstants.BOOLEAN1){boolean isFitValue(String value){
            return value.equals(TRUE)||value.equals(FALSE);
        }};

        private static final String TRUE = "true";
        private static final String FALSE = "false";

        private String typeKey;

        Type(String typeName) {
            this.typeKey = typeName;
        }

        abstract boolean isFitValue(String value);

        @Override
        public String toString() {
            return super.toString();
        }

        private static class TypeConstants {
            private static final String DOUBLE1 = "double";
            private static final String INT = "int";
            private static final String STRING1 = "String";
            private static final String CHAR1 = "char";
            private static final String BOOLEAN1 = "boolean";
        }
    }
}
