package oop.ex6.fileProcessor.scopePackage;

import oop.ex6.fileProcessor.variblePackage.Variable;
import sun.awt.SunHints;

import java.util.HashMap;
import java.util.LinkedList;

public class File extends Scope {


    private static final int FILE_IN_SCOPE = 1;
    private HashMap<String, Method> methods = new HashMap<>();

    private LinkedList<Scope> scopes = new LinkedList<>();

    public File() {
        super();
        addScope(this);
    }

    @Override
    boolean closeScope() {
        return true;
    }

    HashMap<String,Method> getMethods() {
        return methods;
    }


    void addScope(Scope scope) {
        scopes.addFirst(scope);
    }

    void addMethod(Method method) {
        this.methods.put(method.getMethodName(), method);
    }

    public Scope getCurrentScope() {
        return scopes.getFirst();
    }

    boolean isVariableAssigned(String variable) {
        for (Scope scope : scopes) {
            if (scope.getVariables().containsKey(variable)) {
                return true;
            }
        }
        return false;
    }

    public void endScope() throws ScopeException {
        Scope scope;
        scope = scopes.getFirst();
        if (scope.closeScope()&&scopes.size()> FILE_IN_SCOPE) {
            scopes.remove(scope);
        } else {
            throw new ScopeException.ScoopCloserException();
        }
    }

    public LinkedList<Scope> getScopes() {
        return scopes;
    }
    HashMap<String,Variable> getScopeVariables(){
        HashMap<String,Variable> scopeVariabls=new HashMap<>();
        for (Scope scope:scopes) {
            for (Variable variable:scope.getVariables().values()) {
                if (!scopeVariabls.containsValue(variable)){
                    scopeVariabls.put(variable.getName(),variable);
                }

            }
        }
        return scopeVariabls;
    }
    public Variable getVariable(String name){
        for (Scope scope:scopes) {
           if (scope.getVariables().containsKey(name)){
            return scope.getVariables().get(name);
           }
        }
        return null;
    }
}
