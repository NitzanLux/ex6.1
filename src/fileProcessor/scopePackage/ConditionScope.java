package fileProcessor.scopePackage;

import fileProcessor.variblePackage.Variable;

import java.util.HashMap;

public class ConditionScope extends Scope{

    ConditionScope(HashMap variables){
        super(variables);
    }

    @Override
    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    @Override
    boolean closeScope() {
        return true;
    }
}
