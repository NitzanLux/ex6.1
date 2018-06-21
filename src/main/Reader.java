package main;

import javax.swing.plaf.synth.SynthScrollBarUI;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class Reader {
    private Path path;

    /**
     * Constructor
     * @param path path to file
     */
    public Reader(Path path){
        this.path = path;
    }

    /**
     * Gets all List with all files
     * @return LinkedList with all lines in file, each line a String in the list.
     */
    public LinkedList<String> getLines(){
            try {
                LinkedList<String> lines = new LinkedList<>();
                lines.addAll(readAllLines(path));
                return lines;
            } catch (IOException e) {
                System.err.println("Invalid Path");
            }
            return null;
    }
}
