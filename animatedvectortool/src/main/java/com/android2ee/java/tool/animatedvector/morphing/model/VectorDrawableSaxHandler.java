/**
 * <ul>
 * <li>VectorDrawableSaxParser</li>
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

import com.android2ee.java.tool.animatedvector.morphing.tool.CustomLogger;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

/**
 * Created by Mathias Seguy - Android2EE on 25/11/2015.
 * This class aims to read a VectorDrawable Xml file and build a VectorDrawable as Java object
 * (it's my VectorDrawable: com.android2ee.java.tool.animatedvector.morphing.model.vectorDrawable)
 */
public class VectorDrawableSaxHandler extends DefaultHandler {
    /**
     * The vector drawable we build
     */
    VectorDrawable vectorDrawable;

    /**
     * Because Group can contains Groups (damn fuck) we need an arrayList
     */
    ArrayList<Group> currentGroups;

    /**
     * The current group we work in
     */
    Group currentGroup;

    /**
     * The current group we belong
     */
    int currentGroupIndex=0;
    Path currentPath;
    ClipPath currentClipPath;

/******************************************************************************************/
    /** Managing The Document parsing **************************************************************************/
    /******************************************************************************************/

	/*
	 * (non-Javadoc)
	 *
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
    @Override
    public void startDocument() throws SAXException {
        // instanciate the list of forecast you want to parse
        vectorDrawable = new VectorDrawable();
        currentGroups=new ArrayList<Group>();
    }

    /******************************************************************************************/
    /** Managing the begin and the end of a block **************************************************************************/
    /******************************************************************************************/

	/*
	 * (non-Javadoc)
	 *
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String,
	 * java.lang.String, org.xml.sax.Attributes)
	 */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        CustomLogger.log("[startElement] uri :" + uri + ", localName :" + localName + ", qName: " +
                qName + ", Attributes: "
                + attributes.getLength() + ", attributes.getValue(qName) :" + attributes.getValue(qName));

        if (qName.equals(VectorDrawable.rootName)) {
            CustomLogger.log("Parsing the vectorDrawable");
            //we are in the vector drawable xml block, let's parse it:
            if(null!=attributes.getValue(VectorDrawable.android_name)){
                vectorDrawable.setName(attributes.getValue(VectorDrawable.android_name));
            }
            if(null!=attributes.getValue(VectorDrawable.android_width)){
                vectorDrawable.setWidth(attributes.getValue(VectorDrawable.android_width));
            }
            if(null!=attributes.getValue(VectorDrawable.android_height)){
                vectorDrawable.setHeight(attributes.getValue(VectorDrawable.android_height));
            }
            if(null!=attributes.getValue(VectorDrawable.android_viewportWidth)){
                vectorDrawable.setViewPortWidth(attributes.getValue(VectorDrawable.android_viewportWidth));
            }
            if(null!=attributes.getValue(VectorDrawable.android_viewportHeight)){
                vectorDrawable.setViewPortHeight(attributes.getValue(VectorDrawable.android_viewportHeight));
            }
            if(null!=attributes.getValue(VectorDrawable.android_tint)){
                vectorDrawable.setTint(attributes.getValue(VectorDrawable.android_tint));
            }
            if(null!=attributes.getValue(VectorDrawable.android_tintMode)){
                vectorDrawable.setTintMode(attributes.getValue(VectorDrawable.android_tintMode));
            }
            if(null!=attributes.getValue(VectorDrawable.android_autoMirrored)){
                vectorDrawable.setAutoMirrored(attributes.getValue(VectorDrawable.android_autoMirrored));
            }
            if(null!=attributes.getValue(VectorDrawable.android_alpha)){
                vectorDrawable.setAlpha(attributes.getValue(VectorDrawable.android_alpha));
            }

        } else if(qName.equals(Group.rootName)){
            //we are in a group
            CustomLogger.log("Parsing group");
            currentGroup=new Group();
            if(currentGroupIndex==0){
                vectorDrawable.getGroups().add(currentGroup);
                //and we add it at its position in the list
                currentGroups.add(currentGroupIndex, currentGroup);
            }else{
                //else we add it to its predecessor
                currentGroups.get(currentGroupIndex-1).getGroups().add(currentGroup);
                //and we add it at its position in the list
                currentGroups.add(currentGroupIndex,currentGroup);
            }
            currentGroupIndex++;
            //we are in the group xml block, let's parse it:
            if(null!=attributes.getValue(Group.android_name)){
                currentGroup.setName(attributes.getValue(Group.android_name));
            }
            if(null!=attributes.getValue(Group.android_name)){
                currentGroup.setName(attributes.getValue(Group.android_name));
            }
            if(null!=attributes.getValue(Group.android_rotation)){
                currentGroup.setRotation(attributes.getValue(Group.android_rotation));
            }
            if(null!=attributes.getValue(Group.android_pivotX)){
                currentGroup.setPivotX(attributes.getValue(Group.android_pivotX));
            }
            if(null!=attributes.getValue(Group.android_pivotY)){
                currentGroup.setPivotY(attributes.getValue(Group.android_pivotY));
            }
            if(null!=attributes.getValue(Group.android_scaleX)){
                currentGroup.setScaleX(attributes.getValue(Group.android_scaleX));
            }
            if(null!=attributes.getValue(Group.android_scaleY)){
                currentGroup.setScaleY(attributes.getValue(Group.android_scaleY));
            }
            if(null!=attributes.getValue(Group.android_translateX)){
                currentGroup.setTranslationX(attributes.getValue(Group.android_translateX));
            }
            if(null!=attributes.getValue(Group.android_translateY)){
                currentGroup.setTranslationY(attributes.getValue(Group.android_translateY));
            }
        }else if(qName.equals(Path.rootName)){
            //we are in a path
            CustomLogger.log("Parsing path");
            currentPath=new Path();
            if(currentGroup==null){
                vectorDrawable.getPaths().add(currentPath);
            }else{
                currentGroup.getPaths().add(currentPath);
            }

            //we are in the path xml block, let's parse it:
            if(null!=attributes.getValue(Path.android_name)){
                currentPath.setName(attributes.getValue(Path.android_name));
            }
            if(null!=attributes.getValue(Path.android_morphingName)){
                currentPath.setMorphingName(attributes.getValue(Path.android_morphingName));
            }
            if(null!=attributes.getValue(Path.android_pathData)){
                currentPath.setPathData(attributes.getValue(Path.android_pathData));
            }
            if(null!=attributes.getValue(Path.android_fillColor)){
                currentPath.setFillColor(attributes.getValue(Path.android_fillColor));
            }
            if(null!=attributes.getValue(Path.android_strokeColor)){
                currentPath.setStrokeColor(attributes.getValue(Path.android_strokeColor));
            }
            if(null!=attributes.getValue(Path.android_strokeWidth)){
                currentPath.setStrokeWidth(attributes.getValue(Path.android_strokeWidth));
            }
            if(null!=attributes.getValue(Path.android_strokeAlpha)){
                currentPath.setStrokeAlpha(attributes.getValue(Path.android_strokeAlpha));
            }
            if(null!=attributes.getValue(Path.android_fillAlpha)){
                currentPath.setFillAlpha(attributes.getValue(Path.android_fillAlpha));
            }
            if(null!=attributes.getValue(Path.android_trimPathStart)){
                currentPath.setTrimPathStart(attributes.getValue(Path.android_trimPathStart));
            }
            if(null!=attributes.getValue(Path.android_trimPathEnd)){
                currentPath.setTrimPathEnd(attributes.getValue(Path.android_trimPathEnd));
            }
            if(null!=attributes.getValue(Path.android_trimPathOffset)){
                currentPath.setTrimPathOffset(attributes.getValue(Path.android_trimPathOffset));
            }
            if(null!=attributes.getValue(Path.android_strokeLineCap)){
                currentPath.setStrokeLineCap(attributes.getValue(Path.android_strokeLineCap));
            }
            if(null!=attributes.getValue(Path.android_strokeLineJoin)){
                currentPath.setStrokeLineJion(attributes.getValue(Path.android_strokeLineJoin));
            }
            if(null!=attributes.getValue(Path.android_strokeMiterLimit)){
                currentPath.setStrokeMiterLimit(attributes.getValue(Path.android_strokeMiterLimit));
            }
        }else if(qName.equals(ClipPath.rootName)){
            //we are in a path
            CustomLogger.log("Parsing clippath");
            currentClipPath=new ClipPath();
            if(currentGroup==null){
                //not possible case
                if(null!=attributes.getValue(ClipPath.android_name)){
                    throw new SAXException("There is a ClipPath ("+attributes.getValue(ClipPath.android_name)+")that doesn't belong to a group, it's not possible");
                }else{
                    throw new SAXException("There is a ClipPath that doesn't belong to a group, it's not possible");
                }
            }else{
                currentGroup.getClipPaths().add(currentClipPath);
            }
            if(null!=attributes.getValue(ClipPath.android_name)){
                currentClipPath.setName(attributes.getValue(ClipPath.android_name));
            }
            if(null!=attributes.getValue(ClipPath.android_pathData)){
                currentClipPath.setPathData(attributes.getValue(ClipPath.android_pathData));
            }
        }

    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(qName.equals(Group.rootName)){
            //we are leaving the current group
            CustomLogger.log("leaving the current group");
            //remove the group from the list of current groups
            currentGroups.remove(currentGroupIndex-1);
            //decrement the number (==the depth) of currentGroup
            currentGroupIndex--;
            currentGroup=null;
        }else if(qName.equals(Group.rootName)){
            //we are leaving the current path
            CustomLogger.log("leaving the current path");
        }else if(qName.equals(VectorDrawable.rootName)){
            CustomLogger.log("We have parse the following vector : \r\n" + vectorDrawable.toString());
        }
    }

    public VectorDrawable getVectorDrawable() {
        return vectorDrawable;
    }
}
