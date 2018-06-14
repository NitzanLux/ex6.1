package scopePackage;

public class AlreadyAssignedException extends Exception {
    public AlreadyAssignedException(String var){
        super("Variable '" + var + "' is already assigned");
    }
}
