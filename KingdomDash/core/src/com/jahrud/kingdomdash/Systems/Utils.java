package com.jahrud.kingdomdash.Systems;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Utils {

    World Box2dworld;
    float scale;

    public Utils(World Box2dworld, float scale){
        this.Box2dworld = Box2dworld;
        this.scale = scale;
    }

    public Body createBox(int width, int height, float x, float y, int bodyType, int cBits, int mBits){
        Body pBody;
        BodyDef def = new BodyDef();

        if(bodyType == 0){
            def.type = BodyDef.BodyType.StaticBody;
        } else if(bodyType == 1){
            def.type = BodyDef.BodyType.KinematicBody;
        } else if(bodyType == 2){
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(x / scale, y / scale);
        def.fixedRotation = true;

        pBody = Box2dworld.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / (2 * scale), height / (2 * scale));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = -.1f;

        fixtureDef.filter.categoryBits = (short)cBits;
        fixtureDef.filter.maskBits = (short)mBits;

        pBody.createFixture(fixtureDef);
        shape.dispose();

        return pBody;
    }

    public Body createCircle(int radius, float x, float y, int bodyType, int cBits, int mBits){
        Body pBody;
        BodyDef def = new BodyDef();

        if(bodyType == 0){
            def.type = BodyDef.BodyType.StaticBody;
        } else if(bodyType == 1){
            def.type = BodyDef.BodyType.KinematicBody;
        } else if(bodyType == 2){
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(x / scale, y / scale);
        def.fixedRotation = true;

        pBody = Box2dworld.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / (2 * scale));
        shape.setPosition(new Vector2(0 / scale, -8 / scale));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0.0f;

        fixtureDef.filter.categoryBits = (short)cBits;
        fixtureDef.filter.maskBits = (short)mBits;

        pBody.createFixture(fixtureDef);
        shape.dispose();

        return pBody;
    }

}
