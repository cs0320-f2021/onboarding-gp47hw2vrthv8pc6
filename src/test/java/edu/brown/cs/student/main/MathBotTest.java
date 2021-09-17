package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class MathBotTest {

  @Test
  public void testAddition() {
    MathBot matherator9000 = new MathBot();
    double output = matherator9000.add(10.5, 3);
    assertEquals(13.5, output, 0.01);
  }

  @Test
  public void testLargerNumbers() {
    MathBot matherator9001 = new MathBot();
    double output = matherator9001.add(100000, 200303);
    assertEquals(300303, output, 0.01);
  }

  @Test
  public void testSubtraction() {
    MathBot matherator9002 = new MathBot();
    double output = matherator9002.subtract(18, 17);
    assertEquals(1, output, 0.01);
  }

  // TODO: add more unit tests of your own
  @Test 
  public void testSubtractLargerNumbers() {
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.subtract(200303, 100000); 
    assertEquals(100303, output, 0.01);
  }

  @Test 
  public void testNegativeResult() {
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.subtract(1, 2); 
    assertEquals(-1, output, 0.01);
  }

  @Test 
  public void testNegativeLargeNumbers() {
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.subtract(100000, 200303); 
    assertEquals(-100303, output, 0.01);
  }

  @Test 
  public void testAddNegatives() {
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.add(-1, -2); 
    assertEquals(-3, output, 0.01);
  }

  @Test 
  public void testAddNegativePositive() {
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.add(-1, 2); 
    assertEquals(1, output, 0.01);
  }

  @Test 
  public void testAddLargeNegativePositive() {
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.add(100000, -200303); 
    assertEquals(-100303, output, 0.01);
  }

  @Test 
  public void testSmallNumbers() {
    MathBot matherator9003 = new MathBot();
    double output = matherator9003.add(0.00001, 0.00001);
    assertEquals(output, 0.00002, 0.000001);
    output = matherator9003.subtract(0.00002, 0.00001);
    assertEquals(output, 0.00001, 0.000001);
  }

  @Test 
  public void testDistance() {
    MathBot matherator = new MathBot();
    double[] r1 = {3.1, 6.9};
    double[] r2 = {1.45, 100.2};
    double output = matherator.distance(r1, r2);
    assertEquals(output, 93.314, 0.001);
  }  

  @Test 
  public void unequalLengthDistance() {
    MathBot matherator = new MathBot();
    double[] r1 = {2, 1, 3};
    double[] r2 = {0, 0};
    double output = matherator.distance(r1, r2);
    assertEquals(output, 2.236, 0.001);
    
    double[] r3 = {0, 0};
    double[] r4 = {2, 1, 3};
    output = matherator.distance(r3, r4);
    assertEquals(output, 2.236, 0.001);
  }

  @Test 
  public void largeDistance() {
    MathBot matherator = new MathBot();
    double[] r1 = {100000, 100000, 100000};
    double[] r2 = {99000, 99900, 89000};
    double output = matherator.distance(r1, r2);
    assertEquals(output, 11045.813, 0.001);
  }

  @Test 
  public void emptyDistance() {
    MathBot matherator = new MathBot();
    double[] r1 = {};
    double[] r2 = {9, 9, 8};
    double output = matherator.distance(r1, r2);
    assertEquals(output, 0, 0.001);
  }

  @Test 
  public void naiveNeighbors() {
    MathBot matherator = new MathBot();
    int k = 3; 
    double x = 2.134; 
    double y = 4.232; 
    double z = 6.134; 
    List<String[]> data = new ArrayList<String[]>();
    String[] row1 = {"1.123131", "2", "-3"};
    String[] row2 = {"1.123123", "2", "3"};
    String[] row3 = {"11", "2", "100000"};
    data.add(row1);
    data.add(row2);
    data.add(row3);

    Comparator<String[]> comp = new Comparator<String[]>() {
      public int compare(String[] row1, String[] row2) {
        double[] r1 = {Double.parseDouble(row1[0]),
                       Double.parseDouble(row1[1]),
                       Double.parseDouble(row1[2])};
        double[] r2 = {Double.parseDouble(row2[0]),
                       Double.parseDouble(row2[1]),
                       Double.parseDouble(row2[2])};


        double[] r = {x, y, z};

        double dist1 = matherator.distance(r, r1);
        double dist2 = matherator.distance(r, r2);

        return (int) Math.round(dist1 - dist2);
      }
    };

    List<String[]> results = matherator.naiveNeighbors(k, x, y, z, data, comp);

    List<String[]> expectedResults = new ArrayList<String[]>();
    expectedResults.add(row2);
    expectedResults.add(row1);
    expectedResults.add(row3);

    assertEquals(results, expectedResults);
  }

  @Test 
  public void naiveNeighborsRandomizesTies() {
    MathBot matherator = new MathBot();
    int k = 3; 
    double x = 0; 
    double y = 0; 
    double z = 0; 
    List<String[]> data = new ArrayList<String[]>();
    String[] row1 = {"0", "0", "1"};
    String[] row2 = {"0", "1", "0"};
    String[] row3 = {"1", "0", "0"};
    String[] row4 = {"0", "1", "0"};
    String[] row5 = {"0", "0", "1"};
    data.add(row1);
    data.add(row2);
    data.add(row3);
    data.add(row4);
    data.add(row5);

    Comparator<String[]> comp = new Comparator<String[]>() {
      public int compare(String[] row1, String[] row2) {
        double[] r1 = {Double.parseDouble(row1[0]),
                       Double.parseDouble(row1[1]),
                       Double.parseDouble(row1[2])};
        double[] r2 = {Double.parseDouble(row2[0]),
                       Double.parseDouble(row2[1]),
                       Double.parseDouble(row2[2])};


        double[] r = {x, y, z};

        double dist1 = matherator.distance(r, r1);
        double dist2 = matherator.distance(r, r2);

        return (int) Math.round(dist1 - dist2);
      }
    };

    List<String[]> expectedResults = new ArrayList<String[]>();
    expectedResults.add(row1);
    expectedResults.add(row2);
    expectedResults.add(row3);
    expectedResults.add(row4);
    expectedResults.add(row5);

    List<Boolean> equalityResults = new ArrayList<Boolean>();

    for (int i = 0; i < 100; i++) {
      List<String[]> results = matherator.naiveNeighbors(k, x, y, z, data, comp);
      equalityResults.add(results.equals(expectedResults));
    }
    
    // Less than 0.000001 probability that this test will fail. (Binomial 
    // probability that every one of 100 shuffles left ordering unchanged.)
    assertEquals(equalityResults.contains(false), true);
  }

  @Test 
  public void naiveNeighborsKTooLarge() {
    MathBot matherator = new MathBot();
    int k = 4; 
    double x = 1.022; 
    double y = 4.232; 
    double z = 6.134; 

    List<String[]> data = new ArrayList<String[]>();
    String[] row1 = {"1.123131", "2", "-3"};
    String[] row2 = {"1.123123", "2", "3"};
    String[] row3 = {"11", "2", "100000"};
    data.add(row1);
    data.add(row2);
    data.add(row3);

    Comparator<String[]> comp = new Comparator<String[]>() {
      public int compare(String[] row1, String[] row2) {
        double[] r1 = {Double.parseDouble(row1[0]),
                       Double.parseDouble(row1[1]),
                       Double.parseDouble(row1[2])};
        double[] r2 = {Double.parseDouble(row2[0]),
                       Double.parseDouble(row2[1]),
                       Double.parseDouble(row2[2])};

        double[] r = {x, y, z};

        double dist1 = matherator.distance(r, r1);
        double dist2 = matherator.distance(r, r2);

        return (int) Math.round(dist1 - dist2);
      }
    };

    List<String[]> results = matherator.naiveNeighbors(k, x, y, z, data, comp);

    List<String[]> expectedResults = new ArrayList<String[]>();
    expectedResults.add(row2);
    expectedResults.add(row1);
    expectedResults.add(row3);

    assertEquals(results, expectedResults);
  }

  @Test 
  public void naiveNeighborsKEqualsZero() {
    MathBot matherator = new MathBot();
    int k = 0; 
    double x = 1.022; 
    double y = 4.232; 
    double z = 6.134; 

    List<String[]> data = new ArrayList<String[]>();
    String[] row1 = {"1.123131", "2", "-3"};
    String[] row2 = {"1.123123", "2", "3"};
    String[] row3 = {"11", "2", "100000"};
    data.add(row1);
    data.add(row2);
    data.add(row3);

    Comparator<String[]> comp = new Comparator<String[]>() {
      public int compare(String[] row1, String[] row2) {
        double[] r1 = {Double.parseDouble(row1[0]),
                       Double.parseDouble(row1[1]),
                       Double.parseDouble(row1[2])};
        double[] r2 = {Double.parseDouble(row2[0]),
                       Double.parseDouble(row2[1]),
                       Double.parseDouble(row2[2])};

        double[] r = {x, y, z};

        double dist1 = matherator.distance(r, r1);
        double dist2 = matherator.distance(r, r2);

        return (int) Math.round(dist1 - dist2);
      }
    };

    List<String[]> results = matherator.naiveNeighbors(k, x, y, z, data, comp);

    List<String[]> expectedResults = new ArrayList<String[]>();

    assertEquals(results, expectedResults);
  }

  @Test 
  public void naiveNeighborsNonEuclidean() {
    MathBot matherator = new MathBot();
    int k = 3; 
    double x = 1.022; 
    double y = 4.232; 
    double z = 6.134; 

    List<String[]> data = new ArrayList<String[]>();
    String[] row1 = {"1.123131", "2", "-3"};
    String[] row2 = {"1.123123", "2", "3"};
    String[] row3 = {"11", "2", "100000"};
    data.add(row1);
    data.add(row2);
    data.add(row3);

    Comparator<String[]> comp = new Comparator<String[]>() {
      public int compare(String[] row1, String[] row2) {
        double[] r1 = {Double.parseDouble(row1[0]),
                       Double.parseDouble(row1[1]),
                       Double.parseDouble(row1[2])};
        double[] r2 = {Double.parseDouble(row2[0]),
                       Double.parseDouble(row2[1]),
                       Double.parseDouble(row2[2])};

        double[] r = {x, y, z};

        double dist1 = matherator.distance(r, r1);
        double dist2 = matherator.distance(r, r2);

        return (int) (-1 * Math.round(dist1 - dist2));
      }
    };

    List<String[]> results = matherator.naiveNeighbors(k, x, y, z, data, comp);
    List<String[]> expectedResults = new ArrayList<String[]>();

    expectedResults.add(row3);
    expectedResults.add(row1);
    expectedResults.add(row2);

    assertEquals(results, expectedResults);
  }
}
