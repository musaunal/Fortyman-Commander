package com.mudan.fortyman.Elemanlar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

/**
 * Created by musa on 28.09.2017.
 */

public class AskerKalıp extends Sprite {

    protected HashMap<Vaziyet, Animation<TextureRegion>> states;
    public enum Vaziyet {DURGUN , MEFTA ,HUCUM ,KOS}
    protected Vaziyet suanki , onceki;
    protected Animation<TextureRegion> kos;
    protected Animation<TextureRegion> savas;
    protected Animation<TextureRegion> hucum;
    protected Animation<TextureRegion> mefta;
    protected Animation<TextureRegion> dur;
    protected Array<TextureRegion> frames;
    protected float stateTimer = 0;

    public AskerKalıp (float timer ){
        frames = new Array<TextureRegion>();
        for (int i=0; i<10; i++){
            frames.add(new TextureRegion(new Texture("hucum.png"), i*32, 0, 32, 32));
        }for (int i=9; i>=0; i--){
            frames.add(new TextureRegion(new Texture("hucum.png"), i*32, 0, 32, 32));
        }hucum = new Animation<TextureRegion>(timer, frames);
        frames.clear();

        for (int i=0; i<10; i++){
            frames.add(new TextureRegion(new Texture("dur.png"), i*32, 0, 32, 32));
        }for (int i=9; i>=0; i--){
            frames.add(new TextureRegion(new Texture("dur.png"), i*32, 0, 32, 32));
        }dur = new Animation<TextureRegion>(timer, frames);
        frames.clear();

        for (int i=0; i<10; i++){
            frames.add(new TextureRegion(new Texture("kos.png"), i*32, 0, 32, 32));
        }for (int i=9; i>=0; i--){
            frames.add(new TextureRegion(new Texture("kos.png"), i*32, 0, 32, 32));
        }kos = new Animation<TextureRegion>(timer, frames);
        frames.clear();

        for (int i=0; i<10; i++){
            frames.add(new TextureRegion(new Texture("mefta.png"), i*32, 0, 32, 32));
        }for (int i=9; i>=0; i--){
            frames.add(new TextureRegion(new Texture("mefta.png"), i*32, 0, 32, 32));
        }mefta = new Animation<TextureRegion>(timer, frames);

        states = new HashMap<Vaziyet, Animation<TextureRegion>>();
        states.put(Vaziyet.DURGUN, dur);
        states.put(Vaziyet.HUCUM, hucum);
        states.put(Vaziyet.KOS, kos);
        states.put(Vaziyet.MEFTA, mefta);

        suanki = onceki = Vaziyet.DURGUN;
        setState(Vaziyet.DURGUN, Gdx.graphics.getDeltaTime());
    }

    protected String askerID;
    protected World world;
    public Body body;

    public void setState(Vaziyet vaziyet, float dt){
        suanki = vaziyet;
        switch (suanki){
            case DURGUN:
                setRegion(dur.getKeyFrame(stateTimer, true));
                break;
            case HUCUM:
                setRegion(hucum.getKeyFrame(stateTimer, true));     // true looping yapıyor
                break;
            case MEFTA:
                setRegion(mefta.getKeyFrame(stateTimer));
                break;
            case KOS:
                setRegion(kos.getKeyFrame(stateTimer, true));
                break;
        }
        stateTimer = onceki == suanki ? stateTimer + dt : 0;
        onceki = suanki;
    }

    /*private TextureRegion getFrame(float dt){
        if ((b2body.getLinearVelocity().x <0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
*/
    public String getAskerID() {
        return askerID;
    }

}
