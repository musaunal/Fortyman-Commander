package com.mudan.fortyman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mudan.fortyman.Elemanlar.Bitki;
import com.mudan.fortyman.Fortyman;
import com.mudan.fortyman.Elemanlar.Players;

import static com.mudan.fortyman.screens.PlayState.Format.*;

/**
 * Created by musa on 25.07.2017.
 */

public class PlayState implements Screen{
    private OrthographicCamera camera;
    private Viewport viewport;
    private Fortyman fortyman;
    private Array<Players> askerler;
    private Array<Bitki> bitkiler;
    public enum Format {DEFAULT , KISKAC}
    private Format vaziyet;
    private boolean formatDegisMi;
    private boolean ready;
    private boolean kameraOdakMı;
    private float timer;

    public PlayState(Fortyman fortyman) {
        this.fortyman = fortyman;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Fortyman.WITDH , Fortyman.HEIGHT , camera);
        camera.position.set(viewport.getWorldWidth()/2 , viewport.getWorldHeight()/2 ,0);
        camera.setToOrtho(false);

        createSoldiers(40);
        vaziyet = DEFAULT;
        formatDegisMi = false;
        kameraOdakMı = true;
        timer = 0;
        createBitki();

    }

    public void createBitki (){
        bitkiler = new Array<Bitki>();
        float y=800;
        for (int i=0; i<40; i++){
            if (i%10 == 0)
                y -= 80;
            bitkiler.add(new Bitki(this,(i%10)*90,y));
      }
    }

    public void createSoldiers(int miktar){
        askerler = new Array<Players>();
        float hizaciX = 760, hizaciY = 312;
        for (int i=0; i<miktar; i++){
            if (i%10 == 0)
                hizaciY -= 32;
            askerler.add(new Players(i, this, hizaciX - i%10*32 , hizaciY ));
        }
    }

    public boolean defaultFormasyon(){             // dikdörtgen dizilim
        float hizaciX = askerler.get(0).getX(), hizaciY = askerler.get(0).getY()+32;
        for (int i=0; i<40; i++){
            if (i %10 == 0)
                hizaciY -=32;
            askerler.get(i).hizayaSok(hizaciX - i%10*32, hizaciY);
        }
        if(askerler.get(0).getX() - askerler.get(1).getX() == 32)
            return true;
        else
            return false;
    }

    public boolean kıskacFormasyon(){
        int sayac =0;
        float hizaciX = askerler.get(0).getX(),
            hizaciY = askerler.get(0).getY(),
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
   //     hizaciY -= 32;            // son safdan çnce boşluk atar
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
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)){
            for (Players asker : askerler)
                asker.setPosition(asker.getX(), asker.getY()-1);
        }if (Gdx.input.isKeyPressed(Input.Keys.A)){
            for (Players asker : askerler)
                asker.setPosition(asker.getX()-1, asker.getY());
        }else if (Gdx.input.isKeyPressed(Input.Keys.D)){
            for (Players asker : askerler)
                asker.setPosition(asker.getX()+1, asker.getY());
        }if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            vaziyet = DEFAULT;
            formatDegisMi = true;
            ready = false;
        }if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            vaziyet = KISKAC;
            formatDegisMi = true;
            ready = false;
        }if (Gdx.input.isKeyPressed(Input.Keys.E)){      // koordiantları loglar
            for (Players asker : askerler) {
                Gdx.app.log("sayı"+asker.getAskerID(), ": " + asker.getX() + " " + asker.getY());
            }
            Gdx.app.log("came" , ""+camera.position.x+ " "+camera.position.y);
        }
                /*** camera haraketi ***/
        if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_8)){
            kameraOdakMı = false;
            camera.position.y +=1;
        }else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_2)){
            kameraOdakMı = false;
            camera.position.y -=1;
        }if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)){
            kameraOdakMı = false;
            camera.position.x -=1;
        }else if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)){
            kameraOdakMı = false;
            camera.position.x +=1;
        }if (Gdx.input.isKeyPressed(Input.Keys.R)){
            kameraOdakMı = true;
        }
            /*** camera hareketi biter ***/
    }

    public void update(float dt){
        handleInput();
        camera.update();
        formatcı();
        for (Players asker :askerler)
            asker.update(dt);
        if (kameraOdakMı) {
            camera.update();
            if ((int)camera.position.x != (int)askerler.get(0).getX() -144 )     // eşitse posi
                camera.position.x = ((int)camera.position.x - (int)askerler.get(0).getX() +144) <1 ? camera.position.x + 1 : camera.position.x - 1;
            if ((int)camera.position.y != (int) askerler.get(0).getY() +150 )
                camera.position.y = ((int) camera.position.y - (int) askerler.get(0).getY() -150) <1 ? camera.position.y + 1 : camera.position.y - 1;
        }
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

    @Override
    public void render(float delta) {
        update(delta);

        fortyman.batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(128 / 255f, 187 / 255f, 96 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        fortyman.batch.begin();
        for (Players asker : askerler){
            asker.draw(fortyman.batch);
        }
        for (Bitki b : bitkiler)
            b.draw(fortyman.batch);
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
