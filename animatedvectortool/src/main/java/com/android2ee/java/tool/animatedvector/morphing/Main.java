/**
 * <ul>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Android2EE Project</li>
 * <li>Produit par MSE.</li>
 * </ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage at your own risk, see the licence.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://www.android2ee.com/About/Mathias-Seguy-Fondateur.html</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation à vos propres risque, regarder la licence.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://www.android2ee.com/About/Mathias-Seguy-Fondateur.html</em></br> </br>
 * *****************************************************************************************************************</br>
 * <p/>
 * <p/>
 * This code use the library done by bonnyfone :https://github.com/bonnyfone
 * This guy has the right to copy paste the code below, do what he wants to it,
 * because the great job is done by his project
 * I am just adapting it to android for a fun experience
 */
package com.android2ee.java.tool.animatedvector.morphing;

import java.io.IOException;

/**
 * The main class parse your data to find where are the file
 * The command lines expected are:
 * 1)For a simple morphing from one file to another one (separate path using space)
 * #myInitialVectorDrawablePath #myFinalVectorDrawablePath
 * 2)to use the trim technic (Nick Butcher @crafty https://photos.google.com/share/AF1QipMRnZL6gNbS06fnBNtKffRm9HBaxW8iP6w0L1T4nZYLI6s3wi_l8daT6mq4nwPf-w?key=LThZNmFXUUtmNi04bWlEYmVfcWdPenlvaDdCRU13)
 * --It allows you to move the path from one state to an other one
 * trim #myInitialVectorDrawablePath #myFinalVectorDrawablePath
 * 3)For a list of morphing between a set of images where you list all the elements you want to morph
 * #myInitialVectorDrawablePath #myIntermediateVectorDrawable1Path #myIntermediateVectorDrawable2Path  #myFinalVectorDrawablePath
 * <p/>
 * where  #myIntermediateVectorDrawable1Path has to be replaced by the real name of the file
 * <p/>
 * /*\ Mathias :
 * You have to java -jar animatedvectortool.jar
 * And do generate the jar you have to open your gradle view and run the task fatjar
 */
public class Main {


    /**
     * VectAlign commandLine main
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        VectorDrawableParser parser=new VectorDrawableParser(args);
        parser.parseVectorDrawable();
    }

//
//    /***********************************************************
//     * Initialize command line
//     **********************************************************/
////Commandline Options
//    private static final String OPTION_PATHS = "p";
//    //    private static final String OPTION_TO = "e";
//
//    /**
//     * Create commandLine options
//     *
//     * @return
//     */
//    private static Options initCommandLineOptions() {
//        Options options = new Options();
//
//        options.addOption(OptionBuilder.withLongOpt("paths")
//                .withDescription("The list of VectorDrawable paths ")
//                .hasArg()
//                .withArgName("TXT_FILE")
//                .create(OPTION_PATHS));
//
////        options.addOption(OptionBuilder.withLongOpt("end")
////                .withDescription("Ending VectorDrawable path (\"string\", txt file or SVG file)")
////                .hasArg()
////                .withArgName("\"STRING\"|TXT_FILE|SVG_FILE")
////                .create(OPTION_TO));
//
//        options.addOption(OptionBuilder.withLongOpt("version")
//                .withDescription("Print the version of the application")
//                .create(OPTION_VERSION));
//
//        options.addOption(OptionBuilder.withLongOpt("help").create(OPTION_HELP));
//
//
//        return options;
//    }
//
//    /**
//     * Print the help message
//     *
//     * @param options
//     */
//    private static void printHelp(Options options) {
//        String header = "\nAlign two VectorDrawable paths in order to allow morphing animations between them.\n\n";
//        String footer = "\nFor contributions or issues reporting, please visit" + Utils.ANSI_CYAN + " https://github.com/bonnyfone/vectalign \n " + Utils.ANSI_RESET;
//        System.out.println(header);
//        System.out.println(help_message);
//        System.out.println(Reward);
//        System.out.println(footer);
//    }
//
//
//    private void oldCode(String args[]) {
//
//
//
//
//
//
//
//        /*TEST input args
//            String star = "M 48,54 L 31,42 15,54 21,35 6,23 25,23 25,23 25,23 25,23 32,4 40,23 58,23 42,35 z";
//            String arrow = "M 12, 4 L 10.59,5.41 L 16.17,11 L 18.99,11 L 12,4 z M 4, 11 L 4, 13 L 18.99, 13 L 20, 12 L 18.99, 11 L 4, 11 z M 12,20 L 10.59, 18.59 L 16.17, 13 L 18.99, 13 L 12, 20z";
//            //args = new String[]{"-s", star, "--end", arrow};
//            //args = new String[]{"-h"};
//        */
//
//        String fromSequence = null;
//        String toSequence = null;
//        Options options = initCommandLineOptions();
//
//        try {
//            CommandLineParser parser = new DefaultParser();
//            CommandLine commandLine = parser.parse(options, args);
//
//            if (commandLine.getOptions() == null
//                    || commandLine.getOptions().length == 0) {
//
//                System.out.println("I print help because there is no parameter");
//                printHelp(options);
//                return;
//            } else if (commandLine.hasOption(OPTION_VERSION)) {
//                System.out.println(NAME + " v" + VERSION);
//                return;
//            }
//
//            File tmpFile;
//            if (commandLine.hasOption(OPTION_PATHS)) {
//                System.out.println("There are path in the command line");
//                String[] filesPathList = commandLine.getOptionValues(OPTION_PATHS);
//                for (int i = 0; i < filesPathList.length; i++) {
//                    System.out.println("There are path in the command line: " + filesPathList[i]);
//                }
//                fromSequence = commandLine.getOptionValue(OPTION_PATHS);
//                //here extract the list of path (separate by a space)
//                System.out.println("There are path in the command line: " + fromSequence);
//                tmpFile = new File(fromSequence);
//                if (tmpFile.isFile() && tmpFile.exists()) {
//                    System.out.println("the file has been created we can parse it : " + tmpFile.getAbsolutePath());
//                    if (SVGParser.isSVGImage(tmpFile))
//                        fromSequence = SVGParser.getPathDataFromSVGFile(tmpFile);
//                    else
//                        fromSequence = Utils.readSequenceFromFile(tmpFile);
//                }
//            }
//
//
////            if (fromSequence == null || toSequence == null) {
////                if (fromSequence == null)
////                    System.out.println("Missing START path sequence. Specify the starting path using -s (or --start)");
////                else
////                    System.out.println("Missing END path sequence. Specify the ending path using -e (or --end)");
////
////                return;
////            }
//
//            String[] align = null;
//            try {
//                align = VectAlign.align(fromSequence, toSequence, VectAlign.Mode.BASE);
//            } catch (Exception e) {
//                System.out.println("###################### EXCEPTION #####################");
//                e.printStackTrace();
//                System.out.println("######################################################");
//                System.out.println("\nFor contributions or issues reporting, please visit " + Utils.ANSI_CYAN + "https://github.com/bonnyfone/vectalign \n " + Utils.ANSI_RESET);
//            }
//
//            if (align == null) {
//                //Something went wrong, read exceptions!
//                return;
//            }
//
//            System.out.println("\n--------------------");
//            System.out.println("  ALIGNMENT RESULT  ");
//            System.out.println("-------------------- ");
//            System.out.println("\n# new START path:  \n" + Utils.ANSI_GREEN + align[0] + Utils.ANSI_RESET);
//            System.out.println("\n# new END path:  \n" + Utils.ANSI_YELLOW + align[1] + Utils.ANSI_RESET);
//            System.out.println("\nThese sequences are morphable and can be used as 'pathData' attributes inside of VectorDrawable files.\n");
//
//        } catch (ParseException e) {
//            System.out.println("Wrong parameters!\n");
//            printHelp(options);
//        }
//    }
}
