package com.jahrud.kingdomdash.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.jahrud.kingdomdash.Factories.ChunkFactory;
import com.jahrud.kingdomdash.Factories.TileFactory;
import com.jahrud.kingdomdash.components.RemoveTagComponent;
import com.jahrud.kingdomdash.components.map.Chunk;
import com.jahrud.kingdomdash.components.map.MapComponent;
import com.jahrud.kingdomdash.components.map.TileComponent;
import com.jahrud.kingdomdash.components.map.UpdateMapComponent;
import com.jahrud.kingdomdash.components.physics.PhysicsBodyComponent;
import com.jahrud.kingdomdash.components.physics.PositionComponent;
import com.jahrud.kingdomdash.components.player.ScoreComponent;
import com.jahrud.kingdomdash.components.render.layer.LayerThreeComponent;
import com.jahrud.kingdomdash.components.render.layer.LayerTwoComponent;

public class MapSystem extends EntitySystem {
    public int scene;
    Engine ashleyEngine;
    ChunkFactory chunkFactory;
    TileFactory tileFactory;
    public Entity player;
    public int lastScene;
    float scale;
    private ImmutableArray<Entity> tileEntities;
    private ImmutableArray<Entity> mapUpdateEntities;
    private ImmutableArray<Entity> mapEntities;
    private ImmutableArray<Entity> layerTwoEntities;
    private ImmutableArray<Entity> layerThreeEntities;

    public MapSystem(Engine ashleyEngine, int scene, ChunkFactory chunkFactory, TileFactory tileFactory, Entity player, float scale){
        this.ashleyEngine = ashleyEngine;
        this.chunkFactory = chunkFactory;
        this.tileFactory = tileFactory;
        this.player = player;
        this.scale = scale;
        this.scene = scene;
    }

    public void reset() {

    }

    @SuppressWarnings("unchecked")
    public void addedToEngine(Engine engine){
        tileEntities = engine.getEntitiesFor(Family.all(TileComponent.class, PositionComponent.class, PhysicsBodyComponent.class, UpdateMapComponent.class).get());
        layerTwoEntities = engine.getEntitiesFor(Family.all(TileComponent.class, PositionComponent.class, UpdateMapComponent.class, LayerTwoComponent.class).get());
        layerThreeEntities = engine.getEntitiesFor(Family.all(TileComponent.class, PositionComponent.class, UpdateMapComponent.class, LayerThreeComponent.class).get());
        mapUpdateEntities = engine.getEntitiesFor(Family.all(MapComponent.class, UpdateMapComponent.class).get());
        mapEntities = engine.getEntitiesFor(Family.all(MapComponent.class).get());
    }

    public void update(float deltaTime){
        if(player.getComponent(PositionComponent.class).x / 16 > mapEntities.get(0).getComponent(MapComponent.class).physicalChunk[2].tiles[0][0].getComponent(TileComponent.class).x){
            mapEntities.get(0).add(new UpdateMapComponent());
            //System.out.println(player.getComponent(PositionComponent.class).x / 16);
            //System.out.println(mapEntities.get(0).getComponent(MapComponent.class).physicalChunk[2].tiles[0][0].getComponent(TileComponent.class).x);
        }
        for(int i = 0; i < mapUpdateEntities.size(); ++i){
            Entity entity = mapUpdateEntities.get(i);
            MapComponent map = entity.getComponent(MapComponent.class);

            this.removeTile(map.layerTwoChunk[0]);
            this.removeTile(map.layerThreeChunk[0]);
            this.removeChunk(map.physicalChunk[0]);

            for(int j = 0; j < map.physicalChunk.length - 1; j++){
                map.layerTwoChunk[j] = map.layerTwoChunk[j + 1];
                map.layerThreeChunk[j] = map.layerThreeChunk[j + 1];
                map.physicalChunk[j] = map.physicalChunk[j + 1];
            }
            map.layerThreeChunk[map.layerThreeChunk.length - 1] = tileFactory.createTreeTile((map.layerThreeChunk[map.layerThreeChunk.length - 2].getComponent(TileComponent.class).x) + 1);
            map.layerTwoChunk[map.layerTwoChunk.length - 1] = tileFactory.createWaterTile((map.layerTwoChunk[map.layerTwoChunk.length - 2].getComponent(TileComponent.class).x) + 1);

            if(scene == 0){
                //System.out.println("A");
                map.physicalChunk[map.physicalChunk.length - 1] = chunkFactory.createGenericChunk((map.physicalChunk[map.physicalChunk.length - 2].tiles[0][0].getComponent(TileComponent.class).x / 12) + 1);
            } else if((scene == 1 && lastScene == 0)){
                //System.out.println("B");
                map.physicalChunk[map.physicalChunk.length - 1] = chunkFactory.createStartChunk((map.physicalChunk[map.physicalChunk.length - 2].tiles[0][0].getComponent(TileComponent.class).x / 12) + 1);
                player.getComponent(ScoreComponent.class).startChunk = (map.physicalChunk[map.physicalChunk.length - 2].tiles[0][0].getComponent(TileComponent.class).x / 12) + 1;
            } else if(scene == 1){
                //System.out.println("C");
                map.physicalChunk[map.physicalChunk.length - 1] = chunkFactory.createChunk((map.physicalChunk[map.physicalChunk.length - 2].tiles[0][0].getComponent(TileComponent.class).x / 12) + 1);
            }
            lastScene = scene;

            this.addBackgroundChunk(map.layerThreeChunk[map.layerThreeChunk.length - 1]);
            this.addBackgroundChunk(map.layerTwoChunk[map.layerTwoChunk.length - 1]);
            this.addPhysicalChunk(map.physicalChunk[map.physicalChunk.length - 1]);

            entity.remove(UpdateMapComponent.class);
        }
        for(int i = 0; i < tileEntities.size(); ++i){
            Entity entity = tileEntities.get(i);
            TileComponent tile = entity.getComponent(TileComponent.class);
            PositionComponent position = entity.getComponent(PositionComponent.class);
            PhysicsBodyComponent body = entity.getComponent(PhysicsBodyComponent.class);
            position.x = tile.x * 16;
            position.y = tile.y * 16;
            body.body.setTransform(position.x / scale, position.y / scale, 0);
            entity.remove(UpdateMapComponent.class);
        }
        for(int i = 0; i < layerTwoEntities.size(); ++i){
            Entity entity = layerTwoEntities.get(i);
            TileComponent tile = entity.getComponent(TileComponent.class);
            PositionComponent position = entity.getComponent(PositionComponent.class);
            position.x = tile.x * 16 * 12;
            position.y = tile.y * 16;
            entity.remove(UpdateMapComponent.class);
        }
        for(int i = 0; i < layerThreeEntities.size(); ++i){
            Entity entity = layerThreeEntities.get(i);
            TileComponent tile = entity.getComponent(TileComponent.class);
            PositionComponent position = entity.getComponent(PositionComponent.class);
            position.x = tile.x * 16 * 12;
            position.y = tile.y * 16;
            entity.remove(UpdateMapComponent.class);
        }
    }

    public void removeAll(){
        for(int i = 0; i < mapUpdateEntities.size(); ++i){
            Entity entity = mapUpdateEntities.get(i);
            MapComponent map = entity.getComponent(MapComponent.class);

            this.removeTile(map.layerTwoChunk[i]);
            this.removeTile(map.layerThreeChunk[i]);
            this.removeChunk(map.physicalChunk[i]);
        }
    }

    public void removeChunk(Chunk chunk){
        Entity[][] tiles = chunk.tiles;
        Entity[] eyeCandy = chunk.eyeCandy;
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[0].length; j++){
                tiles[i][j].add(new RemoveTagComponent());
            }
        }
        for(int i = 0; i < eyeCandy.length; i++){
            eyeCandy[i].add(new RemoveTagComponent());
        }
    }
    public void removeTile(Entity entity){
        entity.removeAll();
        ashleyEngine.removeEntity(entity);
    }

    public void addPhysicalChunk(Chunk chunk){
        Entity[][] tiles = chunk.tiles;
        Entity[] eyeCandy = chunk.eyeCandy;
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles[0].length; j++){
                ashleyEngine.addEntity(tiles[i][j]);
            }
        }
        for(int i = 0; i < eyeCandy.length; i++){
            ashleyEngine.addEntity(eyeCandy[i]);
        }
    }
    public void addBackgroundChunk(Entity entity){
        ashleyEngine.addEntity(entity);
    }
}
