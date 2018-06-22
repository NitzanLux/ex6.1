package oop.ex6.fileProcessor.scopePackage;
import oop.ex6.fileProcessor.variblePackage.Variable;

import java.util.HashMap;

class Method extends Scope {

    private String methodName;

    private boolean isReturn=false;
    /**constructor*/
     Method(HashMap<String, Variable> variables, String methodName) {
         super(variables);
        this.methodName = methodName;
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
