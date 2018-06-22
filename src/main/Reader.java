package main;

import javax.swing.plaf.synth.SynthScrollBarUI;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class Reader {
    private static Reader instance=new Reader();

    private Reader(){
    }

    public static Reader getInstance() {
        return instance;
    }

    /**
     * Gets all List with all files
     * @return LinkedList with all lines in file, each line a String in the list.
     */
    public ArrayList<String> readLines(String path) throws IOException {
        ArrayList<String> sjavaData=new ArrayList<>();
            try (BufferedReader bufferedReader=new BufferedReader(new FileReader(path))) {
                int counter = 0;
                String line;
                while ((line = bufferedReader.readLine() )!= null) {
                    sjavaData.add(counter, line);
                    counter++;
                }
            }catch (IOException e) {
                throw e;
            }
            return sjavaData;
    }
}
