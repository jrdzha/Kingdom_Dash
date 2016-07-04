package com.jahrud.kingdomdash;

import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.backends.iosmoe.IOSApplicationConfiguration;
import com.intel.moe.natj.general.Pointer;
import com.jahrud.kingdomdash.GameEngine;

import ios.foundation.NSAutoreleasePool;
import ios.uikit.c.UIKit;

public class IOSMoeLauncher extends IOSApplication.Delegate implements OSLauncher{

    protected IOSMoeLauncher(Pointer peer) {
        super(peer);
    }

    @Override
    protected IOSApplication createApplication() {
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.useAccelerometer = false;
        return new IOSApplication(new GameEngine(this), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = NSAutoreleasePool.alloc();
        UIKit.UIApplicationMain(0, null, null, IOSMoeLauncher.class.getName());
        pool.dealloc();
    }

    @Override
    public void showInterstitialAd() {

    }

    @Override
    public void showBannerAd() {

    }

    @Override
    public void hideBannerAd() {

    }
}