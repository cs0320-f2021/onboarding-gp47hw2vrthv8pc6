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
   * @throws FileNotFoundException in the case that the file is not found.
   */
  public void read(String path) throws FileNotFoundException {
    this.clear();
    File file = new File(path);
    Scanner fileReader = new Scanner(file);
    cols = fileReader.nextLine().split(",");
    while (fileReader.hasNextLine()) { 
      String line = fileReader.nextLine();
      rows.add(line.split(","));
    }
    fileReader.close();
  }

  /**
   * "Resets" the CVSParser, clearing instance's rows attribute and setting 
   * cols attribute to null. 
   */
  public void clear() {
    rows.clear();
    cols = null;
  }

  /**
   * Obtain the column index of the specified column name. If the column does 
   * not exist, returns -1.
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

  /**
   * Returns rows of the CSV parsed.
   * @return An ArrayList of String arrays, the rows of the csv file.
   */
  public List<String[]> getRows() {
    return rows;
  }

  /**
   * Returns the columns of the CSV parsed.
   * @return An array of Strings, the column names of the csv file.
   */
  public String[] getCols() {
    return cols;
  }
}
