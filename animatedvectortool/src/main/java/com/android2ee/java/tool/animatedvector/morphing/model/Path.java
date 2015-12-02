/**
 * <ul>
 * <li>Path</li>
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
public class Path {
    //The associated constant we look at when parsing the xml file
    public final static String rootName = "path";
    public final static String
            android_name = "android:name",
            android_morphingName = "android:morphingName",
            android_pathData = "android:pathData",
            android_fillColor = "android:fillColor",
            android_strokeColor = "android:strokeColor",
            android_strokeWidth = "android:strokeWidth",
            android_strokeAlpha = "android:strokeAlpha",
            android_fillAlpha = "android:fillAlpha",
            android_trimPathStart = "android:trimPathStart",
            android_trimPathEnd = "android:trimPathEnd",
            android_trimPathOffset = "android:trimPathOffset",
            android_strokeLineCap = "android:strokeLineCap",
            android_strokeLineJoin = "android:strokeLineJoin",
            android_strokeMiterLimit = "android:strokeMiterLimit";
        //associated attributes
        private String name=null,
                morphingName=null,
                pathData=null,
                fillColor=null,
                fillColorTarget=null,
                strokeColor=null,
                strokeColorTarget=null,
                strokeWidth=null,
                strokeWidthTarget=null,
                strokeAlpha=null,
                strokeAlphaTarget=null,
                fillAlpha=null,
                fillAlphaTarget=null,
                trimPathStart=null,
                trimPathEnd=null,
                trimPathOffset=null,
                strokeLineCap=null,
                strokeLineJion=null,
                strokeMiterLimit=null;

    //the normalized path Data Initial when used as source and final when used as destination
    //both are set because for each VectorDrawable we morph from one to the other
    private String normalizedInitialPathData=null,normalizedFinalPathData=null;



    public Path() {}

    public String getNormalizedFinalPathData() {
        return normalizedFinalPathData;
    }

    public String getMorphingName() {
        return morphingName;
    }

    public void setMorphingName(String morphingName) {
        this.morphingName = morphingName;
    }

    public void setNormalizedFinalPathData(String normalizedFinalPathData) {
        this.normalizedFinalPathData = normalizedFinalPathData;
    }

    public String getNormalizedInitialPathData() {
        return normalizedInitialPathData;
    }

    public void setNormalizedInitialPathData(String normalizedInitialPathData) {
        this.normalizedInitialPathData = normalizedInitialPathData;
    }

    public String getFillAlphaTarget() {
        return fillAlphaTarget;
    }

    public void setFillAlphaTarget(String fillAlphaTarget) {
        this.fillAlphaTarget = fillAlphaTarget;
    }

    public String getStrokeAlphaTarget() {
        return strokeAlphaTarget;
    }

    public void setStrokeAlphaTarget(String strokeAlphaTarget) {
        this.strokeAlphaTarget = strokeAlphaTarget;
    }

    public String getStrokeWidthTarget() {
        return strokeWidthTarget;
    }

    public void setStrokeWidthTarget(String strokeWidthTarget) {
        this.strokeWidthTarget = strokeWidthTarget;
    }

    public String getStrokeColorTarget() {
        return strokeColorTarget;
    }

    public void setStrokeColorTarget(String strokeColorTarget) {
        this.strokeColorTarget = strokeColorTarget;
    }

    public String getFillColorTarget() {
        return fillColorTarget;
    }

    public void setFillColorTarget(String fillColorTarget) {
        this.fillColorTarget = fillColorTarget;
    }

    public String getFillAlpha() {
        return fillAlpha;
    }

    public void setFillAlpha(String fillAlpha) {
        this.fillAlpha = fillAlpha;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
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

    public static String getRootName() {
        return rootName;
    }

    public String getStrokeAlpha() {
        return strokeAlpha;
    }

    public void setStrokeAlpha(String strokeAlpha) {
        this.strokeAlpha = strokeAlpha;
    }

    public String getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(String strokeColor) {
        this.strokeColor = strokeColor;
    }

    public String getStrokeLineCap() {
        return strokeLineCap;
    }

    public void setStrokeLineCap(String strokeLineCap) {
        this.strokeLineCap = strokeLineCap;
    }

    public String getStrokeLineJion() {
        return strokeLineJion;
    }

    public void setStrokeLineJion(String strokeLineJion) {
        this.strokeLineJion = strokeLineJion;
    }

    public String getStrokeMiterLimit() {
        return strokeMiterLimit;
    }

    public void setStrokeMiterLimit(String strokeMiterLimit) {
        this.strokeMiterLimit = strokeMiterLimit;
    }

    public String getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(String strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public String getTrimPathEnd() {
        return trimPathEnd;
    }

    public void setTrimPathEnd(String trimPathEnd) {
        this.trimPathEnd = trimPathEnd;
    }

    public String getTrimPathOffset() {
        return trimPathOffset;
    }

    public void setTrimPathOffset(String trimPathOffset) {
        this.trimPathOffset = trimPathOffset;
    }

    public String getTrimPathStart() {
        return trimPathStart;
    }

    public void setTrimPathStart(String trimPathStart) {
        this.trimPathStart = trimPathStart;
    }

    StringBuffer depthSB=new StringBuffer();

    public String toString(int depth) {
        for(int i=0;i<depth;i++){
            depthSB.append("\t");
        }
        final StringBuffer sb = new StringBuffer("\r\n"+depthSB+"Path{").append("\r\n"+depthSB);
        if(null!=name)sb.append("name=").append(name).append("\r\n"+depthSB);
        if(null!=fillAlpha)sb.append("fillAlpha=").append(fillAlpha).append("\r\n"+depthSB);
        if(null!=pathData)sb.append("pathData=").append(pathData).append("\r\n"+depthSB);
        if(null!=fillColor)sb.append("fillColor=").append(fillColor).append("\r\n"+depthSB);
        if(null!=strokeColor)sb.append("strokeColor=").append(strokeColor).append("\r\n"+depthSB);
        if(null!=strokeWidth)sb.append("strokeWidth=").append(strokeWidth).append("\r\n"+depthSB);
        if(null!=strokeAlpha)sb.append("strokeAlpha=").append(strokeAlpha).append("\r\n"+depthSB);
        if(null!=trimPathStart)sb.append("trimPathStart=").append(trimPathStart).append("\r\n"+depthSB);
        if(null!=trimPathEnd)sb.append("trimPathEnd=").append(trimPathEnd).append("\r\n"+depthSB);
        if(null!=trimPathOffset)sb.append("trimPathOffset=").append(trimPathOffset).append("\r\n"+depthSB);
        if(null!=strokeLineCap)sb.append("strokeLineCap=").append(strokeLineCap).append("\r\n"+depthSB);
        if(null!=strokeLineJion)sb.append("strokeLineJion=").append(strokeLineJion).append("\r\n"+depthSB);
        if(null!=strokeMiterLimit)sb.append("strokeMiterLimit=").append(strokeMiterLimit).append("\r\n"+depthSB);
        sb.append(depthSB+"}");
        return sb.toString();
    }
}
