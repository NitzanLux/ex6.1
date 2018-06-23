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
        boolean relevantLine = !isFirstTime;
        if (!isFirstTime) {
            innerScopeCounter = 0;
        }
        LineType currentlineType = findLineType(line, isFirstTime);
        if (currentlineType != null) {
            if (isFirstTime && innerScopeCounter == 0 && (currentlineType == LineType.ASSIGNMENT||currentlineType==LineType.REASSIGNMENT)) {
                relevantLine = true;

            } else {
                if (currentlineType.isScopeCreater()) {
                    innerScopeCounter++;
                } else if (currentlineType == LineType.CLOSE_SCOPE) {
                    innerScopeCounter--;
                }
            }
            if (!isFirstTime && file.getScopes().size() == File.MIN_SCOPE_SIZE && (currentlineType == LineType.ASSIGNMENT||currentlineType==LineType.REASSIGNMENT)) {
                relevantLine = false;
            }
           if (!relevantLine||cheackReturn(currentlineType)) {
               currentlineType.processSentence(line, this, relevantLine);
               return;
           }
        }
        throw new NoSuchLineException();
    }

    private boolean cheackReturn(LineType lineType) {
        if (lineType == LineType.RETURN && file.getScopes().size() == 2) {
            isReturn = true;
            return true;
        }
        boolean answr =(isReturn||lineType!=LineType.CLOSE_SCOPE||file.getScopes().size()!=2);
        isReturn=false;
        return answr;

    }

    private LineType findLineType(String line, boolean isFirstTime) throws VariableException, ScopeException {
        for (LineType lineType : LineType.values()) {
            if (!isFirstTime) {/**/
                if (file.getScopes().size() > 1) {
                    if (!lineType.scoopAtPosition(true)) {
                        continue;
                    }
                } else {
                    if (!lineType.scoopAtPosition(false)) {
                        continue;
                    }
                }
            }
            if (lineType.processSentence(line, this, false)) {
                return lineType;
            }
        }
        return null;
    }
}



