package oop.ex6.fileProcessor.scopePackage;
import oop.ex6.fileProcessor.LineType;
import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableType;

import java.util.ArrayList;
import java.util.HashMap;

class Method extends Scope {

    private String methodName;
    private ArrayList<VariableType> variableTypesInOrder=new ArrayList<>();
    private boolean isReturn=false;
    /**constructor*/
     Method(HashMap<String, Variable> variables, String methodName,ArrayList<VariableType> variableTypesInOrder) {
         super(variables);
        this.methodName = methodName;
        this.variableTypesInOrder=variableTypesInOrder;
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
    boolean variblesFitToMethodType(int position,VariableType variableType){
        return variableTypesInOrder.get(position)==variableType;
    }
    public static class MethodCalld {
        String methodLine;
        HashMap<String,Variable> currentVariabls;

        MethodCalld(String methodLine, HashMap<String, Variable> currentVariabls) {
            this.methodLine = methodLine;
            this.currentVariabls = currentVariabls;
        }

        public HashMap<String, Variable> getCurrentVariabls() {
            return currentVariabls;
        }

        public String getMethodLine() {
            return methodLine;
        }
    }
    int getVariableAmmoune(){
        return variableTypesInOrder.size();
    }
}
