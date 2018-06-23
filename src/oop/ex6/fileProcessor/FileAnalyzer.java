package oop.ex6.fileProcessor;

import oop.ex6.fileProcessor.scopePackage.ConditionFactory;
import oop.ex6.fileProcessor.scopePackage.File;
import oop.ex6.fileProcessor.scopePackage.MethodFactory;
import oop.ex6.fileProcessor.scopePackage.ScopeException;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import oop.ex6.fileProcessor.variblePackage.VariableFactory;

public class FileAnalyzer {
    private File file=new File();
    private MethodFactory methodFactory=new MethodFactory(file);
    private ConditionFactory conditionFactory=new ConditionFactory(file);
    private VariableFactory variableFactory=new VariableFactory(file);
    private int line=0;
    File getFile() {
        return file;
    }
    ConditionFactory getConditionFactory() {
        return conditionFactory;
    }

    public MethodFactory getMethodFactory() {
        return methodFactory;
    }

    int getLine() {
        return line;
    }

    VariableFactory getVariableFactory() {
        return variableFactory;
    }

    public void anlayzeLine(String line) throws VariableException, ScopeException, NoSuchLineException {
        this.line++;
        for (LineType lineType: LineType.values ()){
            if (file.getScopes().size()>1){
                if (!lineType.scoopAtPosition(true)){
                    continue;
                }
            }else {
                if (!lineType.scoopAtPosition(false)){
                    continue;
                }
            }
            if (lineType.processSentence(line,this)){
                return;
            }
        }
        throw new NoSuchLineException();
    }

}
