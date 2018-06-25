package oop.ex6.fileProcessor.scopePackage;

import oop.ex6.fileProcessor.variblePackage.Variable;

import java.util.HashMap;

/**
 * class represents a condition (if/while)
 * inherits from scope
 */
public class ConditionScope extends Scope{

    /**
     * default constructor
     * @param variables dataset containing all variables declared in condition scope
     */
    ConditionScope(HashMap<String, Variable> variables){
        super(variables);
    }

    /**
     * getter for variables
     * @return variable dataset
     */
    @Override
    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    /**
     * override method, because condition scope is always closable (dont need a return).
     * @return true always.
     */
    @Override
    public boolean closeScope(Scope parentScope) {
        parentScope.assignedInScopeVariables.addAll(assignedInScopeVariables);
        return true;
    }
}
