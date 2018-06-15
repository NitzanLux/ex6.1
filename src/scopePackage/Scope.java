package scopePackage;
import variblePackage.Variable;
import variblePackage.VariableException;

import java.util.HashMap;
import java.util.List;

public class Scope {

    private HashMap<String,Variable> variables;
    private boolean isMethod;

    public Scope(List<Variable> variables, boolean isMethod){
        this.variables = new HashMap<>();
        this.isMethod = isMethod;
    }

    public boolean isVariableAssigned(Variable variable){
        return (this.variables.containsKey(variable.getName()));
    }

    public boolean isAllowedHere(Sentence sentence){
        //TODO: check if sentence is allowed in specific scope
        return false;
    }

    public void addVariable(Variable variable) throws ScopeException {
        if (isVariableAssigned(variable)){
            throw new ScopeException.AlreadyAssignedException(variable.getName());
        }
//        variables.add(variable);
    }
    public void reAssigneVariable(String variableName,String value) throws VariableException {
        Variable variable=variables.get(variableName);
        if (variable!=null){
            variable.assignVariable(value);
        }
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }
}
