package scopePackage;

public enum Sentence {
    METHOD("^[ \\t]*(?:\\bvoid\\b){1}[ \\t]+[\\w]+[ \\t]*\\([ \\t]*(?:(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\,[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+)*[ \\t]*)?\\)[ \\t]*\\{[ \\t]*$", ScoopPosition.OUTTER_SCOPE){},
    ASSIGNMENT("^[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?(?:[ \\t]*\\,[ \\t]*(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?[ \\t]*)*[ \\t]*\\;[ \\t]*$", ScoopPosition.BOTH){},//todo updated insertion f!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    IF(String.format("^[ \\t]*%s[ \\t]*\\((?:[ \\t]*\\w*[ \\t]*(?:(?:\\|\\|)|(?:(?:\\!|\\=)\\=)|(?:\\&\\&)|)[ \\t]*\\w*[ \\t]*)+\\)[ \\t]*{[ \\t]*$", Constants.IF_STATMENT), ScoopPosition.INNER_SCOPE){},
    WHILE(String.format("^[ \\t]*%s[ \\t]*\\((?:[ \\t]*\\w*[ \\t]*(?:(?:\\|\\|)|(?:(?:\\!|\\=)\\=)|(?:\\&\\&)|)[ \\t]*\\w*[ \\t]*)+\\)[ \\t]*{[ \\t]*$", Constants.WHILE_STATMENT), ScoopPosition.INNER_SCOPE){},
    METHOD_CALL("^[ \\t]*[\\w]+[ \\t]*\\([ \\t]*(?:[\\w]+[ \\t]*(?:\\,[ \\t]*[\\w]+[ \\t]*)*)?\\)[ \\t]*\\;[ \\t]*$", ScoopPosition.INNER_SCOPE){},
    RETURN("^[ ]*return\\;[ ]*$", ScoopPosition.INNER_SCOPE){},
    REASSIGNMENT("^[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+[ \\t]*(?:\\,[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)*[ \\t]*\\;[ \\t]*$", ScoopPosition.INNER_SCOPE){},
    BLANK_LINE("^(?:\\\\{2}.*)?[ \\t]*$",ScoopPosition.BOTH),
    CLOSE_SCOPE("^[ \\t]*\\{[ \\t]*$",ScoopPosition.BOTH);
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

    public static void main(String[] args) {
        System.out.println("^\\w+[ ]*=[ ]*\\w+\\; *$");
    }
}
//^[ \t]*(?:final )?\b[ \t]*(?!\bfinal\b)[A-Za-z]{2,}[ \t]+(?:(?!\bfinal\b)[\w])+(?:[ \t]*(?:\=[ \t]*(?:(?![\,\=])[\S])+)?[ \t]*(?:\,[ \t]*(?:(?!
//[\,\=])[\S])+)?){1,}[ \t]*\;[ \t]*
