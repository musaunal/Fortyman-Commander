package com.mudan.fortyman.Elemanlar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mudan.fortyman.screens.PlayState;

import java.util.HashMap;

/**
 * Created by musa on 16.09.2017.
 */

public class Players extends Sprite {
    private HashMap<Vaziyet, TextureRegion> states;
    public enum Vaziyet {DURGUN , MEFTA ,HUCUM ,KOS}
    protected Vaziyet suanki , onceki;
    protected int askerID;
    private World world;
    public Body body;

    public Players(int askerID, PlayState state, float x , float y) {
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

    public void hizayaSok(float x, float y){
        if((getX() != x || getY() != y)){
            setX(getX() < x ? getX()+1 : getX()-1  );
            setY(getY() < y ? getY()+1 : getY()-1 );
        }
    }

    public void update (float dt){

    }

    public void setState(Vaziyet vaziyet){
        onceki = suanki;
        suanki = vaziyet;
        setRegion(states.get(vaziyet));
    }

    public void bodyify (){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(),getY());
        bodyDef.angle = 0;
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape= new PolygonShape();
        shape.setAsBox(32,32);
        fdef.shape = shape;
        body.createFixture(fdef);
    }

    public int getAskerID(){
        return askerID;
    }
}
