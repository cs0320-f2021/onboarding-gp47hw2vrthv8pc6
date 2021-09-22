package edu.brown.cs.student.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import org.eclipse.jetty.io.ssl.SslClientConnectionFactory;

import freemarker.template.Configuration;
import jdk.tools.jlink.internal.SymLinkResourcePoolEntry;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.List;
/**
 * The Main class of our project. This is where execution begins.
 */
public final class Main {

  // use port 4567 by default when running server
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    // set up parsing of command line flags
    OptionParser parser = new OptionParser();

    // "./run --gui" will start a web server
    parser.accepts("gui");

    // use "--port <n>" to specify what port on which the server runs
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);
    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String input;
      List<String[]> data = new ArrayList<String[]>();
      CSVParser csvparser = new CSVParser();
      CommandHandler commandHandler = new CommandHandler();
      MathBot mathbot = new MathBot();

      // Add commands to CommandHandler
      commandHandler.addCommand("add", (args) -> {
        double result = mathbot.add(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        System.out.println(result);
      });

      commandHandler.addCommand("subtract", (args) -> {
        double result = mathbot.subtract(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        System.out.println(result);
      });

      commandHandler.addCommand("stars", (args) -> {
        try {
          csvparser.read(args[0]);
        } catch (FileNotFoundException e) {
          Error.badInputError();
        }

        data.clear();
        data.addAll(csvparser.getRows());
        System.out.println("Read " + data.size() + " stars from " + args[0]);
      });

      commandHandler.addCommand("naive_neighbors", (args) -> {
        List<String[]> neighbors = new ArrayList<String[]>();
        if (args.length == 4) {
          int k = Integer.parseInt(args[0]);
          double x = Double.parseDouble(args[1]);
          double y = Double.parseDouble(args[2]);
          double z = Double.parseDouble(args[3]);
          int xIndex = csvparser.getIndex("X");
          int yIndex = csvparser.getIndex("Y");
          int zIndex = csvparser.getIndex("Z");

          // Clear neighbors ArrayList to ensure we are not adding to 
          // previously computed list of neighbors.
          neighbors.clear();
          neighbors.addAll(mathbot.naiveNeighbors(k, data, (row1, row2) -> {
            double[] r1 = {Double.parseDouble(row1[xIndex]),
                          Double.parseDouble(row1[yIndex]),
                          Double.parseDouble(row1[zIndex])};

            double[] r2 = {Double.parseDouble(row2[xIndex]),
                          Double.parseDouble(row2[yIndex]),
                          Double.parseDouble(row2[zIndex])};

            double[] r = {x, y, z};

            double dist1 = mathbot.distance(r, r1);
            double dist2 = mathbot.distance(r, r2);

            return (int) Math.round(dist1 - dist2);
          }));
        } else if (args.length == 2) {
          String starName = args[1].replaceAll("\"", "");
          List<String[]> filteredData = new ArrayList<String[]>();
          String[] starRow = null;
          for (String[] row : data) {
            if (row[csvparser.getIndex("ProperName")].equals(starName)) {
              starRow = row;
            } else {
              filteredData.add(row);
            }
          }
          int k = Integer.parseInt(args[0]);
          int xIndex = csvparser.getIndex("X");
          int yIndex = csvparser.getIndex("Y");
          int zIndex = csvparser.getIndex("Z");

          double x = Double.parseDouble(starRow[xIndex]);
          double y = Double.parseDouble(starRow[yIndex]);
          double z = Double.parseDouble(starRow[zIndex]);

          // Clear neighbors ArrayList to ensure we are not adding to 
          // previously computed list of neighbors.
          neighbors.clear();
          neighbors.addAll(mathbot.naiveNeighbors(k, filteredData, (row1, row2) -> {
            double[] r1 = {Double.parseDouble(row1[xIndex]),
                          Double.parseDouble(row1[yIndex]),
                          Double.parseDouble(row1[zIndex])};

            double[] r2 = {Double.parseDouble(row2[xIndex]),
                          Double.parseDouble(row2[yIndex]),
                          Double.parseDouble(row2[zIndex])};

            double[] r = {x, y, z};

            double dist1 = mathbot.distance(r, r1);
            double dist2 = mathbot.distance(r, r2);

            return (int) Math.signum(dist1 - dist2);
          }));
        }

        //Print computed neighbors
        for (String[] row : neighbors) {
          System.out.println(row[csvparser.getIndex("StarID")]);
        }
      });

      // Read-evaluate-print loop
      while ((input = br.readLine()) != null) {
        try {
          input = input.trim();
          commandHandler.parseCommand(input);
        } catch (Exception e) {
          Error.badInputError();
        }
      }
    } catch (Exception e) {
      Error.replError();
    }

  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration(Configuration.VERSION_2_3_0);

    // this is the directory where FreeMarker templates are placed
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    // set port to run the server on
    Spark.port(port);

    // specify location of static resources (HTML, CSS, JS, images, etc.)
    Spark.externalStaticFileLocation("src/main/resources/static");

    // when there's a server error, use ExceptionPrinter to display error on GUI
    Spark.exception(Exception.class, new ExceptionPrinter());

    // initialize FreeMarker template engine (converts .ftl templates to HTML)
    FreeMarkerEngine freeMarker = createEngine();

    // setup Spark Routes
    Spark.get("/", new MainHandler(), freeMarker);
  }

  /**
   * Display an error page when an exception occurs in the server.
   */
  private static class ExceptionPrinter implements ExceptionHandler<Exception> {
    @Override
    public void handle(Exception e, Request req, Response res) {
      // status 500 generally means there was an internal server error
      res.status(500);

      // write stack trace to GUI
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  /**
   * A handler to serve the site's main page.
   *
   * @return ModelAndView to render.
   * (main.ftl).
   */
  private static class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      // this is a map of variables that are used in the FreeMarker template
      Map<String, Object> variables = ImmutableMap.of("title",
          "Go go GUI");

      return new ModelAndView(variables, "main.ftl");
    }
  }
}
