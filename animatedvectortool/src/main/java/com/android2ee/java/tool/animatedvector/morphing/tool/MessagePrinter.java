/**
 * <ul>
 * <li>MessagePrinter</li>
 * <li>com.android2ee.java.tool.animatedvector.morphing.tool</li>
 * <li>26/11/2015</li>
 * <p/>
 * <li>======================================================</li>
 * <p/>
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 * <p/>
 * /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br>
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 * Belongs to <strong>Mathias Seguy</strong></br>
 * ***************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * <p/>
 * *****************************************************************************************************************</br>
 * Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 * Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br>
 * Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */

package com.android2ee.java.tool.animatedvector.morphing.tool;

import com.android2ee.java.tool.animatedvector.morphing.VectorDrawableParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Mathias Seguy - Android2EE on 26/11/2015.
 */
public class MessagePrinter {
    /***********************************************************
     * Printing Message
     **********************************************************/


    private static final String help_message = "The command lines expected are:\r\n\r\n" +
            " * 1)For a simple morphing from one file to another one\r\n" +
            " * java -jar animatedvectortool-0.1.jar #myInitialVectorDrawablePath #myFinalVectorDrawablePath\r\n\r\n" +
            " * 2)to use the trim technic (Nick Butcher @crafty droidcon london 2015)\r\n" +
            " * --It allows you to move the path from one state to an other one\r\n" +
            " * java -jar animatedvectortool-0.1.jar trim #myInitialVectorDrawablePath #myFinalVectorDrawablePath\r\n\r\n" +
            " * 3)For a list of morphing between a set of images where you list all the elements you want to morph\r\n" +
            " * java -jar animatedvectortool-0.1.jar #myInitialVectorDrawablePath #myIntermediateVectorDrawable1Path #myIntermediateVectorDrawable2Path  #myFinalVectorDrawablePath\r\n\r\n" +
            " * where  #myIntermediateVectorDrawable1Path has to be replaced by the real name of the file\r\n\r\n" +
            " * 4)java -jar animatedvectortool-0.1.jar v gives you the version \r\n\r\n" +
            " * 5)java -jar animatedvectortool-0.1.jar h gives you the help \r\n\r\n" +
            " * 5)java -jar animatedvectortool-0.1.jar debug gives you the full log \r\n\r\n" ;
    private static final String help_message2="We need at least two files !!!";
    private static final String help_message3="You need to give the same android:morphingName xml tag to the path you want to morph (use the android:morphingName xml tag for this please)";
    private static final String help_message3_1="Just add this tag to your path android:morphingName (it's not an android tag) and give the same name to the paths your want to morph from one to the other\r\n" +
            "The value of the tag android:morphingName has to be unique in the whole files set and it is used to make the pair of Path <InitialPath,FinalPath> in the morphing operation.\r\n" +
            "Sample. The first vectorDrawable contains the following Path :\r\n" +
            "<path\r\n" +
            "            android:name=\"head\"\n" +
            "            android:morphingName=\"myMorphingPairUniqueId\"\n" +
            "            android:fillColor=\"#9FBF3B\"\n" +
            "            android:pathData=\"M301.314,....\n" +
            "            />" +
            "The second VectorDrawable contains the following Path (we don't care where)\r\n" +
            "<path\n" +
            "            android:name=\"left_arm\"\n" +
            "            android:morphingName=\"myMorphingPairUniqueId\"\n" +
            "            android:fillColor=\"#9FBF3B\"\n" +
            "            android:pathData=\"M126.383,297.598c0,.... />\n" +
            "        />\r\n" +
            "Then those two paths will be morph one from the other because they have the same android:morphingName (myMorphingPairUniqueId)\r\n" +
            "Is it ok ? You can fix your drawable now, just by adding the android:morphingName tag to the path you want to morph ";
    private static final String help_message4="We only morph paths that have a name the others are just skipped";
    private static final String Reward = "This tool is based on VectAlign a tool made by Stefano Bonetta https://github.com/bonnyfone/vectalign, all the rewards are his";
    private static String header = "\nThis tool generates the files needed for making a morphing between two (or more) VectorDrawables \r\n";
    private static String footer = "\nFor contributions or issues reporting, please visit https://github.com/MathiasSeguy-Android2EE/AnimatedVectorMorphingTool\r\n ";

    /**
     * Print the help message
     */
    public void printHelp() {
        System.out.println("This is the help:");
        jumpLine();
        jumpLine();
        System.out.println(header);
        System.out.println(help_message);
        jumpLine();
        System.out.println(help_message2);
        jumpLine();
        System.out.println(help_message3);
        jumpLine();
        System.out.println(help_message4);
        jumpLine();
        System.out.println(Reward);
        jumpLine();
        System.out.println(footer);
    }

    /**
     * Print the help message
     */
    public void printVersion() {
        System.out.println("The current version is " + VectorDrawableParser.VERSION);
    }

    /**
     * Print the help message
     */
    private  void printReward() {
        System.out.println(Reward);
        System.out.println(footer);
    }

    /***********************************************************
     *  Error messages
     **********************************************************/

    /**
     * Print the message for the PrologException that sometimes happens
     */
    public void printPrologException(){
        System.out.println("Ok during the test of the tool I have a strange Exception which was :");
        System.out.println("org.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 1; Contenu non autorisé dans le prologue.");
        System.out.println("First ensure there is not char at the before <?xml version=\"1.0\" encoding=\"utf-8\"?>");
        System.out.println("If it does not fix, just create a new file, copy/paste the content of the one that bug inside this new file, and it should be ok.");
        System.out.println("Yep this is fucking weird");
    }

    /**
     * Print message to say that we use android:morphingName to know which path to morph in which path
     * So we display that message when in a vectorDrawable we don't find that xml tag
     */
    public void printExceptionNoMorphingPathNameDefined(){
        System.out.println("Ok you forget to add meta data in your AndroidVector  file for us to know which paths to morphs to which paths. SO here are the rules:");
        System.out.println(help_message3);
        System.out.println(help_message3_1);
    }
    /**
     * Print message to say that we use android:name is mandatory when android:morphingName is set
     * So we display that message when in a vectorDrawable we don't find that xml tag
     */
    public void printExceptionNoPathNameDefined(String morphingName){
        System.out.println("Ok you forget to define your android:name for the android:morphin");
        System.out.println("android:name is mandatory when you defined an android:morphingName");
        System.out.println("the path without android:name has for android:morphing="+morphingName);
    }
    /**
     * Print the exception and generates the bug report
     * @param e
     * @param initialPath
     * @param finalPath
     */
    public void printExceptionInMorphing(Exception e,String absoluteWorkingDirectoryPath, String initialPath, String finalPath) {
        System.out.println("************Exception in the morphing process occurs *********");
        System.out.println("\nPlease report the issue at  https://github.com/bonnyfone/vectalign \n ");
        System.out.println("\nWe have generate the bug report file under the name MorphingBugReport \n ");
        File file=new File(absoluteWorkingDirectoryPath,"MorphingBugReport");
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            //then write in it
            BufferedWriter buff=new BufferedWriter(new FileWriter(file));

            buff.write("Please report the issue at  https://github.com/bonnyfone/vectalign");
            buff.write("Hello Mr Stefano Bonetta ");
            //TODO add gitRef
            buff.write("I am using your tool throught the librairy AnimatedVectorTool of Mathias Seguy (add git ref) and when we try to normalyze those two paths an exception is raised  ");
            buff.write("Would you please have a look and save my life :)");
            buff.write("InitialPath"+initialPath);
            buff.write("FinalPath"+finalPath);
            buff.write("Thanks again for your project");
            buff.flush();
            buff.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    /**
     * Print a message to tell that a file has not been found
     *
     * @param fileNotFound
     */
    public  void fileNotFound(String fileNotFound) {
        System.out.println("File not found exception. The following file " + fileNotFound + " has not been found");
    }

    /**
     * Print a message to tell that a file has been found
     *
     * @param fileFound
     */
    public  void fileFound(String fileFound) {
        System.out.println("File found :). The following file " + fileFound + " has been successfully loaded");
    }

    /**
     * Jump a line
     */
    public  void jumpLine() {
        System.out.println(" ");
    }

    /**
     * Jump a line
     */
    public  void paragraphSeparator() {
        System.out.println("************************************************************");
    }
}
