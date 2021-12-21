package Fractales;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.math3.complex.Complex;


public class App {

    private static double x1 = -2;
    private static double y1 = -2;
    private static double x2 = 2;
    private static double y2 = 2;
    private static double complexReal = -0.70176;
    private static double complexImag = -0.3842;
    private static double step = 0.004;
    private static double zoom = 1;
    private static int iterations = 50;
    private static int pixelSize = 1000;
    private static String type = "";
    private static String filename = "";


    private static void printHelpAndQuit(Options options) {
        System.out.println("");
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("./run", "A fractal explorer and renderer", options, "", true);
        System.exit(0);
    }

    private static CommandLine parseArgs(Options options, String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if(cmd.hasOption("t")) {
            String optVal = cmd.getOptionValue("t");
            if (! (optVal.equals("julia") || optVal.equals("mandelbrot"))) {
                throw new ParseException("invalid fractal type");
            }
            type = optVal;
        }

        if(cmd.hasOption("r")) {
            String optVal = cmd.getOptionValue("r");
            String[] coords = optVal.split(",");
            if (coords.length != 4) {
                throw new ParseException("invalid rectangle. Must be 4 comma separated values, with x1<x2 & y1<y2.");
            }
            try {
                x1 = Double.parseDouble(coords[0]);
                y1 = Double.parseDouble(coords[1]);
                x2 = Double.parseDouble(coords[2]);
                y2 = Double.parseDouble(coords[3]);

                // if (x1 >= x2 || y1 >= y2)
                //     throw new NumberFormatException();

            } catch (NumberFormatException e) {
                throw new ParseException("invalid rectangle. Must be 4 comma separated integers, with x1<x2 & y1<y2.");
            }
        }

        if(cmd.hasOption("c")) {
            String optVal = cmd.getOptionValue("c");
            String[] values = optVal.split(",");
            if (values.length != 2) {
                throw new ParseException("invalid complex. Must be 2 comma separated doubles for real and imaginary parts.");
            }
            try {
                complexReal = Double.parseDouble(values[0]);
                complexImag = Double.parseDouble(values[1]);
            } catch (NumberFormatException e) {
                throw new ParseException("invalid complex. Must be 2 comma separated doubles for real and imaginary parts.");
            }
        }
        if(cmd.hasOption("i")) {
            iterations = ((Number)cmd.getParsedOptionValue("i")).intValue();
        }
        if(cmd.hasOption("s")) {
            step = ((Number)cmd.getParsedOptionValue("s")).doubleValue();
        }
        if(cmd.hasOption("p")) {
            if(!cmd.hasOption("G") && cmd.hasOption("s"))
                throw new ParseException("can't have both step and screen-size options set in command line mode.");
            pixelSize = ((Number)cmd.getParsedOptionValue("p")).intValue();
        }
        if(cmd.hasOption("z")) {
            zoom = ((Number)cmd.getParsedOptionValue("z")).doubleValue();
        }

        filename = cmd.getOptionValue("f", "");

        return cmd;

    }



    public static void main(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption(Option.builder("G")
                                .longOpt("graphical")
                                .desc("Start FractalExplorer in graphical mode")
                                .type(Boolean.class)
                                .numberOfArgs(0)
                                .build()
        );
        options.addOption(Option.builder("t")
                                .longOpt("type")
                                .desc("The fractal type. Can be \"julia\" or \"mandelbrot\". This option is mandatory for command line mode.")
                                .type(String.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("r")
                                .longOpt("rectangle")
                                .desc("Comma separated integers coordinates of start and end points of the rectangle to use in the complex plane to generate the fractal\nFor the rectangle defined by points x1+i*y1 and x2+i*y2, input \"x1,y1,x2,y2\"\nDefault is -2,-2,2,2")
                                .type(String.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("i")
                                .longOpt("iterations")
                                .desc("The number of iterations of the fractal. Default is 50.")
                                .type(Number.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("c")
                                .longOpt("complex")
                                .desc("Comma separated values for complex number to use as constant with julia\nFor x+yi, input \"x,y\"\nDefault is -0.70176,-0.3842")
                                .type(String.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("s")
                                .longOpt("step")
                                .desc("Decimal step between each pixel in the complex plane. You have to choose between this option and screen-size in command line mode. In graphical mode, this option is useless. Default is 0.004")
                                .type(Number.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("p")
                                .longOpt("pixel-size")
                                .desc("Size of produced image or size of window in graphical mode. You have to choose between this option and step in command line mode. Default is 1000.")
                                .type(Number.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("z")
                                .longOpt("zoom")
                                .desc("Multiplies the size of the complex rectangle by this factor")
                                .type(Number.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("f")
                                .longOpt("filename")
                                .desc("Path of the file WITHOUT EXTENSION to write the image in for command line mode. Default is the name of fractal")
                                .type(String.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("?")
                                .longOpt("help")
                                .desc("Prints this help")
                                .numberOfArgs(0)
                                .build()
        );


        CommandLine cmd = null;
        try {
            cmd = parseArgs(options, args);
        } catch (ParseException e) {
            System.out.println("Error : "+e.getMessage());
            printHelpAndQuit(options);
        }


        if (cmd.hasOption("help")) {
            printHelpAndQuit(options);
        }

        if (cmd.hasOption("G")) {

            Application.launch(GraphicalApp.class);

            /**** Possible solution to pass args to GraphicalApp ****/

            // GraphicalApp gApp = new GraphicalApp(pixelSize);
            // try {
            //     gApp.init();
            // } catch (Exception e) {
            //     e.printStackTrace();
            // }
            //
            // Platform.startup(() -> {
            //     Stage stage = new Stage();
            //     gApp.start(stage);
            // });

        } else {
            Fractal frac = null;
            Complex p1 = new Complex(x1, y1);
            Complex p2 = new Complex(x2, y2);
            Complex constant = new Complex(complexReal, complexImag);

            switch(type) {
                case "julia":
                    if(cmd.hasOption("s"))
                        frac = new Julia(p1, p2, constant, step, iterations, 2).zoomed(zoom);
                    else
                        frac = new Julia(p1, p2, constant, pixelSize, iterations, 2).zoomed(zoom);
                    break;
                case "mandelbrot":
                    if(cmd.hasOption("s"))
                        frac = new Mandelbrot(p1, p2, step, iterations, 2).zoomed(zoom);
                    else
                        frac = new Mandelbrot(p1, p2, pixelSize, iterations, 2).zoomed(zoom);
                    break;
                case "":
                    System.out.println("Error : unspecified fractal type for command line mode.");
                    printHelpAndQuit(options);
                break;
            }

            Renderer.saveToFile(filename.isEmpty() ? frac.getName() : filename, Renderer.drawFractal(frac));

			// Mandelbrot mdb = new Mandelbrot(800, 600, 1000, 2);

        }
    }
}
