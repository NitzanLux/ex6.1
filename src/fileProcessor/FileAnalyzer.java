package fileProcessor;

import fileProcessor.scopePackage.ConditionFactory;
import fileProcessor.scopePackage.File;
import fileProcessor.scopePackage.MethodFactory;
import fileProcessor.scopePackage.ScopeException;
import fileProcessor.variblePackage.VariableException;
import fileProcessor.variblePackage.VariableFactory;

public class FileAnalyzer {
    private File file=new File();
    private MethodFactory methodFactory=new MethodFactory(file);
    private ConditionFactory conditionFactory=new ConditionFactory(file);
    private VariableFactory variableFactory=new VariableFactory(file);

    File getFile() {
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
    public void anlayzeLine(String line) throws VariableException, ScopeException, NoSuchLineException {
        for (LineType lineType: LineType.values ()){
            if (lineType.processSentence(line,this)){
                return;
            }
        }
        throw new NoSuchLineException();

    }

}
