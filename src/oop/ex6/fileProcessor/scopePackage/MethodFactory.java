package oop.ex6.fileProcessor.scopePackage;

import oop.ex6.fileProcessor.variblePackage.Variable;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import oop.ex6.fileProcessor.variblePackage.VariableFactory;
import oop.ex6.fileProcessor.variblePackage.VariableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class MethodFactory {

    private static final int METHOD_PLACE_IN_SCOPE = 1;
    private File file;
    private Method currentMethod;
    private ArrayList<String> methodsCalls = new ArrayList<>();

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
        HashMap<String, Variable> variables = getVariables(line);
        Method method = new Method(variables, methodName);
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
    private void methodCall(String line) throws ScopeException {
        String[] sline = sliceLine(line);
        if (file.getMethods().containsKey(sline[0])) {
            String[] vars = sline[1].split(",");
            for (int i = 0; i < vars.length; i++) {
                vars[i] = vars[i].replaceAll("\\s+", "");
            }
            for (String var : vars) {
                var = var.trim();
                if (VariableType.isValueOfType(var)) {
                    continue;
                }
                if (!file.isVariableAssigned(var)) {
                    throw new ScopeException.MethodNotDeclerdException(var);
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
        sline = line.split("\\s*\\(");
        sline[0] = sline[0].split("\\s+")[1];
        return sline;
    }

    /*
     * returns name of the method
     *
     * @param line
     * @return
     */
    private String getName(String line) {
        return sliceLine(line)[0];
    }

    /*
     * returns hashmap with variables in method scope
     *
     * @param line
     * @return
     * @throws VariableException
     */
    private HashMap<String, Variable> getVariables(String line) throws VariableException {
        String varLine = sliceLine(line)[1];
        String[] splitVars = varLine.split(",");
        LinkedList<String> strings = new LinkedList<>();
        strings.addAll(Arrays.asList(splitVars));
        HashMap<String, Variable> variables = new HashMap<>(strings.size());
        {
            VariableFactory variableFactory = new VariableFactory(file);//todo is it good delegation??????????????????????????????????????????????
            Variable[] variablesArray = variableFactory.getVariables(strings, true);
            for (Variable variable : variablesArray) {
                variables.put(variable.getName(), variable);
            }
        }
        return variables;
    }

    public void methodReturn() {
        if (file.getScopes().size() == METHOD_PLACE_IN_SCOPE + 1 && currentMethod != null) {
            currentMethod.setReturn();
        }
    }

    public void cheakMethodCalls() throws ScopeException {
        for (String line : methodsCalls) {
            methodCall(line);
        }

    }

    public void addMethodCall(String line) {
        methodsCalls.add(line);
    }


}
