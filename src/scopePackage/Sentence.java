package scopePackage;

import java.util.regex.Pattern;

public enum Sentence {
    METHOD(Constants.METHOD_REGEX_STR, ScoopPosition.OUTTER_SCOPE)
            {public boolean processSentence(String line){
                return true;//todo kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkpop
            }},
    ASSIGNMENT(Constants.ASSIGNMENT_REGEX_STR, ScoopPosition.BOTH){},
    IF(String.format(Constants.CONDITION_REGEX_STR, Constants.IF_STATMENT), ScoopPosition.INNER_SCOPE){},
    WHILE(String.format(Constants.CONDITION_REGEX_STR, Constants.WHILE_STATMENT), ScoopPosition.INNER_SCOPE){},
    METHOD_CALL(Constants.METHOD_CALL_REGEX_STR, ScoopPosition.INNER_SCOPE){},
    RETURN(Constants.RETURN_REGEX_STR, ScoopPosition.INNER_SCOPE){},
    REASSIGNMENT(Constants.REASSIGNMENT_REGEX_STR, ScoopPosition.INNER_SCOPE){},
    BLANK_LINE(Constants.BLANK_LINE_REGEX_STR,ScoopPosition.BOTH),
    CLOSE_SCOPE(Constants.CLOSE_SCOPE_REGEX_STR,ScoopPosition.BOTH);
    //todo bracket or spaces?
    private final ScoopPosition scopPosition;
    private final Pattern regex;
    Sentence(String regex,ScoopPosition scopPosition){
        this.regex=Pattern.compile(regex);
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
    public abstract boolean processSentence(String line);




    private static class Constants {
        private static final String IF_STATMENT = "if";
        private static final String WHILE_STATMENT = "while";
        private static final String RETURN_REGEX_STR = "^[ ]*return\\;[ ]*$";
        private static final String METHOD_CALL_REGEX_STR = "^[ \\t]*[\\w]+[ \\t]*\\([ \\t]*(?:[\\w]+[ \\t]*(?:\\,[ \\t]*[\\w]+[ \\t]*)*)?\\)[ \\t]*\\;[ \\t]*$";
        private static final String CONDITION_REGEX_STR = "^[ \\t]*%s[ \\t]*\\((?:[ \\t]*\\w*[ \\t]*(?:(?:\\|\\|)|(?:(?:\\!|\\=)\\=)|(?:\\&\\&)|)[ \\t]*\\w*[ \\t]*)+\\)[ \\t]*{[ \\t]*$";
        private static final String ASSIGNMENT_REGEX_STR = "^[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?(?:[ \\t]*\\,[ \\t]*(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?[ \\t]*)*[ \\t]*\\;[ \\t]*$";
        private static final String METHOD_REGEX_STR = "^[ \\t]*(?:\\bvoid\\b){1}[ \\t]+[\\w]+[ \\t]*\\([ \\t]*(?:(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\,[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+)*[ \\t]*)?\\)[ \\t]*\\{[ \\t]*$";
        private static final String REASSIGNMENT_REGEX_STR = "^[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+[ \\t]*(?:\\,[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)*[ \\t]*\\;[ \\t]*$";
        private static final String BLANK_LINE_REGEX_STR = "^(?:\\\\{2}.*)?[ \\t]*$";
        private static final String CLOSE_SCOPE_REGEX_STR = "^[ \\t]*\\}[ \\t]*$";
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
//IF ^[ \t]*%s[ \t]*\((?:[ \t]*\w*[ \t]*(?:(?:\|\|)|(?:(?:\!|\=)\=)|(?:\&\&)|)[ \t]*\w*[ \t]*)+\)[ \t]*{[ \t]*$