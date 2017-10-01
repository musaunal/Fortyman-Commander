package com.mudan.fortyman.Elemanlar.MapElemanı;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mudan.fortyman.Elemanlar.AskerKalıp;
import com.mudan.fortyman.Fortyman;
import com.mudan.fortyman.screens.MapScreen;

/**
 * Created by musa on 1.10.2017.
 */

public class Ordu extends Sprite {

    protected World world;
    public Body body;

    public Ordu (MapScreen screen){
        world = screen.getWorld();
        bodyify();
        setBounds(400,400,40,10);
//        setRegion(states.get(AskerKalıp.Vaziyet.DURGUN));
    }

    public void update(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            body.applyLinearImpulse(new Vector2(0,1), body.getWorldCenter() ,true);
        }if (Gdx.input.isKeyPressed(Input.Keys.S)){
            body.applyLinearImpulse(new Vector2(0,-1), body.getWorldCenter() ,true);
        }if (Gdx.input.isKeyPressed(Input.Keys.A)){
            body.applyLinearImpulse(new Vector2(-1,0), body.getWorldCenter() ,true);
        }if (Gdx.input.isKeyPressed(Input.Keys.D)){
            body.applyLinearImpulse(new Vector2(1,0), body.getWorldCenter() ,true);
        }

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
//        fdef.filter.categoryBits = Fortyman.ASKER;
//        fdef.filter.maskBits = Fortyman.NESNE;
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(40,20);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }
}
