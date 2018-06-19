package scopePackage;

enum ConditionScope {
    IF(Constants.IF_STR){},
    WHILE(Constants.WHILE_STR){};

    boolean isLegalCondition(String condition){
     //TODO parse and check regex. Also check if variable checked fits value.
        return false;
    }

    private String condition;

    ConditionScope(String scopeType){

    }

    private static class Constants {
        private static final String IF_STR = "if";
        private static final String WHILE_STR = "while";
    }
}
