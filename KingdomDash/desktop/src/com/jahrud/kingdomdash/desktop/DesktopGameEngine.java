package com.jahrud.kingdomdash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jahrud.kingdomdash.GameEngine;
import com.jahrud.kingdomdash.AdViewer;

/**
 * Created by JahrudZ on 6/16/16.
 */
public class DesktopGameEngine implements AdViewer{
    public DesktopGameEngine(LwjglApplicationConfiguration config){
        new LwjglApplication(new GameEngine(this), config);
    }

    @Override
    public void loadVideoAd() {

    }

    @Override
    public void showVideoAd() {

    }

    @Override
    public boolean shouldReward() {
        return false;
    }

    @Override
    public void resetReward() {

    }
}
