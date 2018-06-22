package main;

import fileProcessor.FileAnalyzer;
import fileProcessor.scopePackage.ScopeException;
import fileProcessor.variblePackage.VariableException;

import java.io.IOException;
import java.util.ArrayList;

public class LineProcessor {

    private static final int IO_ERROR_MSG = 2;

    public static void main(String[] args) {
        ArrayList<String> sjavaData=new ArrayList<>();
        try {
            sjavaData=Reader.getInstance().readLines(args[0]);
        } catch (IOException e) {
            System.err.println(IO_ERROR_MSG);
            System.exit(0);//todo exit is good?
        }
        FileAnalyzer fileAnalyzer=new FileAnalyzer();
        for (String line:sjavaData) {
            try {
                fileAnalyzer.anlayzeLine(line);
            } catch (VariableException | ScopeException e) {
                System.err.println(e.getMessage());
            }
        }
        fileAnalyzer.getMethodFactory().cheakMethodCalls();
        }

    }


