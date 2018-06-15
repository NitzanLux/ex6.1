package scopePackage;
import variblePackage.Variable;
import java.util.HashMap;
import java.util.List;

public class Scope {

    private HashMap variables;
    private boolean isMethod;

    public Scope(List<Variable> variables, boolean isMethod){
        this.variables = new HashMap<String,Variable>();
        this.isMethod = isMethod;
    }

//    public boolean isVariableAssigned(Variable variable){
//        for(Variable var: globalVariables){
//            if (var.equals(variable)){
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isAllowedHere(Sentence sentence){
        //TODO: check if sentence is allowed in specific scope
        return false;
    }

//    public void addVariable(Variable variable) throws AlreadyAssignedException{
//        if (!isVariableAssigned(variable)){
//            globalVariables.add(variable);
//        }
//        variables.add(variable);
//    }
}
