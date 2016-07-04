package com.jahrud.kingdomdash;

import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import org.robovm.bindings.admob.*;

import com.badlogic.gdx.Gdx;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.uikit.UIScreen;

import java.util.Arrays;


public class IOSLauncher extends IOSApplication.Delegate implements OSLauncher{

    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-4350977853893984/9535912350";
    private static final String TAG = "IOSLauncher";

    private static final boolean USE_TEST_DEVICES = false;
    private boolean adsInitialized = false;
    private GADBannerView bannerView;

    private IOSApplication iosApplication;

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        iosApplication =  new IOSApplication(new GameEngine(this), config);
        return  iosApplication;
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }

    private void initializeAds() {
        if(adsInitialized) {
            Gdx.app.log(TAG, "Ads already initialized exiting early");
            return;
        }
        Gdx.app.log(TAG, "Initalizing ads...");
        adsInitialized = true;
        bannerView = createAndLoadBanner();
        Gdx.app.log(TAG, "Initalizing ads complete.");
    }

    private GADBannerView createAndLoadBanner(){
        Gdx.app.log(TAG, "Setting up banner ad");
        GADBannerView bannerView = new GADBannerView(GADAdSize.smartBannerPortrait());
        bannerView.setAdUnitID(BANNER_AD_UNIT_ID);
        bannerView.setRootViewController(iosApplication.getUIViewController());
        iosApplication.getUIViewController().getView().addSubview(bannerView);
        bannerView.loadRequest(createRequest());

        bannerView.setDelegate(new GADBannerViewDelegateAdapter() {
            @Override
            public void didReceiveAd(GADBannerView view) {
                super.didReceiveAd(view);
                Gdx.app.log(TAG, "didReceiveAd");
            }
            @Override
            public void didFailToReceiveAd(GADBannerView view, GADRequestError error) {
                super.didFailToReceiveAd(view, error);
                Gdx.app.log(TAG, "didFailToReceiveAd:" + error);
            }
        });

        return bannerView;
    }

    private GADRequest createRequest() {
        Gdx.app.log(TAG, "Create request for ad");
        GADRequest request = GADRequest.create();
        // To test on your devices, add their UDIDs here:
        if (USE_TEST_DEVICES) {
            request.setTestDevices(Arrays.asList(GADRequest.GAD_SIMULATOR_ID));
            Gdx.app.log(TAG, "Test devices: " + request.getTestDevices());
        }
        return request;
    }

    @Override
    public void showInterstitialAd() {

    }

    public void showBannerAd() {
        initializeAds();
        displayBannerAd(true);
    }

    public void hideBannerAd() {
        displayBannerAd(false);
    }

    void displayBannerAd(boolean show){

        final CGSize screenSize = UIScreen.getMainScreen().getBounds().getSize();
        double screenWidth = screenSize.getWidth();

        final CGSize adSize = bannerView.getBounds().getSize();
        double adWidth = adSize.getWidth();
        double adHeight = adSize.getHeight();

        Gdx.app.log(TAG, String.format((show ? "Showing" : "Hiding") + "ad. size[%s, %s]", adWidth, adHeight));

        float bannerWidth = (float) screenWidth;
        float bannerHeight = (float) (bannerWidth / adWidth * adHeight);

        if(show)
            bannerView.setFrame(new CGRect((screenWidth / 2) - adWidth / 2, 0, bannerWidth,  bannerHeight));
        else
            bannerView.setFrame(new CGRect(0, -bannerHeight, bannerWidth, bannerHeight));
    }
}