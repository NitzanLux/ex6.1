package fileProcessor;

import fileProcessor.scopePackage.ConditionFactory;
import fileProcessor.scopePackage.File;
import fileProcessor.scopePackage.MethodFactory;
import fileProcessor.variblePackage.VariableFactory;

public class FileAnalyzer {
    File file=new File();
    MethodFactory methodFactory=new MethodFactory(file);
    ConditionFactory conditionFactory=new ConditionFactory(file);
    VariableFactory variableFactory;

    public File getFile() {
        return file;
    }
    public ConditionFactory getConditionFactory() {
        return conditionFactory;
    }

    public MethodFactory getMethodFactory() {
        return methodFactory;
    }

    public VariableFactory getVariableFactory() {
        return variableFactory;
    }

}
