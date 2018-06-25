package oop.ex6.fileProcessor.scopePackage;

import oop.ex6.fileProcessor.variblePackage.Variable;
import sun.awt.SunHints;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * class represents the file we work on.
 * inherits from scope
 */
public class File extends Scope {

    //--constant-//
    public static final int MIN_SCOPE_SIZE = 1;
    //dataset of methods in the file
    private HashMap<String, Method> methods = new HashMap<>();
    //dataset with all scope in the file, works as a stack
    private LinkedList<Scope> scopes = new LinkedList<>();

    /**
     * default constructor
     */
    public File() {
        super();
        addScope(this);
    }

    /**
     * overriding method
     * @return true
     */
    @Override
    public boolean closeScope(Scope parent) {
        return false;
    }

    /**
     * getter for methods
     * @return dataset of methods in file
     */
    HashMap<String, Method> getMethods() {
        return methods;
    }

    /**
     * add scope to file
     * @param scope scope we wnat to add
     */
    void addScope(Scope scope) {
        scopes.addFirst(scope);
    }

    /**
     * adds method to dataset
     * @param method method we add to file
     */
    void addMethod(Method method) {
        this.methods.put(method.getMethodName(), method);
    }

    /**
     * getter for current scope in scopes
     * @return the first scope in datastack
     */
    public Scope getCurrentScope() {
        return scopes.getFirst();
    }

    /**
     * checks if variable assigned already in one of previus scopes
     * @param variable name variable we want to check
     * @return true if assignd
     */
    boolean isVariableAssigned(String variable) {
        for (Scope scope : scopes) {
            if (scope.getVariables().containsKey(variable)) {
                return true;
            }
        }
        return false;
    }

    /**
     * closes current scope (remove is from data set)
     * @throws ScopeException if we try to close the last scope
     */
    public void endScope() throws ScopeException {
        Scope scope;
        scope = scopes.getFirst();
        if (scopes.size() > File.MIN_SCOPE_SIZE) {
            if (scope.closeScope(scopes.get(1)) && scopes.size() > MIN_SCOPE_SIZE) {
                scopes.remove(scope);
                return;
            }
        }
        throw new ScopeException.ScoopCloserException();
    }

    /**
     * @return scope stack
     */
    public LinkedList<Scope> getScopes() {
        return scopes;
    }

    HashMap<String, Variable> getScopeVariables() {
        HashMap<String, Variable> scopeVariabls = new HashMap<>();
        for (Scope scope : scopes) {
            for (Variable variable : scope.getVariables().values()) {
                if (!scopeVariabls.containsValue(variable)) {
                    scopeVariabls.put(variable.getName(), new Variable(variable));
                }

            }
        }
        return scopeVariabls;
    }

    /**
     * returns the first variable matching the name given
     * @param name a given variable name
     * @return the variable that fits it
     */
    public Variable getVariable(String name) {
        for (Scope scope : scopes) {
            if (scope.getVariables().containsKey(name)) {
                return scope.getVariables().get(name);
            }
        }
        return null;
    }
}
