package scopePackage;
import scopePackage.Scope;
import variblePackage.Variable;

import java.util.HashMap;

class Method extends Scope {

    private String methodName;

     Method(HashMap<String, Variable> variables, String methodName) {
        super(variables, true);
        this.methodName = methodName;
        this.variables = variables;
    }

}
