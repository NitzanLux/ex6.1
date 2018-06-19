package scopePackage;
import main.LineType;
import variblePackage.Variable;
import variblePackage.VariableException;
import java.util.HashMap;
import java.util.List;

public class Scope {

     HashMap<String,Variable> variables;
     private boolean isMethod;

     Scope(HashMap<String, Variable> variables, boolean isMethod){
        this.variables = new HashMap<>();
        this.isMethod = isMethod;
    }

    private boolean isVariableAssigned(Variable variable){
        return (this.variables.containsKey(variable.getName()));
    }

    public boolean isAllowedHere(LineType lineType){
        //TODO: check if lineType is allowed in specific scope
        return false;
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
}
