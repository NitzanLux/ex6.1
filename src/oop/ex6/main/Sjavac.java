package oop.ex6.main;

import oop.ex6.fileProcessor.FileAnalyzer;
import oop.ex6.fileProcessor.NoSuchLineException;
import oop.ex6.fileProcessor.scopePackage.ScopeException;
import oop.ex6.fileProcessor.variblePackage.VariableException;

import java.io.IOException;
import java.util.ArrayList;

public class Sjavac {
    /*-CONSTANT-*/
    private static final int IO_ERROR_MSG = 2;
    private static final int ILLEGAL_CODE = 1;
    private static final int IO_CODE = 2;
    private static final int SJAVA_PASSD_CODE = 0;
    private static final int EXIT_CODE = 0;

    public static void main(String[] args) {
        ArrayList<String> sjavaData=new ArrayList<>();
        try {
            sjavaData=Reader.getInstance().readLines(args[0]);
        } catch (IOException e) {
            System.out.println(IO_CODE);
            System.err.println(IO_ERROR_MSG);
            System.exit(EXIT_CODE);
        }
        FileAnalyzer fileAnalyzer=new FileAnalyzer();
        for (String line:sjavaData) {
            try {
                fileAnalyzer.anlayzeLine(line);
            } catch (VariableException | ScopeException |NoSuchLineException e) {
                System.out.println(ILLEGAL_CODE);
                System.err.println(e.getMessage());
                System.exit(EXIT_CODE);
            }
        }
        try {
            fileAnalyzer.getMethodFactory().cheakMethodCalls();
        } catch (ScopeException e) {
            System.out.println(ILLEGAL_CODE);
            System.err.println(e.getMessage());
            System.exit(EXIT_CODE);
        }
        System.out.println(SJAVA_PASSD_CODE);
    }

    }


