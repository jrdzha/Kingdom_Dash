package com.jahrud.kingdomdash.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.World;
import com.jahrud.kingdomdash.components.RemoveTagComponent;
import com.jahrud.kingdomdash.components.physics.PhysicsBodyComponent;

/**
 * Created by JahrudZ on 5/24/16.
 */
public class DisposalSystem extends EntitySystem{

    private ImmutableArray<Entity> removeEntities;
    private Engine ashleyEngine;
    private World Box2dworld;

    public DisposalSystem(Engine ashleyEngine, World Box2dworld){
        this.ashleyEngine = ashleyEngine;
        this.Box2dworld = Box2dworld;
    }

    public void reset() {

    }

    public void addedToEngine(Engine engine){
        removeEntities = engine.getEntitiesFor(Family.all(RemoveTagComponent.class).get());
    }

    public void removeAll(){
        while(ashleyEngine.getEntities().size() > 0) {
            ImmutableArray<Entity> removeArray = ashleyEngine.getEntities();
            for (Entity e : removeArray) {
                e.removeAll();
                ashleyEngine.removeEntity(e);
            }
        }
    }

    public void update(float deltaTime){
        for(int i = 0; i < removeEntities.size(); ++i){
            Entity entity = removeEntities.get(i);
            Box2dworld.destroyBody(entity.getComponent(PhysicsBodyComponent.class).body);

            entity.removeAll();
            ashleyEngine.removeEntity(entity);
        }
    }

}
