public class Variable {
    private enum Type{
        INTEGER(Constants.INT){ boolean isFitValue(String value){
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true; }},
        DOUBLE(Constants.DOUBLE1){ boolean isFitValue(String value) {
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return false;
            }
            return true; }},
        STRING(Constants.STRING1) {boolean isFitValue(String value){
            return true; }},
        CHAR(Constants.CHAR1){boolean isFitValue(String value){
            return value.length() == 1;
        }},
        BOOLEAN(Constants.BOOLEAN1){boolean isFitValue(String value){
            return value.equals(Constants.TRUE)||value.equals(Constants.FALSE);
        }};

        private String type;

        Type(String typeName) {
            this.type = typeName;
        }

        abstract boolean isFitValue(String value);

        @Override
        public String toString() {
            return super.toString();
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
    }
}
