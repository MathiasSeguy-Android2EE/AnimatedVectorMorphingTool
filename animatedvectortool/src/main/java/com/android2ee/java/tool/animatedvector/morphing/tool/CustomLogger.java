/**
 * <ul>
 * <li>CustomLogger</li>
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

/**
 * Created by Mathias Seguy - Android2EE on 26/11/2015.
 */
public class CustomLogger {

    /**
     * Make a log disable when debug is off
     * @param message the message to log
     */
    public static void log(String message){
        if(VectorDrawableParser.DEBUG){
            System.out.println(message);
        }
    }

    /**
     * Make an error log, log what ever happens
     * @param message the message to log
     */
    public static void logError(String message){
        System.out.println(message);
    }
    /**
     * Make an error log, log what ever happens
     * @param message the message to log
     */
    public static void logError(String message,Exception e){
        System.out.println(message);
        e.printStackTrace();
    }
}
