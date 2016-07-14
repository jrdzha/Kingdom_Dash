package com.jahrud.kingdomdash;

/**
 * Created by JahrudZ on 7/14/16.
 */

import android.app.Activity;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;

public class AndroidAdViewer implements AdViewer{

    Activity activity;
    private boolean didCompleteVideo;

    public AndroidAdViewer(Activity activity) {
        this.activity = activity;
        Chartboost.startWithAppId(activity, "5783affb04b0161cf4aac4ca", "538b980bb5e122521eafdaafa727168dd11b9933");
        Chartboost.setDelegate(new ChartboostDelegate() {
            @Override
            public void didCacheRewardedVideo(String location) {
                showVideoAd();
            }

            @Override
            public void didCompleteRewardedVideo(String location, int reward) {
                didCompleteVideo = true;
            }
        });
        Chartboost.onCreate(activity);
    }

    @Override
    public void loadVideoAd() {
        Chartboost.cacheRewardedVideo(CBLocation.LOCATION_GAMEOVER);
    }

    @Override
    public void showVideoAd() {
        Chartboost.showRewardedVideo(CBLocation.LOCATION_DEFAULT);
    }

    @Override
    public boolean shouldReward() {
        return didCompleteVideo;
    }

    @Override
    public void resetReward() {
        didCompleteVideo = false;
    }

    public void onStart(){
        Chartboost.onStart(activity);
    }

    public void onResume(){
        Chartboost.onPause(activity);
    }

    public void onPause(){
        Chartboost.onPause(activity);
    }

    public void onStop(){
        Chartboost.onStop(activity);
    }

    public void onDestroy(){
        Chartboost.onDestroy(activity);
    }

    public boolean onBackPressed(){
        return Chartboost.onBackPressed();
    }
}
