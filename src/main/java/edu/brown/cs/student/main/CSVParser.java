package edu.brown.cs.student.main;

import java.io.File; 
import java.io.FileNotFoundException; 
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for parsing CSV files. Assumes no entries contain commas. Clears memory 
 * upon calling .read().
 * 
 * @author Student
 */
public class CSVParser {
    private List<String[]> rows = new ArrayList<String[]>();
    private String[] cols;

    /**
     * Default constructor.
     */
    public CSVParser() {
        
    }

    /**
     * Read a CSV file: store the rows in memory as a list of String arrays 
     * (rows) and store the column names as a String array (cols).
     * 
     * @param path Path to the CSV file.
     */
    public void read(String path) {
        rows.clear();
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

    /**
     * Obtain the column index of the specified column name.
     * 
     * @param colName A string specifying the desired column.
     * @return The index (zero-indexed) of the given column.
     */ 
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

    public List<String[]> getRows() {
        return rows;
    }

    public String[] getCols() {
        return cols;
    }
}
