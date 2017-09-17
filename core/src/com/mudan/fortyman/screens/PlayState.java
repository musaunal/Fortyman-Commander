package com.mudan.fortyman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mudan.fortyman.Fortyman;
import com.mudan.fortyman.Players;

import static com.mudan.fortyman.screens.PlayState.Format.*;

/**
 * Created by musa on 25.07.2017.
 */

public class PlayState implements Screen{
    private OrthographicCamera camera;
    private Viewport viewport;
    private Fortyman fortyman;
    private Array<Players> askerler;
    public enum Format {DEFAULT , KISKAC}
    private Format vaziyet;
    private boolean formatDegisMi;
    private boolean ready;
    private float timer;

    public PlayState(Fortyman fortyman) {
        this.fortyman = fortyman;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Fortyman.WITDH, Fortyman.HEIGHT, camera);
        askerler = new Array<Players>();
        createSoldiers(40);
        vaziyet = DEFAULT;
        formatDegisMi = false;
        timer = 0;
    }

    public void createSoldiers(int miktar){
        for (int i=0; i<40; i++){
            askerler.add(new Players(i+1));
            askerler.get(i).setPosition(0,0);
        }
        askerler.get(0).makeMefta();
        askerler.get(1).makeMefta();
        int sayac =0, hizaciX = 560, hizaciY = 480;
        int temp = hizaciX;
        for (int i=0; i<4; i++){
            for (int j =0; j<10; j++){
                askerler.get(sayac).setPosition(hizaciX,hizaciY);
                hizaciX -= 32;
                //         Gdx.app.log("sayı" ,""+askerler.get(0).getX() +" "+ askerler.get(0).getY());
                sayac++;
            }
            hizaciX = temp ;
            hizaciY-= 32;
        }

    }

    public boolean defaultFormasyon(){             // dikdörtgen dizilim
        int sayac =0,
            hizaciX = (int)askerler.get(0).getX(),
            hizaciY = (int)askerler.get(0).getY(),
            temp = hizaciX;
        for (int i=0; i<4; i++){
            for (int j =0; j<10; j++){
                askerler.get(sayac).hizayaSok(hizaciX, hizaciY);
                hizaciX -= 32;
                //         Gdx.app.log("sayı" ,""+askerler.get(0).getX() +" "+ askerler.get(0).getY());
                sayac++;
            }
            hizaciX = temp ;
            hizaciY-= 32;
        }
        if(askerler.get(0).getX() - askerler.get(1).getX() == 32)
            return true;
        else
            return false;
    }

    public boolean kıskacFormasyon(){
        int sayac =0,
            hizaciX = (int)askerler.get(0).getX(),
            hizaciY = (int)askerler.get(0).getY(),
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
        hizaciY -= 32;
        for (int i=sayac; i<askerler.size; i++){
            askerler.get(i).hizayaSok(hizaciX,hizaciY);
            hizaciX -= 32;
        }

        if(askerler.get(0).getX() - askerler.get(1).getX() == 288)
            return true;
        else
            return false;
    }

    public void handleInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            for (Players asker : askerler)
                asker.setPosition(asker.getX(), asker.getY()+1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            for (Players asker : askerler)
                asker.setPosition(asker.getX(), asker.getY()-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            for (Players asker : askerler)
                asker.setPosition(asker.getX()-1, asker.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            for (Players asker : askerler)
                asker.setPosition(asker.getX()+1, asker.getY());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            vaziyet = DEFAULT;
            formatDegisMi = true;
            ready = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            vaziyet = KISKAC;
            formatDegisMi = true;
            ready = false;
        }
    }

  /*  public void update(float dt){
        handleInput();
        if (formatDegisMi){
            setFormat();
        }
    }*/

    @Override
    public void render(float delta) {
        handleInput();
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

        Gdx.gl.glClearColor(128 / 255f, 187 / 255f, 96 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fortyman.batch.begin();
        for (Players asker : askerler){
            asker.draw(fortyman.batch);
        }
        fortyman.batch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
