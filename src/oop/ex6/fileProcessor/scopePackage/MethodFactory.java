package oop.ex6.fileProcessor.scopePackage;

import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import oop.ex6.fileProcessor.variblePackage.VariableFactory;
import oop.ex6.fileProcessor.variblePackage.VariableType;

import java.util.*;

/**A class we call when we find a method declare line*/
public class MethodFactory {

    //--constants--//
    private static final int METHOD_PLACE_IN_SCOPE = 1;
    //--Data Members--//
    private File file;
    private Method currentMethod;
    private ArrayList<Method.MethodCall> methodsCalls = new ArrayList<>();

    /**
     * constructor
     * @param file file we work om
     */
    public MethodFactory(File file) {
        this.file = file;
    }

    /**
     * this method is called when we want to create new method
     * @param line line string
     * @throws VariableException if variable not valid
     */
    public void createMethod(String line) throws VariableException, ScopeException {
        String methodName = getName(line);
        ArrayList<Variable> variables = getArrayVariables (line);
        ArrayList<VariableType> variableTypes=variableToTypesInOrder(variables);
        Method method = new Method(variablesToHashmap(variables), methodName,variableTypes);
        this.currentMethod = method;
        this.file.addScope(this.currentMethod);
        this.file.addMethod(method);
    }

    /**
     * checker for method call
     * @param methodCall a methodCall object
     * @throws ScopeException in case the method was not call legally
     */
    private void methodCallsChecker(Method.MethodCall methodCall) throws ScopeException {
        String line= methodCall.getMethodLine();
        HashMap<String,Variable> currentVariables= methodCall.getCurrentVariabls();
        String[] sline = sliceLine(line);
        Method currentMethod =  file.getMethods().get(sline[0]);
        if (currentMethod!=null) {
            if (sline.length > 1) {
                String[] vars = sline[1].split(",");
                checkCallVariables (currentMethod, vars, currentVariables);
            }else {
                if (currentMethod.getVariableAmmount ()!=0){
                    throw new ScopeException.MethodVariablesUnfitException();
                }
            }
        }else {
            throw new ScopeException.MethodNotDeclaredException (sline[0]);
        }
    }

    /**
     * checks method call variables, helper for main check method
     * @param currentMethod the method we call
     * @param vars variables as sliced from method call line
     * @param currentVariables variables of the current method
     * @throws ScopeException in case method variables found to be unfit
     */
    private void checkCallVariables(Method currentMethod, String[] vars, HashMap<String, Variable>
            currentVariables) throws ScopeException {
        int counter=0;
        if (vars.length!=currentMethod.getVariableAmmount ()){
            throw new ScopeException.MethodVariablesUnfitException();
        }
        for (String var : vars) {
            var = var.trim();
            VariableType variableType=VariableType.getVariableType(var);
            if (variableType!=null) {
                if (currentMethod.variblesFitToMethodType(counter,variableType)) {
                    counter++;
                    continue;
                }
                else {
                    throw new ScopeException.MethodVariablesUnfitException();
                }
            }
            Variable currentVar=currentVariables.get(var);
            if (currentVar==null||!currentVar.isValueAssigned()) {
                throw new ScopeException.VariableNotAssignedException (var);
            }

        }
    }

    /**
     * slices line and returns array size 2:
     * one with method name, and one with variables
     * @param line line string
     * @return ths line parsed by name and variables
     */
    private String[] sliceLine(String line) {
        String[] sline;
        line = line.substring(0, line.indexOf(")"));
        line = line.trim();
        sline = line.split("\\s*\\(\\s*");
        return sline;
    }

    /**
     * returns name of the method
     * @param line the string line
     * @return name of method
     */
    private String getName(String line) {
        String methodName=sliceLine(line)[0];
        methodName=methodName.replace("void","").trim();
        return methodName;
    }

    /**
     * gets the types of variables in the method var line by the order they called
     * @param variables the variables from the line
     * @return arrayList with the types of variables
     */
    private ArrayList<VariableType> variableToTypesInOrder(ArrayList<Variable> variables){
        ArrayList<VariableType> variableTypes = new ArrayList<>();
        for (Variable variable:variables) {
            variableTypes.add(variable.getVariableType());
        }
        return variableTypes;
    }

    /**
     * creates a variable hashmaps from variables
     * @return hashmap with variables in method scope
     */
    private HashMap<String, Variable> variablesToHashmap(ArrayList<Variable> variables) {
        HashMap<String,Variable> variableHashMap=new HashMap<>(variables.size());
        for (Variable variable:variables) {
            variableHashMap.put(variable.getName(),variable);
        }
        return variableHashMap;
    }

    /**
     * creates arrayList of variables from method declare line
     * @param line code line declaring method
     * @return arrayList consists of variables in parnethouses
     * @throws VariableException in case a variable is not legal
     * @throws ScopeException if variable appear more than once
     */
    private ArrayList<Variable> getArrayVariables(String line) throws VariableException, ScopeException {
        line = line.trim();
        String[] varLines = sliceLine(line);
        if (varLines.length >= 2) {
            String varLine = varLines[1];
            String[] splitVars = varLine.split(",");
            LinkedList<String> strings = new LinkedList<>();
            strings.addAll(Arrays.asList(splitVars));
            return generateVariables(strings);
        }
        return new ArrayList<>();
    }

    /**
     * Helper for getArrayVariables, generates the variables from a linked list
     * @param strings linked list of strings represent variables
     * @return array list containing the variables
     * @throws VariableException in case a variable is not legal
     * @throws ScopeException if variable appear more than once
     */
    private ArrayList<Variable> generateVariables(LinkedList<String> strings)
            throws VariableException, ScopeException{
        ArrayList< Variable> variables = new ArrayList<>(strings.size());
        HashSet<String> names=new HashSet<>();
        VariableFactory variableFactory = new VariableFactory(file);
        Variable[] variablesArray;
        for (String var:strings) {
            LinkedList<String >varLinkedList=new LinkedList<>();
            varLinkedList.add(var);
            variablesArray = variableFactory.getVariables(varLinkedList);
            variablesArray[0].assignedVariable(file.getCurrentScope());
            if (names.contains(variablesArray[0].getName())){
                throw new ScopeException.AlreadyAssignedException(variablesArray[0].getName());
            }
            variables.addAll(Arrays.asList(variablesArray));
            names.add(variablesArray[0].getName());
        }
        return variables;
    }

    /**
     * sets return of current method
     */
    public void methodReturn() {
        if (file.getScopes().size() == METHOD_PLACE_IN_SCOPE + 1 && currentMethod != null) {
            currentMethod.setReturn();
        }
    }

    /**
     * adds metodCall object to method calls
     * @param line code line recognized as method call
     */
    public void addMethodCall(String line){
        methodsCalls.add(new Method.MethodCall (line,file.getScopeVariables()));
    }

    /**
     * checks method calls
     * @throws ScopeException if variable appear more than once in method call line
     */
    public void checkMethodCalls() throws ScopeException {
        for (Method.MethodCall methodCall :methodsCalls) {
            methodCall.updateGlobalVariabls(file.getVariables());
            methodCallsChecker(methodCall);
        }
    }


}
