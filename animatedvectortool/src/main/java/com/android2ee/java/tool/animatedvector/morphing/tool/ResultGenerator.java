/**
 * <ul>
 * <li>ResultGenerator</li>
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

import com.android2ee.java.tool.animatedvector.morphing.model.ClipPath;
import com.android2ee.java.tool.animatedvector.morphing.model.Group;
import com.android2ee.java.tool.animatedvector.morphing.model.Path;
import com.android2ee.java.tool.animatedvector.morphing.model.VectorDrawable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Mathias Seguy - Android2EE on 26/11/2015.
 * This class aims to generate the files that are the result of the operation
 */
public class ResultGenerator extends ResultTemplates {
    /**
     * The working DirectoryPath
     */
    String ressourcesDirectoryPath;
    /**
     * The set of vectors
     */
    ArrayList<VectorDrawable> vectors;
    /**
     * The folder in which we will generates files
     */
    File valuesfolder, drawableFolder, animatorFolder, javaFolder ;
    /**
     * The bufferedWriter to write the file of Strings that contains the string of the paths
     * And the bufferedWriter to write the file of colors that contains the colors of the paths
     */
    BufferedWriter stringFileWriter, colorFileWriter;
    /**
     * The list of colors already defined in the colorFile
     */
    ArrayList<String> colorValuesAlreadyDefined;
    /**
     * The list of colors already defined in the colorFile
     */
    ArrayList<String> vectorAlreadyDefined;
    /**
     * The current vectorDrawable file name
     * The TempKey to use when no name set to a path (use for the string keyname)
     */
    String currentVectorFileName, tempKey;


    /**
     * Constructor
     *
     * @param workingDirectoryPath The working directory path in which files will be generated
     * @param vectors              The list of Vectors
     */
    public ResultGenerator(String workingDirectoryPath, ArrayList<VectorDrawable> vectors) {
        this.ressourcesDirectoryPath = workingDirectoryPath;
        this.vectors = vectors;
        colorValuesAlreadyDefined = new ArrayList<String>();
        vectorAlreadyDefined = new ArrayList<String>();
        createFoldersStructure();


    }


    /**
     * Launch the files generation
     *
     * @throws IOException
     */
    public void launchGeneration() throws IOException {
        //create the file in which we write the ptha (extracted path in a string file is a good idea
        File stringFile = new File(valuesfolder, stringFileName);
        stringFileWriter = createIt(stringFile);
        stringFileWriter.write(stringfilestart);
        stringFileWriter.newLine();
        File colorsFile = new File(valuesfolder, colorsFileName);
        colorFileWriter = createIt(colorsFile);
        colorFileWriter.write(stringfilestart);
        colorFileWriter.newLine();
        for (int i = 0; i < vectors.size(); i++) {
            //launch generation
            if(i == (vectors.size() - 1)){
                CustomLogger.logError("----------->i == (vectors.size() - 1 is true ");
                //this is the last element
                generateVectorDrawableResult(vectors.get(i),"null",true,i==0 );
            }else{
                //this is not the last element
                generateVectorDrawableResult(vectors.get(i),vectors.get(i+1).getFileName(),false,i==0 );
            }
        }
        //generate the LevelListVectorDrawable
        generateLevelListVectorDrawable();
        //generate the roundTrip AnimatedVectorDrawable for each drawable
        generateRoundTripVectorDrawable();
        //close the bufferedWriter
        stringFileWriter.write(stringfileend);
        stringFileWriter.newLine();
        stringFileWriter.flush();
        stringFileWriter.close();
        colorFileWriter.write(stringfileend);
        colorFileWriter.newLine();
        colorFileWriter.flush();
        colorFileWriter.close();
        generateJavaFile();
    }

    /**
     * Generates the files associated with vector gave as parameters
     *
     * @param vector
     */
    private void generateVectorDrawableResult(VectorDrawable vector,String nextVectorName, boolean isLast,boolean isFirst) throws IOException {

        CustomLogger.logError("generateVectorDrawableResult" + vector.getFileName() + " islast==" + isLast);
        //you can do loop (ex:java -jar animatedvectortool-0.1.jar android.xml backup.xml android.xml)
        //but you don't want to generate twice the elements
        if(!vectorAlreadyDefined.contains(vector.getFileName())){
            currentVectorFileName = vector.getFileName();
            CustomLogger.logError("Generating the following file " + currentVectorFileName);
            File vectorFile = new File(drawableFolder, currentVectorFileName + ".xml");
            BufferedWriter vectorBW = createIt(vectorFile);
            CustomLogger.log("Generating the following file " + vectorFile.getAbsolutePath());
            generateVectorDrawable(vector, vectorBW, isLast);
            vectorAlreadyDefined.add(vector.getFileName());

            vectorBW.flush();
            vectorBW.close();
        }
        //if it's not the last file generates the animatedVector and the animatorSet
        //else don't do it it's nonsense
        if (!isLast) {
            File animatedVectorFile = new File(drawableFolder, animatedVector.replace("#fileName", currentVectorFileName));
            BufferedWriter animatedVectorBW = createIt(animatedVectorFile);
            CustomLogger.log("Generating the following file " + animatedVectorFile.getAbsolutePath());
            CustomLogger.logError("----------->Generating the following file " + animatedVectorFile.getAbsolutePath() + ", next VectorName=" + nextVectorName);
            generateAnimatedVector(vector, animatedVectorBW, nextVectorName, false);
            generateAnimatorSet(vector, false);
            animatedVectorBW.flush();
            animatedVectorBW.close();
        }
        //do the same for reverse
        if (!isLast) {
            File animatedVectorFile = new File(drawableFolder, animatedVector.replace("#fileName", currentVectorFileName+reverse));
            BufferedWriter animatedVectorBW = createIt(animatedVectorFile);
            CustomLogger.log("Generating the following file " + animatedVectorFile.getAbsolutePath());
            CustomLogger.logError("----------->Generating the following file " + animatedVectorFile.getAbsolutePath() + ", next VectorName=" + nextVectorName);
            generateAnimatedVector(vector, animatedVectorBW, nextVectorName, true);
            generateAnimatorSet(vector, true);
            animatedVectorBW.flush();
            animatedVectorBW.close();
        }
    }
    /***********************************************************
     *  Generate LevelList Drawables
     **********************************************************/

    /**
     * generates the vector drawable level list
     *
     * @throws IOException
     */
    private void generateLevelListVectorDrawable() throws IOException {
        CustomLogger.log("Generating the following file " + levelListAnimatedVector);
        File animatedVectorList = new File(drawableFolder, levelListAnimatedVector);
        BufferedWriter animatedLevelListBW = createIt(animatedVectorList);
        CustomLogger.log("Generating the following file " + animatedVectorList.getAbsolutePath());
        animatedLevelListBW.write(levellist_start);
        animatedLevelListBW.newLine();
        //Browse the vector drawable (don't do for the last, it doesn't exist)
        for (int i = 0; i < vectors.size() - 1; i++) {
            animatedLevelListBW.write(
                    levellist_item.replace("#itemNumber", "" + i)
                            .replace("#animatedVectorName", animatedVectorSimpleName.replace("#fileName", vectors.get(i).getFileName()))
            );
            animatedLevelListBW.newLine();
        }
        animatedLevelListBW.write(levellist_end);
        animatedLevelListBW.flush();
        animatedLevelListBW.close();
    }
    /**
     * generates the vector drawable roundTrip
     * For each the file to make go and back
     * @throws IOException
     */
    private void generateRoundTripVectorDrawable() throws IOException {
        CustomLogger.log("Generating the following file " + animatedVectorRoundTrip);
        //for each vector generate the appropriated LevelList Drawable
        String fileName;
        for(int i=0;i<vectors.size();i++) {
            currentVectorFileName = vectors.get(i).getFileName();
            File animatedRoundTripVectorFile = new File(drawableFolder, animatedVectorRoundTrip.replace("#fileName",currentVectorFileName));
            BufferedWriter animatedRoundTripBW = createIt(animatedRoundTripVectorFile);
            CustomLogger.log("Generating the following file " + animatedRoundTripVectorFile.getAbsolutePath());
            animatedRoundTripBW.write(levellist_start);
            animatedRoundTripBW.newLine();
            //Browse the vector drawable (don't do for the last, it doesn't exist)
            animatedRoundTripBW.write(
                    levellist_item.replace("#itemNumber", "" + 0)
                            .replace("#animatedVectorName", animatedVectorSimpleName.replace("#fileName", currentVectorFileName))
            );
            animatedRoundTripBW.newLine();
            animatedRoundTripBW.write(
                    levellist_item.replace("#itemNumber", "" + 1)
                            .replace("#animatedVectorName", animatedVectorSimpleName.replace("#fileName", currentVectorFileName+reverse))
            );
            animatedRoundTripBW.newLine();
            animatedRoundTripBW.write(levellist_end);
            animatedRoundTripBW.flush();
            animatedRoundTripBW.close();
        }
    }
    /***********************************************************
     * Generates VectorDrawable Files
     **********************************************************/

    /**
     * Generate VectorFile
     *
     * @param vector   The vector to write down
     * @param vectorBW The BufferedWriter we write in
     * @throws IOException
     */
    private void generateVectorDrawable(VectorDrawable vector, BufferedWriter vectorBW, boolean isLast) throws IOException {
        //write the first bloc
        vectorBW.write(xml_start);
        vectorBW.newLine();
        vectorBW.write(vector_start);
        vectorBW.newLine();
        if (vector.getName() != null) {
            vectorBW.write(name.replace("#name", vector.getName()));
            vectorBW.newLine();
        }
        if (vector.getViewPortWidth() != null) {
            vectorBW.write(viewportWidth.replace("#viewportWidth", vector.getViewPortWidth()));
            vectorBW.newLine();
        }
        if (vector.getViewPortHeight() != null) {
            vectorBW.write(viewportHeight.replace("#viewportHeight", vector.getViewPortHeight()));
            vectorBW.newLine();
        }
        if (vector.getWidth() != null) {
            vectorBW.write(width.replace("#width", vector.getWidth()));
            vectorBW.newLine();
        }
        if (vector.getHeight() != null) {
            vectorBW.write(height.replace("#height", vector.getHeight()));
            vectorBW.newLine();
        }
        if (vector.getTint() != null) {
            vectorBW.write(tint.replace("#tint", vector.getTint()));
            vectorBW.newLine();
        }
        if (vector.getTintMode() != null) {
            vectorBW.write(tintMode.replace("#tintMode", vector.getTintMode()));
            vectorBW.newLine();
        }
        if (vector.getAutoMirrored() != null) {
            vectorBW.write(autoMirrored.replace("#autoMirrored", vector.getAutoMirrored()));
            vectorBW.newLine();
        }
        if (vector.getAlpha() != null) {
            vectorBW.write(alpha.replace("#alpha", vector.getAlpha()));
            vectorBW.newLine();
        }
        vectorBW.write(endtag);
        vectorBW.newLine();
        //then write the group if any
        if (vector.getGroups() != null) {
            for (int i = 0; i < vector.getGroups().size(); i++) {
                generateGroup(vector.getGroups().get(i), vectorBW, isLast);
            }
        }
        //then write the path if any
        if (vector.getPaths() != null) {
            for (int i = 0; i < vector.getPaths().size(); i++) {
                generatePath(vector.getPaths().get(i), vectorBW, isLast);
            }
        }
        vectorBW.write(vector_end);
        vectorBW.newLine();
    }

    /**
     * Generate Group Bloc
     *
     * @param group    The group to write down
     * @param vectorBW The BufferedWriter we write in
     * @throws IOException
     */
    private void generateGroup(Group group, BufferedWriter vectorBW, boolean isLast) throws IOException {
        vectorBW.write(group_start);
        if (group.getName() != null) {
            vectorBW.write(name.replace("#name", group.getName()));
            vectorBW.newLine();
        }
        if (group.getPivotX() != null) {
            vectorBW.write(pivotX.replace("#pivotX", group.getPivotX()));
            vectorBW.newLine();
        }
        if (group.getPivotY() != null) {
            vectorBW.write(pivotY.replace("#pivotY", group.getPivotY()));
            vectorBW.newLine();
        }
        if (group.getScaleX() != null) {
            vectorBW.write(scaleX.replace("#scaleX", group.getScaleX()));
            vectorBW.newLine();
        }
        if (group.getScaleY() != null) {
            vectorBW.write(scaleY.replace("#scaleY", group.getScaleY()));
            vectorBW.newLine();
        }
        if (group.getTranslationX() != null) {
            vectorBW.write(translateX.replace("#translateX", group.getTranslationX()));
            vectorBW.newLine();
        }
        if (group.getTranslationY() != null) {
            vectorBW.write(translateY.replace("#translateY", group.getTranslationY()));
            vectorBW.newLine();
        }
        vectorBW.write(endtag);
        vectorBW.newLine();
        //then write the group if any
        if (group.getGroups() != null) {
            for (int i = 0; i < group.getGroups().size(); i++) {
                generateGroup(group.getGroups().get(i), vectorBW, isLast);
            }
        }
        //then write the path if any
        if (group.getPaths() != null) {
            for (int i = 0; i < group.getPaths().size(); i++) {
                generatePath(group.getPaths().get(i), vectorBW, isLast);
            }
        }
        //then write the clippath if any
        if (group.getClipPaths() != null) {
            for (int i = 0; i < group.getClipPaths().size(); i++) {
                generateClipPath(group.getClipPaths().get(i), vectorBW);
            }
        }
        vectorBW.write(group_end);
        vectorBW.newLine();

    }

    /**
     * Generate Path Bloc
     *
     * @param path     The path to write down
     * @param vectorBW The BufferedWriter we write in
     * @throws IOException
     */
    private void generatePath(Path path, BufferedWriter vectorBW, boolean isLast) throws IOException {
        vectorBW.write(path_start);
        vectorBW.newLine();
        if (path.getName() != null) {
            vectorBW.write(name.replace("#name", path.getName()));
            vectorBW.newLine();
        }
        if (path.getMorphingName() != null) {
            vectorBW.write(morphingName.replace("#morphingName", path.getMorphingName()));
            vectorBW.newLine();
        }
        if (path.getPathData() != null) {
            if (!isLast) {
                if (null != path.getMorphingName()) {
                    //It means we have to generate the full structure for morphing
                    CustomLogger.logError("path.getName()=" + path.getName() + "path.getMorphingName()=" + path.getMorphingName()
                            + " InitPath" + path.getNormalizedInitialPathData() + "\r\n final " + path.getNormalizedFinalPathData());
                    if (path.getName() == null) {
                        //generate a key to drop in the string file
                        tempKey = path.getMorphingName();
                    } else {
                        //use the path name for the key
                        tempKey = path.getName();
                    }

                    if (path.getNormalizedInitialPathData() != null) {
                        //write the path in the string file
                        stringFileWriter.write(stringline.replace("#stringkey", stringPathPrefixInitial + tempKey + currentVectorFileName).replace("#stringvalue", path.getNormalizedInitialPathData()));
                        stringFileWriter.newLine();
                        stringFileWriter.write(stringline.replace("#stringkey", stringPathPrefixFinal + tempKey + currentVectorFileName).replace("#stringvalue", path.getNormalizedFinalPathData()));
                        stringFileWriter.newLine();
                        //link it too the path data values
                        vectorBW.write(pathData.replace("#stringPathData", stringPathPrefixInitial + tempKey + currentVectorFileName));
                        vectorBW.newLine();
                    } else {
                        //it means we just have to copy the path
                        //write the path in the string file
                        stringFileWriter.write(stringline.replace("#stringkey", stringPathPrefixInitial + tempKey + currentVectorFileName).replace("#stringvalue", path.getPathData()));
                        stringFileWriter.newLine();
                        //link it too the path data values
                        vectorBW.write(pathData.replace("#stringPathData", stringPathPrefixInitial + tempKey + currentVectorFileName));
                        vectorBW.newLine();
                    }

                } else {
                    //it means we copy paste the pathData like this (only extracting it in the string file)
                    if (path.getName() == null) {
                        //generate a key to drop in the string file
                        tempKey = currentVectorFileName + (int) Math.ceil(Math.random() * 100000);
                    } else {
                        //use the path name for the key
                        tempKey = path.getName();
                    }
                    //it means we just have to copy the path
                    //write the path in the string file
                    stringFileWriter.write(stringline.replace("#stringkey", stringPathPrefixInitial + tempKey + currentVectorFileName).replace("#stringvalue", path.getPathData()));
                    stringFileWriter.newLine();
                    //link it too the path data values
                    vectorBW.write(pathData.replace("#stringPathData", stringPathPrefixInitial + tempKey + currentVectorFileName));
                    vectorBW.newLine();
                }
            } else {
                //it's the last: no initialPath or finalPath fro morphing
                //it means we copy paste the pathData like this (only extracting it in the string file)
                if (path.getName() == null) {
                    //generate a key to drop in the string file
                    tempKey = currentVectorFileName + (int) Math.ceil(Math.random() * 100000);
                } else {
                    //use the path name for the key
                    tempKey = path.getName();
                }
                //it means we just have to copy the path
                //write the path in the string file
                stringFileWriter.write(stringline.replace("#stringkey", stringPathPrefixInitial + tempKey + currentVectorFileName).replace("#stringvalue", path.getPathData()));
                stringFileWriter.newLine();
                //link it too the path data values
                vectorBW.write(pathData.replace("#stringPathData", stringPathPrefixInitial + tempKey + currentVectorFileName));
                vectorBW.newLine();
            }
        }
        if (path.getFillColor() != null) {
            vectorBW.write(fillColor.replace("#fillColor", path.getFillColor()));
            vectorBW.newLine();
        }
        if (path.getStrokeColor() != null) {
            vectorBW.write(strokeColor.replace("#strokeColor", path.getStrokeColor()));
            vectorBW.newLine();
        }
        if (path.getStrokeWidth() != null) {
            vectorBW.write(strokeWidth.replace("#strokeWidth", path.getStrokeWidth()));
            vectorBW.newLine();
        }
        if (path.getStrokeAlpha() != null) {
            vectorBW.write(strokeAlpha.replace("#strokeAlpha", path.getStrokeAlpha()));
            vectorBW.newLine();
        }
        if (path.getFillAlpha() != null) {
            vectorBW.write(fillAlpha.replace("#fillAlpha", path.getFillAlpha()));
            vectorBW.newLine();
        }
        if (path.getTrimPathStart() != null) {
            vectorBW.write(trimPathStart.replace("#trimPathStart", path.getTrimPathStart()));
            vectorBW.newLine();
        }
        if (path.getTrimPathEnd() != null) {
            vectorBW.write(trimPathEnd.replace("#trimPathEnd", path.getTrimPathEnd()));
            vectorBW.newLine();
        }
        if (path.getTrimPathOffset() != null) {
            vectorBW.write(trimPathOffset.replace("#trimPathOffset", path.getTrimPathOffset()));
            vectorBW.newLine();
        }
        if (path.getStrokeLineCap() != null) {
            vectorBW.write(strokeLineCap.replace("#strokeLineCap", path.getStrokeLineCap()));
            vectorBW.newLine();
        }
        if (path.getStrokeLineJion() != null) {
            vectorBW.write(strokeLineJoin.replace("#strokeLineJoin", path.getStrokeLineJion()));
            vectorBW.newLine();
        }
        if (path.getStrokeMiterLimit() != null) {
            vectorBW.write(strokeMiterLimit.replace("#strokeMiterLimit", path.getStrokeMiterLimit()));
            vectorBW.newLine();
        }

        vectorBW.write(endtagbloc);
        vectorBW.newLine();

    }

    /**
     * Generate ClipPath Bloc
     *
     * @param clipPath The clipPath to write down
     * @param vectorBW The BufferedWriter we write in
     * @throws IOException
     */
    private void generateClipPath(ClipPath clipPath, BufferedWriter vectorBW) throws IOException {
        vectorBW.write(clippath_start);
        vectorBW.newLine();
        if (clipPath.getName() != null) {
            vectorBW.write(name.replace("#name", clipPath.getName()));
        }
        if (clipPath.getPathData() != null) {
            //write the path in the string file
            stringFileWriter.write(stringline.replace("#stringkey", currentVectorFileName + stringClipPathSuffix + clipPath.getName()).replace("#stringvalue", clipPath.getPathData()));
            stringFileWriter.newLine();
            vectorBW.write(pathData.replace("#stringPathData", stringClipPathSuffix + clipPath.getName()));
            vectorBW.newLine();
        }
        vectorBW.write(endtagbloc);
        vectorBW.newLine();
    }
    /***********************************************************
     * Generates AnimatedVector Files
     **********************************************************/
    /**
     * Generate the AnimatedVector file with all the targets
     *
     * @param vector           The vector associated
     * @param animatedvectorBW Where to write
     * @throws IOException
     */
    private void generateAnimatedVector(VectorDrawable vector, BufferedWriter animatedvectorBW,String nextVectorName,boolean reverseMode) throws IOException {
        //write the first bloc
        animatedvectorBW.write(xml_start);
        animatedvectorBW.newLine();
        animatedvectorBW.write(animatedvector_start);
        animatedvectorBW.newLine();
        if(reverseMode){
            animatedvectorBW.write(animatedvector_drawable.replace("#drawable", nextVectorName));
        }else{
            animatedvectorBW.write(animatedvector_drawable.replace("#drawable", vector.getFileName()));
        }
        animatedvectorBW.newLine();
        animatedvectorBW.write(endtag);
        animatedvectorBW.newLine();
        //then write the tag: base on the morphing name of course
        String currentPathName;
        Path currentPath;
        for (Map.Entry<String, Path> entry : vector.getPathToMorphSortByMorphingName().entrySet()) {
            currentPathName = entry.getKey();
            currentPath = vector.getPathToMorphSortByMorphingName().get(currentPathName);
            if (currentPath.getNormalizedInitialPathData() != null) {
                animatedvectorBW.write(target_start);
                if (currentPath.getName() == null) {
                    MessagePrinter printer = new MessagePrinter();
                    printer.printExceptionNoPathNameDefined(currentPathName);
                    throw new IllegalArgumentException("No android:name defined");
                }
                animatedvectorBW.write(name.replace("#name", currentPath.getName()));
                animatedvectorBW.newLine();
                if(reverseMode){
                    animatedvectorBW.write(animation.replace("#animation", animatorSet.replace("#fileName", currentVectorFileName + "_" + currentPathName+reverse)));
                }else{
                    animatedvectorBW.write(animation.replace("#animation", animatorSet.replace("#fileName", currentVectorFileName + "_" + currentPathName)));
                }
                animatedvectorBW.newLine();
                animatedvectorBW.write(endtagbloc);
                animatedvectorBW.newLine();
            }
        }
        animatedvectorBW.write(animatedvector_end);
        animatedvectorBW.newLine();
    }
    /***********************************************************
     * Generates ObjectAnimator Files
     **********************************************************/
    //TODO generates alpha and width

    /**
     * Generate ObjectAnimator associated with this path
     * @param vector
     * @throws IOException
     */
    private void generateAnimatorSet(VectorDrawable vector,boolean reverseMode) throws IOException {
        //write the objectAnimator
        //For each morphingPathName generates an objectAnimator
        String currentPathName;
        Path currentPath;
        for (Map.Entry<String, Path> entry : vector.getPathToMorphSortByMorphingName().entrySet()) {
            currentPathName = entry.getKey();
            currentPath = vector.getPathToMorphSortByMorphingName().get(currentPathName);
            if (currentPath.getNormalizedInitialPathData() != null && currentPath.getNormalizedFinalPathData() != null) {
                //we should creat one file by animator
                File animtorSetFile;
                if(reverseMode){
                    animtorSetFile = new File(animatorFolder, animatorSetWithExtension.replace("#fileName", currentVectorFileName + "_" + currentPathName+reverse));
                }else{
                    animtorSetFile = new File(animatorFolder, animatorSetWithExtension.replace("#fileName", currentVectorFileName + "_" + currentPathName));
                }
                BufferedWriter animatorSetBW = createIt(animtorSetFile);
                CustomLogger.log("Generating the following file " + animtorSetFile.getAbsolutePath());
                //write the first bloc
                animatorSetBW.write(xml_start);
                animatorSetBW.newLine();
                animatorSetBW.write(animatorset_start);
                animatorSetBW.newLine();
                //generate the objectAnimtor for the morphing
                animatorSetBW.write(objectanimator);
                animatorSetBW.newLine();
                animatorSetBW.write(duration);
                animatorSetBW.newLine();
                animatorSetBW.write(interpolator);
                animatorSetBW.newLine();
                animatorSetBW.write(repeatMode);
                animatorSetBW.newLine();
                animatorSetBW.write(repeatCount);
                animatorSetBW.newLine();
                animatorSetBW.write(propertyName);
                animatorSetBW.newLine();
                animatorSetBW.write(valueType);
                animatorSetBW.newLine();
                if (currentPath.getName() == null) {
                    //generate a key to drop in the string file
                    tempKey = currentPath.getMorphingName();
                } else {
                    //use the path name for the key
                    tempKey = currentPath.getName();
                }
                if(reverseMode){
                    animatorSetBW.write(valueFrom.replace("#valueFrom", stringToFinal + tempKey + currentVectorFileName));
                    animatorSetBW.newLine();
                    animatorSetBW.write(valueTo.replace("#valueTo", stringToInitial + tempKey + currentVectorFileName));
                }else {
                    animatorSetBW.write(valueFrom.replace("#valueFrom", stringToInitial + tempKey + currentVectorFileName));
                    animatorSetBW.newLine();
                    animatorSetBW.write(valueTo.replace("#valueTo", stringToFinal + tempKey + currentVectorFileName));
                }
                animatorSetBW.newLine();
                animatorSetBW.write(endtagbloc);
                animatorSetBW.newLine();
                //generate the objectAnimator for the argb animation
                //Do it for the fill color
                generateFillColorObjectAnimator(currentPath, animatorSetBW,reverseMode);
                //Do it for the strokeColor color
                generateStrokeColorObjectAnimator(currentPath, animatorSetBW,reverseMode);

                //TODO do the same with strokeWidth, strokeAlpha and fillAlpha (already set and defined in the path, just add the right code here)
                animatorSetBW.write(animatorset_end);
                animatorSetBW.newLine();

                animatorSetBW.flush();
                animatorSetBW.close();
            }
        }


    }


    /**
     * Generate the FillColor ObjectAnimator part. Looks like something like that:
     * <objectAnimator
    *     android:duration="1"
    *     android:startOffset="1500"
    *     android:interpolator="@android:interpolator/linear"
    *     android:propertyName="fillColor"
    *     android:valueType="intType"
    *     android:valueFrom="@color/fillcolor_00f000"
    *     android:valueTo="@color/fillcolor_ff0000"
    *     />
     * @param currentPath
     * @param animatorSetBW
     * @throws IOException
     */
    private void generateFillColorObjectAnimator(Path currentPath, BufferedWriter animatorSetBW,boolean reverseMode) throws IOException {
        if (currentPath.getFillColor() != null && currentPath.getFillColorTarget() != null) {
            //extract the color in the colorsFile
            if (!colorValuesAlreadyDefined.contains("fillColor" + currentPath.getFillColor())) {
                colorFileWriter.write(fillColorName.replace("#colorARGB", ("" + currentPath.getFillColor()).replace("#", ""))
                        .replace("#colorValue", "" + currentPath.getFillColor()));
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add("fillColor" + currentPath.getFillColor());
            }
            if (!colorValuesAlreadyDefined.contains("fillColor" + currentPath.getFillColorTarget())) {
                colorFileWriter.write(fillColorName.replace("#colorARGB", ("" + currentPath.getFillColorTarget()).replace("#", ""))
                        .replace("#colorValue", "" + currentPath.getFillColorTarget()));
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add("fillColor" + currentPath.getFillColorTarget());
            }
            //then generate the objectAnimator
            animatorSetBW.write(objectanimator);
            animatorSetBW.newLine();
            animatorSetBW.write(durationColor);
            animatorSetBW.newLine();
            animatorSetBW.write(startOffset);
            animatorSetBW.newLine();
            animatorSetBW.write(interpolator);
            animatorSetBW.newLine();
            animatorSetBW.write(propertyNameFillColor);
            animatorSetBW.newLine();
            animatorSetBW.write(valueTypeArgb);
            animatorSetBW.newLine();
            if(reverseMode){
                animatorSetBW.write(valueFrom.replace("#valueFrom", refToFillColor.replace("#colorARGB", ("" + currentPath.getFillColorTarget()).replace("#", "")) ));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo",refToFillColor.replace("#colorARGB", ("" + currentPath.getFillColor()).replace("#", ""))));
            }else {
                animatorSetBW.write(valueFrom.replace("#valueFrom", refToFillColor.replace("#colorARGB", ("" + currentPath.getFillColor()).replace("#", ""))));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo", refToFillColor.replace("#colorARGB", ("" + currentPath.getFillColorTarget()).replace("#", ""))));
            }
            animatorSetBW.newLine();
            animatorSetBW.write(endtagbloc);
            animatorSetBW.newLine();
        }else if (currentPath.getFillColor() == null && currentPath.getFillColorTarget() != null) {
            //extract the color in the colorsFile
            if (!colorValuesAlreadyDefined.contains(translucent)) {
                colorFileWriter.write(translucentColorName);
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add(translucent);
            }
            if (!colorValuesAlreadyDefined.contains("fillColor" + currentPath.getFillColorTarget())) {
                colorFileWriter.write(fillColorName.replace("#colorARGB", ("" + currentPath.getFillColorTarget()).replace("#", ""))
                        .replace("#colorValue", "" + currentPath.getFillColorTarget()));
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add("fillColor" + currentPath.getFillColorTarget());
            }
            //then generate the objectAnimator
            animatorSetBW.write(objectanimator);
            animatorSetBW.newLine();
            animatorSetBW.write(durationColor);
            animatorSetBW.newLine();
            animatorSetBW.write(startOffset);
            animatorSetBW.newLine();
            animatorSetBW.write(interpolator);
            animatorSetBW.newLine();
            animatorSetBW.write(propertyNameFillColor);
            animatorSetBW.newLine();
            animatorSetBW.write(valueTypeArgb);
            animatorSetBW.newLine();
            if(reverseMode){
                animatorSetBW.write(valueFrom.replace("#valueFrom",refToFillColor.replace("#colorARGB", ("" + currentPath.getFillColorTarget()).replace("#", "")) ));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo",refToTranslucentColor ));
            }else {
                animatorSetBW.write(valueFrom.replace("#valueFrom", refToTranslucentColor));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo", refToFillColor.replace("#colorARGB", ("" + currentPath.getFillColorTarget()).replace("#", ""))));
            }
            animatorSetBW.newLine();
            animatorSetBW.write(endtagbloc);
            animatorSetBW.newLine();
        }else if (currentPath.getFillColor() != null && currentPath.getFillColorTarget() == null) {
            //extract the color in the colorsFile
            if (!colorValuesAlreadyDefined.contains("fillColor" + currentPath.getFillColor())) {
                colorFileWriter.write(fillColorName.replace("#colorARGB", ("" + currentPath.getFillColor()).replace("#", ""))
                        .replace("#colorValue", "" + currentPath.getFillColor()));
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add("fillColor" + currentPath.getFillColor());
            }
            if (!colorValuesAlreadyDefined.contains(translucent) ){
                colorFileWriter.write(translucentColorName);
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add(translucent);
            }
            //then generate the objectAnimator
            animatorSetBW.write(objectanimator);
            animatorSetBW.newLine();
            animatorSetBW.write(durationColor);
            animatorSetBW.newLine();
            animatorSetBW.write(startOffset);
            animatorSetBW.newLine();
            animatorSetBW.write(interpolator);
            animatorSetBW.newLine();
            animatorSetBW.write(propertyNameFillColor);
            animatorSetBW.newLine();
            animatorSetBW.write(valueTypeArgb);
            animatorSetBW.newLine();
            if(reverseMode){
                animatorSetBW.write(valueFrom.replace("#valueFrom",refToTranslucentColor ));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo",refToFillColor.replace("#colorARGB", ("" + currentPath.getFillColor()).replace("#", "")) ));
            }else{
                animatorSetBW.write(valueFrom.replace("#valueFrom", refToFillColor.replace("#colorARGB", ("" + currentPath.getFillColor()).replace("#", ""))));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo",refToTranslucentColor));
            }
            animatorSetBW.newLine();
            animatorSetBW.write(endtagbloc);
            animatorSetBW.newLine();
        }
    }

    /**
     * Generate the strokeColor ObjectAnimator part.
     *
     * Looks like something like that:
     * <objectAnimator
    *     android:duration="300"
    *     android:startOffset="1500"
    *     android:interpolator="@android:interpolator/linear"
    *     android:propertyName="strokeColor"
    *     android:valueType="intType"
    *     android:valueFrom="@color/strokecolor_00f000"
    *     android:valueTo="@color/translucent"
    *     />
     * @param currentPath
     * @param animatorSetBW
     * @throws IOException
     */
    private void generateStrokeColorObjectAnimator(Path currentPath, BufferedWriter animatorSetBW,boolean reverseMode) throws IOException {
        if (currentPath.getStrokeColor() != null && currentPath.getStrokeColorTarget() != null) {
            //extract the color in the colorsFile
            if (!colorValuesAlreadyDefined.contains("strokecolor" + currentPath.getStrokeColor())) {
                colorFileWriter.write(strokeColorName.replace("#colorARGB", ("" + currentPath.getStrokeColor()).replace("#", ""))
                        .replace("#colorValue", "" + currentPath.getStrokeColor()));
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add("strokecolor" + currentPath.getStrokeColor());
            }
            if (!colorValuesAlreadyDefined.contains("strokecolor" + currentPath.getStrokeColorTarget())) {
                colorFileWriter.write(strokeColorName.replace("#colorARGB", ("" + currentPath.getStrokeColorTarget()).replace("#", ""))
                        .replace("#colorValue", "" + currentPath.getStrokeColorTarget()));
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add("strokecolor" + currentPath.getStrokeColorTarget());
            }
            //then generate the objectAnimator
            animatorSetBW.write(objectanimator);
            animatorSetBW.newLine();
            animatorSetBW.write(durationColor);
            animatorSetBW.newLine();
            animatorSetBW.write(startOffset);
            animatorSetBW.newLine();
            animatorSetBW.write(interpolator);
            animatorSetBW.newLine();
            animatorSetBW.write(propertyNameStrokeColor);
            animatorSetBW.newLine();
            animatorSetBW.write(valueTypeArgb);
            animatorSetBW.newLine();
            if(reverseMode){
                animatorSetBW.write(valueFrom.replace("#valueFrom", refToStrokeColor.replace("#colorARGB", ("" + currentPath.getStrokeColorTarget()).replace("#", "")) ));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo",refToStrokeColor.replace("#colorARGB", ("" + currentPath.getStrokeColor()).replace("#", ""))));
            }else {
                animatorSetBW.write(valueFrom.replace("#valueFrom", refToStrokeColor.replace("#colorARGB", ("" + currentPath.getStrokeColor()).replace("#", ""))));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo", refToStrokeColor.replace("#colorARGB", ("" + currentPath.getStrokeColorTarget()).replace("#", ""))));
            }
            animatorSetBW.newLine();
            animatorSetBW.write(endtagbloc);
            animatorSetBW.newLine();
        }else if (currentPath.getStrokeColor() == null && currentPath.getStrokeColorTarget() != null) {
            //extract the color in the colorsFile
            if (!colorValuesAlreadyDefined.contains(translucent) ){
                colorFileWriter.write(translucentColorName);
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add(translucent);
            }
            if (!colorValuesAlreadyDefined.contains("strokecolor" + currentPath.getStrokeColorTarget())) {
                colorFileWriter.write(strokeColorName.replace("#colorARGB", ("" + currentPath.getStrokeColorTarget()).replace("#", ""))
                        .replace("#colorValue", "" + currentPath.getStrokeColorTarget()));
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add("strokecolor" + currentPath.getStrokeColorTarget());
            }
            //then generate the objectAnimator
            animatorSetBW.write(objectanimator);
            animatorSetBW.newLine();
            animatorSetBW.write(durationColor);
            animatorSetBW.newLine();
            animatorSetBW.write(startOffset);
            animatorSetBW.newLine();
            animatorSetBW.write(interpolator);
            animatorSetBW.newLine();
            animatorSetBW.write(propertyNameStrokeColor);
            animatorSetBW.newLine();
            animatorSetBW.write(valueTypeArgb);
            animatorSetBW.newLine();
            if(reverseMode){
                animatorSetBW.write(valueFrom.replace("#valueFrom",refToStrokeColor.replace("#colorARGB", ("" + currentPath.getStrokeColorTarget()).replace("#", "")) ));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo",refToTranslucentColor ));
            }else {
                animatorSetBW.write(valueFrom.replace("#valueFrom", refToTranslucentColor));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo", refToStrokeColor.replace("#colorARGB", ("" + currentPath.getStrokeColorTarget()).replace("#", ""))));
            }
            animatorSetBW.newLine();
            animatorSetBW.write(endtagbloc);
            animatorSetBW.newLine();
        }else if (currentPath.getStrokeColor() != null && currentPath.getStrokeColorTarget() == null) {
            //extract the color in the colorsFile
            if (!colorValuesAlreadyDefined.contains("strokecolor" + currentPath.getStrokeColor())) {
                colorFileWriter.write(strokeColorName.replace("#colorARGB", ("" + currentPath.getStrokeColor()).replace("#", ""))
                        .replace("#colorValue", "" + currentPath.getStrokeColor()));
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add("strokecolor" + currentPath.getStrokeColor());
            }
            if (!colorValuesAlreadyDefined.contains(translucent)) {
                colorFileWriter.write(translucentColorName);
                colorFileWriter.newLine();
                colorValuesAlreadyDefined.add(translucent);
            }
            //then generate the objectAnimator
            animatorSetBW.write(objectanimator);
            animatorSetBW.newLine();
            animatorSetBW.write(durationColor);
            animatorSetBW.newLine();
            animatorSetBW.write(startOffset);
            animatorSetBW.newLine();
            animatorSetBW.write(interpolator);
            animatorSetBW.newLine();
            animatorSetBW.write(propertyNameStrokeColor);
            animatorSetBW.newLine();
            animatorSetBW.write(valueTypeArgb);
            animatorSetBW.newLine();
            if(reverseMode) {
                animatorSetBW.write(valueFrom.replace("#valueFrom", refToTranslucentColor));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo",refToStrokeColor.replace("#colorARGB", ("" + currentPath.getStrokeColor()).replace("#", "")) ));
            }else {
                animatorSetBW.write(valueFrom.replace("#valueFrom", refToStrokeColor.replace("#colorARGB", ("" + currentPath.getStrokeColor()).replace("#", ""))));
                animatorSetBW.newLine();
                animatorSetBW.write(valueTo.replace("#valueTo", refToTranslucentColor));
            }
            animatorSetBW.newLine();
            animatorSetBW.write(endtagbloc);
            animatorSetBW.newLine();
        }
    }
    /***********************************************************
     *  Generate Java File
     **********************************************************/

    /**
     * generates the MainActivity java code for exemple
     * And its associated layout
     *
     * @throws IOException
     */
    private void generateJavaFile() throws IOException {
        //the Java file
        CustomLogger.log("Generating the following file " + javaFileName);
        File mainActivityFile = new File(javaFolder, javaFileName);
        BufferedWriter mainActivityBW = createIt(mainActivityFile);
        CustomLogger.log("Generating the following file " + mainActivityFile.getAbsolutePath());
        mainActivityBW.write(javaCode);
        mainActivityBW.flush();
        mainActivityBW.close();
        //the layout
        CustomLogger.log("Generating the following file " + mainLayoutName);
        File mainLayoutFile = new File(javaFolder, mainLayoutName);
        BufferedWriter mainLayoutBW = createIt(mainLayoutFile);
        CustomLogger.log("Generating the following file " + mainLayoutFile.getAbsolutePath());
        mainLayoutBW.write(layoutContent);
        mainLayoutBW.flush();
        mainLayoutBW.close();
    }
    /***********************************************************
     *  Managing files methods
     **********************************************************/
    /**
     * create The file and return a BufferedWriter to write in
     *
     * @param file The file to create
     * @return The BufferedWriter to write in
     * @throws IOException
     */
    private BufferedWriter createIt(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        return new BufferedWriter(new FileWriter(file));
    }

    /**
     * Generates the folder structure:
     * res\values
     * \drawable
     * \animator
     */
    private void createFoldersStructure() {
        //first create the folders structure
        File file = new File(ressourcesDirectoryPath, res);
        if (!file.exists()) {
            file.mkdirs();
        }
        valuesfolder = new File(file, values);
        if (!valuesfolder.exists()) {
            valuesfolder.mkdirs();
        }
        drawableFolder = new File(file, drawable);
        if (!drawableFolder.exists()) {
            drawableFolder.mkdirs();
        }
        animatorFolder = new File(file, animator);
        if (!animatorFolder.exists()) {
            animatorFolder.mkdirs();
        }
        //the java folder structure
        javaFolder = new File(ressourcesDirectoryPath, java);
        javaFolder=new File(javaFolder,com);
        javaFolder=new File(javaFolder,android2ee);
        javaFolder=new File(javaFolder,tool);
        javaFolder=new File(javaFolder,animatedvector);
        javaFolder=new File(javaFolder,morphing);
        if (!javaFolder.exists()) {
            javaFolder.mkdirs();
        }
    }

}
