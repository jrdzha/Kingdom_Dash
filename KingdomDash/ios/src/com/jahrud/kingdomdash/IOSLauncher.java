package com.jahrud.kingdomdash;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.pods.chartboost.CBLoadError;
import org.robovm.pods.chartboost.CBLocation;
import org.robovm.pods.chartboost.Chartboost;
import org.robovm.pods.chartboost.ChartboostDelegateAdapter;


public class IOSLauncher extends IOSApplication.Delegate implements AdViewer{

    private IOSApplication iosApplication;
    private boolean didCompleteVideo;

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        iosApplication =  new IOSApplication(new GameEngine(this), config);
        return  iosApplication;
    }

    @Override
    public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions options){
        Chartboost.start("5783affb04b0161cf4aac4c8", "3feb11f3f4fb0612f3ddf29e61efc806570bd3bc", new ChartboostDelegateAdapter(){
            @Override
            public void didCompleteRewardedVideo(String location, int reward){
                didCompleteVideo = true;
            }

            @Override
            public void didCacheRewardedVideo(String location){
                showVideoAd();
            }

            @Override
            public void didFailToLoadRewardedVideo(String location, CBLoadError error){
                switch (error) {
                    case InternetUnavailable:
                        System.out.println("Failed to load Video, no Internet connection !");
                        break;
                    case Internal:
                        System.out.println("Failed to load Video, internal error !");
                        break;
                    case NetworkFailure:
                        System.out.println("Failed to load Video, network error !");
                        break;
                    case WrongOrientation:
                        System.out.println("Failed to load Video, wrong orientation !");
                        break;
                    case TooManyConnections:
                        System.out.println("Failed to load Video, too many connections !");
                        break;
                    case FirstSessionInterstitialsDisabled:
                        System.out.println("Failed to load Video, first session !");
                        break;
                    case NoAdFound:
                        System.out.println("Failed to load Video, no ad found !");
                        break;
                    case SessionNotStarted:
                        System.out.println("Failed to load Video, session not started !");
                        break;
                    case NoLocationFound:
                        System.out.println("Failed to load Video, missing location parameter !");
                        break;
                    default:
                        System.out.println("Failed to load Video, unknown error !");
                        break;
                }
            }
        });
        return super.didFinishLaunching(application, options);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    @Override
    public void loadVideoAd() {
        Chartboost.cacheRewardedVideo(CBLocation.Default);
    }

    @Override
    public void showVideoAd() {
        Chartboost.showRewardedVideo(CBLocation.Default);
    }

    @Override
    public boolean shouldReward() {
        return didCompleteVideo;
    }

    @Override
    public void resetReward(){
        didCompleteVideo = false;
    }
}