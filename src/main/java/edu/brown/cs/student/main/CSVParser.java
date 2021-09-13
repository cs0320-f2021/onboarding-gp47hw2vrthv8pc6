package edu.brown.cs.student.main;

import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    List<String[]> rows = new ArrayList<String[]>();
    String[] cols;

    public CSVParser(String path) {
        try { 
            File file = new File(path);
            Scanner fileReader = new Scanner(file);
            cols = fileReader.nextLine().split(",");
            while (fileReader.hasNextLine()) { 
                String line = fileReader.nextLine();
                rows.add(line.split(","));
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public int getIndex(String colName) {
        if (cols == null) {
            return -1;
        }
        
        int arrayLength = cols.length;
        for (int i = 0; i < arrayLength; i++) {
            if (cols[i].equals(colName)) {
                return i;
            } else {
                continue;
            }
        }

        return -1;
    }
}
