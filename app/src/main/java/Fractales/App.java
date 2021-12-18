package Fractales;

import Fractales.CommandLineParser;
import Fractales.GraphicalApp;
import Fractales.Julia;

import javafx.application.Application;


public class App {
    public static void main(String[] args) {
        CommandLineParser.Options opts = CommandLineParser.parse(args);

        if (opts.doLaunchGUI()) {
            Application.launch(GraphicalApp.class);
        } else {
            System.out.println("Command line version");
            Julia julia = new Julia(800, 600, 20, -0.4, 0.6, 2);
            julia.drawFractal();
        }
    }
}
