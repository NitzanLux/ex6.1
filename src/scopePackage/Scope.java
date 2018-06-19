package scopePackage;
import variblePackage.Variable;
import variblePackage.VariableException;
import java.util.HashMap;
import java.util.LinkedList;

public class Scope {

     HashMap<String,Variable> variables;
     private boolean isMethod;
     private Scope father;

     Scope(HashMap<String, Variable> variables, boolean isMethod, Scope father){
        this.variables = new HashMap<>();
        this.isMethod = isMethod;
        this. father = father;
    }

    private boolean isVariableAssigned(Variable variable){
        return (this.variables.containsKey(variable.getName()));
    }

    public boolean isAllowedHere(String varName){
        //TODO: check if lineType is allowed in specific scope
        Scope current = this.getFather();
        while(current != null){
            if(current.getVariables().containsKey(varName)){
                return true;
            }
            current = current.getFather();
        }
        return false;
    }

    public Variable getVariableUp(String varName){
         //TODO this method looks up for variable named varName, and returns the variable object of its first appearance.
        //todo else it throws an exception.
        Scope current = getFather();
        while(current!=null){
            if(current.getVariables().containsKey(varName)){
                return current.getVariables().get(varName);
            }
        }
        return null;
//        throw new VariableNorFoundException();
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

    public Scope getFather() {
        return father;
    }
}
