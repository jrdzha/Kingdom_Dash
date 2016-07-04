package com.jahrud.kingdomdash.components.player;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraComponent implements Component{
    int cameraHeight = 330;
    float scale = ((float)Gdx.graphics.getHeight() / (float)cameraHeight);
    float cameraWidth = Gdx.graphics.getWidth() / scale;
    public OrthographicCamera camera = new OrthographicCamera(cameraWidth, cameraHeight);
}
