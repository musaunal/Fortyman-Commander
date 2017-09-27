package com.mudan.fortyman.Elemanlar;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.HashMap;

/**
 * Created by musa on 28.09.2017.
 */

public class AskerKalıp extends Sprite {

    protected HashMap<Vaziyet, TextureRegion> states;
    public enum Vaziyet {DURGUN , MEFTA ,HUCUM ,KOS}
    protected Vaziyet suanki , onceki;
    protected int askerID;
    protected World world;
    public Body body;

    public void setState(Vaziyet vaziyet){
        onceki = suanki;
        suanki = vaziyet;
        setRegion(states.get(vaziyet));
    }


}
