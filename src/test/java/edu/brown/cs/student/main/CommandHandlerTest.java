package edu.brown.cs.student.main;


import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.ArrayList;


public class CommandHandlerTest {
  @Test 
  public void parseWithoutInitTest() {
    CommandHandler cmdHandler = new CommandHandler();
    Boolean caughtException = false;
    try {
      cmdHandler.parseCommand("this_command_does_not_exist");
    } catch (Exception e) {
      caughtException = true;
    }

    assert(caughtException);
  }

  @Test 
  public void mutationTest() {
    CommandHandler cmdHandler = new CommandHandler();
    List<String> array = new ArrayList<String>();
    array.add("A");

    List<String> expectedArray = new ArrayList<String>();
    expectedArray.add("A");
    expectedArray.add("B");

    cmdHandler.addCommand("add1", (args) -> {
      array.add("B");
    });
    try {
      cmdHandler.parseCommand("add1");
    } catch (Exception e) {
      e.printStackTrace();
    }

    assertEquals(array, expectedArray);
  }

  @Test 
  public void duplicateCommandTest() {
    CommandHandler cmdHandler = new CommandHandler();
    List<String> array = new ArrayList<String>();
    array.add("A");

    List<String> expectedArray = new ArrayList<String>();
    expectedArray.add("A");
    expectedArray.add("C");

    cmdHandler.addCommand("add1", (args) -> {
      array.add("B");
    });

    cmdHandler.addCommand("add1", (args) -> {
      array.add("C");
    });
    try {
      cmdHandler.parseCommand("add1");
    } catch (Exception e) {
      e.printStackTrace();
    }

    assertEquals(array, expectedArray);
  }

  @Test
  public void multipleSpacesTest() {
    CommandHandler cmdHandler = new CommandHandler();
    String[] actual = cmdHandler.parseArgs("a b  c  d");
    String[] expected = {"a", "b", "c", "d"};
    int i = 0;
    for (String str : actual) {
      assertEquals(str, expected[i]);
      i += 1;
    }
  }

  @Test
  public void argWithSpacesTest() {
    CommandHandler cmdHandler = new CommandHandler();
    String[] actual = cmdHandler.parseArgs("a b \"hello world\" d");
    String[] expected = {"a", "b", "\"hello world\"", "d"};
    int i = 0;
    for (String str : actual) {
      assertEquals(str, expected[i]);
      i += 1;
    }

    String[] actual2 = cmdHandler.parseArgs("a b \"hello world\"");
    String[] expected2 = {"a", "b", "\"hello world\""};
    i = 0;
    for (String str : actual2) {
      assertEquals(str, expected2[i]);
      i += 1;
    }
  }
}
