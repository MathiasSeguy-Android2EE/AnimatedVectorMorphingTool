package com.android2ee.tool.animatedvector.morphing;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * This class aims to show you the code to use to animated your AnimatedVectorDrawables
 * enjoy
 * (by the way the bug id is https://code.google.com/p/android/issues/detail?id=195999)
 */
public class MainActivity extends AppCompatActivity {
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
     * The handler to automaticly launch the next animation
     */
    Handler uiHandler;
    /**
     * The Runnable that launches the next animation
     */
    Runnable uiRunnable;
    /**
     * To know is the animation have been already launched
     */
    boolean animatedVectorFirstLaunched=true;
    /***********************************************************
     * Managing RoundTrip animation (VectorDrawable1 to VectorDrawable 2 and back again
     **********************************************************
    /**
     * The LevelList that contains only two AnimatedVectorDrawable,
     * the ones used to go from on to the other
     */
    LevelListDrawable backupRoundTrip;
    /**
     * The current AnimatedVector diaplsyed by the RoundTrip
     */
    AnimatedVectorDrawable currentBackupDrawable;
    /**
     * To know is the animation have been already launched
     */
    boolean backupRoundTripFirstLaunched=true;

    /***********************************************************
     * Managing LifeCycle
     **********************************************************/
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //managing the levelList to chain animations
        //----------------------------------------------
        animatedVectorListMaxLevel = 4;//TODO can not be compute, you have to set it yourself!!!
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
        });uiRunnable=new Runnable() {
            @Override
            public void run() {
                launchAnimVectorList();
            }
        };
        uiHandler=new Handler();

        //managing the round trip scenario
        //--------------------------------
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
        //and insure your remove every runnable from the handler (memory leak else)
        uiHandler.removeCallbacks(uiRunnable);
//        llScreenRecorder.stopRecording();
    }

    /***********************************************************
     *  Managing LevelListDrawable to chain animations
     **********************************************************/

    /**
     * Launch the animation on the ImageView1
     * And update the level of the drawable
     */
    private void launchAnimVectorList() {
        if(!animatedVectorFirstLaunched) {
            if (animatedVectorList.getLevel() < animatedVectorListMaxLevel) {
                //then increment
                animatedVectorList.setLevel(animatedVectorList.getLevel() + 1);
                currentAnimatedVectorFromList = (AnimatedVectorDrawable) animatedVectorList.getCurrent();
            } else {
                //go back to the beginning
                animatedVectorList.setLevel(0);
                currentAnimatedVectorFromList = (AnimatedVectorDrawable) animatedVectorList.getCurrent();
            }
        }else {
            animatedVectorFirstLaunched=false;
        }
        //start the animation on the current element
        currentAnimatedVectorFromList.start();
        //launch it again in 300 ms + the time your animation take
        uiHandler.postDelayed(uiRunnable,300+3000);//TODO instead of 3000 set your animation duration !!! 
    }

  /***********************************************************
     *  Managing backup button round trip
     **********************************************************/
    /**
     * Launch the animation on the currentAnimatedVectorDrawable
     * This solution used LevelList but there is a bug that occurs sometime
     * Bug: The second animation is not played after 2 times it runs
     * I mean you run it twice, the third times it doesn't animate anything...
     * I hope the bug will be fixed one day
     * So I changed the way to do the stuff, rename this method and set it as deprecated
     */
    @Deprecated
    private void launchAnimBackupOld() {
        if (!backupRoundTripFirstLaunched) {
            currentBackupDrawable.stop();

            if (backupRoundTrip.getLevel() == 1) {
                //then reverse
                backupRoundTrip.setLevel(0);
            } else {
                //then reverse
                backupRoundTrip.setLevel(1);
            }
        } else {
            backupRoundTripFirstLaunched = false;
        }
        //find the current AnimatedVectorDrawable displayed
        currentBackupDrawable = (AnimatedVectorDrawable) backupRoundTrip.getCurrent();
        //start the animation
        currentBackupDrawable.start();
    }

    /**
     * The reverse and the initial AnimatedDrawable
     */
    AnimatedVectorDrawable reverse, initial;
    /**
     * To know which direction is used (reverse or not)
     */
    boolean reverseState = false;

    /**
     * Launch the animation on the currentAnimatedVectorDrawable
     * As the levellist solution (ahead) has a bug, I do it in a simple way
     */
    private void launchAnimBackup() {
        //initialize
        if (backupRoundTripFirstLaunched) {
            reverse = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_ic_check_box_black_24dp_reverse, getTheme());
            initial = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_ic_check_box_black_24dp, getTheme());
            //if you want to avoid the color bug (animation not played on color at the second time)
            //comment that line (ok it's memory consumption)
            backupRoundTripFirstLaunched=false;
        }
        //set the drawable depending on the direction (reverse or not)
        if (reverseState) {
            //then reverse
            imageView2.setImageDrawable(reverse);
        } else {
            //then reverse
            imageView2.setImageDrawable(initial);
        }
        reverseState=!reverseState;
        //launch the animation
        ((AnimatedVectorDrawable) imageView2.getDrawable()).start();
    }    /***********************************************************
     * Launching simple animation on AnimatedVectorDrawable
     **********************************************************/
  /**
     * Launch the animation on the AnimatedVectorDrawable displayed by the imageView3
     */
    private void launchAnim3() {
//            llScreenRecorder.startRecording();
        animatedVector3.start();
    }

    /**
     * Launch the animation on the AnimatedVectorDrawable displayed by the imageView4
     */
    private void launchAnim4() {
        animatedVector4.start();
    }

}