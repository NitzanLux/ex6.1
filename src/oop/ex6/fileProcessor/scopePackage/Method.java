package oop.ex6.fileProcessor.scopePackage;

import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * method class. inherit scope
 */
class Method extends Scope {

    //--constants--//
    private static final Pattern namePattern=Pattern.compile("^[a-zA-Z][\\w]*$");
    //--Data Members--//
    private String methodName;
    private ArrayList<VariableType> variableTypesInOrder;
    private boolean isReturn=false;

    /**
     * constructor
     * @param variables variables in dataset
     * @param methodName name of method
     * @param variableTypesInOrder methods variables in order they are declared
     */
    Method(HashMap<String, Variable> variables, String methodName,ArrayList<VariableType> variableTypesInOrder)
             throws ScopeException.IllegalMethodNameException {
         super(variables);
        setMethodName(methodName);
        this.variableTypesInOrder=variableTypesInOrder;
    }

    /**
     * gets the name of method
     * @return name of method
     */
    String getMethodName() {
        return methodName;
    }

    /**sets return if return line found in scope*/
    void setReturn(){
         isReturn=true;
    }


    /**closes scope
     * @return isreturn
     */
    @Override
    public boolean closeScope(Scope parent) {
        for (Variable variable: assignedInScopeVariables) {
            variable.reStoreVariable();
        }
        return isReturn;
    }

    /**
     * checks if variables positions fit
     * @param position the position of variable
     * @param variableType the type of variable
     * @return true if the type fits in the same position
     */
    boolean variblesFitToMethodType(int position, VariableType variableType){
        return variableTypesInOrder.get(position)==variableType;
    }

    /**
     * @return the ammount of variables
     */
    int getVariableAmmount(){
        return variableTypesInOrder.size();
    }

    /**
     * set name for the method only if the name is legal
     * @param methodName name for method
     * @throws ScopeException.IllegalMethodNameException if name illegal
     */
    private void setMethodName(String methodName) throws ScopeException.IllegalMethodNameException {
        Matcher matcher=namePattern.matcher(methodName);
        if (matcher.matches()){
            this.methodName=methodName;
        }else {
            throw new ScopeException.IllegalMethodNameException();
        }

    }

    /**
     * static class for case of method call
     */
    static class MethodCall {

        //--Data Members--//
        String methodLine;
        HashMap<String,Variable> currentVariabls;

        /**
         * constructor for method call
         * @param methodLine the line calling for method
         * @param currentVariabls variables in method call
         */
        MethodCall(String methodLine, HashMap<String, Variable> currentVariabls) {
            this.methodLine = methodLine;
            this.currentVariabls = currentVariabls;
        }

        /**
         * @return currentVariables
         */
        HashMap<String, Variable> getCurrentVariabls() {
            return currentVariabls;
        }

        /**
         * @return string line of method call
         */
        String getMethodLine() {
            return methodLine;
        }

        /**
         * update the variables
         * @param globalVariabls hashmap we want to get vars from
         */
        void updateGlobalVariabls(HashMap<String, Variable> globalVariabls){
            for (Variable variable:globalVariabls.values()) {
                if (!currentVariabls.containsValue(variable)){
                    currentVariabls.put(variable.getName(),variable);
                }
            }
        }

    }
}
