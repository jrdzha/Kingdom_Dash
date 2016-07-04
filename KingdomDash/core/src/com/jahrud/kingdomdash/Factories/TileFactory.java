package com.jahrud.kingdomdash.Factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.jahrud.kingdomdash.Systems.Utils;
import com.jahrud.kingdomdash.components.map.TileComponent;
import com.jahrud.kingdomdash.components.physics.PhysicsBodyComponent;
import com.jahrud.kingdomdash.components.render.AnimationComponent;
import com.jahrud.kingdomdash.components.render.SpriteComponent;

import java.util.ArrayList;

public class TileFactory {

    AnimationFactory animFactory;
    EntityFactory entityFactory;
    Utils Box2dUtils;

    public TileFactory(World physicsWorld, float scale){
        animFactory = new AnimationFactory();
        entityFactory = new EntityFactory();
        Box2dUtils = new Utils(physicsWorld, scale);
    }

    public Entity createTile(int x, int y, int type, int cBits, int mBits){
        Entity tile = entityFactory.createTile();
        tile.getComponent(TileComponent.class).x = x;
        tile.getComponent(TileComponent.class).y = y;
        tile.getComponent(TileComponent.class).type = type;

        if(type == -1){
            tile.getComponent(PhysicsBodyComponent.class).body = Box2dUtils.createBox(16, 16, x * 16, y * 16, 0, cBits, mBits);
        } else if(type / 10 > 3 || type % 10 > 6 || (type / 10 == 3 && type % 10  == 4)){
            tile.getComponent(PhysicsBodyComponent.class).body = Box2dUtils.createBox(16, 16, x * 16, y * 16, 0, cBits, mBits);
        } else {
            tile.getComponent(PhysicsBodyComponent.class).body = Box2dUtils.createBox(16, 16, x * 16, y * 16, 0, cBits, mBits);
        }


        if(type != -1){
            Object[] tileSprite = animFactory.block(tile.getComponent(TileComponent.class).type);
            tile.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) tileSprite[1]);
            tile.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) tileSprite[0]);
        } else {
            tile.getComponent(AnimationComponent.class).framesDisplayed = -1;
        }
        int[] frame = {0, 0};
        tile.getComponent(AnimationComponent.class).currentFrame = frame;


        return tile;
    }

    public Entity createWaterTile(int x){
        Entity tile = entityFactory.createWaterTile();
        tile.getComponent(TileComponent.class).x = x;
        tile.getComponent(TileComponent.class).y = 5;
        tile.getComponent(TileComponent.class).type = -2;

        Object[] tileSprite = animFactory.waterChunk();
        tile.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) tileSprite[1]);
        tile.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) tileSprite[0]);

        int[] frame = {0, 0};
        tile.getComponent(AnimationComponent.class).currentFrame = frame;
        return tile;
    }

    public Entity createTreeTile(int x) {
        Entity tile = entityFactory.createTreeTile();
        tile.getComponent(TileComponent.class).x = x;
        tile.getComponent(TileComponent.class).y = 13;
        tile.getComponent(TileComponent.class).type = -2;

        Object[] tileSprite = animFactory.treeChunk();
        tile.getComponent(AnimationComponent.class).animationTimeList.add((ArrayList<Integer>) tileSprite[1]);
        tile.getComponent(SpriteComponent.class).spriteList.add((ArrayList<Sprite>) tileSprite[0]);

        int[] frame = {0, 0};
        tile.getComponent(AnimationComponent.class).currentFrame = frame;
        return tile;
    }
}
