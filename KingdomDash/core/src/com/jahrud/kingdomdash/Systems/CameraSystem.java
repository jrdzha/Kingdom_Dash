package com.jahrud.kingdomdash.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.jahrud.kingdomdash.components.physics.PositionComponent;
import com.jahrud.kingdomdash.components.player.CameraComponent;

public class CameraSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<CameraComponent> cm = ComponentMapper.getFor(CameraComponent.class);

    public CameraSystem(){

    }

    public void reset() {

    }

    @SuppressWarnings("unchecked")
    public void addedToEngine(Engine engine){
        entities = engine.getEntitiesFor(Family.all(PositionComponent.class, CameraComponent.class).get());
    }

    public void update(float deltaTime){
        for(int i = 0; i < entities.size(); ++i){
            Entity entity = entities.get(i);
            PositionComponent position = pm.get(entity);
            CameraComponent camera = cm.get(entity);
            camera.camera.position.set((position.x + camera.camera.viewportWidth / 3), 200, 0);
        }
    }
}
