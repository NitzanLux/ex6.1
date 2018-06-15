package variblePackage;

abstract class VariableException extends Exception{
    private static final long serialVersionUID = 1L;

    /*
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    private VariableException(String message) {
        super(message);
    }

    static class TypeNotFoundException extends VariableException {
        private static final String TYPE_NOT_FOUND_MSG = "variable type was not found";

        /*
         * Constructs a new exception with the specified detail message.  The
         * cause is not initialized, and may subsequently be initialized by
         * a call to {@link #initCause}.
         *
         */
        TypeNotFoundException() {
            super(TYPE_NOT_FOUND_MSG);
        }
    }
    static class NoVariableNameException extends VariableException {

        private static final String NO_VARIABLE_NAME_MSG = "no variable name assigned.";

        /*
         * Constructs a new exception with the specified detail message.  The
         * cause is not initialized, and may subsequently be initialized by
         * a call to {@link #initCause}.
         *
         */
        NoVariableNameException() {
            super(NO_VARIABLE_NAME_MSG);
        }
    }
    static class ValueNotMatchingTypeException extends VariableException{


        private static final String VAL_ASGMNT_MSG = "value you tried to assign doesn't fit variable type";

        /*
         * Constructs a new exception with the specified detail message.  The
         * cause is not initialized, and may subsequently be initialized by
         * a call to {@link #initCause}.
         *
         */
        ValueNotMatchingTypeException() {
            super(VAL_ASGMNT_MSG);
        }
    }
    static class FinalException extends VariableException{
        FinalException(String massege){
            super(massege);
        }
        static class FinalAssignedAlradyException extends VariableException.FinalException{


            private static final String VAL_FINAL_REASSIGMENT_MSG = "the final variable is already assigned";

            /*
             * Constructs a new exception with the specified detail message.  The
             * cause is not initialized, and may subsequently be initialized by
             * a call to {@link #initCause}.
             *
             */
            FinalAssignedAlradyException() {
                super(VAL_FINAL_REASSIGMENT_MSG);
            }
        }
        static class FinalNotAssignedException extends VariableException.FinalException{


            private static final String VAL_FINAL_VALUE_ASSIGMENT_MSG =
                    "the final variable you tried to assigned has no value";

            /*
             * Constructs a new exception with the specified detail message.  The
             * cause is not initialized, and may subsequently be initialized by
             * a call to {@link #initCause}.
             *
             */
            FinalNotAssignedException() {
                super(VAL_FINAL_VALUE_ASSIGMENT_MSG);
            }
        }


    }
}