package com.mudan.fortyman.Elemanlar.MapElemanı;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mudan.fortyman.Elemanlar.düşmanlar.DusmanPiyade;
import com.mudan.fortyman.Fortyman;
import com.mudan.fortyman.screens.MapScreen;

/**
 * Created by musa on 1.10.2017.
 */

public class DusmanOrdu extends Sprite {
    protected World world;
    private Body body;
    public Texture army;
    private Array<DusmanPiyade> askerler;
    private boolean savasBasladıMı;

    public DusmanOrdu (MapScreen screen){
        world = screen.getWorld();
        army = new Texture("9.png");
        setBounds(900,1000,80,40);
        setRegion(army);

//            Gdx.app.log(" T" + getX(), " " +getY());
        bodyify();
        askerler = new Array<DusmanPiyade>();
    }

    public void update(){
        if (savasBasladıMı){
            for (DusmanPiyade asker :askerler)
                asker.update();
        }else {
            restitution();
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        }
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
        fdef.filter.categoryBits = Fortyman.KUTU_DUSMAN;
        fdef.filter.maskBits = Fortyman.KUTU_ORDU;
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(40,20);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        if (!savasBasladıMı)
            super.draw(batch);
        if (savasBasladıMı)
            for (DusmanPiyade asker : askerler){
                asker.draw(batch);
            }
    }

    public void setSavasBasladıMı(boolean ifade) {
        savasBasladıMı = ifade;
    }

    public Array<DusmanPiyade> getAskerler() {
        return askerler;
    }
}
