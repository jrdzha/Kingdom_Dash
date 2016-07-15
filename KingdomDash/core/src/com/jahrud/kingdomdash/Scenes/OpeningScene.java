package com.jahrud.kingdomdash.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by JahrudZ on 6/23/16.
 */
public class OpeningScene extends Scene{

    private Texture titleTexture;
    private Texture logoTexture;
    private Sprite titleSprite;
    private Sprite logoSprite;
    private SpriteBatch renderBatch;
    private int timesRendered;

    public OpeningScene(){
        IDENTIFIER = 0;
    }

    @Override
    public void init() {
        timesRendered = 0;

        renderBatch = new SpriteBatch();
        titleTexture = new Texture(Gdx.files.internal("OpeningScreen/TitleSprite.png"));
        logoTexture = new Texture(Gdx.files.internal("OpeningScreen/PotionSprite.png"));
        titleSprite = new Sprite(titleTexture);
        logoSprite = new Sprite(logoTexture);

        titleSprite.setScale((float)Gdx.graphics.getWidth() / titleSprite.getWidth() * .95f);
        logoSprite.setScale((float)Gdx.graphics.getHeight() / logoSprite.getHeight() * .4f);

        titleSprite.setPosition((Gdx.graphics.getWidth() / 2) - (titleSprite.getWidth() / 2), (Gdx.graphics.getHeight() / 5) - (titleSprite.getHeight() / 2));
        logoSprite.setPosition((Gdx.graphics.getWidth() / 2) - (logoSprite.getWidth() / 2), (Gdx.graphics.getHeight() * 3 / 5) - (logoSprite.getHeight() / 2));
    }

    @Override
    public int render() throws InterruptedException {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        renderBatch.begin();
        //renderBatch.draw(titleSprite, (Gdx.graphics.getWidth() / 2) - (titleSprite.getWidth() / 2), (Gdx.graphics.getHeight() / 4) - (titleSprite.getHeight() / 2));
        //renderBatch.draw(logoSprite, (Gdx.graphics.getWidth() / 2) - (logoSprite.getWidth() / 2), (Gdx.graphics.getHeight() * 2 / 3) - (logoSprite.getHeight() / 2));
        titleSprite.draw(renderBatch);
        logoSprite.draw(renderBatch);
        renderBatch.end();

        int nextScene;
        timesRendered++;
        if(timesRendered > 100){
            nextScene = 1;
            this.dispose();
            this.isRunning = false;
        } else {
            nextScene = IDENTIFIER;
        }
        return nextScene;
    }

    public void dispose(){
        renderBatch.dispose();
        titleTexture.dispose();
        logoTexture.dispose();
    }
}
