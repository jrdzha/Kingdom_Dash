package com.jahrud.kingdomdash;

import com.badlogic.gdx.ApplicationAdapter;
import com.jahrud.kingdomdash.Scenes.KingdomDashScene;
import com.jahrud.kingdomdash.Scenes.OpeningScene;
import com.jahrud.kingdomdash.Scenes.Scene;

import java.util.ArrayList;

public class GameEngine extends ApplicationAdapter {

    ArrayList<Scene> scenes;

    private int currentScene;

    private AdViewer adViewer;

    public GameEngine(AdViewer platform){
        this.adViewer = platform;
    }

    @Override
    public void create () {
        currentScene = 0;
        scenes = new ArrayList<Scene>();
        scenes.add(new OpeningScene());
        scenes.add(new KingdomDashScene(adViewer));
    }

    @Override
    public void render () {
        for(Scene scene : scenes){
            if(currentScene == scene.IDENTIFIER){
                if(!scene.isRunning){
                    scene.init();
                    scene.isRunning = true;
                }
                try {
                    currentScene = scene.render();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
