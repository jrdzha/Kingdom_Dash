package com.jahrud.kingdomdash.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.jahrud.kingdomdash.AdViewer;
import com.jahrud.kingdomdash.components.physics.PositionComponent;
import com.jahrud.kingdomdash.components.player.IsDeadComponent;

public class EndGameSystem extends EntitySystem{

    public Entity player;
    public AdViewer adViewer;

    public EndGameSystem(Entity player, AdViewer adViewer) {
        this.player = player;
        this.adViewer = adViewer;
    }

    public void reset() {

    }

    public void update(float deltaTime){
        if(player.getComponent(PositionComponent.class).y < 0f){
            player.getComponent(IsDeadComponent.class).isDead = true;
            adViewer.loadVideoAd();
        }
    }

}
