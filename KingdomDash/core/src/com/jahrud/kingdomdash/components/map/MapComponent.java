package com.jahrud.kingdomdash.components.map;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class MapComponent implements Component{
    public Chunk[] physicalChunk;
    public Entity[] layerTwoChunk;
    public Entity[] layerThreeChunk;
}
