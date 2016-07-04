package com.jahrud.kingdomdash.Factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jahrud.kingdomdash.components.UI.ScreenPositionComponent;
import com.jahrud.kingdomdash.components.UI.SizeComponent;
import com.jahrud.kingdomdash.components.map.MapComponent;
import com.jahrud.kingdomdash.components.map.TileComponent;
import com.jahrud.kingdomdash.components.map.UpdateMapComponent;
import com.jahrud.kingdomdash.components.physics.Box2DComponent;
import com.jahrud.kingdomdash.components.physics.IsGroundedComponent;
import com.jahrud.kingdomdash.components.physics.PhysicsBodyComponent;
import com.jahrud.kingdomdash.components.physics.PositionComponent;
import com.jahrud.kingdomdash.components.player.CameraComponent;
import com.jahrud.kingdomdash.components.player.IsDeadComponent;
import com.jahrud.kingdomdash.components.player.PlayerComponent;
import com.jahrud.kingdomdash.components.player.ScoreComponent;
import com.jahrud.kingdomdash.components.render.AnimationComponent;
import com.jahrud.kingdomdash.components.render.RenderableComponent;
import com.jahrud.kingdomdash.components.render.SpriteComponent;
import com.jahrud.kingdomdash.components.UI.UIComponent;
import com.jahrud.kingdomdash.components.render.layer.EntityLayerComponent;
import com.jahrud.kingdomdash.components.render.layer.LayerThreeComponent;
import com.jahrud.kingdomdash.components.render.layer.LayerTwoComponent;

public class EntityFactory {

    public EntityFactory(){

    }

    public Entity createPlayer(){
        Entity entity = new Entity();
        entity.add(new PositionComponent());
        entity.add(new SpriteComponent());
        entity.add(new AnimationComponent());
        entity.add(new RenderableComponent());
        entity.add(new PlayerComponent());
        entity.add(new PhysicsBodyComponent());
        entity.add(new IsGroundedComponent());
        entity.add(new CameraComponent());
        entity.add(new IsDeadComponent());
        entity.add(new EntityLayerComponent());
        entity.add(new ScoreComponent());
        return entity;
    }

    public Entity createTile(){
        Entity entity = new Entity();
        entity.add(new TileComponent());
        entity.add(new PositionComponent());
        entity.add(new AnimationComponent());
        entity.add(new SpriteComponent());
        entity.add(new RenderableComponent());
        entity.add(new PhysicsBodyComponent());
        entity.add(new UpdateMapComponent());
        return entity;
    }

    public Entity createWaterTile(){
        Entity entity = new Entity();
        entity.add(new TileComponent());
        entity.add(new PositionComponent());
        entity.add(new AnimationComponent());
        entity.add(new SpriteComponent());
        entity.add(new RenderableComponent());
        entity.add(new UpdateMapComponent());
        entity.add(new LayerTwoComponent());
        return entity;
    }

    public Entity createPhysicsEngine(float scale){
        Entity entity = new Entity();
        entity.add(new Box2DComponent());
        entity.getComponent(Box2DComponent.class).Box2dworld = new World(new Vector2(0 / scale, -160 / scale), true);
        return entity;
    }

    public Entity createMap(){
        Entity entity = new Entity();
        entity.add(new MapComponent());
        return entity;
    }

    public Entity createTreeTile() {
        Entity entity = new Entity();
        entity.add(new TileComponent());
        entity.add(new PositionComponent());
        entity.add(new AnimationComponent());
        entity.add(new SpriteComponent());
        if(Math.random() < .07) entity.add(new RenderableComponent());
        entity.add(new UpdateMapComponent());
        entity.add(new LayerThreeComponent());
        return entity;
    }

    public Entity createUI(){
        Entity entity = new Entity();
        entity.add(new UIComponent());
        entity.add(new ScreenPositionComponent());
        entity.add(new SpriteComponent());
        entity.add(new SizeComponent());
        entity.add(new AnimationComponent());
        entity.add(new RenderableComponent());
        return  entity;
    }
}
