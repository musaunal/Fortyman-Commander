package com.mudan.fortyman.Elemanlar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mudan.fortyman.screens.PlayState;

/**
 * Created by musa on 21.09.2017.
 */

public class Bitki extends Sprite {
    private TextureRegion tBitki;
    protected Rectangle kare;

    public Bitki(PlayState state, float x, float y){
        tBitki = new TextureRegion(new Texture("va.png"), 64,32);
        setBounds(x,y,64,32);
        setRegion(tBitki);
        kare = new Rectangle(getX(),getY(),64,32);
    }

/*
   public void bitkiCollision(float x, float y){
        BodyDef b= new BodyDef();
        b.position.set(x, y);
        b.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(b);
        Gdx.app.log("a" ,""+body.getPosition().x+" "+ body.getPosition().y);

        FixtureDef f = new FixtureDef();
        CircleShape s = new CircleShape();
        s.setRadius(16);
        f.shape = s;
        body.createFixture(f);
    }*/

    public void collide (Rectangle diger){
        if (kare.overlaps(diger)){
            // temas gerçekleşti
        }
    }

}
