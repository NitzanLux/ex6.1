package oop.ex6.fileProcessor.scopePackage;

import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * this we call when we get a condition line.
 * it checks everything is fine with the condition, then sticks the condition in the head of the stack.
 */
public class ConditionFactory {

    //the file object representing the file we work on
    private File file;

    /**
     * constructor
     * @param file the file object representing the file we work on
     */
     public ConditionFactory(File file){
        this.file=file;
     }

    /**
     * gets variables from parentouses.
     * @param line code line
     * @return a list of strings representing the variabes in the parentouses
     */
    private List<String> getVariables(String line){
        String l = line.split("\\{")[0];
        l = l.split("\\(")[1];
        l = l.split("\\)")[0];
        String[] vars = l.split("(?:\\|\\|)|(?:&&)");
        List<String> variables = new LinkedList<>();
        for(String s: vars){
            variables.add(s.replaceAll("\\s++", ""));
        }
        return variables;
    }

    /**
     * checks if variables are legal to assign
     * @param variables string list of variables
     * @return true if variables are legal
     */
    private boolean areVariablesLegit(List<String> variables){
        for(String variable: variables){
            VariableType variableType= VariableType.getVariableType(variable);
            if (variableType==null){
                if (!file.isVariableAssigned(variable)){
                    return false;
                }else {
                    Variable currentVariable = file.getVariable(variable);
                    if (!currentVariable.isValueAssigned()){
                        return false;
                    }
                    variableType=currentVariable.getVariableType();
                }
                if (!VariableType.isTypeIsParsable(VariableType.BOOLEAN,variableType)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * assigns new scope drives from line
     * @param line code line
     * @throws ScopeException if line is not legal
     */
    public void assignScope(String line) throws ScopeException {
        if(areVariablesLegit(getVariables(line))){
            HashMap<String, Variable> variables = new HashMap<>();
            ConditionScope conditionScope = new ConditionScope(variables);
            file.addScope(conditionScope);
        }
        else {
            throw new ScopeException.IllegalConditionOperatorException ();
        }
    }

}
