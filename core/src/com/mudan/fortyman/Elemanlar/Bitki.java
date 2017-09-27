package com.mudan.fortyman.Elemanlar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mudan.fortyman.screens.PlayState;

/**
 * Created by musa on 21.09.2017.
 */

public class Bitki extends Sprite {
    private TextureRegion tBitki;
    protected Rectangle kare;
    private Body body;
    private World world;

    public Bitki(PlayState state, float x, float y){
        this.world = state.getWorld();
        tBitki = new TextureRegion(new Texture("va.png"), 64,32);
        setBounds(x,y,64,32);
        setRegion(tBitki);
        bodyify();
    }


   public void bodyify(){
        BodyDef b= new BodyDef();
        b.position.set(getX(), getY());
        b.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(b);

        FixtureDef f = new FixtureDef();
        CircleShape s = new CircleShape();
        s.setRadius(16);
        f.shape = s;
        body.createFixture(f);
    }
}
