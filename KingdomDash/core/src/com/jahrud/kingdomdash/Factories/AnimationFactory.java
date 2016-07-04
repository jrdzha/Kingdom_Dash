package com.jahrud.kingdomdash.Factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class AnimationFactory {

    Texture Characters, Tileset, WaterChunk, Instructions, PlayButton, PauseButton, HomeButton;

    public AnimationFactory(){
        //load assets
        Characters = new Texture(Gdx.files.internal("Characters.png"));
        Tileset = new Texture(Gdx.files.internal("Tileset.png"));
        WaterChunk = new Texture(Gdx.files.internal("WaterChunk.png"));
        Instructions = new Texture(Gdx.files.internal("UI/Instructions.png"));
        PlayButton = new Texture(Gdx.files.internal("UI/PlayButton.png"));
        PauseButton = new Texture((Gdx.files.internal("UI/PauseButton.png")));
        HomeButton = new Texture(Gdx.files.internal("UI/HomeButton.png"));
    }

    public Sprite spriteRegion(Texture tex, int x, int y, int w, int h){
        Sprite sprite = new Sprite(new TextureRegion(tex, x * w, y * h, w, h));
        return sprite;
    }

    public Object[] HomeButton(){
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        Sprite sprite = new Sprite(new TextureRegion(HomeButton, 0, 0, 32, 32));
        sprite.setScale(2.5f);
        sprites.add(sprite);
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(-1));
        completeAnimation[1] = animations;
        return completeAnimation;
    }

    public Object[] PauseButton(){
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        Sprite sprite = new Sprite(new TextureRegion(PauseButton, 0, 0, 32, 32));
        sprite.setScale(1f);
        sprites.add(sprite);
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(-1));
        completeAnimation[1] = animations;
        return completeAnimation;
    }

    public Object[] PlayButton(){
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        Sprite sprite = new Sprite(new TextureRegion(PlayButton, 0, 0, 32, 32));
        sprite.setScale(1f);
        sprites.add(sprite);
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(-1));
        completeAnimation[1] = animations;
        return completeAnimation;
    }

    public Object[] Instructions(){
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        Sprite sprite = new Sprite(Instructions);
        sprite.setScale((float)Gdx.graphics.getHeight() / sprite.getHeight() / 2.5f);
        sprites.add(sprite);
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(-1));
        completeAnimation[1] = animations;
        return completeAnimation;
    }

    public Object[] kingWalkAnimation(){
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(spriteRegion(Characters, 14, 1, 32, 32));
        sprites.add(spriteRegion(Characters, 15, 1, 32, 32));
        sprites.add(spriteRegion(Characters, 16, 1, 32, 32));
        sprites.add(spriteRegion(Characters, 17, 1, 32, 32));
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(5));
        animations.add(new Integer(5));
        animations.add(new Integer(5));
        animations.add(new Integer(5));
        completeAnimation[1] = animations;
        return completeAnimation;
    }

    public Object[] kingJumpUpAnimation(){
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(spriteRegion(Characters, 5, 1, 32, 32));
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(-1));
        completeAnimation[1] = animations;
        return completeAnimation;
    }

    public Object[] kingFallDownAnimation(){
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(spriteRegion(Characters, 6, 1, 32, 32));
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(-1));
        completeAnimation[1] = animations;
        return completeAnimation;
    }

    public Object[] block(int index){
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(spriteRegion(Tileset, (index % 10) + 7, index / 10, 16, 16));
        sprites.get(0).setScale(1.02f);
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(-1));
        completeAnimation[1] = animations;
        return completeAnimation;
    }

    public Object[] waterChunk(){
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        sprites.add(spriteRegion(WaterChunk, 0, 0, 192, 192));
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(-1));
        completeAnimation[1] = animations;
        return completeAnimation;
    }

    public Object[] treeChunk() {
        Object[] completeAnimation = new Object[2];
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        Sprite tree = new Sprite(new TextureRegion(Tileset, 32, 0, 80, 128));
        tree.setScale(2.5f);
        sprites.add(tree);
        completeAnimation[0] = sprites;
        ArrayList<Integer> animations = new ArrayList<Integer>();
        animations.add(new Integer(-1));
        completeAnimation[1] = animations;
        return completeAnimation;
    }
}
