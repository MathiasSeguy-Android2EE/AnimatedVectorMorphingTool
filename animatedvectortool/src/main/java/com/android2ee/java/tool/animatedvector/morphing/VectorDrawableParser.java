/**
 * <ul>
 * <li>VectorDrawableParser</li>
 * <li>com.android2ee.java.tool.animatedvector.morphing</li>
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

package com.android2ee.java.tool.animatedvector.morphing;

import com.android2ee.java.tool.animatedvector.morphing.model.Path;
import com.android2ee.java.tool.animatedvector.morphing.model.VectorDrawable;
import com.android2ee.java.tool.animatedvector.morphing.model.VectorDrawableSaxHandler;
import com.android2ee.java.tool.animatedvector.morphing.tool.CustomLogger;
import com.android2ee.java.tool.animatedvector.morphing.tool.MessagePrinter;
import com.android2ee.java.tool.animatedvector.morphing.tool.ResultGenerator;
import com.bonnyfone.vectalign.VectAlign;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Mathias Seguy - Android2EE on 26/11/2015.
 * This class load the files, analyse them, builds the VectorDrawable, Vector align them
 * and generates the result files
 * Ok, this is the main class that does all the work
 */
public class VectorDrawableParser {
    /***********************************************************
     * Attributes
     **********************************************************/
    //Commandline Options
    private static final String OPTION_TRIM = "trim";
    private static final String OPTION_DEBUG = "debug";
    private static final String OPTION_HELP = "h";
    private static final String OPTION_VERSION = "v";
    private static final String OPTION_HELP2 = "-h";
    private static final String OPTION_VERSION2 = "-v";


    //Application infos
    public static final String VERSION = "0.0";
    public static final String NAME = "Animated Vector Morphing";

    /**
     * To know if we are in the Trim context
     */
    public static boolean TRIM_CONTEXT = false;
    /**
     * To know if we need to log every think or not
     */
    public static boolean DEBUG = false;
    /**
     * The absolute path of the current directory we work in
     */
    public  String absoluteWorkingDirectoryPath;
    /**
     * Correction of the lenght of the files gave as parameters depending on the option Trim and Debug
     */
    int firstFileIndexCorrection = 0, fileSize;
    /**
     * The list of files found
     */
    File[] files;
    /**
     * Boolean to know if an exception occurs when loading the files in memory
     */
    boolean exceptionOccursWhenLoadingFiles = false;
    /**
     * The list of VectorDrawable loaded from the xml files
     * Eachg file generates a VectorDrawable
     */
    ArrayList<VectorDrawable> vectorDrawables;
    /**
     * The arguments gave by the command line
     */
    String[] args;
    /**
     * The printer to print messages
     */
    MessagePrinter printer=new MessagePrinter();
    /***********************************************************
     *  Constructor
     **********************************************************/
    /**
     * Constructor
     * @param args The arguments gave by the command line
     */
    public VectorDrawableParser(String args[] ){
        this.args=args;
    }

    /***********************************************************
     *  Parsing Method
     **********************************************************/

    /**
     * Main method to parse the VectorDrawable and generate the output files
     */
    public void parseVectorDrawable(){
        //check if inputs are ok
        if (ManageInputArguments()){
            return;
        }
        //initialize the number of files
        fileSize = args.length;
        //read the arguments:
        //analyseTrimAndDebugOptions
        analyseTrimAndDebugOption();
        //////////////////////////
        //Read the files and load them in memory
        /////////////////////////
        loadFilesInMemory();
        //if a problem occurs, die
        if (exceptionOccursWhenLoadingFiles) {
            //print help
            printer.printHelp();
            //stop the work and apologize
            return;
        }
        //find the absolute path of the working directory, we will need it
        findWorkingDirectoryPath();

        //////////////////////////
        //Parse the file and generate the Java object structure
        /////////////////////////
        //so now, we have all the files, we have to parse them to rebuild our data as Object
        if (BuildVectorDrawablesFromFiles()){
            //a problem occurs, stop the process
            return;
        }

        //////////////////////////////
        //Run Stephano Bonetta tool to normalyse the path
        /////////////////////////
        vectAlignThePath();
        //At that point all the Path have their normalizedInitialPathData||normalizedFinalPathData defined

        //////////////////////////////
        //Then generates the result
        /////////////////////////
        ResultGenerator generator=new ResultGenerator(absoluteWorkingDirectoryPath,vectorDrawables);
        try {
            generator.launchGeneration();
        } catch (IOException e) {
            CustomLogger.logError("Danm fuck the generation failed", e);
        }

    }



    /***********************************************************
     *  Algorithmic methods
     **********************************************************/

    /**
     * Find the absolute path of the working directory
     */
    private void findWorkingDirectoryPath() {
        absoluteWorkingDirectoryPath=files[0].getAbsolutePath();
        boolean endsWithSlash = absoluteWorkingDirectoryPath.endsWith(File.separator);
        absoluteWorkingDirectoryPath=absoluteWorkingDirectoryPath.substring(0, absoluteWorkingDirectoryPath.lastIndexOf(File.separatorChar,
                endsWithSlash ? absoluteWorkingDirectoryPath.length() - 2 : absoluteWorkingDirectoryPath.length() - 1));
        CustomLogger.log("we found the absolute path " + absoluteWorkingDirectoryPath);
    }

    /**
     * Read the file and Load them in memory as Java File object
     */
    private void loadFilesInMemory() {
        printer.jumpLine();
        CustomLogger.log("Then I find the file you want me to analyse and try to load them:");

        files = new File[fileSize];
        for (int i = 0; i < args.length - firstFileIndexCorrection; i++) {
            files[i] = new File(args[i + firstFileIndexCorrection]);
            //check if the file is found
            if (!files[i].exists()) {
                printer.fileNotFound(args[i + firstFileIndexCorrection]);
                exceptionOccursWhenLoadingFiles = true;
            } else {
                //write that the file has been found
                printer.fileFound(args[i + firstFileIndexCorrection]);
            }

        }
        if(files.length<2){
            exceptionOccursWhenLoadingFiles = true;
        }
    }

    /**
     * Parse the files and build the associated VectorDrawable
     * @return true if a failure happens
     */
    private boolean BuildVectorDrawablesFromFiles() {
        vectorDrawables=new ArrayList<VectorDrawable>(files.length);
        VectorDrawable currentVectorDrawable;
        for (int i = 0; i < files.length; i++) {
            currentVectorDrawable=parseFile(files[i]);
            //set its file name
            currentVectorDrawable.setFileName(getFileNameWithoutExtension(files[i]));
            //we can set it up to initialize the hashmap and test if there are morphingNames in that Vector
            if(currentVectorDrawable.setup()){
                vectorDrawables.add(currentVectorDrawable);
            }else{
                printer.printExceptionNoMorphingPathNameDefined();
                return true;
            }

        }
        return false;
    }


    /**
     * Parse the file and build the associated VectoçrDrawable
     * @param file The xml VectorDrawable file
     * @return The VectorDrawavble java object
     */
    private  VectorDrawable parseFile(File file) {
//        readFileFirst(file);
        printer.jumpLine();
        printer.paragraphSeparator();
        CustomLogger.log("Trying to parse the file " + file.getName());
        //Create the Handler to handle each of the XML tags.
        VectorDrawableSaxHandler vectorHandler = new VectorDrawableSaxHandler();
        try {
            // Create a new instance of the SAX parser
            SAXParserFactory saxPF = SAXParserFactory.newInstance();
            SAXParser saxP = saxPF.newSAXParser();
            // The xml reader
            XMLReader xmlR = saxP.getXMLReader();
            //set the content
            xmlR.setContentHandler(vectorHandler);
            // then parse
            xmlR.parse(new InputSource(new InputStreamReader(new FileInputStream(file))));

        } catch (ParserConfigurationException e) {
            CustomLogger.logError("ParserConfigurationException ", e);
        } catch (SAXException e) {
            CustomLogger.logError("SAXException", e);
            printer.printPrologException();
        } catch (IOException e) {
            CustomLogger.logError("IOException",e);
        }
        // and retrieve the parsed vectorDrawable
        return vectorHandler.getVectorDrawable();
    }

    /**
     * For each Path to morph run the Stephano tool
     * Find the Pair of path to vector align
     * Run the tool on them
     * Store the result in the Path object
     */
    private void vectAlignThePath() {
        //NOw we want to normalise each path to make the mapping
        //So we take pair by pair and find the path to morph
        VectorDrawable vectorInitial, vectorFinal;
        String currentPathName;
        Path pathInitial,pathFinal;
        //The return of the path normalisation of bonetta 0 is initial, 1 is final
        String[] align = null;
        printer.jumpLine();
        printer.paragraphSeparator();
        CustomLogger.log("I begin to morph the path");
        for (int i = 0; i < vectorDrawables.size()-1; i++) {
            //load the current pair
            vectorInitial=vectorDrawables.get(i);
            vectorFinal=vectorDrawables.get(i+1);
            //then find the Path that has the same name
            for (Map.Entry<String, Path> entry : vectorInitial.getPathToMorphSortByMorphingName().entrySet()) {
                currentPathName= entry.getKey();
                CustomLogger.log("We found a android:MorphingName :" + currentPathName + " in the initial vector");
//                String value = entry.getValue();
                if(vectorFinal.getPathToMorphSortByMorphingName().containsKey(currentPathName)){
                    CustomLogger.log("We found the same PathName :" + currentPathName + " in the final vector");
                    //then try to morph those two paths
                    pathInitial=vectorInitial.getPathToMorphSortByMorphingName().get(currentPathName);
                    pathFinal=vectorFinal.getPathToMorphSortByMorphingName().get(currentPathName);
                    try {
                        CustomLogger.log("We launch the morphing process between those two elements");
                        align = VectAlign.align(pathInitial.getPathData(), pathFinal.getPathData(), VectAlign.Mode.BASE);
                    } catch (Exception e) {
                        CustomLogger.logError("It failed :(", e);
                        printer.printExceptionInMorphing(e,absoluteWorkingDirectoryPath, pathInitial.getPathData(), pathFinal.getPathData());
                    }
                    CustomLogger.log("It works, we normalize the path called " + currentPathName);
                    CustomLogger.log("Initial Path returns " + align[0]);
                    CustomLogger.log("Final Path returns " + align[1]);
                    //then manage the result: both are set to the initial VectorDrawable
                    pathInitial.setNormalizedInitialPathData(align[0]);
                    pathInitial.setNormalizedFinalPathData(align[1]);
                    //define the target color (fill and strocke)
                    if(pathFinal.getFillColor()!=null){
                        pathInitial.setFillColorTarget(pathFinal.getFillColor());
                    }
                    if(pathFinal.getStrokeColor()!=null){
                        pathInitial.setStrokeColorTarget(pathFinal.getStrokeColor());
                    }
                    if(pathFinal.getStrokeWidth()!=null){
                        pathInitial.setStrokeWidthTarget(pathFinal.getStrokeWidth());
                    }
                    if(pathFinal.getStrokeAlpha()!=null){
                        pathInitial.setStrokeAlphaTarget(pathFinal.getStrokeAlpha());
                    }
                    if(pathFinal.getStrokeAlpha()!=null){
                        pathInitial.setStrokeAlphaTarget(pathFinal.getStrokeAlpha());
                    }
                }

            }
        }
    }
    /**
     * Unused method used to check during developmenet if file has problem
     * Legacy code to delete
     * @param file
     */
    private  void readFileFirst(File file){
        String line;
        try (
                InputStream fis = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);
        ) {
            while ((line = br.readLine()) != null) {
                // Deal with the line
                CustomLogger.log(line);
            }
        } catch (FileNotFoundException e) {
            CustomLogger.logError("FileNotFoundException", e);
        } catch (IOException e) {
            CustomLogger.logError("IOException", e);
        }
    }

    /**
     * Return the name of the file without its extension
     * @param file
     * @return
     */
    private String getFileNameWithoutExtension(File file){
        String nameWithoutExt=file.getName().replaceFirst("[.][^.]+$", "");;
        CustomLogger.log("The file " + file.getName() + " without ext = " + nameWithoutExt);
        return nameWithoutExt;
    }

    /***********************************************************
     *  Managing command lines arguments
     **********************************************************/
    /**
     * Manage if the input arguments are nok
     * @return true if the arguments are not ok, return false if it's ok
     */
    private boolean ManageInputArguments() {
        //Make some logs to check the parameters received
        CustomLogger.log("I begin to parse the command line, your arguments (size=" + args.length + ") are :");
        for (int i = 0; i < args.length; i++) {
            CustomLogger.log("Main args : " + args[i]);
        }
        printer.jumpLine();
        CustomLogger.log("Then I analyse them");
        //check if there are parameters
        if (args.length == 0) {
            CustomLogger.log("No arguments found dude, I print you the help :");
            printer.printHelp();
            return true;
        }
        //check if the first parameter is v (for version)
        if (args[0].equalsIgnoreCase(OPTION_VERSION) || args[0].equalsIgnoreCase(OPTION_VERSION2)) {
            CustomLogger.log("You want to find the version of the tool, here it is:");
            printer.printVersion();
            return true;
        }
        //check if the first parameter is h (for help)
        if (args[0].equalsIgnoreCase(OPTION_HELP) || args[0].equalsIgnoreCase(OPTION_HELP2)) {
            CustomLogger.log("You want to see the help :");
            printer.printHelp();
            return true;
        }
        return false;
    }

    /**
     * Analyse if the arguments contains Trim or Debug
     * We don't care about the order
     */
    private void analyseTrimAndDebugOption() {
        if (args[0].equals(OPTION_TRIM)) {
            TRIM_CONTEXT = true;
            //update the file index, because first parameter is not a file in that case
            firstFileIndexCorrection = 1;
            //an d file size is less
            fileSize = fileSize - 1;
            if(args[1].equals(OPTION_DEBUG)){
                DEBUG = true;
                //update the file index, because first parameter is not a file in that case
                firstFileIndexCorrection = 2;
                //an d file size is less
                fileSize = fileSize - 1;
            }
        }else if(args[0].equals(OPTION_DEBUG)){
            DEBUG = true;
            //update the file index, because first parameter is not a file in that case
            firstFileIndexCorrection = 1;
            //and file size is less
            fileSize = fileSize - 1;
            if(args[1].equals(OPTION_TRIM)){
                TRIM_CONTEXT = true;
                //update the file index, because first parameter is not a file in that case
                firstFileIndexCorrection = 2;
                //and file size is less
                fileSize = fileSize - 1;
            }
        }
        CustomLogger.log("The file size is so :"+fileSize);
    }


}
