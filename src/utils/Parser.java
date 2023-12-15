package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static List<String[]> parseFile (String path) throws IOException, FileNotFoundException {
        List<String[]> toReturn = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            // Read and print each line from the file
            while ((line = reader.readLine()) != null) {
                String[] aux = new String[3];
                aux = line.split("\\s+");
                toReturn.add(aux);
            }
        }

        return toReturn;
    }
}
