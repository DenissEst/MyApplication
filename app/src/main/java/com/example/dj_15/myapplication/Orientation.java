package com.example.dj_15.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by Carlotta on 12/08/2017.
 */

public class Orientation{

    private Activity activity;
    public enum eScreenOrientation
    {
        PORTRAIT (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT),
        LANDSCAPE (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE),
        PORTRAIT_REVERSE (ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT),
        LANDSCAPE_REVERSE (ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE),
        UNSPECIFIED_ORIENTATION (ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        public final int activityInfoValue;

        eScreenOrientation ( int orientation )
        {
            activityInfoValue = orientation;
        }
    }

    public Orientation(Activity activity){
        this.activity = activity;
    }

    public eScreenOrientation getCurrentOrientation(){
        final int rotation = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();

        final int orientation = activity.getResources().getConfiguration().orientation;

        switch(orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                if ( rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90 )
                    return eScreenOrientation.PORTRAIT;
                else
                    return eScreenOrientation.PORTRAIT_REVERSE;
            case Configuration.ORIENTATION_LANDSCAPE:
                if ( rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90 )
                    return eScreenOrientation.LANDSCAPE;
                else
                    return eScreenOrientation.LANDSCAPE_REVERSE;
            default:
                return eScreenOrientation.UNSPECIFIED_ORIENTATION;
        }
    }
}
