package com.jahrud.kingdomdash.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.jahrud.kingdomdash.components.RemoveTagComponent;
import com.jahrud.kingdomdash.components.UI.ScreenPositionComponent;
import com.jahrud.kingdomdash.components.UI.UIComponent;
import com.jahrud.kingdomdash.components.physics.PositionComponent;
import com.jahrud.kingdomdash.components.player.CameraComponent;
import com.jahrud.kingdomdash.components.player.ScoreComponent;
import com.jahrud.kingdomdash.components.render.AnimationComponent;
import com.jahrud.kingdomdash.components.render.RenderableComponent;
import com.jahrud.kingdomdash.components.render.SpriteComponent;
import com.jahrud.kingdomdash.components.render.layer.EntityLayerComponent;
import com.jahrud.kingdomdash.components.render.layer.LayerOneComponent;
import com.jahrud.kingdomdash.components.render.layer.LayerThreeComponent;
import com.jahrud.kingdomdash.components.render.layer.LayerTwoComponent;

public class RenderSystem extends EntitySystem {

    public int scene;
    SpriteBatch batch, UIBatch;
    public Entity player;
    BitmapFont font;
    Box2DDebugRenderer debugRenderer;
    Sprite currentSprite;
    FreeTypeFontGenerator fontGenerator;
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter;
    BitmapFont bigFont, smallFont;
    GlyphLayout bigLayout, smallLayout;
    int lastScore;
    int i;
    float totalTime;
    int FPS;
    float scale;
    private ImmutableArray<Entity> entities;
    private ImmutableArray<Entity> layerOne;
    private ImmutableArray<Entity> layerTwo;
    private ImmutableArray<Entity> layerThree;
    private ImmutableArray<Entity> UI;
    private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private float[] currentRGB = {.7f, 0.90f, .85f};
    private float[] goalRGB = {.7f, 0.90f, .85f};
    private float[] defaultRGB = {.7f, 0.90f, .85f};
    private float[][] colorPalette =    {{.7f, 0.90f, .85f},
            {1.0f, .745f, .745f},
            {1.0f, 0.8f, 0.6f},
            {0.7f, 0.9f, 0.7f},
            {0.55f, 0.9f, 0.8f},
            {0.7f, 0.85f, 0.95f},
            {0.6f, 0.8f, 1.0f},
            {.745f, .745f, 1.0f},
            {0.9f, .745f, 0.9f}};
    //private int changeColors = 0;

    public RenderSystem(Entity player, float scale){

        debugRenderer = new Box2DDebugRenderer();

        batch = new SpriteBatch();
        this.player = player;
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        UIBatch = new SpriteBatch();

        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("slkscrb.ttf"));
        fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        fontParameter.color = Color.WHITE;
        fontParameter.size = Gdx.graphics.getHeight() / 14;
        bigFont = fontGenerator.generateFont(fontParameter);
        bigLayout = new GlyphLayout();

        fontParameter.size = Gdx.graphics.getHeight() / 35;
        smallFont = fontGenerator.generateFont(fontParameter);
        smallLayout = new GlyphLayout();

        lastScore = -1;

        this.scale = scale;

        i = 0;
        totalTime = 0;
        FPS = 0;

        scene = 0;
    }

    public void reset() {

    }

    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(AnimationComponent.class, SpriteComponent.class, PositionComponent.class, EntityLayerComponent.class, RenderableComponent.class).get());
        layerOne = engine.getEntitiesFor(Family.all(AnimationComponent.class, SpriteComponent.class, PositionComponent.class, LayerOneComponent.class, RenderableComponent.class).get());
        layerTwo = engine.getEntitiesFor(Family.all(AnimationComponent.class, SpriteComponent.class, PositionComponent.class, LayerTwoComponent.class, RenderableComponent.class).get());
        layerThree = engine.getEntitiesFor(Family.all(AnimationComponent.class, SpriteComponent.class, PositionComponent.class, LayerThreeComponent.class, RenderableComponent.class).get());
        UI = engine.getEntitiesFor(Family.all(AnimationComponent.class, SpriteComponent.class, UIComponent.class, ScreenPositionComponent.class, RenderableComponent.class).get());
    }

    public void update(float deltaTime){
        updateRGB();
        Gdx.gl.glClearColor(currentRGB[0], currentRGB[1], currentRGB[2], 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.getComponent(CameraComponent.class).camera.update();
        batch.setProjectionMatrix(player.getComponent(CameraComponent.class).camera.projection);

        batch.begin();

        this.render(layerThree);
        this.render(layerTwo);
        this.render(layerOne);
        this.render(entities);

        batch.end();

        UIBatch.begin();

        if(scene == 0) {
            goalRGB = defaultRGB;
            bigLayout.setText(bigFont, "Kingdom");
            bigFont.draw(UIBatch, "Kingdom", (Gdx.graphics.getWidth() / 2) - (bigLayout.width / 2), (Gdx.graphics.getHeight() * 17 / 20));

            bigLayout.setText(bigFont, "Dash");
            bigFont.draw(UIBatch, "Dash", (Gdx.graphics.getWidth() / 2) - (bigLayout.width / 2), (Gdx.graphics.getHeight() * 16 / 20));

            smallLayout.setText(smallFont, "Tap to Play");
            smallFont.draw(UIBatch, "Tap to Play", (Gdx.graphics.getWidth() / 2) - (smallLayout.width / 2), (Gdx.graphics.getHeight() / 10));
        } else if(scene == 1) {
            if(lastScore != player.getComponent(ScoreComponent.class).score) {
                float[] newRGB = chooseRGB();
                while(goalRGB == newRGB) {
                    newRGB = chooseRGB();
                }
                goalRGB = newRGB;
            }
            lastScore = player.getComponent(ScoreComponent.class).score;

            bigLayout.setText(bigFont, "" + player.getComponent(ScoreComponent.class).score);
            bigFont.draw(UIBatch, "" + player.getComponent(ScoreComponent.class).score, (Gdx.graphics.getWidth() / 2) - (bigLayout.width / 2), (Gdx.graphics.getHeight() * 13 / 16));
        } else if(scene == 2) {
            goalRGB = defaultRGB;
            bigLayout.setText(bigFont, "Game Over");
            bigFont.draw(UIBatch, "Game Over", (Gdx.graphics.getWidth() / 2) - (bigLayout.width / 2), (Gdx.graphics.getHeight() * 14 / 16));

            smallLayout.setText(smallFont, "Highscore: " + player.getComponent(ScoreComponent.class).highscore);
            smallFont.draw(UIBatch, "Highscore: " + player.getComponent(ScoreComponent.class).highscore, (Gdx.graphics.getWidth() / 2) - (smallLayout.width / 2), (Gdx.graphics.getHeight() * 13 / 16));

            smallLayout.setText(smallFont, "Score: " + player.getComponent(ScoreComponent.class).score);
            smallFont.draw(UIBatch, "Score: " + player.getComponent(ScoreComponent.class).score, (Gdx.graphics.getWidth() / 2) - (smallLayout.width / 2), (Gdx.graphics.getHeight() * 25 / 32));

            smallLayout.setText(smallFont, "Tap to Play Again");
            smallFont.draw(UIBatch, "Tap to Play Again", (Gdx.graphics.getWidth() / 2) - (smallLayout.width / 2), (Gdx.graphics.getHeight() / 8));
        }

        this.renderUI(UI);

        UIBatch.end();

        /*
        if(i < 30){
            i++;
            totalTime += deltaTime;
        } else {
            FPS = (int)(i / totalTime);
            i = 0;
            totalTime = 0;
            //System.out.println(FPS);
        }

        UIBatch.begin();
        font.draw(UIBatch, "FPS: " + FPS, 10, 20);
        UIBatch.end();
        */

    }

    public void renderUI(ImmutableArray<Entity> UI){
        for(int i = 0; i < UI.size(); ++i){
            Entity entity = UI.get(i);

            if(shouldRender(entity)) {
                SpriteComponent sprite = sm.get(entity);
                AnimationComponent animation = am.get(entity);
                if (animation.framesDisplayed != -1) {
                    if (0 > animation.animationTimeList.get(animation.currentFrame[0]).get(animation.currentFrame[1])) {
                        currentSprite = sprite.spriteList.get(animation.currentFrame[0]).get(animation.currentFrame[1]);
                    } else if (animation.framesDisplayed <= animation.animationTimeList.get(animation.currentFrame[0]).get(animation.currentFrame[1])) {
                        currentSprite = sprite.spriteList.get(animation.currentFrame[0]).get(animation.currentFrame[1]);
                        animation.framesDisplayed++;
                    } else {
                        animation.framesDisplayed = 0;
                        if (animation.currentFrame[1] < animation.animationTimeList.get(animation.currentFrame[0]).size() - 1) {
                            animation.currentFrame[1]++;
                        } else {
                            animation.currentFrame[1] = 0;
                        }
                        currentSprite = sprite.spriteList.get(animation.currentFrame[0]).get(animation.currentFrame[1]);
                    }
                    currentSprite.setPosition((entity.getComponent(ScreenPositionComponent.class).x - (currentSprite.getWidth() / 2)), (entity.getComponent(ScreenPositionComponent.class).y - (currentSprite.getHeight() / 2)));
                    currentSprite.draw(UIBatch);
                }
            }
        }
    }

    public void render(ImmutableArray<Entity> renderLayer){
        for(int i = 0; i < renderLayer.size(); ++i) {
            Entity entity = renderLayer.get(i);
            PositionComponent position = pm.get(entity);
            SpriteComponent sprite = sm.get(entity);
            AnimationComponent animation = am.get(entity);
            if (animation.framesDisplayed != -1) {
                if (0 > animation.animationTimeList.get(animation.currentFrame[0]).get(animation.currentFrame[1])) {
                    currentSprite = sprite.spriteList.get(animation.currentFrame[0]).get(animation.currentFrame[1]);
                } else if (animation.framesDisplayed <= animation.animationTimeList.get(animation.currentFrame[0]).get(animation.currentFrame[1])) {
                    currentSprite = sprite.spriteList.get(animation.currentFrame[0]).get(animation.currentFrame[1]);
                    animation.framesDisplayed++;
                } else {
                    animation.framesDisplayed = 0;
                    if (animation.currentFrame[1] < animation.animationTimeList.get(animation.currentFrame[0]).size() - 1) {
                        animation.currentFrame[1]++;
                    } else {
                        animation.currentFrame[1] = 0;
                    }
                    currentSprite = sprite.spriteList.get(animation.currentFrame[0]).get(animation.currentFrame[1]);
                }
                currentSprite.setPosition((position.x - player.getComponent(CameraComponent.class).camera.position.x - (currentSprite.getWidth() / 2)), (position.y - player.getComponent(CameraComponent.class).camera.position.y - (currentSprite.getHeight() / 2)));
                //currentSprite.setScale(1.01f);
                currentSprite.draw(batch);
            } else {
                entity.add(new RemoveTagComponent());
            }
        }
    }

    public boolean shouldRender(Entity UI){
        for(Integer i : UI.getComponent(UIComponent.class).screen){
            if(i.equals(new Integer(scene))) return true;
        }
        return false;
    }

    public void updateRGB(){
        for(int i = 0; i < currentRGB.length; i++){
            if(currentRGB[i] != goalRGB[i]){
                currentRGB[i] += (goalRGB[i] - currentRGB[i]) / 20;
            }
        }
    }

    public float[] chooseRGB(){
        int next = (int)(Math.random() * colorPalette.length - .0001);
        //System.out.println(next);
        return colorPalette[next];

        /*
        int next = (changeColors / 300) % colorPalette.length;
        System.out.println(next);
        changeColors++;
        return  colorPalette[next];
        */
    }
}
