package oop.ex6.fileProcessor.scopePackage;
import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Scope {
    HashSet<Variable> assigendInScopeVariables=new HashSet<>();
     HashMap<String,Variable> variables;
     Scope(HashMap<String, Variable> variables){
        this.variables = variables;
    }

    Scope(){
         this.variables=new HashMap<>();
    }

    private boolean isVariableAssigned(Variable variable){
        return (this.variables.containsKey(variable.getName()));
    }
    public void addVariable(Variable variable) throws ScopeException, VariableException {
        if (isVariableAssigned(variable)){
            throw new ScopeException.AlreadyAssignedException(variable.getName());
        }
        if (variable.isValueAssigned()) {
            reAssignVariable(variable, true);
        }
        variables.put(variable.getName(), variable);
    }
    public void reAssignVariable(Variable variable,boolean assignedInThisScope) throws VariableException {
        if (assignedInThisScope){
            assigendInScopeVariables.add(variable);
        }
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    public abstract boolean closeScope(Scope parentScope);

    public void setVariables(HashMap<String, Variable> variables) {
        this.variables = variables;
    }
}
