package oop.ex6.fileProcessor.scopePackage;

public abstract class ScopeException extends Exception {

    private static final long serialVersionUID = 1L;

    ScopeException(String message) {
        super(message);
    }

    static class AlreadyAssignedException extends ScopeException{
        private static final String ALREADY_ASSIGNED_MSG = "variable %s is already assigned";

        AlreadyAssignedException(String name) {
            super(String.format(ALREADY_ASSIGNED_MSG, name));
        }
    }
    static class ScoopCloserException extends ScopeException{
        private static final String CLOSER_SCOOP_ERROR_MSG = "the scoop closer is illegal";

        ScoopCloserException() {
            super(CLOSER_SCOOP_ERROR_MSG);
        }
    }
    static class VeriableNotAssignedException extends ScopeException{
        private static final String VARIABLE_NOT_ASSIGNED_MSG ="variable %s is not assigned!";

        VeriableNotAssignedException(String variable) {
            super(String.format(VARIABLE_NOT_ASSIGNED_MSG,variable));
        }
    }
    static class MethodNotDeclerdException extends ScopeException{
        private static final String METHOD_NOT_DECLERD_MSG ="Method %s is undeclared";

        MethodNotDeclerdException(String variable) {
            super(String.format(METHOD_NOT_DECLERD_MSG,variable));
        }
    }
    static class MethodVariablesUnfitException extends ScopeException{
        private static final String METHOD_VARIABLE_UNFIT_MSG ="Method call variables are illegal";

        MethodVariablesUnfitException() {
            super(METHOD_VARIABLE_UNFIT_MSG);
        }
    }
    static class IleagleConditionOperatorException extends ScopeException{
        private static final String CONDITION_IS_UNVALID_MSG ="illegal condition operator";

        IleagleConditionOperatorException() {
            super(CONDITION_IS_UNVALID_MSG);
        }
    }
    static class MethodParameterProblemException extends ScopeException{
        private static final String METHOD_PARAMETER_UNFIT_MSG ="method parameters are unfit";

        MethodParameterProblemException() {
            super(METHOD_PARAMETER_UNFIT_MSG);
        }
    }

}
