package com.mudan.fortyman.Elemanlar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mudan.fortyman.Fortyman;
import com.mudan.fortyman.screens.PlayScreen;

/**
 * Created by musa on 2.11.2017.
 */

public class Cavus extends AskerKalıp {

public Cavus() {
        super((float)(Math.random()*10)/8+0.07f);
    }

    public void update (float dt){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            setState(Vaziyet.KOS, dt);
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                body.applyLinearImpulse(new Vector2(1, 1), body.getWorldCenter(), true);
            else if (Gdx.input.isKeyPressed(Input.Keys.A))
                body.applyLinearImpulse(new Vector2(-1, 1), body.getWorldCenter(), true);
            else
                body.applyLinearImpulse(new Vector2(0, 1), body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            setState(Vaziyet.KOS, dt);
            if (Gdx.input.isKeyPressed(Input.Keys.D))
                body.applyLinearImpulse(new Vector2(1, -1), body.getWorldCenter(), true);
            else if (Gdx.input.isKeyPressed(Input.Keys.A))
                body.applyLinearImpulse(new Vector2(-1, -1), body.getWorldCenter(), true);
            else
                body.applyLinearImpulse(new Vector2(0, -1), body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            setState(Vaziyet.KOS, dt);
            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            setState(Vaziyet.KOS, dt);
            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);
        } else {
            setState(Vaziyet.DURGUN, dt);
        }
            restitution();
            stateTimer = onceki == suanki ? stateTimer + dt : 0;

        setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
    }

    public void cavusUret(float x, float y, PlayScreen screen){
        setBounds(x,y,32,32);
        world = screen.getWorld();
        bodyify();
    }

    public void bodyify (){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX()+getWidth()/2,getY()+getHeight()/2);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        fdef.filter.categoryBits = Fortyman.PIYADE;
        fdef.filter.maskBits = Fortyman.NESNE ;
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(8,8);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
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



}
