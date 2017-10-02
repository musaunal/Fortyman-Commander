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
import com.mudan.fortyman.Elemanlar.AskerKalıp;
import com.mudan.fortyman.Elemanlar.Bitki;
import com.mudan.fortyman.Elemanlar.Piyade;
import com.mudan.fortyman.Fortyman;
import com.mudan.fortyman.screens.MapScreen;
import com.mudan.fortyman.screens.PlayScreen;

/**
 * Created by musa on 1.10.2017.
 */

public class Ordu extends Sprite {

    protected World world;
    public Body body;
    public Texture army;
    private Array<Piyade> askerler;
    protected boolean savasBasladıMı;

    public enum Format {DEFAULT , KISKAC}
    private Format vaziyet;
    private boolean formatDegisMi;
    private boolean ready;      // format değişimi bitti mi


    public Ordu (MapScreen screen){
        world = screen.getWorld();
        army = new Texture("18.png");
        setBounds(400,400,80,40);
        setRegion(army);
        savasBasladıMı = false;
        bodyify();
        askerler = new Array<Piyade>();
        vaziyet = Format.DEFAULT;
        formatDegisMi = false;
    }

    public void girdiler(){
        if (!savasBasladıMı) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                body.applyLinearImpulse(new Vector2(0, 1), body.getWorldCenter(), true);
            }if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                body.applyLinearImpulse(new Vector2(0, -1), body.getWorldCenter(), true);
            }if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);
            }if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);
            }
        }else {
            if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
                vaziyet = Format.DEFAULT;
                formatDegisMi = true;
                ready = false;
            }if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
                vaziyet = Format.KISKAC;
                formatDegisMi = true;
                ready = false;
            }if (Gdx.input.isKeyPressed(Input.Keys.E)) {      // koordiantları loglar
                for (Piyade asker : askerler) {
                    Gdx.app.log("sayı" + asker.getAskerID(), ": " + asker.getX() + " " + asker.getY());
                }
            }
        }
    }

    public void update(){
        if (savasBasladıMı){
            formatcı();
            for (Piyade asker :askerler)
                asker.update();
        }else{
            restitution();
            setPosition(body.getPosition().x-getWidth()/2, body.getPosition().y-getHeight()/2);
        }
        girdiler();
//        Gdx.app.log("==" +body.getPosition().x ," " + body.getPosition().y);
    }

    public boolean defaultFormasyon(){             // dikdörtgen dizilim
        float hizaciX = askerler.get(0).body.getPosition().x, hizaciY = askerler.get(0).body.getPosition().y+32;
        for (int i=0; i<40; i++){
            if (i %10 == 0)
                hizaciY -=32;
            askerler.get(i).hizayaSok(hizaciX - i%10*32, hizaciY);
        }
        if(Math.abs(askerler.get(0).getX() - askerler.get(1).getX() -32) <= 2)
            return true;
        else
            return false;
    }

    public boolean kıskacFormasyon(){
        int sayac =0;
        float hizaciX = askerler.get(0).body.getPosition().x,
                hizaciY = askerler.get(0).body.getPosition().y,
                simetri = -288,
                temp = hizaciX;
        for (int i=1; i<6; i++) {
            for (int j = 0; j <i ; j++) {
                askerler.get(sayac).hizayaSok(hizaciX, hizaciY);
                sayac++;
                askerler.get(sayac).hizayaSok(hizaciX +simetri ,hizaciY);
                sayac++;
                hizaciX -=32;
                simetri +=64;
            }
            hizaciY -= 32;
            hizaciX = temp;
            simetri = -288;
        }

        for (int i=sayac; i<askerler.size; i++){
            askerler.get(i).hizayaSok(hizaciX,hizaciY);
            hizaciX -= 32;
        }

        if(Math.abs(askerler.get(0).getX() - askerler.get(1).getX() -288) <= 2)  // aradaki fark 0-2 arasında mı ?
            return true;
        else
            return false;
    }

    public void formatcı (){
        if (formatDegisMi){
            if (!ready) {
                switch (vaziyet){
                    case DEFAULT:
                        ready = defaultFormasyon();
                        break;
                    case KISKAC:
                        ready = kıskacFormasyon();
                        break;
                }
            }
            else{
                formatDegisMi = false;
            }
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
        fdef.filter.categoryBits = Fortyman.KUTU_ORDU;
        fdef.filter.maskBits = Fortyman.KUTU_DUSMAN;
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
            for (Piyade asker : askerler){
                asker.draw(batch);
            }
    }

    public void savasBaslat(DusmanOrdu ordu){
        savasBasladıMı = true;
    }

    public boolean getSavasBasladıMı (){
        return savasBasladıMı;
    }

    public Array<Piyade> getAskerler() {
        return askerler;
    }
}
