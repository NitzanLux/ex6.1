package scopePackage;

import java.util.HashMap;
import java.util.List;

public class ConditionFactory {

    private String line;

    private static ConditionFactory instance = new ConditionFactory();

    ////////////////     //////      ////////         //////
          ///          ///    ///    ///    ///     ///    ///
          ///          ///    ///    ///     ///    ///    ///
          ///          ///    ///    ///     ///    ///    ///
          ///          ///    ///    ///    ///     ///    ///
          ///            //////       ////////        //////

    private ConditionFactory(){

    }

    public static ConditionFactory getInstance() {
        return instance;
    }

    private String[] parseCondition(){
        String l = line.split("\\{")[0];
        l = l.split("\\(")[1];
        l = l.split("\\)")[0];
        return l.split(" ");
    }

//    private List<String[]> splitByVar(String[] strings){
//
//    }

}
