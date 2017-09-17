package com.mudan.fortyman;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.HashMap;

/**
 * Created by musa on 16.09.2017.
 */

public class Players extends Sprite {
    private HashMap<Vaziyet, TextureRegion> states;
    public enum Vaziyet {DURGUN , MEFTA ,HUCUM ,KOS}
    protected Vaziyet suanki , onceki;
    protected int askerID;
    protected boolean meftaMı;
    private int durumSayaci;


    public Players(int askerID) {
        states = new HashMap<Vaziyet, TextureRegion>();
        states.put(Vaziyet.DURGUN, new TextureRegion(new Texture("warrior1.png"), 0,0,32,32));
        states.put(Vaziyet.HUCUM, new TextureRegion(new Texture("warrior1.png"), 96,0,32,32));
        states.put(Vaziyet.KOS, new TextureRegion(new Texture("warrior1.png"), 32,0,32,32));
        states.put(Vaziyet.MEFTA, new TextureRegion(new Texture("warrior1.png"), 128,0,32,32));
        this.askerID = askerID;

        suanki = onceki = Vaziyet.DURGUN;
        setBounds(0,0,32,32);
        setRegion(states.get(Vaziyet.DURGUN));
        durumSayaci =0;
    }

    public void hizayaSok(int x, int y){
        if((getX() != x || getY() != y)){
            setX(getX() < x ? getX()+1 : getX()-1 );
            setY(getY() < y ? getY()+1 : getY()-1);
        }
    }

    public void setState(Vaziyet vaziyet){
        onceki = suanki;
        suanki = vaziyet;
        setRegion(states.get(vaziyet));
    }
}
