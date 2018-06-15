package scopePackage;
import variblePackage.Variable;
import java.util.HashMap;
import java.util.List;

public class Scope {

    private HashMap<String, Variable> variables;
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

    public void addVariable(Variable variable) throws AlreadyAssignedException{
        if (isVariableAssigned(variable)){
            throw new AlreadyAssignedException(variable.getName());
        }
//        variables.add(variable);
    }
}
