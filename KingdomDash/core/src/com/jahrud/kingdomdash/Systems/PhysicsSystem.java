package com.jahrud.kingdomdash.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.jahrud.kingdomdash.components.physics.*;
import com.jahrud.kingdomdash.components.player.IsDeadComponent;
import com.jahrud.kingdomdash.components.player.ScoreComponent;
import com.jahrud.kingdomdash.components.render.FallAnimationComponent;
import com.jahrud.kingdomdash.components.render.JumpAnimationComponent;
import com.jahrud.kingdomdash.components.render.WalkAnimationComponent;

public class PhysicsSystem extends EntitySystem {
    Engine ashleyEngine;
    Entity physicsEngine;
    float scale;
    World Box2dworld;
    public Entity player;
    private ImmutableArray<Entity> jumpedEntities;
    private ImmutableArray<Entity> continueJumpEntities;
    public float speed = 160;

    public PhysicsSystem(Engine ashleyEngine, Entity physicsEngine, World Box2dworld, float scale, Entity player){
        this.ashleyEngine = ashleyEngine;
        this.physicsEngine = physicsEngine;
        this.Box2dworld = Box2dworld;
        this.scale = scale;
        this.player = player;
    }

    public void reset() {

    }

    @SuppressWarnings("unchecked")
    public void addedToEngine(Engine engine){
        jumpedEntities = engine.getEntitiesFor(Family.all(PhysicsBodyComponent.class, JumpPhysicsComponent.class, PositionComponent.class).get());
        continueJumpEntities = engine.getEntitiesFor(Family.all(PhysicsBodyComponent.class, ContinueJumpComponent.class).get());
    }

    public void update(float deltaTime){
        if(player.getComponent(ScoreComponent.class).startChunk != -1) {
            speed += ((player.getComponent(PositionComponent.class).x - (player.getComponent(ScoreComponent.class).startChunk - 3) * 16 * 12) / 8000f);
        } else {
            speed = 170;
        }
        if(speed > 230) speed = 230;


        Box2dworld.step(deltaTime, 6, 2);
        if(continueJumpEntities.size() > 0){
            Box2dworld.setGravity(new Vector2(0, -240 / scale));
        } else {
            Box2dworld.setGravity(new Vector2(0, -1200 / scale));
        }

        Body playerBody = player.getComponent(PhysicsBodyComponent.class).body;
        PositionComponent playerPosition = player.getComponent(PositionComponent.class);
        IsGroundedComponent isPlayerGrounded = player.getComponent(IsGroundedComponent.class);

        playerPosition.x = playerBody.getPosition().x * scale;
        playerPosition.y = playerBody.getPosition().y * scale;

        if((int)playerBody.getLinearVelocity().y < 0){
            player.add(new FallAnimationComponent());
        } else if ((int)playerBody.getLinearVelocity().y > 0){
            player.add(new JumpAnimationComponent());
        } else {
            player.add(new WalkAnimationComponent());
        }

        if(playerBody.getLinearVelocity().y == 0f){
            isPlayerGrounded.isGrounded = true;
            playerBody.setLinearVelocity(speed / scale, 0 / scale);
            if(playerPosition.x < player.getComponent(ScoreComponent.class).startChunk * 12 * 16 || player.getComponent(ScoreComponent.class).startChunk == -1) {
                playerBody.applyLinearImpulse(0, -50, playerBody.getPosition().x, playerBody.getPosition().y, true);
            }
        } else {
            isPlayerGrounded.isGrounded = false;
        }

        if(player.getComponent(IsDeadComponent.class).isDead){
            playerBody.setLinearVelocity(playerBody.getLinearVelocity().x /= 1.01, 0);
        }

        for(int i = 0; i < jumpedEntities.size(); ++i){
            Entity entity = jumpedEntities.get(i);
            PhysicsBodyComponent physics = entity.getComponent(PhysicsBodyComponent.class);
            IsGroundedComponent grounded = entity.getComponent(IsGroundedComponent.class);
            physics.body.setLinearVelocity(speed / scale, 200 / scale);
            entity.remove(JumpPhysicsComponent.class);
            grounded.isGrounded = false;
        }
    }

    public void removeAll(){
        Array<Body> bodies = new Array<Body>();
        Box2dworld.getBodies(bodies);
        for(int i = 0; i < bodies.size; i++)
        {
            if(!Box2dworld.isLocked())
                Box2dworld.destroyBody(bodies.get(i));
        }
    }
}