/**
 * <ul>
 * <li>Group</li>
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

/**
 * Created by Mathias Seguy - Android2EE on 25/11/2015.
 * see : https://github.com/android/platform_frameworks_base/blob/master/graphics/java/android/graphics/drawable/VectorDrawable.java
 */
public class Group {
    //The associated constant we look at when parsing the xml file
    public final static String rootName="group";
    public final static String
            android_name = "android:name",
            android_rotation = "android:rotation",
            android_pivotX = "android:pivotX",
            android_pivotY = "android:pivotY",
            android_scaleX = "android:scaleX",
            android_scaleY = "android:scaleY",
            android_translateX = "android:translateX",
            android_translateY = "android:translateY";
    //the associated attributes
    private String name = null,
            rotation = null,
            pivotX = null,
            pivotY = null,
            scaleX = null,
            scaleY = null,
            translationX = null,
            translationY = null;
    //the list of groups
    ArrayList<Group> groups;
    //the list of paths
    ArrayList<Path> paths;
    //the list of clippaths
    ArrayList<ClipPath> clipPaths;

    public ArrayList<ClipPath> getClipPaths() {
        if(clipPaths==null){
            clipPaths=new ArrayList<ClipPath>();
        }
        return clipPaths;
    }

    public void setClipPaths(ArrayList<ClipPath> clipPaths) {
        this.clipPaths = clipPaths;
    }

    public ArrayList<Group> getGroups() {
        if(groups==null){
            groups=new ArrayList<Group>();
        }
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPivotX() {
        return pivotX;
    }

    public void setPivotX(String pivotX) {
        this.pivotX = pivotX;
    }

    public String getPivotY() {
        return pivotY;
    }

    public void setPivotY(String pivotY) {
        this.pivotY = pivotY;
    }

    public static String getRootName() {
        return rootName;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getScaleX() {
        return scaleX;
    }

    public void setScaleX(String scaleX) {
        this.scaleX = scaleX;
    }

    public String getScaleY() {
        return scaleY;
    }

    public void setScaleY(String scaleY) {
        this.scaleY = scaleY;
    }

    public String getTranslationY() {
        return translationY;
    }

    public void setTranslationY(String translationY) {
        this.translationY = translationY;
    }

    public String getTranslationX() {
        return translationX;
    }

    public void setTranslationX(String translatrionX) {
        this.translationX = translatrionX;
    }

    StringBuffer depthSB=new StringBuffer();

    public String toString(int depth) {
        for(int i=0;i<depth;i++){
            depthSB.append("\t");
        }
        final StringBuffer sb = new StringBuffer("\r\n"+depthSB+"Group{").append("\r\n"+depthSB);
        if(null!=name)sb.append("name=").append(name).append("\r\n"+depthSB);
        if(null!=clipPaths)sb.append("clipPaths=").append(clipPaths).append("\r\n"+depthSB);
        if(null!=rotation)sb.append("rotation=").append(rotation).append("\r\n"+depthSB);
        if(null!=pivotX)sb.append("pivotX=").append(pivotX).append("\r\n"+depthSB);
        if(null!=pivotY)sb.append("pivotY=").append(pivotY).append("\r\n"+depthSB);
        if(null!=scaleX)sb.append("scaleX=").append(scaleX).append("\r\n"+depthSB);
        if(null!=scaleY)sb.append("scaleY=").append(scaleY).append("\r\n"+depthSB);
        if(null!=translationX)sb.append("translatrionX=").append(translationX).append("\r\n"+depthSB);
        if(null!=translationY)sb.append("translationY=").append(translationY).append("\r\n"+depthSB);
        if(groups!=null) {
            sb.append("\r\n"+depthSB+"groups=<\r\n");
            for (int i = 0; i < groups.size(); i++) {
                sb.append("\t").append(groups.get(i).toString(depth+1));
                sb.append("\r\n").append("\r\n");
            }
            sb.append(">\r\n");
        }
        if(paths!=null) {
            sb.append("\r\n"+depthSB+"paths=<\r\n");
            for (int i = 0; i < paths.size(); i++) {
                sb.append(paths.get(i).toString(depth+1));
                sb.append("\r\n").append("\r\n");
            }
            sb.append(">\r\n");
        }

        if(clipPaths!=null) {
            sb.append("clipPaths=<\r\n");
            for (int i = 0; i < clipPaths.size(); i++) {
                sb.append(clipPaths.get(i).toString(depth+1));
                sb.append("\r\n").append("\r\n");
            }
            sb.append(">\r\n");
        }
        sb.append(depthSB+"}");
        return sb.toString();
    }
}
