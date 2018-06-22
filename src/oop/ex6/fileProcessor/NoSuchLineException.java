package oop.ex6.fileProcessor;

public class NoSuchLineException extends Exception {
    private static final long serialVersionUID = 1L;
    private static final String ILLEGAL_LINE_MSG = "no such line in sjava is legal";

    NoSuchLineException(){
        super(ILLEGAL_LINE_MSG);
    }
}
