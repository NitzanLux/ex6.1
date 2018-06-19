package main;

public enum LineType {
    METHOD(Constants.regex, ScopePosition.OUTER_SCOPE){},
//    ASSIGNMENT("^[a-zA-z]+ +\\w+ *\\= *[\\w]+ *\\;", ScopePosition.BOTH){},
    ASSIGNMENT(String.format("^[ ]*\\w+[ ]+[\\w]+(?:[ ]*,[\\w]*){%s}[ ]*=[ ]*[\\w]+(?:[ ]*,[\\w]*){%s}[ ]*\\;",
            Constants.MULTI_ASSIGNMENT_MINUS_ONE, Constants.MULTI_ASSIGNMENT_MINUS_ONE)
            , ScopePosition.BOTH){},
    IF("^"+ Constants.IF_STATEMENT +" *\\((?:\\w*(?:(?:\\|\\|)|(?:\\=\\=)|(?:\\&\\&))\\w*)+\\){", ScopePosition.INNER_SCOPE){},
    WHILE("^"+ Constants.WHILE_STATEMENT +" *\\((?:\\w*(?:(?:\\|\\|)|(?:\\=\\=)|(?:\\&\\&))\\w*)+\\){", ScopePosition.INNER_SCOPE){},
    METHOD_CALL(Constants.regex, ScopePosition.INNER_SCOPE){},
    RETURN("^[ ]*return\\;[ ]*$", ScopePosition.INNER_SCOPE){},
//    REASSIGNMENT("^\\w+[ ]*=[ ]*\\w+\\; *$", ScopePosition.INNER_SCOPE){},
    REASSIGNMENT(String.format("^[ ]*[\\w]+(?:[ ]*,[\\w]*){%s}[ ]*=[ ]*[\\w]+(?:[ ]*,[\\w]*){%s}[ ]*\\;"//todo change varibls to all charcter variable(for string)
            , Constants.MULTI_ASSIGNMENT_MINUS_ONE, Constants.MULTI_ASSIGNMENT_MINUS_ONE), ScopePosition.INNER_SCOPE){},
    BLANK_LINE(Constants.regex, ScopePosition.BOTH){};
    //todo bracket or spaces?
    private final ScopePosition scopePosition;
    private final String regex;
    LineType(String regex, ScopePosition scopePosition){
        this.regex=regex;
        this.scopePosition =scopePosition;
    }
    private enum ScopePosition {
        INNER_SCOPE(false,true),
        OUTER_SCOPE(true,false),
        BOTH(true,true);
        private final boolean inOuterScope;
        private final boolean inInnerScope;
        ScopePosition(boolean isOuter, boolean isInner){
            this.inInnerScope =isInner;
            this.inOuterScope =isOuter;
        }
        private boolean isOuter(){
            return inOuterScope;
        }
        private boolean isInner(){
            return inInnerScope;
        }

    }



    private static class Constants {
        private static final String regex = "ff";//todo "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"
        private static final String MULTI_ASSIGNMENT_MINUS_ONE = "MULTI_ASSIGNMENT_MINUS_ONE";
        private static final String IF_STATEMENT = "if";
        private static final String WHILE_STATEMENT = "while";
    }
//todo  is this the proper way?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????

    /**
     *
     * @param scopPosition
     * @return
     */
    public static LineType[] getScoping(ScopePosition scopPosition){
        int counter = 0;
        LineType[] currentValues = LineType.values();
        for (LineType lineType :currentValues) {
            if (lineType.scopePosition == scopPosition){
                counter++;
            }
        }
        LineType[] lineTypes =new LineType[counter];
        counter=0;
        for (LineType currentValue : currentValues) {
            if (currentValue.scopePosition == scopPosition) {
                lineTypes[counter] = currentValue;
                counter++;
            }
        }
        return lineTypes;
    }
}
