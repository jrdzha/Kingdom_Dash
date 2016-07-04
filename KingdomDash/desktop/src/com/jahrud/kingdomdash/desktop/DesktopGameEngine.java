package com.jahrud.kingdomdash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jahrud.kingdomdash.GameEngine;
import com.jahrud.kingdomdash.OSLauncher;

/**
 * Created by JahrudZ on 6/16/16.
 */
public class DesktopGameEngine implements OSLauncher{
    public DesktopGameEngine(LwjglApplicationConfiguration config){
        new LwjglApplication(new GameEngine(this), config);
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
