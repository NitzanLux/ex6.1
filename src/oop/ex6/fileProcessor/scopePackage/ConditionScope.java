package oop.ex6.fileProcessor.scopePackage;

import oop.ex6.fileProcessor.variblePackage.Variable;

import java.util.HashMap;

public class ConditionScope extends Scope{

    ConditionScope(HashMap<String, Variable> variables){
        super(variables);
    }

    @Override
    public HashMap<String, Variable> getVariables() {
        return variables;
    }


}
