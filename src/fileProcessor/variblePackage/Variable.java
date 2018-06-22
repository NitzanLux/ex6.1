package fileProcessor.variblePackage;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Variable {
    private boolean isValueAssigned;
    private String variableName;
    private boolean isFinal = false;
    private VariableType variableType;
    private ArrayList<Variable> variablesThatPointer=new ArrayList<>();
    private static final Pattern namePattern=Pattern.compile("(?:^(?:[A-Za-z]+|(?:[_]+\\w*[A-Za-z]))\\w*\\b)*$");//todo megic number
    public Variable(String type, String variableName, String value, boolean isFinal)
            throws VariableException{
        if(value != null){
                assignVariable(value);

        }
        if (isFinal&&value==null){
            throw new VariableException.FinalException.FinalNotAssignedException();
        }

        setVariable(type,variableName,isFinal);

    }
    public Variable(String type, String variableName,Variable variableRefernce, boolean isFinal)
            throws VariableException{
        this.isValueAssigned=variableRefernce.isValueAssigned;
        variableRefernce.variablesThatPointer.add(this);
        setVariable(type,variableName,isFinal);
    }
    public void setVariable(String type, String variableName, boolean isFinal) throws VariableException {
        this.isFinal = isFinal;
        this.variableType = VariableType.parseType(type);
        if (Variable.NameChecker(variableName)||variableName==null){
            this.variableName = variableName;
        }else {
            throw new VariableException.NoVariableNameException();
        }

        if (variableType == null) {
            throw new VariableException.TypeNotFoundException();
        }

    }

    public String getName(){
        return variableName;
    }

    public void assignVariable(String value) throws VariableException {
        if (isFinal&&isValueAssigned){
            throw new  VariableException.FinalException.FinalAssignedAlradyException();
        }
        if(variableType.isFitValue(value)){
            isValueAssigned = true;
            for (Variable variable:variablesThatPointer) {//update all pointers
                variable.isValueAssigned=true;
            }
        }else{
            throw new VariableException.ValueNotMatchingTypeException();
        }
    }

    private static boolean NameChecker(String variableName){
        Matcher matcher=Variable.namePattern.matcher(variableName);
        return matcher.matches();

    }

    void setValueAssigned(){
        isValueAssigned = true;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public boolean isFinal() {
        return isFinal;
    }
}


