package fileProcessor.scopePackage;

import fileProcessor.variblePackage.Variable;
import java.util.HashMap;
import java.util.LinkedList;

public class File extends Scope {

    private HashMap<String, Method> methods=new HashMap<>();

    private LinkedList<Scope> scopes = new LinkedList<Scope>();

    public File() {
        super();
    }

    public void setValues(HashMap<String, Method> methods, Variable[] globalVariables) {
        this.methods = methods;
        addScope(this);
    }


    public HashMap getMethods() {
        return methods;
    }


    public void addScope(Scope scope) {
        scopes.addFirst(scope);
    }

    public void addMethod(Method method){
        this.methods.put(method.getMethodName(), method);
    }

    public Scope getCurrentScope() {
        return scopes.getFirst();
    }

    public boolean isVariableAssigned(String variable){
        for(Scope scope:scopes){
            if(scope.getVariables().containsKey(variable)){
                return true;
            }
        }
        return false;
    }

    public void endScope(Scope scope) {
        scopes.remove(scope);
    }

    public LinkedList<Scope> getScopes() {
        return scopes;
    }
}
