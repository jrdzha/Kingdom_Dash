package com.jahrud.kingdomdash.components.render;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;

public class AnimationComponent implements Component{
    public ArrayList<ArrayList<Integer>> animationTimeList = new ArrayList<ArrayList<Integer>>();
    public int[] currentFrame = new int[2];
    public int framesDisplayed = 0;
}
