package edu.brown.cs.student.main;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class CSVParserTest {
  @Test 
  public void readTest() {
    CSVParser parser = new CSVParser();
    try {
      parser.read("./data/stars/one-star.csv");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    String[] result = {"StarID", "ProperName", "X", "Y", "Z"};
    assertArrayEquals(parser.getCols(), result);
  }

  @Test 
  public void indexTest() {
    CSVParser parser = new CSVParser();
    try {
      parser.read("./data/stars/one-star.csv");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals(parser.getIndex("ProperName"), 1);
  }

  @Test 
  public void missingFileTest() {
    CSVParser parser = new CSVParser();
    boolean caughtException = false;
    try {
      parser.read("");
    } catch (FileNotFoundException e) {
      caughtException = true;
    }

    assert(caughtException);
  }

  @Test 
  public void missingColumnTest() {
    CSVParser parser = new CSVParser();
    try {
      parser.read("./data/stars/one-star.csv");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    
    assertEquals(parser.getIndex(""), -1);
  }

  @Test 
  public void rereadTest() {
    CSVParser parser = new CSVParser();
    try {
      parser.read("./data/stars/one-star.csv");
      parser.read("./data/stars/ten-star.csv");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals(parser.getRows().size(), 10);
  }

  @Test 
  public void callBeforeRead() {
    CSVParser parser = new CSVParser();
    int getIndexTest = parser.getIndex("");
    List<String[]> getRowsTest = parser.getRows();
    String[] getColsTest = parser.getCols();
    assertEquals(getIndexTest, -1);
    assertEquals(getRowsTest, new ArrayList<String[]>());
    // assertNull doesn't seem to work with String[]?
  }

}
