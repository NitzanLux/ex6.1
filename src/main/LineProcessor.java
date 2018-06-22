package main;

import fileProcessor.FileAnalyzer;
import fileProcessor.scopePackage.ScopeException;
import fileProcessor.variblePackage.VariableException;

import java.io.IOException;
import java.util.ArrayList;

public class LineProcessor {
    public static void main(String[] args) {
        ArrayList<String> sjavaData=new ArrayList<>();
        try {
            sjavaData=Reader.getInstance().readLines(args[0]);
        } catch (IOException e) {
            //todo messege;
        }
        FileAnalyzer fileAnalyzer=new FileAnalyzer();
        for (String line:sjavaData) {
            try {
                fileAnalyzer.anlayzeLine(line);
                System.out.println(0);
            } catch (VariableException e) {
                System.out.println();
//                e.printStackTrace();//todo somthing (proper msg)
            } catch (ScopeException e) {
//                e.printStackTrace();//todo somthing  (proper msg)
            }
        }
    }

}
