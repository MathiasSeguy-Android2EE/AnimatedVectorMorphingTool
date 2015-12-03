/**
 * <ul>
 * <li>LinearLayoutScreenCapture</li>
 * <li>com.android2ee.droidcon.greece.animation.customviews</li>
 * <li>16/07/2015</li>
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

package com.android2ee.tool.animatedvector.morphing;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Mathias Seguy - Android2EE on 16/07/2015.
 * This LinearLayout is a LinearLayout that can take screen capture at the rate you want
 * and store those captures in your pictures public directory.
 * The name of the files saved on the disk is the tag of the linearLayout defined in your xml
 * with the suffix of the number of the screen capture
 */
public class LinearLayoutScreenRecorder extends LinearLayout {
    /**The layout width and heigth*/
    int layoutW, layoutH;
    /** The screen capture is active*/
    boolean screenCaptureOn = false;
    /** The canvas to draw within to generate the bitmap*/
    Canvas mBitmapCanvas;
    /** The bitmap handling the screen capture*/
    Bitmap mCurrentBitmap;
    /** The root folder to store the picture inside*/
    File mRootFolder = null;
    /**Paint used to draw the background     */
    Paint paint;

    /***********************************************************
     * Constructors
     **********************************************************/
    public LinearLayoutScreenRecorder(Context context) {
        super(context);
        initiliazePaint();
    }
    public LinearLayoutScreenRecorder(Context context, AttributeSet attrs) {
        super(context, attrs);
        initiliazePaint();
    }
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public LinearLayoutScreenRecorder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initiliazePaint();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LinearLayoutScreenRecorder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initiliazePaint();
    }
    /***
     * Initiliaze the Paint
     */
    private void initiliazePaint() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
    }
    /***********************************************************
     * The magic method that tells us we know the size of the layout
     **********************************************************/
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //so here are our size
        layoutH = h;
        layoutW = w;
    }

    /***********************************************************
     * The public methods start and stop recording
     * And others Parameters
     **********************************************************/

    /**
     * Start recording the screen
     */
    public void startRecording() {
        //only do it in debug mode
        String externalStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
            // We can read and write the media
            if(BuildConfig.DEBUG) {
                screenCaptureOn = true;
                recorderHandler.postDelayed(recorderRunnable, 32);
            }
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalStorageState)) {
            // We can only read the media
            Log.e("LinearLayoutScreenRec", "sdCard ko read only "+externalStorageState);
        }else{
            Log.e("LinearLayoutScreenRec", "sdCard ko no access "+externalStorageState);
        }
    }

    /**
     * Stop recording the screen
     * Insure it has been called when the onPause method has finished
     */
    public void stopRecording() {
        //only do it in debug mode
        if(BuildConfig.DEBUG) {
            screenCaptureOn = false;
            recorderHandler.removeCallbacks(recorderRunnable);
        }
    }
    /**
     * To define the number of frame you want to skip
     * There is a frame each 16ms to reach 60 fps
     * @param skipFrames
     */
    public void setSkipFrames(int skipFrames) {
        this.skipFrames = skipFrames;
    }

    /**
     * Define the max number of screen recorded
     * @param maxScreenRecord
     */
    public void setMaxScreenRecord(int maxScreenRecord) {
        this.maxScreenRecord = maxScreenRecord;
    }
    /**
     * Define the name of the file on the disk you have to use the tag attribute
     * @return The name of the file on the disk
     */
    private String getName() {
        if (mName == null) {
            if (getTag() != null && getTag() instanceof String) {
                mName = (String) getTag() ;
            } else {
                mName = "NoTagSet" ;
            }
        }
        return mName;
    }


//There is 60 frames by second
    //so skip frames keeping that in mind
    /** Number of frames to skip */
    int skipFrames = 6;
    /**Number of skipped frames */
    int skippedFrames = 0;
    /**Number of recorded screen capture*/
    int recordedFrameNum = 0;
    /** The name of the screen capture on the disk     */
    String mName = null;
    /**Mas number of screen records */
    int maxScreenRecord=90;//30
    /** Use to track the time of some operations*/
    long debut,fin;
    @Override
    public void draw(Canvas canvas) {
            if(canvas!=null){
                super.draw(canvas);
            }
            if(screenCaptureOn) {
                skippedFrames++;
                if (skippedFrames == skipFrames) {
//                    debut= System.currentTimeMillis();
//                    Log.e("LinearLayoutScreenRec", "onDraw screenCaptureOn skippedFrames" + skippedFrames);
                    mCurrentBitmap = Bitmap.createBitmap(layoutW, layoutH, Bitmap.Config.ARGB_8888);
                    mBitmapCanvas = new Canvas(mCurrentBitmap);
                    mBitmapCanvas.drawRect(0, 0, layoutW, layoutH, paint);
                    super.draw(mBitmapCanvas);
                    recordedFrameNum++;
                    new Thread(new saveOnDiskRunnable(mCurrentBitmap, recordedFrameNum)).start();
//                    fin = System.currentTimeMillis();
                    //using this method take -1091milli
//                    Log.e("LinearLayoutScreenRec", "saveBitmap finished in  " + (debut - fin));
                    skippedFrames = 0;
                }

            }
    }

    /***********************************************************
     *  The Handler and the Runnable to launch a screen capture
     **********************************************************/
    /**
     * The Handler to run the recorderRunnable in the UI thread
     */
    Handler recorderHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //call draw
            draw(null);
            if(maxScreenRecord>recordedFrameNum){
                recorderHandler.postDelayed(recorderRunnable,16);
            }
        }
    };
    /**
     * The Runnbale to record the screen
     */
    Runnable recorderRunnable =new Runnable() {
        @Override
        public void run() {
            recorderHandler.sendEmptyMessage(0);
        }
    };

    /***********************************************************
     * Saving the Bitmap as a file on the disk
     **********************************************************/
    /**
     * The runnnable that saves a Bitmap on the disk as a file
     */
    public class saveOnDiskRunnable implements Runnable{

        /**The bitmap to save*/
        Bitmap currentBitmap;
        /**The number of the bitmap to define the file's name*/
        int frameNum;

        /**
         * Constructor
         * @param currentBitmap The bitmap to save
         * @param frameNum The number of the bitmap to define the file's name
         */
        public saveOnDiskRunnable(Bitmap currentBitmap, int frameNum) {
            this.currentBitmap = currentBitmap;
            this.frameNum = frameNum;
        }

        @Override
        public void run() {
            //so save the bitmap
            saveBitmap(currentBitmap,frameNum);
        }
    }

    /**
     * Saving a bitmap
     * @param currentBitmap The bitmap to save
     * @param frameNum The number of the bitmap to define the file's name
     */
    private void saveBitmap(Bitmap currentBitmap,int frameNum) {
        if (mRootFolder == null) {
            //Save the picture
            //--------------------------
            //Find the external storage directory
            File filesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            //Retrieve the name of the subfolder where your store your picture
            //(You have set it in your string ressources)
            String pictureFolderName = getContext().getString(R.string.app_name);
            //then create the subfolder
            mRootFolder = new File(filesDir, pictureFolderName);

            //Check if this subfolder exists
            if (!mRootFolder.exists()) {
                //if it doesn't create it
                mRootFolder.mkdirs();
            }
            //tell to the media scanner to had this folder in its scan
            //(can be optimized...)
            MediaScannerConnection.scanFile(getContext(), new String[]{mRootFolder.getAbsolutePath()}, null, null);
        }
        try {
            //Define the file to store your picture in
            File filePicture = new File(mRootFolder, getName()+frameNum+ ".png");//TODO +frameNumber
            filePicture.setWritable(true);
            //Open an OutputStream on that file
            FileOutputStream fos = new FileOutputStream(filePicture);
            //Write in that stream your bitmap in png with the max quality (100 is max, 0 is min quality)
            currentBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            //The close properly your stream
            fos.flush();
            fos.close();
            //then recycle the bitmap
            currentBitmap.recycle();
            //and again tell the mediascanner to add this file
//            MediaScannerConnection.scanFile(getContext(), new String[]{filePicture.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
//                @Override
//                public void onScanCompleted(String path, Uri uri) {
//                    Log.d("LinearLayoutScreenRec", "Scan done for the path : " + path);
//                }
//            });
            MediaScannerConnection.scanFile(getContext(), new String[]{filePicture.getAbsolutePath()}, null,null);
            Log.d("LinearLayoutScreenRec", "The file has been recorded as " + filePicture.getName() + " in " + mRootFolder);

        } catch (FileNotFoundException e) {
            //Ok, I should have managed my exception, but this only for debug
            Log.e("LinearLayoutScreenRec", "Fuck occurs FileNotFoundException", e);
        } catch (IOException e) {
            //Ok, I should have managed my exception, but this only for debug
            Log.e("LinearLayoutScreenRec", "Fuck occurs IOException", e);
        }
    }
}
