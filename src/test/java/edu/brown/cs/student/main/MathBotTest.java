package edu.brown.cs.student.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
}
