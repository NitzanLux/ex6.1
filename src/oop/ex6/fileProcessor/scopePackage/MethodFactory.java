package oop.ex6.fileProcessor.scopePackage;

import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import oop.ex6.fileProcessor.variblePackage.VariableFactory;
import oop.ex6.fileProcessor.variblePackage.VariableType;

import java.util.*;

public class MethodFactory {

    private static final int METHOD_PLACE_IN_SCOPE = 1;
    private File file;
    private Method currentMethod;
    private ArrayList<Method.MethodCalld> methodsCalls = new ArrayList<>();

    /**
     * constructor
     *
     * @param file
     */
    public MethodFactory(File file) {
        this.file = file;
    }

    /**
     * this method is called when we want to create new method
     *
     * @param line
     * @throws VariableException
     */
    public void createMethod(String line) throws VariableException {
        String methodName = getName(line);
        ArrayList<Variable> variables =getArrayVariabls(line);
        ArrayList<VariableType> variableTypes=variableToTypesInOrder(variables);
        Method method = new Method(variablesToHashmap(variables), methodName,variableTypes);
        this.currentMethod = method;
        this.file.addScope(this.currentMethod);
        this.file.addMethod(method);
    }

    /*
     * this method called when we call a method
     *
     * @param line
     * @throws ScopeException
     */

    public void methodCallsChecker(Method.MethodCalld methodCalld) throws ScopeException {
        String line=methodCalld.getMethodLine();
        HashMap<String,Variable> currentVariables=methodCalld.getCurrentVariabls();
        String[] sline = sliceLine(line);
        int counter=0;
        Method currentMethod =  file.getMethods().get(sline[0]);
        if (currentMethod!=null) {
            if (sline.length > 1) {
                String[] vars = sline[1].split(",");
                if (vars.length!=currentMethod.getVariableAmmoune()){
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
                        throw new ScopeException.MethodNotDeclerdException(var);
                    }

                }
            }else {
                if (currentMethod.getVariableAmmoune()!=0){
                    throw new ScopeException.MethodVariablesUnfitException();
                }
            }
        } else {
            throw new ScopeException.MethodNotDeclerdException(sline[0]);
        }
    }

    /*
     * slices line and returns array size 2:
     * one with method name, and one with variables
     *
     * @param line
     * @return
     */
    private String[] sliceLine(String line) {
        String[] sline;
        line = line.substring(0, line.indexOf(")"));
        line = line.trim();
        sline = line.split("\\s*\\(\\s*");
        return sline;
    }

    /*
     * returns name of the method
     *
     * @param line
     * @return
     */
    private String getName(String line) {
        String methodName=sliceLine(line)[0];
        methodName=methodName.replace("void","").trim();
        return methodName;
    }
    private ArrayList<VariableType> variableToTypesInOrder(ArrayList<Variable> variabls){
        ArrayList<VariableType> variableTypes = new ArrayList<>();
        for (Variable variable:variabls) {
            variableTypes.add(variable.getVariableType());
        }
        return variableTypes;
    }

    /*
     * returns hashmap with variables in method scope
     *
     * @param line
     * @return
     * @throws VariableException
     */
    private HashMap<String, Variable> variablesToHashmap(ArrayList<Variable> variabls) throws VariableException {
        HashMap<String,Variable> variableHashMap=new HashMap<>(variabls.size());
        for (Variable variable:variabls) {
            variableHashMap.put(variable.getName(),variable);
        }
        return variableHashMap;
    }
    private ArrayList<Variable> getArrayVariabls (String line) throws VariableException {
        line = line.trim();
        String[] varLines = sliceLine(line);
        if (varLines.length >= 2) {
            String varLine = varLines[1];
            String[] splitVars = varLine.split(",");
            LinkedList<String> strings = new LinkedList<>();
            ArrayList< Variable> variables = new ArrayList<>(strings.size());
            strings.addAll(Arrays.asList(splitVars));
            {
                VariableFactory variableFactory = new VariableFactory(file);//todo is it good delegation??????????????????????????????????????????????
                Variable[] variablesArray = variableFactory.getVariables(strings, true);
                variables.addAll(Arrays.asList(variablesArray));
            }
            return variables;
        }
        return new ArrayList<>();
    }
    public void methodReturn() {
        if (file.getScopes().size() == METHOD_PLACE_IN_SCOPE + 1 && currentMethod != null) {
            currentMethod.setReturn();
        }
    }
    public void addMethodCall(String line){
        methodsCalls.add(new Method.MethodCalld(line,file.getScopeVariables()));
    }
    public void cheakMethodCalls() throws ScopeException {
        for (Method.MethodCalld methodCalld:methodsCalls) {
            methodCalld.updateGlobalVariabls(file.getVariables());
            methodCallsChecker(methodCalld);
        }
    }


}
