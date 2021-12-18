package Fractales;

public class CommandLineParser {

    public static Options parse(String[] args) {
        Options opts = new Options();
        for(int i=0; i<args.length; i++) {
            switch(args[i]) {
                case "-G":
                case "--graphical":
                    opts.launchGUI();
            }
        }
        return opts;
    }


    public static class Options {
        private boolean launchGUI = false;

        public void launchGUI() {
            this.launchGUI = true;
        }

        public boolean doLaunchGUI() {
            return this.launchGUI;
        }

    }

}
