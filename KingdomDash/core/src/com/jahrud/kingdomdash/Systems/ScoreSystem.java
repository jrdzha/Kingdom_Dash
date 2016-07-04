package com.jahrud.kingdomdash.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.jahrud.kingdomdash.components.physics.PositionComponent;
import com.jahrud.kingdomdash.components.player.ScoreComponent;

/**
 * Created by JahrudZ on 5/27/16.
 */

public class ScoreSystem extends EntitySystem {

    public Entity player;
    ScoreComponent scoreComponent;
    Preferences preferences;

    public ScoreSystem(Entity player){
        this.player = player;
        preferences = Gdx.app.getPreferences("KingdomDashPreferences");
        scoreComponent = player.getComponent(ScoreComponent.class);
        scoreComponent.highscore = loadHighScore();
    }

    public void reset(){
        preferences = Gdx.app.getPreferences("KingdomDashPreferences");
        scoreComponent = player.getComponent(ScoreComponent.class);
        scoreComponent.highscore = loadHighScore();
    }

    public void addedToEngine(Engine engine){

    }

    public void update(float deltaTime){

        //System.out.println((int)(player.getComponent(PositionComponent.class).x / 16 / 12) + "   " + scoreComponent.startChunk);

        scoreComponent.score = (int)(player.getComponent(PositionComponent.class).x / 16 / 12) - scoreComponent.startChunk;
        if((int)(player.getComponent(PositionComponent.class).x / 16 / 12) < scoreComponent.startChunk || scoreComponent.startChunk == -1){
            scoreComponent.score = 0;
        }
        if(scoreComponent.score > scoreComponent.highscore){
            scoreComponent.highscore = scoreComponent.score;
            saveHighScore();
        }
    }

    public int loadHighScore(){
        return preferences.getInteger("Highscore", 0);
    }

    public void saveHighScore(){
        preferences.putInteger("Highscore", scoreComponent.highscore);
        preferences.flush();
    }

}
