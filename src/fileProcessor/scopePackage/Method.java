package fileProcessor.scopePackage;
import fileProcessor.variblePackage.Variable;

import java.util.HashMap;

public class Method extends Scope {

    private String methodName;
    private boolean isReturn=false;
    /**constructor*/
     Method(HashMap<String, Variable> variables, String methodName) {
        super(variables);

         ///////////////     //////      ////////         //////
               ///         ///    ///    ///    ///     ///    ///
               ///         ///    ///    ///     ///    ///    ///
               ///         ///    ///    ///     ///    ///    ///
               ///         ///    ///    ///    ///     ///    ///
               ///           //////      ////////         //////

        this.methodName = methodName;
        this.variables = variables;
    }


    public String getMethodName() {
        return methodName;
    }


}
