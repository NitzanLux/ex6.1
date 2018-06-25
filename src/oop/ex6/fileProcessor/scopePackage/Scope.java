package oop.ex6.fileProcessor.scopePackage;
import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * abstract class scope
 */
public abstract class Scope {

    //data members
    HashSet<Variable> assignedInScopeVariables =new HashSet<>();
     HashMap<String,Variable> variables;

    /**
     * data constructor
     * @param variables variables dataset
     */
     Scope(HashMap<String, Variable> variables){
        this.variables = variables;
    }

    /**default constructor*/
    Scope(){
         this.variables=new HashMap<>();
    }

    /**
     * check if variable assigned
     * @param variable variable to check
     * @return true if yes
     */
    private boolean isVariableAssigned(Variable variable){
        return (this.variables.containsKey(variable.getName()));
    }

    /**
     * adds variable to scope data set
     * @param variable we want to add
     * @throws ScopeException in case variable already assigned
     */
    public void addVariable(Variable variable) throws ScopeException, VariableException {
        if (isVariableAssigned(variable)){
            throw new ScopeException.AlreadyAssignedException(variable.getName());
        }
        if (variable.isValueAssigned()) {
            reAssignVariable(variable, true);
        }
        variables.put(variable.getName(), variable);
    }

    /**
     * reassignment of variable
     * @param variable variable we want to assign
     * @param assignedInThisScope if the variable is assigned in scope
     */
    public void reAssignVariable(Variable variable,boolean assignedInThisScope) {
        if (assignedInThisScope){
            assignedInScopeVariables.add(variable);
        }
    }

    /**
     * @return variables
     */
    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    /**
     * @return if we can close the scope
     */
    public abstract boolean closeScope(Scope parentScope);

}
