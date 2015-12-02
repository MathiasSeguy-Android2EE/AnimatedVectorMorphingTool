package com.android2ee.tool.animatedvector.morphing;

import android.annotation.TargetApi;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    LinearLayoutScreenRecorder llScreenRecorder;
    /**
     * The ImageViews
     */
    ImageView imageView1, imageView2, imageView3, imageView4;
    /**
     * Too simple AnimatedVectorDrawables for having a simple exemple
     */
    AnimatedVectorDrawable  animatedVector3, animatedVector4;
    /***********************************************************
     * Managing Level List: To chain animations
     **********************************************************/
    //The LevelListDrawable that contains all the AnimatedVectorDrawables
    LevelListDrawable animatedVectorList;
    /**
     * The current VectorDrawable displayed by the animatedVectorList
     */
    AnimatedVectorDrawable currentAnimatedVectorFromList;
    /**
     * The max level of your LevelList animatedVectorList
     */
    int animatedVectorListMaxLevel = 0;
    /**
     * A CallBack to know when the animation of the VectorDrawable is over
     */
    Animatable2.AnimationCallback animationCallback;
    /***********************************************************
     * Managing RoundTrip animation (VectorDrawable1 to VectorDrawable 2 and back again
     **********************************************************
     /**
     * A CallBack to know when the animation of the VectorDrawable is over
     */
    Animatable2.AnimationCallback animationBackupCallback;
    /**
     * The LevelList that contains only two AnimatedVectorDrawable,
     * the ones used to go from on to the other
     */
    LevelListDrawable backupRoundTrip;
    /**
     * The current AnimatedVector diaplsyed by the RoundTrip
     */
    AnimatedVectorDrawable currentBackupDrawable;

    /***********************************************************
     * Managing LifeCycle
     **********************************************************/
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llScreenRecorder= (LinearLayoutScreenRecorder) findViewById(R.id.llDrawableScreenRecorder);

        //managing the levelList to chain animations
        //----------------------------------------------
        animatedVectorListMaxLevel = 4;//can not be compute, you have to set it yourself!!!
        //define the animation call back
        animationCallback = new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                updateAnimVectorListLevel();
            }
        };
        //instantiate drawable and imageView
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        animatedVectorList = (LevelListDrawable) imageView1.getDrawable();
        currentAnimatedVectorFromList = (AnimatedVectorDrawable) animatedVectorList.getCurrent();
        //launch animation when the click is done on the imageView
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAnimVectorList();
            }
        });

        //managing the round trip scenario
        //--------------------------------
        //define the animation callback
        animationBackupCallback= new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                updateBackupDrawable();
            }
        };
        //instantiate drawable and imageView
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        backupRoundTrip = (LevelListDrawable) imageView2.getDrawable();
        currentBackupDrawable= (AnimatedVectorDrawable) backupRoundTrip.getCurrent();
        //launch animation when the click is done on the imageView
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAnimBackup();
            }
        });


        //managing simple animated vector drawable
        //------------------------------------------
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        animatedVector3 = (AnimatedVectorDrawable) imageView3.getDrawable();
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        animatedVector4 = (AnimatedVectorDrawable) imageView4.getDrawable();
        //set on click listener on them to launch animation
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAnim3();
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAnim4();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        llScreenRecorder.stopRecording();
    }
    /***********************************************************
     *  Managing LevelListDrawable to chain animations
     **********************************************************/

    /**
     * Launch the animation on the ImageView1
     * And update the level of the drawable
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void launchAnimVectorList() {
        // start recording the layout
        llScreenRecorder.startRecording();
        //the goal is to run the animation and when the animation is over
        // update the level of the currentAnimatedVectorFromList
        currentAnimatedVectorFromList.start();
        //then calculate when the animation is over
        //only works for level 23 (damn it)
        currentAnimatedVectorFromList.registerAnimationCallback(animationCallback);
        //if you want to be compatible with level 21: use handler and runnable :'(
    }

    /**
     * Increment the Level if you don't have reach your max
     * Update the currentAnimatedVectorFromList
     */
    private void updateAnimVectorListLevel() {
        llScreenRecorder.stopRecording();
        //first unregister the callback
        currentAnimatedVectorFromList.unregisterAnimationCallback(animationCallback);
        //update the level
        if (animatedVectorList.getLevel() < animatedVectorListMaxLevel) {
            //then increment
            animatedVectorList.setLevel(animatedVectorList.getLevel() + 1);
            currentAnimatedVectorFromList = (AnimatedVectorDrawable) animatedVectorList.getCurrent();
        } else {
            //go back to the beginning
            animatedVectorList.setLevel(0);
            currentAnimatedVectorFromList = (AnimatedVectorDrawable) animatedVectorList.getCurrent();
        }
        //then go back to its initial level (reset it state) else
        //it seems that reset doesn't reset also the stroke anf fill color :(
        //https://code.google.com/p/android/issues/detail?id=195999
        currentAnimatedVectorFromList.reset();
        launchAnimVectorList();
    }

    /***********************************************************
     *  Managing backup button round trip
     **********************************************************/
    /**
     * Launch the animation on the currentAnimatedVectorDrawable
     */
    private void launchAnimBackup(){
        //the goal is to run the animation and when the animation is over
        // update the level of the currentAnimatedVectorFromList
        currentBackupDrawable.start();
        //then calculate when the animation is over
        //only works for level 23 (damn it)
        currentBackupDrawable.registerAnimationCallback(animationBackupCallback);
        //if you want to be compatible with level 21: use handler and runnable :'(

    }

    private void updateBackupDrawable(){
        //first unregister the callback
        currentBackupDrawable.unregisterAnimationCallback(animationBackupCallback);
        //update the level
        if (backupRoundTrip.getLevel() ==1) {
            //then reverse
            backupRoundTrip.setLevel(0);
        } else {
            //then reverse
            backupRoundTrip.setLevel(1);
        }
        //find the current AnimatedVectorDrawable displayed
        currentBackupDrawable = (AnimatedVectorDrawable) backupRoundTrip.getCurrent();
        //then go back to its initial level (reset it state) else
        //it seems that reset doesn't reset also the stroke anf fill color :(
        //https://code.google.com/p/android/issues/detail?id=195999
        currentBackupDrawable.reset();
    }
    /***********************************************************
     * Launching simple animation on AnimatedVectorDrawable
     **********************************************************/
    /**
     * Launch the animation on the AnimatedVectorDrawable displayed by the imageView3
     */
    private void launchAnim3() {
        animatedVector3.start();
    }

    /**
     * Launch the animation on the AnimatedVectorDrawable displayed by the imageView4
     */
    private void launchAnim4() {
        animatedVector4.start();
    }

}
