package scopePackage;

public abstract class ScopeException extends Exception {
    private static final long serialVersionUID = 1L;
    ScopeException(String message) {
        super(message);
    }

    public static class AlreadyAssignedException extends ScopeException{
        private static final String ALREADY_ASSIGNED_MSG = "variable %sis already assigned";

        public AlreadyAssignedException(String name) {
            super(String.format(ALREADY_ASSIGNED_MSG, name));
        }
    }
}
