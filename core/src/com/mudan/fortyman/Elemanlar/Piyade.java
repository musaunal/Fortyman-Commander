package com.mudan.fortyman.Elemanlar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mudan.fortyman.Fortyman;
import com.mudan.fortyman.screens.PlayState;

import java.util.HashMap;

/**
 * Created by musa on 16.09.2017.
 */

public class Piyade extends AskerKalıp {

    public Piyade(int askerID, PlayState state, float x , float y) {
        this.world = state.getWorld();
        states = new HashMap<Vaziyet, TextureRegion>();
        states.put(Vaziyet.DURGUN, new TextureRegion(new Texture("warrior1.png"), 0,0,32,32));
        states.put(Vaziyet.HUCUM, new TextureRegion(new Texture("warrior1.png"), 96,0,32,32));
        states.put(Vaziyet.KOS, new TextureRegion(new Texture("warrior1.png"), 32,0,32,32));
        states.put(Vaziyet.MEFTA, new TextureRegion(new Texture("warrior1.png"), 128,0,32,32));
        this.askerID = askerID;

        suanki = onceki = Vaziyet.DURGUN;
        setBounds(x,y,32,32);
        setRegion(states.get(Vaziyet.DURGUN));
        bodyify();
    }

    public void hizayaSok(float x, float y) {       // impulse restitutiondan etkileniyor  && traverse düzgün çalışıyor
      /*  if (body.getPosition().x > x - 1 && body.getPosition().x < x + 1 && body.getPosition().y > y - 1 && body.getPosition().y < y + 1) {
            body.setLinearVelocity(0, 0);
            return;
        }
        if (body.getPosition().x < x)
            body.applyLinearImpulse(new Vector2(0.7f, 0), body.getWorldCenter(), true);
        if (body.getPosition().x > x)
            body.applyLinearImpulse(new Vector2(-0.7f, 0), body.getWorldCenter(), true);
        if (body.getPosition().y < y)
            body.applyLinearImpulse(new Vector2(0, 0.8f), body.getWorldCenter(), true);
        if (body.getPosition().y > y)
            body.applyLinearImpulse(new Vector2(0, -0.7f), body.getWorldCenter(), true);
    }*/
        if (!(body.getPosition().x > x-0.3f && body.getPosition().x < x+0.3f) || !(body.getPosition().y > y-0.1f && body.getPosition().y < y + 0.1f)) {
            body.setTransform(body.getPosition().x < x ? body.getPosition().x +1 : body.getPosition().x -1 ,
            body.getPosition().y < y ? body.getPosition().y +1 : body.getPosition().y - 1 , 0);
        }
    }

    public void update (float dt){
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                body.applyLinearImpulse(new Vector2(1,1),body.getWorldCenter(), true);
            else if (Gdx.input.isKeyPressed(Input.Keys.A))
                body.applyLinearImpulse(new Vector2(-1,1),body.getWorldCenter(), true);
            else
                body.applyLinearImpulse(new Vector2(0,1),body.getWorldCenter(), true);
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)){
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                body.applyLinearImpulse(new Vector2(1,-1),body.getWorldCenter(), true);
            else if (Gdx.input.isKeyPressed(Input.Keys.A))
                body.applyLinearImpulse(new Vector2(-1,-1),body.getWorldCenter(), true);
            else
                body.applyLinearImpulse(new Vector2(0,-1),body.getWorldCenter(),true);
        }else if (Gdx.input.isKeyPressed(Input.Keys.A)){
            body.applyLinearImpulse(new Vector2(-1,0),body.getWorldCenter(),true);
        }else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.applyLinearImpulse(new Vector2(1,0), body.getWorldCenter(), true);
        }if (Gdx.input.isKeyPressed(Input.Keys.NUM_5))
            if (askerID == 39)
                hizayaSok(250,250);

        restitution();
        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
    }

    public void restitution(){          //  yavaşlatmak için geçiçi yüzeyin sürtünme kuvveti
        if (body.getLinearVelocity().x > -1 && body.getLinearVelocity().x <1)
            body.setLinearVelocity(0,body.getLinearVelocity().y);
        if (body.getLinearVelocity().y > -1 && body.getLinearVelocity().y <1)
            body.setLinearVelocity(body.getLinearVelocity().x,0);
        if (body.getLinearVelocity().x < 0 )
            body.applyLinearImpulse(new Vector2(0.5f,0),body.getWorldCenter(),true);
        if (body.getLinearVelocity().x > 0 )
            body.applyLinearImpulse(new Vector2(-0.5f,0),body.getWorldCenter(),true);
        if (body.getLinearVelocity().y < 0)
            body.applyLinearImpulse(new Vector2(0,0.5f),body.getWorldCenter(), true);
        if (body.getLinearVelocity().y > 0)
            body.applyLinearImpulse(new Vector2(0,-0.5f),body.getWorldCenter(), true);

    }

    public void bodyify (){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX()+getWidth()/2,getY()+getHeight()/2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = Fortyman.ASKER;
        fdef.filter.maskBits = Fortyman.NESNE;
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(8,8);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    public int getAskerID(){
        return askerID;
    }
}
