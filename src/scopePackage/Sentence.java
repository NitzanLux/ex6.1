package scopePackage;

public enum Sentence {
    METHOD(Constants.regex, ScoopPosition.OUTTER_SCOPE){},
    ASSIGNMENT("^[a-zA-z]+ +\\w+ *\\= *[\\w]+ *\\;", ScoopPosition.BOTH){},
    MULTI_ASSIGNMENT(String.format("^[ ]*\\w+[ ]+[\\w]+(?:[ ]*,[\\w]*){%s}[ ]*=[ ]*[\\w]+(?:[ ]*,[\\w]*){%s}[ ]*\\;",
            Constants.MULTI_ASSIGNMENT_MINUS_ONE, Constants.MULTI_ASSIGNMENT_MINUS_ONE)
            , ScoopPosition.BOTH){},
    IF("^"+ Constants.IF_STATMENT +" *\\((?:\\w*(?:(?:\\|\\|)|(?:\\=\\=)|(?:\\&\\&))\\w*)+\\){", ScoopPosition.INNER_SCOPE){},
    WHILE("^"+ Constants.WHILE_STATMENT +" *\\((?:\\w*(?:(?:\\|\\|)|(?:\\=\\=)|(?:\\&\\&))\\w*)+\\){", ScoopPosition.INNER_SCOPE){},
    METHOD_CALL(Constants.regex, ScoopPosition.INNER_SCOPE){},
    RETURN("^[ ]*return\\;[ ]*$", ScoopPosition.INNER_SCOPE){},
    REASSIGNMENT("^\\w+[ ]*=[ ]*\\w+\\; *$", ScoopPosition.INNER_SCOPE){},
    MULTI_REASSIGNMENT(String.format("^[ ]*[\\w]+(?:[ ]*,[\\w]*){%s}[ ]*=[ ]*[\\w]+(?:[ ]*,[\\w]*){%s}[ ]*\\;"//todo change varibls to all charcter variable(for string)
            , Constants.MULTI_ASSIGNMENT_MINUS_ONE, Constants.MULTI_ASSIGNMENT_MINUS_ONE), ScoopPosition.INNER_SCOPE){};
    //todo bracket or spaces?
    private final ScoopPosition scopPosition;
    private final String regex;
    Sentence(String regex,ScoopPosition scopPosition){
        this.regex=regex;
        this.scopPosition=scopPosition;
    }
    private enum ScoopPosition {
        INNER_SCOPE(false,true),
        OUTTER_SCOPE(true,false),
        BOTH(true,true);
        private final boolean inOutterScope;
        private final boolean inInnerScope;
        ScoopPosition(boolean isOutter, boolean isInner){
            this.inInnerScope =isInner;
            this.inOutterScope =isOutter;
        }
        private boolean isOutter(){
            return inOutterScope;
        }
        private boolean isInner(){
            return inInnerScope;
        }

    }



    private static class Constants {
        private static final String regex = "ff";//todo "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"
        private static final String MULTI_ASSIGNMENT_MINUS_ONE = "MULTI_ASSIGNMENT_MINUS_ONE";
        private static final String IF_STATMENT = "if";
        private static final String WHILE_STATMENT = "while";
    }
//todo  is this the proper way?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
    public static Sentence[] getScoping(ScoopPosition scopPosition){
        int counter=0;
        Sentence[] currentValues=Sentence.values();
        for (Sentence sentence:currentValues) {
            if (sentence.scopPosition==scopPosition){
                counter++;
            }
        }
        Sentence[] sentences=new Sentence[counter];
        counter=0;
        for (Sentence currentValue : currentValues) {
            if (currentValue.scopPosition == scopPosition) {
                sentences[counter] = currentValue;
                counter++;
            }
        }
        return sentences;
    }
}
