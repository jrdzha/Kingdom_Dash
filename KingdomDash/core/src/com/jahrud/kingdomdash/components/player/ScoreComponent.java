package com.jahrud.kingdomdash.components.player;

import com.badlogic.ashley.core.Component;

/**
 * Created by JahrudZ on 5/27/16.
 */
public class ScoreComponent implements Component{
    public int highscore = 0;
    public int score = 0;
    public int startChunk = -1;
}
