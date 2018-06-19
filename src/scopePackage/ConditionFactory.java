package scopePackage;

import java.util.LinkedList;
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

    /**
     *
     * @param line
     * @return
     */
    private List<String[]> parseCondition(String line){
        String l = line.split("\\{")[0];
        l = l.split("\\(")[1];
        l = l.split("\\)")[0];
        String[] vars = l.split("\\||");
        List<String[]> variables = new LinkedList<>();
        for(String s: vars){
            variables.add(s.split("&&"));
        }
        for(String[] strings: variables){
            for(int i = 0; i < strings.length; i++){
                strings[i] = strings[i].replace(" ", "");
            }
        }

        return variables;
    }

//    private boolean isLegalCondition(String[] variables){
//        for(String var: variables){
//            if(instance.isAllowedHere(var)){
//                if()
//            }
//        }
//    }

//    private List<String[]> splitByVar(String[] strings){
//
//    }
    //if(nitzan == 3){


}
