package ch.ee.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    public static String[] readAllLinesOfFile(String filePath){
        try{
            InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> lines = reader.lines().collect(Collectors.toList());

            reader.close();
            inputStream.close();

            return lines.toArray(new String[0]);
        } catch (Exception e) {
            // TODO: Put a log here!
            e.printStackTrace();
            return new String[0];
        }
    }
}
