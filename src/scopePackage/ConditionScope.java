package scopePackage;

public class ConditionScope extends Scope{

    boolean isLegalCondition(String condition){
     //TODO parse and check regex. Also check if variable checked fits value.
        return false;
    }

    private String condition;

    public ConditionScope(){
        super(null, false, null);
        ///////////////     //////      ////////         //////
              ///         ///    ///    ///    ///     ///    ///
              ///         ///    ///    ///     ///    ///    ///
              ///         ///    ///    ///     ///    ///    ///
              ///         ///    ///    ///    ///     ///    ///
              ///           //////       ////////        //////
    }

    private static class Constants {
        private static final String IF_STR = "if";
        private static final String WHILE_STR = "while";
    }
}
