package fileProcessor;

public class NoSuchLineException extends Exception {
    private static final long serialVersionUID = 1L;
    public NoSuchLineException(){
        super("no such line in sjava is legal");
    }
}
