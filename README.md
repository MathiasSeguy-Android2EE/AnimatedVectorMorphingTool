AnimatedVectorMorphingTool
==========================
Download it! [ ![Download](http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/Downloads2.png) ](http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/animatedvectortool-0.1.jar)
---------------

**AnimatedVectorMorphingTool**  is a command line tool which generates, for you, all the files needed to animate your VectorDrawable. You just drop your VectorDrawables (you can drop n files) and it generates all the files needed for your android project in a res folder. You just have to copy paste this folder into your own project and run your application.           
<img src="http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/video_small.gif"><img src="http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/QueuedAnimGQ2.gif">  <img src="http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/RoundTrip.gif">   <img src="http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/normal.gif">  

<img src="http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/AndroidToBackup.gif"> <img src="http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/QueuedAnim.gif">     
It also generates a Java class exemple (and its associated layout), for you to quickly copy-paste the code in you own Activity to run you animations.    
**It generates several scenarios: **
>-**Simple one**: for each pair of files (in order) it generates the appropriates files to animate from one to the other;    
>-**The queue animations**: it generates all the files needed to animate all the VectorDrawables you gave to the tool, runing from one to the other, to the other;     
>-**The roundTrip**: For each file the round trip scenario that allows you to reverse your animation.     

*Rewards, acknowledgment and Big thanks*
==========    
It uses VectAlign a tool made by **Stefano Bonetta** (github project here [Have a look and thank him](https://github.com/bonnyfone)) that makes the magic VectorDrawables alignement. Without that tool, nothing could have been done, the core treatment is there.
So again and again **A huge big thank to Stefano** for its tool.    



*Vidéos*     
=========
Here is the video of the result you can easily obtain :    
[![Watch it on YouTube](http://img.youtube.com/vi/BknPqH8NW2I/0.jpg)](http://www.youtube.com/watch?v=BknPqH8NW2I)    
Here is the video where I show you how to manage the project         
->English version (sorry for my accent)     
[![Watch it on YouTube](http://img.youtube.com/vi/lysYCbkEHcg/0.jpg)](http://www.youtube.com/watch?v=lysYCbkEHcg)    
->French version     
[![Watch it on YouTube](http://img.youtube.com/vi/t2077-74ODw/0.jpg)](http://www.youtube.com/watch?v=t2077-74ODw)

**Usage**    
========
You run AnimationVectorMorphingTool from the command line. You give him, as arguments, the list of the VectorDrawable you want to animate. The tool will generate for you, the AnimatedVectorDrawables, the VectorDrawables (the same as you gave) and the ObjectAnimators into a folder called res. Then you copy paste this folder into your project and you have the files needed to make the animations between the VectorDrawables you gave.
The tool also generates a MainActivity Java file and its associated layout in a folder called 'java'. You can copy paste them into your project and see what happens. It generates those two files for you to have the code to write in your project.
How does the command line works ? Simply :
```
// Generate the animation from android.xml to backup.xml which are VectorDrawables
java -jar animatedvectortool-0.1.jar android.xml backup.xml
```
Running this command line will generates 3 scenarios:    
>-**Simple one** based on the AnimatedVectorDrawable called animated_android which allows you to make a morphing from Android to backup.    
>-**The queue animations** based on the file  animated_levellist which allows you to make morphings from android to backup to android to backup... and so on    
>-**The roundTrip**:  based on animated_android_roundtrip which allows you to make morphings from android to backup and back to android.    
With two files the differrence between queue and roundtrip is not obvious.    
So let's see what happens when you use more than two files:     
```
// Generate the animation from android.xml to backup.xml which are VectorDrawables
java -jar animatedvectortool-0.1.jar debug android.xml backup.xml explore.xml favorite.xml fingerprint.xml android.xml
```
Running this command line will generates 3 scenarios:    
>-**Simple one** based on the AnimatedVectorDrawables called animated_android, animated_backup, animated_explore, animated_favorite, animated_fingerprint, which allows you to make a morphing from android to backup, from backup to explore, explore to favorite, favorite to fingerprint, fingerprint to android.    
>-**The queue animations** based on the file  animated_levellist which allows you to make morphings from android->backup->explore->favorite->fingerprint->android... and so on    
>-**The roundTrip**:  based on animated_android_roundtrip, animated_backup_roundtrip, animated_explore_roundtrip, animated_fingerprint_roundtrip which allows you to make morphings from android to backup and back to android, backup to explore and back to backup,... and so on

***Important - Warning - You need to***
============================
You need to add information into the VectorDrawables you want to morph. The tool needs to know which path has to be morphed to which path. So in your initial VectorDrawable you need to add the **android:morphingName** (not an android tag) and paths that have the same morphing name will be animated the one to the other.    
So in the first vectorDrawable I add the xml tag android:morphingDrawable like this:     
```

  <?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:targetApi="21"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">

    <path
        android:name="zero"
        android:morphingName="zero"
        android:strokeColor="#ff0000"
        android:pathData=".." />
    <path
        android:name="first"
        android:morphingName="first"
        android:strokeColor="#00f000"
        android:fillColor="#00f000"
        android:pathData="..." />
    <path
        android:name="second"
        android:morphingName="second"
        android:strokeColor="#00f000"
        android:fillColor="#00f000"
        android:pathData="..." />

</vector>  
```
So in the secondvectorDrawable like this:
```
<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:targetApi="21"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
    <path
        android:name="zero"
        android:morphingName="zero"
        android:strokeColor="#ff0000"
        android:pathData="..." />
    <path
        android:name="first"
        android:morphingName="first"
        android:fillColor="#00f0f0"
        android:pathData="..." />
    <path
        android:name="second"
        android:morphingName="second"
        android:fillColor="#ff0000"
        android:pathData="..." />
</vector>
```
The path with the same morphing name will be linked in the morphing.
**You also need to give a name to your path using the android:name tag**     
The result looks like that:    
<img src="http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/AndroidToBackup.gif"> 


Others command line arguments   
====
To print the help :
```
// Print help
java -jar animatedvectortool-0.1.jar h
//or
java -jar animatedvectortool-0.1.jar -h
```    
To print the version :
```
// Print version
java -jar animatedvectortool-0.1.jar v
//or
java -jar animatedvectortool-0.1.jar -v
```    
To enable the debug mode that will log you every think that happens:
```
// Print debug logs
java -jar animatedvectortool-0.1.jar debug android.xml backup.xml
```   
To generate the bug report when a bug occurs in the VectAlign treatment:
```
// Generate errors file
java -jar animatedvectortool-0.1.jar error android.xml backup.xml
```  
**but it's not yet implemented it's in the pipe.**    

The Trim mode for animations    
==========================
Not yet implemented but in the pipe.    

Limitation and Errors     
==========================
As Stefano said :     
-------------------
"- This is an experimental tool which faces a complex task. **Result's quality may vary depending on the inputs**; thus, *wow effect* of the resulting animation is not guaranteed.    
 - Aligning complex shapes **may create visual artifacts on one or both images**; in this case, try to simplify the original SVG path (e.g. using [InkScape]) and then run VectAlign again (see also the *Tips* section).    
 - When referring a SVG file, all the path groups which compose the image will be merged in one single path.    
 - If your SVG path is too much complex the system renderer will throw a silent exception: "*OpenGLRenderer﹕ Path too large to be rendered into a texture*"; in this case you need to simplify your image further."        


Tips    
--
 -When playing with that tool, and in particular with android material icon, I found that splitting VectorDrawable path was a good idea and the resul often looks more "waa"    
 So instead of :    
 ```
    <path
        android:fillColor="#000000"
        app:vc_fillColor="#000000"
        android:pathData="M6 18c0 .55 .45 1 1 1h1v3.5c0 .83 .67 1.5 1.5 1.5s1.5-.67 1.5-1.5V19h2v3.5c0 .83
.67 1.5 1.5 1.5s1.5-.67 1.5-1.5V19h1c.55 0 1-.45 1-1V8H6v10zM3.5 8C2.67 8 2 8.67
2 9.5v7c0 .83 .67 1.5 1.5 1.5S5 17.33 5 16.5v-7C5 8.67 4.33 8 3.5 8zm17 0c-.83
0-1.5 .67 -1.5 1.5v7c0 .83 .67 1.5 1.5 1.5s1.5-.67
1.5-1.5v-7c0-.83-.67-1.5-1.5-1.5zm-4.97-5.84l1.3-1.3c.2-.2 .2 -.51
0-.71-.2-.2-.51-.2-.71 0l-1.48 1.48C13.85 1.23 12.95 1 12 1c-.96 0-1.86 .23
-2.66 .63 L7.85 .15 c-.2-.2-.51-.2-.71 0-.2 .2 -.2 .51 0 .71l1.31 1.31C6.97 3.26
6 5.01 6 7h12c0-1.99-.97-3.75-2.47-4.84zM10 5H9V4h1v1zm5 0h-1V4h1v1z"/>
```   
 I split it into :    
 ```
   <path
        android:name="first"
        android:morphingName="first"
        android:strokeColor="#00f000"
        android:fillColor="#00f000"
        android:pathData="M6 18c0 .55 .45 1 1 1h1v3.5c0 .83 .67 1.5 1.5 1.5s1.5-.67 1.5-1.5V19h2v3.5c0 .83
.67 1.5 1.5 1.5s1.5-.67 1.5-1.5V19h1c.55 0 1-.45 1-1V8H6v10z" />
    <path
        android:name="second"
        android:morphingName="second"
        android:strokeColor="#00f000"
        android:fillColor="#00f000"
        android:pathData="M3.5 8C2.67 8 2 8.67
2 9.5v7c0 .83 .67 1.5 1.5 1.5S5 17.33 5 16.5v-7C5 8.67 4.33 8 3.5 8zm17 0c-.83
0-1.5 .67 -1.5 1.5v7c0 .83 .67 1.5 1.5 1.5s1.5-.67
1.5-1.5v-7c0-.83-.67-1.5-1.5-1.5zm-4.97-5.84l1.3-1.3c.2-.2 .2 -.51
0-.71-.2-.2-.51-.2-.71 0l-1.48 1.48C13.85 1.23 12.95 1 12 1c-.96 0-1.86 .23
-2.66 .63 L7.85 .15 c-.2-.2-.51-.2-.71 0-.2 .2 -.2 .51 0 .71l1.31 1.31C6.97 3.26
6 5.01 6 7h12c0-1.99-.97-3.75-2.47-4.84zM10 5H9V4h1v1zm5 0h-1V4h1v1z" />

```
And the way I split is when finding a sequence of "*zM*" I can split in the middle.   
for exemple "M3.5 8C2.67 8 2 8.67....zM6 18c0" =>"M3.5 8C2.67 8 2 8.67....z" and "M6 18c0"    
It makes a real better effect.    


The way I use the tool    
==================
<-First I have my SVG picture    
<-Then I go on InLoop: http://inloop.github.io/svg2android/ to convert them and I ensure not to generate compatible elements    
<-I save those file in my folder "xml root" and copy them (also) in my working directory. let's call those files (in the working directory) working files.    
<-I open each file, delete the first charcter of the file "<" and replace it with the same "<" (becasue inLoop generates a bad char that broke the xml parsing using Sax, don't know why) and I add my xml tag android:morphingName and android:name on each path I want to morph    
<-Then I run the tool on those files (in my working directory) and I copy paste the elements in my Android project    
<-I look at what happens and I go back to my working files to enhance them, splitting some paths, adding fillColor or Stroke color, I run the tool again, copy-paste, look, enhance    
As generating the new files take several second, looking at what happens and enhance the working file is really easy and fast.    
Hope you'll enjoy it.    

And at last : thanks to https://stackedit.io/editor, because markup is hell without.