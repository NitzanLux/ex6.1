package scopePackage;

import variblePackage.Variable;

import java.util.List;

public class Scope {

    private List<Variable> myVariables;
    private boolean isMethod;

    public Scope(List<Variable> variables, boolean isMethod){
        this.myVariables = variables;
        this.isMethod = isMethod;
    }

    public boolean isVariableAssigned(Variable variable){
        for(Variable var: myVariables){
            if (var.equals(variable)){
                return true;
            }
        }
        return false;
    }

    public boolean isAllowedHere(Sentence sentence){
        //TODO: check if sentence is allowed in specific scope
        return false;
    }

    public void addVariable(Variable variable) throws AlreadyAssignedException{
        if (isVariableAssigned(variable)){
            throw new AlreadyAssignedException(variable.getName());
        }
        myVariables.add(variable);
    }
}
