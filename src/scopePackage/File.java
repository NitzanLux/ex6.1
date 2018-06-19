package scopePackage;

import variblePackage.Variable;

import java.util.HashMap;
import java.util.LinkedList;

public class File {

    private static File instance = new File();

    private HashMap methods;

    private Variable[] globalVariables;

    private LinkedList scopes = new LinkedList<Scope>();

    private File(){

    }

    public static File getInstance() {
        return instance;
    }

    public void setValues(HashMap<String, Method> methods, Variable[] globalVariables){
        this.globalVariables = globalVariables;
        this.methods = methods;
    }


    public HashMap getMethods() {
        return methods;
    }

    public Variable[] getGlobalVariables() {
        return globalVariables;
    }

    public void addScope(Scope scope){
        scopes.addFirst(scope);
    }

    public Object getCurrentScope(){
        return scopes.getFirst();
    }

}
