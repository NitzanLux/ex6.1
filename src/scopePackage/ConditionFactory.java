package scopePackage;

import variblePackage.Variable;
import variblePackage.VariableType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ConditionFactory {

    private String line;

    private ConditionScope conditionScope;

    private static ConditionFactory instance = new ConditionFactory();

    ///////////////     //////      ////////         //////
          ///         ///    ///    ///    ///     ///    ///
          ///         ///    ///    ///     ///    ///    ///
          ///         ///    ///    ///     ///    ///    ///
          ///         ///    ///    ///    ///     ///    ///
          ///           //////      ////////         //////

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
    private List<String> parseCondition(String line){
        String l = line.split("\\{")[0];
        l = l.split("\\(")[1];
        l = l.split("\\)")[0];
        String[] vars = l.split("\\||");
        List<String[]> variables = new LinkedList<>();
        for(String s: vars){
            variables.add(s.split("&&"));
        }
        List<String> varis = new LinkedList<>();
        for(String[] strings: variables){
            for(String s: strings){
                varis.add(s.replace(" ", ""));
            }
        }
        return varis;
    }

    private boolean areVariablesLegit(List<String> variables){
        Scope mama = this.conditionScope.getFather();
        HashMap<String, Variable> fatherVars = mama.getVariables();
        VariableType[] legalTypes = {VariableType.INTEGER, VariableType.BOOLEAN, VariableType.BOOLEAN};
        while(mama != null){
            for(String variable: variables){
                if(fatherVars.containsKey(variable)){
                    for(VariableType type: legalTypes){
                        if(fatherVars.get(variable).getVariableType().equals(type)){
                            return true;
                        }
                    }
                }
            }
            mama = mama.getFather();
            fatherVars = mama.getVariables();
        }
        //TODO check also in global variables
        return false;
    }

    public void setConditionScope(ConditionScope conditionScope){
        this.conditionScope = conditionScope;
    }

    public ConditionScope getConditionScope() {
        return conditionScope;
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
