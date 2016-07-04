package com.jahrud.kingdomdash.Scenes;

public abstract class Scene {
    public int IDENTIFIER;
    public boolean isRunning = false;
    public abstract void init();
    public abstract int render() throws InterruptedException;
}
