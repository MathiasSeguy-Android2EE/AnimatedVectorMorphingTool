/**
 * <ul>
 * <li>VectorDrawable</li>
 * <li>com.android2ee.java.tool.animatedvector.morphing.model</li>
 * <li>25/11/2015</li>
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

package com.android2ee.java.tool.animatedvector.morphing.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mathias Seguy - Android2EE on 25/11/2015.
 * Ok dudes, I copy paste a lot of fields from the aosp :
 * https://github.com/android/platform_frameworks_base/blob/master/graphics/java/android/graphics/drawable/VectorDrawable.java
 * because it's the best way to do
 *
 */
public class VectorDrawable {
    /***********************************************************
     * Attributes
     **********************************************************/
    //The associated constant we look at when parsing the xml file
    public final static String rootName="vector";
    public final static String
            android_name = "android:name",
            android_width = "android:width",
            android_height = "android:height",
            android_viewportWidth = "android:viewportWidth",
            android_viewportHeight = "android:viewportHeight",
            android_tint = "android:tint",
            android_tintMode = "android:tintMode",
            android_autoMirrored = "android:autoMirrored",
            android_alpha = "android:alpha";

    //The size of the vector
    private String viewPortHeight = null,
            viewPortWidth = null,
            width = null,
            height = null;
    //Tint and alpha
    private String tint = null,
            tintMode = null,
            alpha = null,
            autoMirrored = null;
    private String name;
    private String fileName;
    //the list of groups
    ArrayList<Group> groups;
    //the list of paths
    ArrayList<Path> paths;
    /**
     * The hashmap that makes the link betwwen the PathName and the Path
     * This is used to find the path to morph between two vectordrawable
     * We morph only the path that have the same name in both vectorDrawable
     * It contains all the path of the vectordrawable
     */
    HashMap<String,Path> pathToMorphSortByMorphingName;

    /***********************************************************
     * Constructor
     **********************************************************/
    public VectorDrawable() {
    }

    private boolean morphPathNameFound=false;
    /**
     * Once you have instanciate your VectorDrawable, call this method to build the hashMap the linkPathName and Path
     *
     * @return true if a android:morphingPathName have been found else false
     */
    public boolean setup(){
        pathToMorphSortByMorphingName =new HashMap<String,Path>();
        //browse this object to build the map
        if(paths!=null) {
            for (int i = 0; i < paths.size(); i++) {
                if (paths.get(i).getMorphingName() != null) {
                    morphPathNameFound=true;
                    pathToMorphSortByMorphingName.put(paths.get(i).getMorphingName(), paths.get(i));
                }
            }
        }
        if(groups!=null) {
            //do the same for the group
            for (int i = 0; i < groups.size(); i++) {
                browseGroup(groups.get(i));
            }
        }
        return morphPathNameFound;
    }

    /**
     * Recursive browse of the Group to find path with name
     * @param group
     */
    private void browseGroup(Group group){
        if(group.getPaths()!=null) {
            for (int i = 0; i < group.getPaths().size(); i++) {
                if (group.getPaths().get(i).getMorphingName() != null) {
                    morphPathNameFound=true;
                    pathToMorphSortByMorphingName.put(group.getPaths().get(i).getMorphingName(), group.getPaths().get(i));
                }
            }
        }
        if(group.getGroups()!=null) {
            for (int i = 0; i < group.getGroups().size(); i++) {
                browseGroup(group.getGroups().get(i));
            }
        }
    }

    /***********************************************************
     * Getters Setter and toString
     **********************************************************/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getAutoMirrored() {
        return autoMirrored;
    }

    public void setAutoMirrored(String autoMirrored) {
        this.autoMirrored = autoMirrored;
    }

    public ArrayList<Group> getGroups() {
        if(groups==null){
            groups=new ArrayList<Group>();
        }
        return groups;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setGroups(ArrayList<Group> groups) {

        this.groups = groups;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public ArrayList<Path> getPaths() {
        if(paths==null){
            paths=new ArrayList<Path>();
        }
        return paths;
    }

    public void setPaths(ArrayList<Path> paths) {
        this.paths = paths;
    }

    public String getTint() {
        return tint;
    }

    public void setTint(String tint) {
        this.tint = tint;
    }

    public String getTintMode() {
        return tintMode;
    }

    public void setTintMode(String tintMode) {
        this.tintMode = tintMode;
    }

    public String getViewPortHeight() {
        return viewPortHeight;
    }

    public void setViewPortHeight(String viewPortHeight) {
        this.viewPortHeight = viewPortHeight;
    }

    public String getViewPortWidth() {
        return viewPortWidth;
    }

    public void setViewPortWidth(String viewPortWidth) {
        this.viewPortWidth = viewPortWidth;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public HashMap<String, Path> getPathToMorphSortByMorphingName() {
        return pathToMorphSortByMorphingName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("VectorDrawable{\r\n");

        if(null!=name)sb.append("name=").append(name).append("\r\n");
        if(null!=alpha)sb.append("alpha=").append(alpha).append("\r\n");
        if(null!=viewPortHeight)sb.append("viewPortHeight=").append(viewPortHeight).append("\r\n");
        if(null!=viewPortWidth)sb.append("viewPortWidth=").append(viewPortWidth).append("\r\n");
        if(null!=width)sb.append("width=").append(width).append("\r\n");
        if(null!=height)sb.append("height=").append(height).append("\r\n");
        if(null!=tint)sb.append("tint=").append(tint).append("\r\n");
        if(null!=tintMode)sb.append("tintMode=").append(tintMode).append("\r\n");
        if(null!=autoMirrored)sb.append("autoMirrored=").append(autoMirrored).append("\r\n");
        if(groups!=null) {
            sb.append("groups=<\r\n");
            for (int i = 0; i < groups.size(); i++) {
                sb.append("\t").append(groups.get(i).toString(1));
                sb.append("\r\n").append("\r\n");
            }
            sb.append(">\r\n");
        }
        if(paths!=null) {
            sb.append("paths=<\r\n");
            for (int i = 0; i < paths.size(); i++) {
                sb.append(paths.get(i).toString(1));
                sb.append("\r\n").append("\r\n");
            }
            sb.append(">\r\n");
        }
        sb.append('}');
        return sb.toString();
    }
}
