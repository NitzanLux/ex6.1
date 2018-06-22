package oop.ex6.fileProcessor.scopePackage;

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

    HashMap getMethods() {
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
}
