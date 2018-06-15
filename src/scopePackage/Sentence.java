package scopePackage;

public enum Sentence {
    METHOD(Constants.regex, ScoopPosition.OUTTER_SCOPE){},
    ASSIGNMENT(Constants.regex, ScoopPosition.BOTH){},
    MULTI_ASSIGNMENT(Constants.regex, ScoopPosition.BOTH){},
    IF(Constants.regex, ScoopPosition.INNER_SCOPE){},
    WHILE(Constants.regex, ScoopPosition.INNER_SCOPE){},
    METHOD_CALL(Constants.regex, ScoopPosition.INNER_SCOPE){},
    RETURN(Constants.regex, ScoopPosition.INNER_SCOPE){},
    REASSIGNMENT(Constants.regex, ScoopPosition.INNER_SCOPE){},
    MULTI_REASSIGNMENT(Constants.regex, ScoopPosition.INNER_SCOPE){};
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
