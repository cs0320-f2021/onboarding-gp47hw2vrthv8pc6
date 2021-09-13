package edu.brown.cs.student.main;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CSVParserTest {
    @Test 
    public void readTest() {
        CSVParser parser = new CSVParser("./data/stars/one-star.csv");
        String[] result = {"StarID", "ProperName", "X", "Y", "Z"};
        assertArrayEquals(parser.cols, result);
    }

    @Test 
    public void indexTest() {
        CSVParser parser = new CSVParser("./data/stars/one-star.csv");
        assertEquals(parser.getIndex("ProperName"), 1);
    }
}
