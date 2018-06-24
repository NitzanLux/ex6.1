package oop.ex6.fileProcessor;

import oop.ex6.fileProcessor.scopePackage.ConditionFactory;
import oop.ex6.fileProcessor.scopePackage.File;
import oop.ex6.fileProcessor.scopePackage.MethodFactory;
import oop.ex6.fileProcessor.scopePackage.ScopeException;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import oop.ex6.fileProcessor.variblePackage.VariableFactory;

public class FileAnalyzer {
    private File file = new File();
    private MethodFactory methodFactory = new MethodFactory(file);
    private ConditionFactory conditionFactory = new ConditionFactory(file);
    private VariableFactory variableFactory = new VariableFactory(file);
    private int innerScopeCounter = 0;
    private boolean isReturn = false;

    public File getFile() {
        return file;
    }

    ConditionFactory getConditionFactory() {
        return conditionFactory;
    }

    public MethodFactory getMethodFactory() {
        return methodFactory;
    }

    VariableFactory getVariableFactory() {
        return variableFactory;
    }

    public void anlayzeLine(String line, boolean isFirstTime) throws VariableException, ScopeException, NoSuchLineException {
        boolean isInnerScope;
        if (isFirstTime) {
            isInnerScope=innerScopeCounter!=0;
        }else {
            isInnerScope=file.getScopes().size()>File.MIN_SCOPE_SIZE;
        }
        LineType currentlineType = findLineType(line,isInnerScope);
        if (currentlineType==null){
            throw new NoSuchLineException();
        }
        if (isFirstTime){
            if (currentlineType.isScopeCreater()){
                innerScopeCounter++;
            }else if (currentlineType==LineType.CLOSE_SCOPE){
                innerScopeCounter--;
            }else if (innerScopeCounter==0&&(currentlineType==LineType.ASSIGNMENT||currentlineType==LineType.REASSIGNMENT)){
                currentlineType.processSentence(line,this,true,true);
            }
        }else {
            if (!((currentlineType==LineType.ASSIGNMENT||currentlineType==LineType.REASSIGNMENT)
                    &&file.getScopes().size()==File.MIN_SCOPE_SIZE)) {
                if (!cheackReturn(currentlineType)){
                    throw new ScopeException.ReturnCloserException();
                }
                currentlineType.processSentence(line,this,true,true);
            }
        }
    }

    private boolean cheackReturn(LineType lineType) {
        if (lineType == LineType.RETURN && file.getScopes().size() == File.MIN_SCOPE_SIZE+1) {
            isReturn = true;
        }else if (!(lineType == LineType.CLOSE_SCOPE && file.getScopes().size() == File.MIN_SCOPE_SIZE+1)){
            isReturn=false;
        }
        return (lineType!=LineType.CLOSE_SCOPE||file.getScopes().size()!=File.MIN_SCOPE_SIZE+1||isReturn);

    }

    private LineType findLineType(String line,boolean isInnerScope) throws VariableException, ScopeException {
        for (LineType lineType : LineType.values()) {
            /**/
                if (isInnerScope) {
                    if (!lineType.scoopAtPosition(true)) {
                        continue;
                    }
                } else {
                    if (!lineType.scoopAtPosition(false)) {
                        continue;
                    }
                }
            if (lineType.processSentence(line, this, false,false)) {
                return lineType;
            }
        }
        return null;
    }
}



