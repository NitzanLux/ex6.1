package scopePackage;

import java.util.HashMap;
import java.util.List;

public class ConditionFactory {

    private String line;

    private static ConditionFactory instance = new ConditionFactory();

    ///////////////     //////      ////////         //////
          ///         ///    ///    ///    ///     ///    ///
          ///         ///    ///    ///     ///    ///    ///
          ///         ///    ///    ///     ///    ///    ///
          ///         ///    ///    ///    ///     ///    ///
          ///           //////       ////////        //////

    private ConditionFactory(){

    }

    public static ConditionFactory getInstance() {
        return instance;
    }

    private String[] parseCondition(){
        String l = line.split("\\{")[0];
        l = l.split("\\(")[1];
        l = l.split("\\)")[0];
        String[] vars = l.split("||");
        List<String[]>
        for(String s: vars){
            s = s.split("&&");
        }
        return l.split(" ");
    }

    private boolean isLegalCondition(String[] condition){
        if()
    }

//    private List<String[]> splitByVar(String[] strings){
//
//    }
    //if(nitzan == 3){


}
