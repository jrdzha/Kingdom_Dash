package com.jahrud.kingdomdash.Systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.jahrud.kingdomdash.components.physics.IsGroundedComponent;
import com.jahrud.kingdomdash.components.render.AnimationComponent;
import com.jahrud.kingdomdash.components.render.FallAnimationComponent;
import com.jahrud.kingdomdash.components.render.JumpAnimationComponent;
import com.jahrud.kingdomdash.components.render.WalkAnimationComponent;

public class AnimationManagerSystem extends EntitySystem{
    private ImmutableArray<Entity> jumpedEntities;
    private ImmutableArray<Entity> groundedEntities;
    private ImmutableArray<Entity> fallEntities;

    private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
    private ComponentMapper<IsGroundedComponent> gm = ComponentMapper.getFor(IsGroundedComponent.class);

    public AnimationManagerSystem(){

    }

    public void reset() {

    }

    @SuppressWarnings("unchecked")
    public void addedToEngine(Engine engine){
        jumpedEntities = engine.getEntitiesFor(Family.all(AnimationComponent.class, JumpAnimationComponent.class, IsGroundedComponent.class).get());
        groundedEntities = engine.getEntitiesFor(Family.all(AnimationComponent.class, WalkAnimationComponent.class, IsGroundedComponent.class).get());
        fallEntities = engine.getEntitiesFor(Family.all(AnimationComponent.class, FallAnimationComponent.class, IsGroundedComponent.class).get());
    }

    public void update(float deltaTime){
        for(int i = 0; i < jumpedEntities.size(); ++i){
            Entity entity = jumpedEntities.get(i);
            AnimationComponent animation = am.get(entity);
            IsGroundedComponent grounded = gm.get(entity);
            animation.currentFrame[1] = 0;
            animation.currentFrame[0] = 1;
            animation.framesDisplayed = 0;
            grounded.isGrounded = false;
            entity.remove(JumpAnimationComponent.class);
        }
        for(int i = 0; i < fallEntities.size(); ++i){
            Entity entity = fallEntities.get(i);
            AnimationComponent animation = am.get(entity);
            IsGroundedComponent grounded = gm.get(entity);
            animation.currentFrame[1] = 0;
            animation.currentFrame[0] = 2;
            animation.framesDisplayed = 0;
            grounded.isGrounded = false;
            entity.remove(FallAnimationComponent.class);
        }
        for(int i = 0; i < groundedEntities.size(); ++i){
            Entity entity = groundedEntities.get(i);
            AnimationComponent animation = am.get(entity);
            IsGroundedComponent grounded = gm.get(entity);
            if(grounded.isGrounded && animation.currentFrame[0] != 0){
                animation.currentFrame[1] = 0;
                animation.currentFrame[0] = 0;
                animation.framesDisplayed = 0;
                entity.remove(WalkAnimationComponent.class);
            }
        }
    }
}
