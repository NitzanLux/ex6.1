package oop.ex6.fileProcessor.scopePackage;

/**exceptions class for scopes*/
public abstract class ScopeException extends Exception {

    private static final long serialVersionUID = 1L;

    /**default const.*/
    ScopeException(String message) {
        super(message);
    }

    /**exception for case if variable already assigned in scope*/
    static class AlreadyAssignedException extends ScopeException{
        private static final String ALREADY_ASSIGNED_MSG = "variable %s is already assigned";

        AlreadyAssignedException(String name) {
            super(String.format(ALREADY_ASSIGNED_MSG, name));
        }
    }

    /**in case scope closer is illegal*/
    static class ScoopCloserException extends ScopeException{
        private static final String CLOSER_SCOOP_ERROR_MSG = "the scope closer is illegal";

        ScoopCloserException() {
            super(CLOSER_SCOOP_ERROR_MSG);
        }
    }

    /**in case return not in legal place*/
    public static class ReturnCloserException extends ScopeException{
        private static final String CLOSER_RETURN_ERROR_MSG = "the return closer is illegal";

        public ReturnCloserException() {
            super(CLOSER_RETURN_ERROR_MSG);
        }
    }

    /**in case variable we look for isn't assigned yet*/
    static class VariableNotAssignedException extends ScopeException{
        private static final String VARIABLE_NOT_ASSIGNED_MSG ="variable %s is not assigned!";

        VariableNotAssignedException(String variable) {
            super(String.format(VARIABLE_NOT_ASSIGNED_MSG,variable));
        }
    }

    /**method is not declared*/
    static class MethodNotDeclaredException extends ScopeException{
        private static final String METHOD_NOT_DECLARED_MSG ="Method %s is undeclared";

        MethodNotDeclaredException(String variable) {
            super(String.format(METHOD_NOT_DECLARED_MSG,variable));
        }
    }

    /**method we call with unfit variable types*/
    static class MethodVariablesUnfitException extends ScopeException{
        private static final String METHOD_VARIABLE_UNFIT_MSG ="Method call variables are illegal";

        MethodVariablesUnfitException() {
            super(METHOD_VARIABLE_UNFIT_MSG);
        }
    }

    /**illegal condition operator*/
    static class IllegalConditionOperatorException extends ScopeException{
        private static final String CONDITION_IS_INVALID_MSG ="illegal condition operator";

        IllegalConditionOperatorException() {
            super(CONDITION_IS_INVALID_MSG);
        }
    }

    /**method name is not a legal name*/
    static class IllegalMethodNameException extends ScopeException{
        private static final String METHOD_NAME_ERROR_MSG ="method name is illegal";

        IllegalMethodNameException() {
            super(METHOD_NAME_ERROR_MSG);
        }
    }

}
