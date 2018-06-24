package oop.ex6.fileProcessor.scopePackage;
import oop.ex6.fileProcessor.LineType;
import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import oop.ex6.fileProcessor.variblePackage.VariableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Method extends Scope {

    private static final Pattern namePattern=Pattern.compile("^[a-zA-Z][\\w]*$");
    private String methodName;
    private ArrayList<VariableType> variableTypesInOrder=new ArrayList<>();
    private boolean isReturn=false;
    /**constructor*/
     Method(HashMap<String, Variable> variables, String methodName,ArrayList<VariableType> variableTypesInOrder)
             throws ScopeException.IllegalMethodNameException {
         super(variables);
        setMethodName(methodName);
        this.variableTypesInOrder=variableTypesInOrder;
    }


    String getMethodName() {
        return methodName;
    }

    void setReturn(){
         isReturn=true;
    }
    @Override
    public boolean closeScope() {
        super.closeScope();
        return isReturn;
    }
    boolean variblesFitToMethodType(int position,VariableType variableType){
        return variableTypesInOrder.get(position)==variableType;
    }

    int getVariableAmmoune(){
        return variableTypesInOrder.size();
    }
    private void setMethodName(String methodName) throws ScopeException.IllegalMethodNameException {
        Matcher matcher=namePattern.matcher(methodName);
        if (matcher.matches()){
            this.methodName=methodName;
        }else {
            throw new ScopeException.IllegalMethodNameException();
        }

    }


    static class MethodCalld {
        String methodLine;

        HashMap<String,Variable> currentVariabls;

        MethodCalld(String methodLine, HashMap<String, Variable> currentVariabls) {
            this.methodLine = methodLine;
            this.currentVariabls = currentVariabls;
        }

        HashMap<String, Variable> getCurrentVariabls() {
            return currentVariabls;
        }

        String getMethodLine() {
            return methodLine;
        }
        void updateGlobalVariabls(HashMap<String, Variable> globalVariabls){
            for (Variable variable:globalVariabls.values()) {
                if (!currentVariabls.containsValue(variable)){
                    currentVariabls.put(variable.getName(),variable);
                }
            }
        }

    }
}
