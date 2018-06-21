package fileProcessor;


import fileProcessor.scopePackage.ScopeException;
import fileProcessor.variblePackage.Variable;
import fileProcessor.variblePackage.VariableException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LineType {
    METHOD(Constants.METHOD_REGEX_STR, ScoopPosition.OUTTER_SCOPE)
            {public boolean processSentence(String line, FileAnalyzer fileAnalyzer)
                    throws VariableException {
                if (this.isMatch(line)){
                    fileAnalyzer.getMethodFactory().createMethod(line);
                    return true;
                }
                return false;
            }},
    ASSIGNMENT(Constants.ASSIGNMENT_REGEX_STR, ScoopPosition.BOTH){
        public boolean processSentence(String line, FileAnalyzer fileAnalyzer)
                throws VariableException, ScopeException {
            if (this.isMatch(line)){
                fileAnalyzer.variableFactory.makeAssignment(line);
                return true;
            }
            return false;
        }
    },
    IF(String.format(Constants.CONDITION_REGEX_STR, Constants.IF_STATMENT), ScoopPosition.INNER_SCOPE){
        @Override
        public boolean processSentence(String line, FileAnalyzer fileAnalyzer) throws ScopeException {
            if (this.isMatch(line)){
                fileAnalyzer.conditionFactory.assignScope(line);
                return true;
            }
            return false;
        }
    },
    WHILE(String.format(Constants.CONDITION_REGEX_STR, Constants.WHILE_STATMENT), ScoopPosition.INNER_SCOPE){
        @Override
        public boolean processSentence(String line,FileAnalyzer fileAnalyzer) throws ScopeException {
            return IF.processSentence(line,fileAnalyzer);
        }
    },
    METHOD_CALL(Constants.METHOD_CALL_REGEX_STR, ScoopPosition.INNER_SCOPE){
        @Override
        public boolean processSentence(String line, FileAnalyzer fileAnalyzer) throws ScopeException {
            if (this.isMatch(line)){
                fileAnalyzer.methodFactory.methodCall(line);
                return true;
            }
            return false;
        }
    },
    RETURN(Constants.RETURN_REGEX_STR, ScoopPosition.INNER_SCOPE){
        @Override
        public boolean processSentence(String line,FileAnalyzer fileAnalyzer) {
            if (this.isMatch(line)){
                fileAnalyzer.methodFactory.methodReturn();
                return true;
            }
            return false;
        }
    },
    REASSIGNMENT(Constants.REASSIGNMENT_REGEX_STR, ScoopPosition.INNER_SCOPE){
        @Override
        public boolean processSentence(String line,FileAnalyzer fileAnalyzer) {
            if (this.isMatch(line)){
                fileAnalyzer.variableFactory.
                return true;
            }
            return false;
        }
    },
    BLANK_LINE(Constants.BLANK_LINE_REGEX_STR,ScoopPosition.BOTH) {
        @Override
        public boolean processSentence(String line) {
            return this.isMatch(line);
        }
    },
    CLOSE_SCOPE(Constants.CLOSE_SCOPE_REGEX_STR,ScoopPosition.BOTH) {
        @Override
        public boolean processSentence(String line) {
            if (this.isMatch(line)){

                return true;
            }
            return false;
        }
    };
    //todo bracket or spaces?
    private final ScoopPosition scopePosition;
    private final Pattern regex;
    LineType(String regex, ScoopPosition scopePosition){
        this.regex=Pattern.compile(regex);
        this.scopePosition = scopePosition;
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
    public abstract boolean processSentence(String line, FileAnalyzer fileAnalyzer) throws VariableException,
            ScopeException;
    boolean isMatch(String line){
        Matcher matcher=this.regex.matcher(line);
        return matcher.matches();
    }



    private static class Constants {
        private static final String IF_STATMENT = "if";
        private static final String WHILE_STATMENT = "while";
        private static final String RETURN_REGEX_STR = "^[ ]*return\\;[ ]*$";
        private static final String METHOD_CALL_REGEX_STR = "^[ \\t]*[\\w]+[ \\t]*\\([ \\t]*(?:[\\w]+[ \\t]*(?:\\,[ \\t]*[\\w]+[ \\t]*)*)?\\)[ \\t]*\\;[ \\t]*$";
        private static final String CONDITION_REGEX_STR = "^[ \\t]*%s[ \\t]*\\([ \\t]*\\w+(?:\\.?\\w+)?[ \\t]*(?:(?:(?:\\|\\|)|(?:\\&\\&))[ \\t]*\\w+(?:\\.?\\w+)?[ \\t]*)*\\)[ \\t]*{[ \\t]*$";
        private static final String ASSIGNMENT_REGEX_STR = "^[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?(?:[ \\t]*\\,[ \\t]*(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?[ \\t]*)*[ \\t]*\\;[ \\t]*$";
        private static final String METHOD_REGEX_STR = "^[ \\t]*(?:\\bvoid\\b){1}[ \\t]+[\\w]+[ \\t]*\\([ \\t]*(?:(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\,[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+)*[ \\t]*)?\\)[ \\t]*\\{[ \\t]*$";
        private static final String REASSIGNMENT_REGEX_STR = "^[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+[ \\t]*(?:\\,[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)*[ \\t]*\\;[ \\t]*$";
        private static final String BLANK_LINE_REGEX_STR = "^(?:\\\\{2}.*)?[ \\t]*$";
        private static final String CLOSE_SCOPE_REGEX_STR = "^[ \\t]*\\}[ \\t]*$";
    }
    //todo  is this the proper way?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
    public static LineType[] getScoping(ScoopPosition scopPosition){
        //todo why i need this method.
        int counter=0;
        LineType[] currentValues= LineType.values();
        for (LineType sentence:currentValues) {
            if (sentence.scopePosition ==scopPosition){
                counter++;
            }
        }
        LineType[] sentences=new LineType[counter];
        counter=0;
        for (LineType currentValue : currentValues) {
            if (currentValue.scopePosition == scopPosition) {
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


//package fileProcessor;
//
//
//import fileProcessor.scopePackage.ScopeException;


//import fileProcessor.variblePackage.VariableException;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public enum LineType {
//    METHOD(Constants.METHOD_REGEX_STR, ScoopPosition.OUTTER_SCOPE)
//            {public boolean processSentence(String line, FileAnalyzer fileFactory)
//                    throws VariableException, ScopeException.NoParentException {
//                if (this.isMatch(line)){
//                    fileFactory.getMethodFactory().createMethod(line);
//                    return true;
//    //TODO: FileAnalyzer object.
//    METHOD(Constants.METHOD_REGEX_STR, ScoopPosition.OUTTER_SCOPE)
//            {public boolean processSentence(String line){
//                if (this.isMatch(line)){
//                    /////TODO
//                }
//                return false;
//            }},
//    ASSIGNMENT(Constants.ASSIGNMENT_REGEX_STR, ScoopPosition.BOTH){
//        public boolean processSentence(String line){
//            if (this.isMatch(line)){
//
//        public boolean processSentence(String line) {
//            if (this.isMatch(line)){
//                /////TODO
//                return true;
//            }
//            return false;
//        }
//    },
//    IF(String.format(Constants.CONDITION_REGEX_STR, Constants.IF_STATMENT), ScoopPosition.INNER_SCOPE){
//        @Override
//        public boolean processSentence(String line) {
//            if (this.isMatch(line)){
//                /////TODO
//
//                return true;
//            }
//            return false;
//        }
//    },
//    WHILE(String.format(Constants.CONDITION_REGEX_STR, Constants.WHILE_STATMENT), ScoopPosition.INNER_SCOPE){
//        @Override
//        public boolean processSentence(String line) {
//            if (this.isMatch(line)){
//
//                /////TODO
//                return true;
//            }
//            return false;
//        }
//    },
//    METHOD_CALL(Constants.METHOD_CALL_REGEX_STR, ScoopPosition.INNER_SCOPE){
//        @Override
//        public boolean processSentence(String line) {
//            if (this.isMatch(line)){
//
//                /////TODO
//                return true;
//            }
//            return false;
//        }
//    },
//    RETURN(Constants.RETURN_REGEX_STR, ScoopPosition.INNER_SCOPE){
//        @Override
//        public boolean processSentence(String line) {
//            if (this.isMatch(line)){
//
//                /////TODO
//                return true;
//            }
//            return false;
//        }
//    },
//    REASSIGNMENT(Constants.REASSIGNMENT_REGEX_STR, ScoopPosition.INNER_SCOPE){
//        @Override
//        public boolean processSentence(String line) {
//            if (this.isMatch(line)){
//
//                /////TODO
//                return true;
//            }
//            return false;
//        }
//    },
//    BLANK_LINE(Constants.BLANK_LINE_REGEX_STR,ScoopPosition.BOTH) {
//        @Override
//        public boolean processSentence(String line) {
//            /////TODO
//            return this.isMatch(line);
//        }
//    },
//    CLOSE_SCOPE(Constants.CLOSE_SCOPE_REGEX_STR,ScoopPosition.BOTH) {
//        @Override
//        public boolean processSentence(String line) {
//            if (this.isMatch(line)){
//
//                /////TODO
//                return true;
//            }
//            return false;
//        }
//    };
//    //todo bracket or spaces?
//    private final ScoopPosition scopePosition;
//    private final Pattern regex;
//    LineType(String regex, ScoopPosition scopePosition){
//        this.regex=Pattern.compile(regex);
//        this.scopePosition = scopePosition;
//    }
//    enum ScoopPosition {
//        INNER_SCOPE(false,true),
//        OUTTER_SCOPE(true,false),
//        BOTH(true,true);
//        private final boolean inOutterScope;
//        private final boolean inInnerScope;
//        ScoopPosition(boolean isOutter, boolean isInner){
//            this.inInnerScope =isInner;
//            this.inOutterScope =isOutter;
//        }
//        private boolean isOutter(){
//            return inOutterScope;
//        }
//        private boolean isInner(){
//            return inInnerScope;
//        }
//
//    }
//    public abstract boolean processSentence(String line, FileAnalyzer fileFactory) throws VariableException, ScopeException.NoParentException;
//    public abstract boolean processSentence(String line);
//    boolean isMatch(String line){
//        Matcher matcher=this.regex.matcher(line);
//        return matcher.matches();
//    }
////    private boolean conditionProcess()
//
//
//
//    private static class Constants {
//        private static final String IF_STATMENT = "if";
//        private static final String WHILE_STATMENT = "while";
//        private static final String RETURN_REGEX_STR = "^[ ]*return\\;[ ]*$";
//        private static final String METHOD_CALL_REGEX_STR = "^[ \\t]*[\\w]+[ \\t]*\\([ \\t]*(?:[\\w]+[ \\t]*(?:\\,[ \\t]*[\\w]+[ \\t]*)*)?\\)[ \\t]*\\;[ \\t]*$";
//        private static final String CONDITION_REGEX_STR = "^[ \\t]*%s[ \\t]*\\([ \\t]*\\w+(?:\\.?\\w+)?[ \\t]*(?:(?:(?:\\|\\|)|(?:\\&\\&))[ \\t]*\\w+(?:\\.?\\w+)?[ \\t]*)*\\)[ \\t]*{[ \\t]*$";
//        private static final String ASSIGNMENT_REGEX_STR = "^[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?(?:[ \\t]*\\,[ \\t]*(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?[ \\t]*)*[ \\t]*\\;[ \\t]*$";
//        private static final String METHOD_REGEX_STR = "^[ \\t]*(?:\\bvoid\\b){1}[ \\t]+[\\w]+[ \\t]*\\([ \\t]*(?:(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\,[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+)*[ \\t]*)?\\)[ \\t]*\\{[ \\t]*$";
//        private static final String REASSIGNMENT_REGEX_STR = "^[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+[ \\t]*(?:\\,[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)*[ \\t]*\\;[ \\t]*$";
//        private static final String BLANK_LINE_REGEX_STR = "^(?:\\\\{2}.*)?[ \\t]*$";
//        private static final String CLOSE_SCOPE_REGEX_STR = "^[ \\t]*\\}[ \\t]*$";
//    }
////todo  is this the proper way?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//    public static LineType[] getScoping(ScoopPosition scopPosition){
//     //todo why i need this method.
//        private static final String METHOD_CALL_REGEX_STR = "^[ \\t]*[\\w]+[ \\t]*\\([ \\t]*(?:[\\w]+" +
//                "[ \\t]*(?:\\,[ \\t]*[\\w]+[ \\t]*)*)?\\)[ \\t]*\\;[ \\t]*$";
//        private static final String CONDITION_REGEX_STR = "^[ \\t]*%s[ \\t]*\\([ \\t]*\\w+(?:\\.?\\w+)" +
//                "?[ \\t]*(?:(?:(?:\\|\\|)|(?:\\&\\&))[ \\t]*\\w+(?:\\.?\\w+)?[ \\t]*)*\\)[ \\t]*{[ \\t]*$";
//        private static final String ASSIGNMENT_REGEX_STR = "^[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)" +
//                "[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?" +
//                "(?:[ \\t]*\\,[ \\t]*(?:(?!\\bfinal\\b)[\\w])+(?:[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)?" +
//                "[ \\t]*)*[ \\t]*\\;[ \\t]*$";
//        private static final String METHOD_REGEX_STR = "^[ \\t]*(?:\\bvoid\\b){1}[ \\t]+[\\w]+[ \\t]*\\" +
//                "([ \\t]*(?:(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+(?:(?!\\bfinal\\b)" +
//                "[\\w])+(?:[ \\t]*\\,[ \\t]*(?:final )?\\b[ \\t]*(?:(?!\\bfinal\\b)[A-Za-z]){2,}[ \\t]+" +
//                "(?:(?!\\bfinal\\b)[\\w])+)*[ \\t]*)?\\)[ \\t]*\\{[ \\t]*$";
//        private static final String REASSIGNMENT_REGEX_STR = "^[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)" +
//                "[\\S])+[ \\t]*(?:\\,[ \\t]*\\w+[ \\t]*\\=[ \\t]*(?:(?!\\=|\\,)[\\S])+)*[ \\t]*\\;[ \\t]*$";
//        private static final String BLANK_LINE_REGEX_STR = "^(?:\\\\{2}.*)?[ \\t]*$";
//        private static final String CLOSE_SCOPE_REGEX_STR = "^[ \\t]*\\}[ \\t]*$";
//    }
//    //todo  is this the proper way???????????????????????????????????????????????????????????????
//    public static LineType[] getScoping(ScoopPosition scopPosition){
//        //todo why i need this method.
//        int counter=0;
//        LineType[] currentValues= LineType.values();
//        for (LineType sentence:currentValues) {
//            if (sentence.scopePosition ==scopPosition){
//                counter++;
//            }
//        }
//        LineType[] sentences=new LineType[counter];
//        counter=0;
//        for (LineType currentValue : currentValues) {
//            if (currentValue.scopePosition == scopPosition) {
//                sentences[counter] = currentValue;
//                counter++;
//            }
//        }
//        return sentences;
//    }
//
//    public static void main(String[] args) {
//        System.out.println("^\\w+[ ]*=[ ]*\\w+\\; *$");
//    }
//}
//}
////^[ \t]*(?:final )?\b[ \t]*(?!\bfinal\b)[A-Za-z]{2,}[ \t]+(?:(?!\bfinal\b)[\w])+(?:[ \t]*(?:\=[ \t]*(?:(?![\,\=])[\S])+)?[ \t]*(?:\,[ \t]*(?:(?!
////[\,\=])[\S])+)?){1,}[ \t]*\;[ \t]*
////IF ^[ \t]*%s[ \t]*\((?:[ \t]*\w*[ \t]*(?:(?:\|\|)|(?:(?:\!|\=)\=)|(?:\&\&)|)[ \t]*\w*[ \t]*)+\)[ \t]*{[ \t]*$