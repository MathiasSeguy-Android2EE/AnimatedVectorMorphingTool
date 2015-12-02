/**
 * <ul>
 * <li>ClipPath</li>
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

/**
 * Created by Mathias Seguy - Android2EE on 25/11/2015.
 * see : https://github.com/android/platform_frameworks_base/blob/master/graphics/java/android/graphics/drawable/VectorDrawable.java
 */
public class ClipPath {
    //The associated constant we look at when parsing the xml file
    public final static String rootName="clip-path";
    public final static String
            android_name = "android:name",
            android_pathData = "android:pathData";
    //associated attributes
    private String name=null,
            pathData=null;

    public ClipPath() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPathData() {
        return pathData;
    }

    public void setPathData(String pathData) {
        this.pathData = pathData;
    }

    StringBuffer depthSB=new StringBuffer();

    public String toString(int depth) {
        for(int i=0;i<depth;i++){
            depthSB.append("\t");
        }
        final StringBuffer sb = new StringBuffer("\r\n"+depthSB+"ClipPath{").append("\r\n"+depthSB);
        if(null!=name)sb.append("name=").append(name).append("\r\n"+depthSB);
        if(null!=pathData)sb.append("pathData=").append(pathData).append("\r\n"+depthSB);
        sb.append(depthSB+"}");
        return sb.toString();
    }
}
