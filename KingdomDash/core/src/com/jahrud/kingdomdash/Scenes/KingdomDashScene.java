package com.jahrud.kingdomdash.Scenes;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jahrud.kingdomdash.AdViewer;
import com.jahrud.kingdomdash.Factories.AnimationFactory;
import com.jahrud.kingdomdash.Factories.ChunkFactory;
import com.jahrud.kingdomdash.Factories.EntityFactory;
import com.jahrud.kingdomdash.Factories.TileFactory;
import com.jahrud.kingdomdash.Systems.*;
import com.jahrud.kingdomdash.components.UI.ScreenPositionComponent;
import com.jahrud.kingdomdash.components.UI.SizeComponent;
import com.jahrud.kingdomdash.components.UI.UIComponent;
import com.jahrud.kingdomdash.components.map.Chunk;
import com.jahrud.kingdomdash.components.map.MapComponent;
import com.jahrud.kingdomdash.components.physics.*;
import com.jahrud.kingdomdash.components.player.IsDeadComponent;
import com.jahrud.kingdomdash.components.render.AnimationComponent;
import com.jahrud.kingdomdash.components.render.RenderableComponent;
import com.jahrud.kingdomdash.components.render.SpriteComponent;

import java.util.ArrayList;

public class KingdomDashScene extends Scene{

    Engine ashleyEngine;

    EntityFactory entityFactory;
    AnimationFactory animFactory;
    TileFactory tileFactory;
    ChunkFactory chunkFactory;

    Entity player;
    Entity physicsEngine;
    Entity map;
    Utils Box2dUtils;
    float physicsScale;

    PhysicsSystem physicsSystem;
    AnimationManagerSystem animationManagerSystem;
    CameraSystem cameraSystem;
    EndGameSystem endGameSystem;
    RenderSystem renderSystem;
    MapSystem mapSystem;
    DisposalSystem disposalSystem;
    ScoreSystem scoreSystem;

    int currentScreen;

    Audio audio;
    Music music;

    private AdViewer adViewer;

    public KingdomDashScene(AdViewer platform){
        IDENTIFIER = 1;
        this.adViewer = platform;

        currentScreen = 0;

        //init Physics Engine
        physicsScale = 16;
        entityFactory = new EntityFactory();
        physicsEngine = entityFactory.createPhysicsEngine(physicsScale);
        Box2dUtils = new Utils(physicsEngine.getComponent(Box2DComponent.class).Box2dworld, physicsScale);

        //init mission critical
        ashleyEngine = new Engine();
        animFactory = new AnimationFactory();
        tileFactory = new TileFactory(physicsEngine.getComponent(Box2DComponent.class).Box2dworld, physicsScale);
        chunkFactory = new ChunkFactory(physicsEngine.getComponent(Box2DComponent.class).Box2dworld, physicsScale);

        //create player
        player = entityFactory.createPlayer();
        player.getComponent(PositionComponent.class).x = 64;
        player.getComponent(PositionComponent.class).y = 121;
        player.getComponent(PhysicsBodyComponent.class).body = Box2dUtils.createCircle(16, player.getComponent(PositionComponent.class).x, player.getComponent(PositionComponent.class).y, 2, 1, 2);

        Object[] walkAnimation = animFactory.kingWalkAnimation();
        player.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) walkAnimation[1]);
        player.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) walkAnimation[0]);

        Object[] jumpAnimation = animFactory.kingJumpUpAnimation();
        player.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) jumpAnimation[1]);
        player.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) jumpAnimation[0]);

        Object[] fallAnimation = animFactory.kingFallDownAnimation();
        player.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) fallAnimation[1]);
        player.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) fallAnimation[0]);

        int[] frame = {0, 0};
        player.getComponent(AnimationComponent.class).currentFrame = frame;
        player.getComponent(AnimationComponent.class).framesDisplayed = 0;
        ashleyEngine.addEntity(player);


        //systems
        physicsSystem = new PhysicsSystem(ashleyEngine, physicsEngine, physicsEngine.getComponent(Box2DComponent.class).Box2dworld, physicsScale, player);
        animationManagerSystem = new AnimationManagerSystem();
        cameraSystem = new CameraSystem();
        endGameSystem = new EndGameSystem(player);
        renderSystem = new RenderSystem(player, physicsScale);
        mapSystem = new MapSystem(ashleyEngine, currentScreen, chunkFactory, tileFactory, player, physicsScale);
        scoreSystem = new ScoreSystem(player);
        disposalSystem = new DisposalSystem(ashleyEngine, physicsEngine.getComponent(Box2DComponent.class).Box2dworld);

        //map?
        map = entityFactory.createMap();
        map.getComponent(MapComponent.class).physicalChunk = new Chunk[4];
        map.getComponent(MapComponent.class).layerTwoChunk = new Entity[4];
        map.getComponent(MapComponent.class).layerThreeChunk = new Entity[4];

        for(int i = 0; i < map.getComponent(MapComponent.class).layerTwoChunk.length; i++){
            map.getComponent(MapComponent.class).layerTwoChunk[i] = tileFactory.createWaterTile(i);
            mapSystem.addBackgroundChunk(map.getComponent(MapComponent.class).layerTwoChunk[i]);

            map.getComponent(MapComponent.class).layerThreeChunk[i] = tileFactory.createTreeTile(i);
            mapSystem.addBackgroundChunk(map.getComponent(MapComponent.class).layerThreeChunk[i]);
            map.getComponent(MapComponent.class).layerThreeChunk[i].remove(RenderableComponent.class);

            map.getComponent(MapComponent.class).physicalChunk[i] = chunkFactory.createGenericChunk(i);
            mapSystem.addPhysicalChunk(map.getComponent(MapComponent.class).physicalChunk[i]);
        }
        ashleyEngine.addEntity(map);

        //UI
        Entity Instructions = entityFactory.createUI();
        Object[] InstructionAnimation = animFactory.Instructions();
        Instructions.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) InstructionAnimation[1]);
        Instructions.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) InstructionAnimation[0]);
        Instructions.getComponent(ScreenPositionComponent.class).x = (Gdx.graphics.getWidth() / 2);
        Instructions.getComponent(ScreenPositionComponent.class).y = (Gdx.graphics.getHeight() / 2);
        Instructions.getComponent(SizeComponent.class).x = 512;
        Instructions.getComponent(SizeComponent.class).y = 434;
        Instructions.getComponent(UIComponent.class).screen.add(new Integer(0));
        ashleyEngine.addEntity(Instructions);
        /*
        Entity Pause = entityFactory.createUI();
            Object[] PauseAnimation = animFactory.PauseButton();
            Pause.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) PauseAnimation[1]);
            Pause.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) PauseAnimation[0]);
            Pause.getComponent(ScreenPositionComponent.class).x = (Gdx.graphics.getWidth()) - 36;
            Pause.getComponent(ScreenPositionComponent.class).y = (Gdx.graphics.getHeight()) - 36;
            Pause.getComponent(SizeComponent.class).x = 32;
            Pause.getComponent(SizeComponent.class).y = 32;
            Pause.getComponent(UIComponent.class).screen.add(new Integer(1));
            Pause.getComponent(UIComponent.class).screen.add(new Integer(2));
            ashleyEngine.addEntity(Pause);
        Entity Play = entityFactory.createUI();
            Object[] PlayAnimation = animFactory.PlayButton();
            Play.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) PlayAnimation[1]);
            Play.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) PlayAnimation[0]);
            Play.getComponent(ScreenPositionComponent.class).x = (Gdx.graphics.getWidth()) - 36;
            Play.getComponent(ScreenPositionComponent.class).y = (Gdx.graphics.getHeight()) - 36;
            Play.getComponent(SizeComponent.class).x = 32;
            Play.getComponent(SizeComponent.class).y = 32;
            Play.getComponent(UIComponent.class).screen.add(new Integer(3));
            ashleyEngine.addEntity(Play);
        */

        //add Ashley Engine systems
        ashleyEngine.addSystem(disposalSystem);
        ashleyEngine.addSystem(mapSystem);
        ashleyEngine.addSystem(physicsSystem);
        ashleyEngine.addSystem(animationManagerSystem);
        ashleyEngine.addSystem(cameraSystem);
        ashleyEngine.addSystem(endGameSystem);
        ashleyEngine.addSystem(renderSystem);
    }

    @Override
    public void init() {
        audio = Gdx.audio;
        music = Gdx.audio.newMusic(Gdx.files.internal("Insert-Quarter.mp3"));
        music.setLooping(true);

        adViewer.loadVideoAd();
        System.out.println("Loading");
        System.out.println("Waiting to show");
        //adViewer.showVideoAd();

        music.play();
        System.gc();
    }

    public void reset(){
        disposalSystem.removeAll();
        physicsSystem.removeAll();
        mapSystem.removeAll();

        player = entityFactory.createPlayer();
        player.getComponent(PositionComponent.class).x = 64;
        player.getComponent(PositionComponent.class).y = 121;
        player.getComponent(PhysicsBodyComponent.class).body = Box2dUtils.createCircle(16, player.getComponent(PositionComponent.class).x, player.getComponent(PositionComponent.class).y, 2, 1, 2);

        Object[] walkAnimation = animFactory.kingWalkAnimation();
        player.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) walkAnimation[1]);
        player.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) walkAnimation[0]);

        Object[] jumpAnimation = animFactory.kingJumpUpAnimation();
        player.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) jumpAnimation[1]);
        player.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) jumpAnimation[0]);

        Object[] fallAnimation = animFactory.kingFallDownAnimation();
        player.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) fallAnimation[1]);
        player.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) fallAnimation[0]);

        int[] frame = {0, 0};
        player.getComponent(AnimationComponent.class).currentFrame = frame;
        player.getComponent(AnimationComponent.class).framesDisplayed = 0;
        ashleyEngine.addEntity(player);

        endGameSystem.player = player;
        renderSystem.player = player;
        mapSystem.player = player;
        scoreSystem.player = player;
        physicsSystem.player = player;
        physicsSystem.speed = 170;

        map = entityFactory.createMap();
        map.getComponent(MapComponent.class).physicalChunk = new Chunk[4];
        map.getComponent(MapComponent.class).layerTwoChunk = new Entity[4];
        map.getComponent(MapComponent.class).layerThreeChunk = new Entity[4];

        for(int i = 0; i < map.getComponent(MapComponent.class).layerTwoChunk.length; i++){
            map.getComponent(MapComponent.class).layerTwoChunk[i] = tileFactory.createWaterTile(i);
            mapSystem.addBackgroundChunk(map.getComponent(MapComponent.class).layerTwoChunk[i]);

            map.getComponent(MapComponent.class).layerThreeChunk[i] = tileFactory.createTreeTile(i);
            mapSystem.addBackgroundChunk(map.getComponent(MapComponent.class).layerThreeChunk[i]);
            map.getComponent(MapComponent.class).layerThreeChunk[i].remove(RenderableComponent.class);

            map.getComponent(MapComponent.class).physicalChunk[i] = chunkFactory.createGenericChunk(i);
            mapSystem.addPhysicalChunk(map.getComponent(MapComponent.class).physicalChunk[i]);
        }
        ashleyEngine.addEntity(map);

        //UI
        Entity Instructions = entityFactory.createUI();
        Object[] InstructionAnimation = animFactory.Instructions();
        Instructions.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) InstructionAnimation[1]);
        Instructions.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) InstructionAnimation[0]);
        Instructions.getComponent(ScreenPositionComponent.class).x = (Gdx.graphics.getWidth() / 2);
        Instructions.getComponent(ScreenPositionComponent.class).y = (Gdx.graphics.getHeight() / 2);
        Instructions.getComponent(SizeComponent.class).x = 512;
        Instructions.getComponent(SizeComponent.class).y = 434;
        Instructions.getComponent(UIComponent.class).screen.add(new Integer(0));
        ashleyEngine.addEntity(Instructions);

        scoreSystem.reset();

        System.gc();
    }

    @Override
    public int render() throws InterruptedException {

        if(currentScreen == 0){
            mapSystem.scene = 0;
            renderSystem.scene = 0;
            ashleyEngine.removeSystem(scoreSystem);
            if(Gdx.input.justTouched()){
                currentScreen = 1;
            }
        } else if(currentScreen == 1) {
            mapSystem.scene = 1;
            renderSystem.scene = 1;
            ashleyEngine.addSystem(scoreSystem);
            if (Gdx.input.isTouched() && player.getComponent(IsGroundedComponent.class).isGrounded) {
                player.add(new JumpPhysicsComponent());
                player.add(new ContinueJumpComponent());
            }
            if (!Gdx.input.isTouched()) {
                player.remove(ContinueJumpComponent.class);
            }
            if(player.getComponent(IsDeadComponent.class).isDead) {
                currentScreen = 2;
            }

        } else if(currentScreen == 2){
            renderSystem.scene = 2;
            ashleyEngine.removeSystem(scoreSystem);
            if (Gdx.input.justTouched()) {
                this.reset();
                currentScreen = 0;
                mapSystem.scene = 0;
                mapSystem.lastScene = 0;
                renderSystem.scene = 0;
            }
        }

        ashleyEngine.update(Gdx.graphics.getDeltaTime());

        return IDENTIFIER;
    }
}
