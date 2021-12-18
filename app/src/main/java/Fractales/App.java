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
            Julia julia = new Julia(w, h, max, real, imaginary, r);
            julia.drawFractal();
        }
    }
}
