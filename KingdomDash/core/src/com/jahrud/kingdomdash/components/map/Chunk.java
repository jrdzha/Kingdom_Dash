package com.jahrud.kingdomdash.components.map;

import com.badlogic.ashley.core.Entity;

public class Chunk {

    public Entity[][] tiles;
    public Entity[] eyeCandy;

    public Chunk(Entity[][] tiles, Entity[] eyeCandy){
        this.tiles = tiles;
        this.eyeCandy = eyeCandy;
    }

}
