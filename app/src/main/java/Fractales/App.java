package Fractales;

import javafx.application.Application;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.HelpFormatter;


public class App {
    public static void main(String[] args) throws ParseException {
        // CommandLineParser.Options opts = CommandLineParser.parse(args);

        Options options = new Options();
        // options.addOption("G", "graphical", false, "Start FractalExplorer in graphical mode");
        // options.addOption("-t", "fractal", true, "The fractal type. Can be \"julia\" or \"mandelbrot\". This option is mandatory for command line mode.");
        // options.addOption("-w", )

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
        options.addOption(Option.builder("w")
                                .longOpt("width")
                                .desc("The width of the generated image for command line mode, or the window for graphical mode")
                                .type(Number.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("h")
                                .longOpt("height")
                                .desc("The height of the generated image for command line mode, or the window for graphical mode")
                                .type(Number.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("i")
                                .longOpt("iterations")
                                .desc("The number of iterations of the fractal")
                                .type(Number.class)
                                .numberOfArgs(1)
                                .build()
        );
        options.addOption(Option.builder("c")
                                .longOpt("complex")
                                .desc("Comma separated values for complex number to use with julia and mandelbrot sets\nFor x+yi, input \"x,y\"")
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


        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("./run", "A fractal explorer and renderer", options, "", true);
            System.exit(0);
        }

        if (cmd.hasOption("G")) {
            Application.launch(GraphicalApp.class);
        } else {
            System.out.println("Command line version");
            Julia julia = new Julia(800, 600, 20, -0.70176, -0.3842, 2);
						Mandelbrot mdb = new Mandelbrot(800, 600, 1000, 2);
						mdb.drawFractal();
						mdb.saveImage();
        }
    }
}
