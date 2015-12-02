/**
 * <ul>
 * <li>ResultTemplates</li>
 * <li>com.android2ee.java.tool.animatedvector.morphing.tool</li>
 * <li>27/11/2015</li>
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

/**
 * Created by Mathias Seguy - Android2EE on 27/11/2015.
 * This class aims to defined all the string used to generate the files results
 */
public class ResultTemplates extends ResultTemplateJava{

    /**
     * Xml files first tag
     */
    public static final String xml_start="<?xml version=\"1.0\" encoding=\"utf-8\"?>";

    /***********************************************************
     *  VectorDrawable attribute
     **********************************************************/
    /**
     * VectorDrawable elements :Vector, Path, Group, Clip-Path
     */
    public static final String vector_start="<vector xmlns:android=\"http://schemas.android.com/apk/res/android\"";
    public static final String vector_end="</vector>";
    public static final String group_start="<group";
    public static final String group_end="</group>";
    public static final String path_start="<path";
    public static final String clippath_start="<clip-path";
    public static final String name="    android:name=\"#name\"";
    public static final String viewportWidth="    android:viewportWidth=\"#viewportWidth\"";
    public static final String viewportHeight="    android:viewportHeight=\"#viewportHeight\"";
    public static final String width="    android:width=\"#width\"";
    public static final String height="    android:height=\"#height\"";
    public static final String tint="    android:tint=\"#tint\"";
    public static final String tintMode="    android:tintMode=\"#tintMode\"";
    public static final String autoMirrored="    android:autoMirrored=\"#autoMirrored\"";
    public static final String alpha="    android:alpha=\"#alpha\"";
    public static final String endtag=">";
    public static final String endtagbloc="/>";
    public static final String pivotX="        android:pivotX=\"#pivotX\"";
    public static final String pivotY="        android:pivotY=\"#pivotY\"";
    public static final String scaleX="        android:scaleX=\"#scaleX\"";
    public static final String scaleY="        android:scaleY=\"#scaleY\"";
    public static final String translateX="        android:translateX=\"#translateX\"";
    public static final String translateY="        android:translateY=\"#translateY\"";
    public static final String morphingName="       ";//"        avt:morphingName=\"#morphingName\"";
    public static final String pathData="        android:pathData=\"@string/#stringPathData\"";
    public static final String fillColor="        android:fillColor=\"#fillColor\"";
    public static final String strokeColor="        android:strokeColor=\"#strokeColor\"";
    public static final String strokeWidth="        android:strokeWidth=\"#strokeWidth\"";
    public static final String strokeAlpha="        android:strokeAlpha=\"#strokeAlpha\"";
    public static final String fillAlpha="        android:fillAlpha=\"#fillAlpha\" ";
    public static final String trimPathStart="        android:trimPathStart=\"#trimPathStart\"";
    public static final String trimPathEnd="        android:trimPathEnd=\"#trimPathEnd\"";
    public static final String trimPathOffset="        android:trimPathOffset=\"#trimPathOffset\"";
    public static final String strokeLineCap="        android:strokeLineCap=\"#strokeLineCap\"";
    public static final String strokeLineJoin="        android:strokeLineJoin=\"#strokeLineJoin\"";
    public static final String strokeMiterLimit="        android:strokeMiterLimit=\"#strokeMiterLimit\" ";
    /***********************************************************
     *  AnimatedVectorDrawable attribute
     **********************************************************/
    public static final String animatedvector_start="<animated-vector xmlns:android=\"http://schemas.android.com/apk/res/android\"";
    public static final String animatedvector_drawable="android:drawable=\"@drawable/#drawable\"";
    public static final String target_start="<target";
    public static final String animation="        android:animation=\"@animator/#animation\"";
    public static final String animatedvector_end="</animated-vector>";
    public static final String animatornamesuffix="#pathname_animator";
    /***********************************************************
     *  LevelListAnimatedDrawable Attributes
     **********************************************************/
    public static final String levellist_start="<level-list xmlns:android=\"http://schemas.android.com/apk/res/android\">";
    public static final String levellist_item="<item android:maxLevel=\"#itemNumber\" android:drawable=\"@drawable/#animatedVectorName\" />";
    public static final String levellist_end="</level-list>";
    /***********************************************************
     *  ObjectAnimator attribute
     **********************************************************/
    public static final String animatorset_start="<set xmlns:android=\"http://schemas.android.com/apk/res/android\">";
    public static final String animatorset_end="</set>";
    public static final String objectanimator="<objectAnimator";
    public static final String startOffset="        android:startOffset=\"1500\"";
    public static final String duration="        android:duration=\"3000\"";
    public static final String durationColor="        android:duration=\"1\"";
    public static final String propertyName="        android:propertyName=\"pathData\"";
    public static final String valueFrom="        android:valueFrom=\"#valueFrom\"";
    public static final String valueTo="        android:valueTo=\"#valueTo\"";
    public static final String valueType="        android:valueType=\"pathType\"";
    public static final String interpolator="        android:interpolator=\"@android:interpolator/linear\"";
    public static final String repeatMode="        android:repeatMode=\"reverse\"";
    public static final String repeatCount="        android:repeatCount=\"0\"";
    public static final String propertyNameFillColor ="        android:propertyName=\"fillColor\"";
    public static final String propertyNameStrokeColor="        android:propertyName=\"strokeColor\"";
    public static final String valueTypeArgb="        android:valueType=\"intType\"";
    public static final String reverse="_reverse";
    /***********************************************************
     *  String attribute
     **********************************************************/
    public static final String stringFileName="paths_strings.xml";
    public static final String stringline="<string name=\"#stringkey\">#stringvalue</string>";
    public static final String stringfilestart="<resources>";
    public static final String stringfileend="</resources>";
    public static final String stringPathPrefixInitial="init_path_";
    public static final String stringPathPrefixFinal="final_path_";
    public static final String stringToInitial="@string/init_path_";
    public static final String stringToFinal="@string/final_path_";
    public static final String stringClipPathSuffix="paths_strings.xml";
    /***********************************************************
     *  Colors attribute
     **********************************************************/
    public static final String colorsFileName="paths_colors.xml";
    public static final String fillColorName ="<item name=\"fillcolor_#colorARGB\" type=\"color\">#colorValue</item>";
    public static final String strokeColorName="<item name=\"strokecolor_#colorARGB\" type=\"color\">#colorValue</item>";
    public static final String refToFillColor ="@color/fillcolor_#colorARGB";
    public static final String refToStrokeColor ="@color/strokecolor_#colorARGB";
    public static final String translucentColorName ="<item name=\"translucent\" type=\"color\">#00000000</item>";
    public static final String refToTranslucentColor ="@color/translucent";
    public static final String translucent ="translucent";
    /***********************************************************
     *  Folder Name
     **********************************************************/
    public static final String java="java";
    public static final String com="com";
    public static final String android2ee="android2ee";
    public static final String tool="tool";
    public static final String animatedvector="animatedvector";
    public static final String morphing="morphing";
    public static final String res="res";
    public static final String values="values";
    public static final String drawable="drawable";
    public static final String animator="animator";
    public static final String animatedVector="animated_#fileName.xml";
    public static final String animatedVectorRoundTrip="animated_#fileName_roundtrip.xml";
    public static final String animatedVectorSimpleName="animated_#fileName";
    public static final String levelListAnimatedVector="animated_levellist.xml";
    public static final String animatorSet="animator_#fileName";
    public static final String animatorSetWithExtension="animator_#fileName.xml";
}
