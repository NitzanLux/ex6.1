package scopePackage;

import com.sun.xml.internal.ws.server.ServerRtException;
import variblePackage.Variable;

import java.util.HashMap;

public class ConditionScope extends Scope{

    boolean isLegalCondition(String condition){
        //TODO parse and check regex. Also check if variable checked fits value.
        return false;
    }

    private String condition;

    private Scope father;

    private HashMap<String, Variable> variables;

    public ConditionScope(Scope father, HashMap<String, Variable> variables){
        super(variables, false, father);
        ///////////////     //////      ////////         //////
              ///         ///    ///    ///    ///     ///    ///
              ///         ///    ///    ///     ///    ///    ///
              ///         ///    ///    ///     ///    ///    ///
              ///         ///    ///    ///    ///     ///    ///
              ///           //////       ////////        //////
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public Scope getFather() {
        return father;
    }

    @Override
    public HashMap<String, Variable> getVariables() {
        return variables;
    }
}
