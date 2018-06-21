package fileProcessor.scopePackage;
import fileProcessor.variblePackage.Variable;

import java.util.HashMap;

public class Method extends Scope {

    private String methodName;

    private boolean isReturn=false;
    Method(HashMap<String, Variable> variables, String methodName) {
        super(variables, null);
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

    String getMethodName() {
        return methodName;
    }

    void setReturn(){
         isReturn=true;
    }
    @Override
    boolean closeScope() {
        return isReturn;
    }
}
