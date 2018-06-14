package variblePackage;

abstract class VaribleException extends Exception{
    private static final long serialVersionUID = 1L;

    /*
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    private VaribleException(String message) {
        super(message);
    }

    static class TypeNotFoundException extends VaribleException{
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
    static class NoVariableNameException extends VaribleException{

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
}
