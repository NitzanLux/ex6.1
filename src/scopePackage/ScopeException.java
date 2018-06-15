package scopePackage;

public abstract class ScopeException extends Exception {
    private static final long serialVersionUID = 1L;
    ScopeException(String message) {
        super(message);
    }

    public static class AlreadyAssignedException extends ScopeException{
        public AlreadyAssignedException() {
            super("variable is already assigned");
        }
    }
}
