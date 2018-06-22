package oop.ex6.fileProcessor.scopePackage;
import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import java.util.HashMap;

public abstract class Scope {

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

    public void addVariable(Variable variable) throws ScopeException {
        if (isVariableAssigned(variable)){
            throw new ScopeException.AlreadyAssignedException(variable.getName());
        }
        variables.put(variable.getName(), variable);
    }
    public void reAssignVariable(String variableName,String value) throws VariableException {
        Variable variable=variables.get(variableName);
        if (variable!=null){
            variable.assignVariable(value);
        }
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    abstract boolean closeScope();

    public void setVariables(HashMap<String, Variable> variables) {
        this.variables = variables;
    }
}
