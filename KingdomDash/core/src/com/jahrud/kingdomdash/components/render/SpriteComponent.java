package com.jahrud.kingdomdash.components.render;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class SpriteComponent implements Component {
    public ArrayList<ArrayList<Sprite>> spriteList = new ArrayList<ArrayList<Sprite>>();
}