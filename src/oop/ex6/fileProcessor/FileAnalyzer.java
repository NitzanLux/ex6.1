package oop.ex6.fileProcessor;

import oop.ex6.fileProcessor.scopePackage.ConditionFactory;
import oop.ex6.fileProcessor.scopePackage.File;
import oop.ex6.fileProcessor.scopePackage.MethodFactory;
import oop.ex6.fileProcessor.scopePackage.ScopeException;
import oop.ex6.fileProcessor.variblePackage.VariableException;
import oop.ex6.fileProcessor.variblePackage.VariableFactory;

/**class FileAnalyzer responsible for analyzing the File object*/
public class FileAnalyzer {

    //--data members--//
    private File file = new File();
    private MethodFactory methodFactory = new MethodFactory(file);
    private ConditionFactory conditionFactory = new ConditionFactory(file);
    private VariableFactory variableFactory = new VariableFactory(file);
    private int innerScopeCounter = 0;
    private boolean isReturn = false;

    /**
     * @return the file data member
     */
    public File getFile() {
        return file;
    }

    /**
     * @return the conditionFactory data member
     */
    ConditionFactory getConditionFactory() {
        return conditionFactory;
    }

    /**
     * @return the methodFactory data member
     */
    public MethodFactory getMethodFactory() {
        return methodFactory;
    }

    /**
     * @return the variableFactory data member
     */
    VariableFactory getVariableFactory() {
        return variableFactory;
    }

    /**
     * analyzes the line and checks for exceptions
     * @param line code line
     * @param isFirstTime if it's first run
     * @throws VariableException if variable illegal
     * @throws ScopeException if scope is not valid
     * @throws NoSuchLineException if line doesn't fit any of line types
     */
    public void analyzeLine(String line, boolean isFirstTime)
            throws VariableException, ScopeException, NoSuchLineException {
        boolean isInnerScope;
        if (isFirstTime) {
            isInnerScope=innerScopeCounter!=0;
        }else {
            isInnerScope=file.getScopes().size()>File.MIN_SCOPE_SIZE;
        }
        LineType currentLineType = findLineType(line,isInnerScope);
        if (currentLineType==null){
            throw new NoSuchLineException();
        }
        analyzeHelper (line, currentLineType, isFirstTime);
    }

    /**
     * helper for the analyzer method
     * @param line ths line to analyze
     * @param currentLineType the line type we think that fits the line
     * @param isFirstTime if its first run on data
     * @throws VariableException if process find out
     * @throws ScopeException if we need return and not in found
     */
    private void analyzeHelper(String line, LineType currentLineType, boolean isFirstTime)
            throws VariableException, ScopeException {
        if (isFirstTime){
            if (currentLineType.isScopeCreater()){
                innerScopeCounter++;
            }else if (currentLineType==LineType.CLOSE_SCOPE){
                innerScopeCounter--;
            }else if (innerScopeCounter==0&&(currentLineType==LineType.ASSIGNMENT||
                    currentLineType==LineType.REASSIGNMENT)){
                currentLineType.processSentence(line,this,true,true);
            }
        }else {
            if (!((currentLineType==LineType.ASSIGNMENT||currentLineType==LineType.REASSIGNMENT)
                    &&file.getScopes().size()==File.MIN_SCOPE_SIZE)) {
                if (!checkReturn (currentLineType)){
                    throw new ScopeException.ReturnCloserException();
                }
                currentLineType.processSentence(line,this,true,true);
            }
        }
    }

    /**
     * checks if we need return in this scope (aka its method type)
     * @param lineType the type of line
     * @return true if wee need return
     */
    private boolean checkReturn(LineType lineType) {
        if (lineType == LineType.RETURN && file.getScopes().size() == File.MIN_SCOPE_SIZE+1) {
            isReturn = true;
        }else if (!(lineType == LineType.CLOSE_SCOPE && file.getScopes().size() == File.MIN_SCOPE_SIZE+1)){
            isReturn=false;
        }
        return (lineType!=LineType.CLOSE_SCOPE||file.getScopes().size()!=File.MIN_SCOPE_SIZE+1||isReturn);

    }

    /**
     * check what line type fits the line
     * @param line the line
     * @param isInnerScope if we are inside method
     * @return the line type that fits
     * @throws VariableException from process sentence
     * @throws ScopeException from process sentence
     */
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



