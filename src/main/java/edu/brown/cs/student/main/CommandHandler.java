package edu.brown.cs.student.main;

import java.util.function.Consumer;

import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class for handling commands. Allows user to add commands, specify error 
 * messages, so on. 
 * 
 * @author Student. 
 */
public class CommandHandler {
  private Map<String, Consumer<String[]>> commands = new HashMap<String, Consumer<String[]>>();
  /**
   * Default constructor.
   */
  public CommandHandler() {

  }

  /**
   * Add a new command to the commandHandler. CommandParser will call the given 
   * function func with an array of Strings as argument. If added command 
   * already exists, override previous command. 
   * 
   * @param command Name of command to be executed 
   * @param func Function to be called upon parsing the command name
   */
  public void addCommand(String command, Consumer<String[]> func) { 
    commands.put(command, func);
  }

  public String[] parseArgs(String input) {
    input = input + ' ';
    List<String> tempArguments = new ArrayList<String>();
    boolean inQuotes = false;
    int start = 0; 
    int i = 0;
    for (char c : input.toCharArray()) {
      String substr = input.substring(start, i);
      if (c == ' ' && !inQuotes && !(substr.replaceAll(" ", "").equals(""))) {
        tempArguments.add(substr.replaceAll(" ", ""));
        start = i + 1;
      } else if (c == '"') {
        if (inQuotes) {
          tempArguments.add(substr + "\"");
          start = i + 1;
        }
        inQuotes = !inQuotes;
      }
      i++;
    }

    // tempArguments.add(input.substring(start).replaceAll(" ", ""));
    String[] arguments = new String[tempArguments.size()];
    tempArguments.toArray(arguments);
    return arguments;    
  }

  /**
   * Parse the given string into a command and arguments for that command. 
   * 
   * @param input A string
   * @throws Exception In case the command is not in the CommandParser's HashMap
   */
  public void parseCommand(String input) throws Exception {
    String[] arguments = this.parseArgs(input);
    Consumer<String[]> func = commands.get(arguments[0]);

    // TODO make exception more descriptive, or perhaps use Error class?
    if (Objects.isNull(func)) {
      throw new Exception("Invalid command");
    }
    // Obtain function parameters, i.e., the argument String[] without the 
    // command itself.
    List<String> paramsList = Arrays.asList(arguments);
    String[] params = new String[paramsList.size() - 1];
    paramsList.subList(1, arguments.length).toArray(params);
    func.accept(params);
  }
}
