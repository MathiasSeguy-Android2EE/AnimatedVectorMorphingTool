AnimatedVectorMorphingTool
==========================
Download it! [ ![Download](http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/Downloads2.png) ](http://www.android2ee.com/images/stories/AnimatedVectorMorphingTool/animatedvectortool-0.1.jar)
---------------


**AnimatedVectorMorphingTool**  is a command line tool which generates, for you, all the files needed to animate your VectorDrawable. You just drop your VectorDrawables (you can drop n files) and it generates all the files needed for your android project in a res folder. You just have to copy paste this folder into your own project and run your application.       
It also generates a Java class exemple (and its associated layout), for you to quickly copy-paste the code in you own Activity to run you animations.    
**It generates several scenarios: **
>-**Simple one**: for each pair of files (in order) it generates the appropriates files to animate from one to the other    
>-**The queue animations**: it generates all the files needed to animates all the VectorDrawable you gave to the tool runing from one to the other, to the other     
>-**The roundTrip**: For each file the round trip scenario that allows you to reverse your animation.     

**Rewards**    
It uses VectAlign a tool made by **Stefano Bonetta** (github project here [Have a look and thank him](https://github.com/bonnyfone)) that makes the magic VectorDrawables alignement. Without that tool, nothing could have been done, the core threatment is there.
So again and again **A huge big thank to Stefano** for its tool.    



**Vidéos**
Here is the video of the result you can easily obtain :    
[![Watch it on YouTube](http://img.youtube.com/vi/BknPqH8NW2I/0.jpg)](http://www.youtube.com/watch?v=BknPqH8NW2I)
Here is the video where I show you how to manage the project     
->English version (sorry for my accent)     
[![Watch it on YouTube](http://img.youtube.com/vi/lysYCbkEHcg/0.jpg)](http://www.youtube.com/watch?v=lysYCbkEHcg)
->French version     
[![Watch it on YouTube](http://img.youtube.com/vi/t2077-74ODw/0.jpg)](http://www.youtube.com/watch?v=t2077-74ODw)

**Usage**    
You run AnimationVectorMorphingTool from the command line. You give him, as arguments, the list of the VectorDrawable you want to animate. The tool will generate for you, the AnimatedVectorDrawable, the VectorDrawable (the same as you gave) and the ObjectAnimator into a folder called res. Then you copy paste this folder into your project and you have the files needed to make the animations between the VectorDrawables you gave.
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

*Important - Warning - You need to*
============================
You need to add information into the VectorDrawable you want to morph. The tool needs to know which path has to be morphed to which path. So in your initial VectorDrawable you need to add the android:morphingName and path that have the same morphing name will be morphone to the other.
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
**You also need to give a name to your path**
[To be conitnued, my daighter is crying loud :(]