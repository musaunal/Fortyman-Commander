package com.mudan.fortyman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by musa on 16.09.2017.
 */

public class Players extends Sprite {

    private TextureRegion durgun, mefta, saldırı, kos;
    public enum Vaziyet {DURGUN , MEFTA ,HUCUM ,KOS}
    protected Vaziyet suanki , onceki;
    protected int askerID;
    protected boolean meftaMı;
    private int durumSayaci;


    public Players(int askerID) {
        this.askerID = askerID;
        durgun = new TextureRegion(new Texture("warrior1.png"), 0,0,32,32);
        kos = new TextureRegion(new Texture("warrior1.png"), 32,0,32,32);
        saldırı = new TextureRegion(new Texture("warrior1.png"), 96,0,32,32);
        mefta = new TextureRegion(new Texture("warrior1.png"), 128,0,32,32);
        suanki = onceki = Vaziyet.DURGUN;
        setBounds(0,0,32,32);
        setRegion(durgun);
        durumSayaci =0;
    }

    public void hizayaSok(int x, int y){

        if((getX() != x || getY() != y)){
            setX(getX() < x ? getX()+1 : getX()-1 );
            setY(getY() < y ? getY()+1 : getY()-1);
        }
    }

    public void makeMefta(){
        setRegion(mefta);
    }
}
