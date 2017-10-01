package com.mudan.fortyman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mudan.fortyman.CollisionHandler;
import com.mudan.fortyman.Elemanlar.Bitki;
import com.mudan.fortyman.Elemanlar.Piyade;
import com.mudan.fortyman.Fortyman;

import static com.mudan.fortyman.screens.PlayState.Format.*;

/**
 * Created by musa on 25.07.2017.
 */

public class PlayState implements Screen{
    private OrthographicCamera camera;
    private Viewport viewport;
    private Fortyman fortyman;
    private Array<Piyade> askerler;
    private Array<Bitki> bitkiler;
    public enum Format {DEFAULT , KISKAC}
    private Format vaziyet;
    private boolean formatDegisMi;
    private boolean ready;      // format değişimi bitti mi
    private boolean kameraOdakMı;

    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayState(Fortyman fortyman) {
        this.fortyman = fortyman;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Fortyman.WITDH , Fortyman.HEIGHT , camera);
        camera.position.set(viewport.getWorldWidth()/2 , viewport.getWorldHeight()/2 ,0);
        camera.setToOrtho(false);

        world = new World(new Vector2(0,0), false);
        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new CollisionHandler());

        createSoldiers(40);
        vaziyet = DEFAULT;
        formatDegisMi = false;
        kameraOdakMı = true;
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
        askerler = new Array<Piyade>();
        float hizaciX = 760, hizaciY = 312;
        for (int i=0; i<miktar; i++){
            if (i%10 == 0)
                hizaciY -= 32;
            askerler.add(new Piyade(i,this, hizaciX - i%10*32 , hizaciY ));
        }
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

    public void handleInput(){
        /*if (Gdx.input.isKeyPressed(Input.Keys.W)){
            for (Piyade asker : askerler)
                asker.body.applyLinearImpulse(new Vector2(0,1), asker.body.getWorldCenter(), true);
        }else if (Gdx.input.isKeyPressed(Input.Keys.S)){
            for (Piyade asker : askerler)
                asker.setPosition(asker.getX(), asker.getY()-1);
        }if (Gdx.input.isKeyPressed(Input.Keys.A)){
            for (Piyade asker : askerler)
                asker.setPosition(asker.getX()-1, asker.getY());
        }else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            for (Piyade asker : askerler)
                asker.setPosition(asker.getX() + 1, asker.getY());
        }*/
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            vaziyet = DEFAULT;
            formatDegisMi = true;
            ready = false;
        }if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            vaziyet = KISKAC;
            formatDegisMi = true;
            ready = false;
        }if (Gdx.input.isKeyPressed(Input.Keys.E)){      // koordiantları loglar
            for (Piyade asker : askerler) {
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
        /*if (Gdx.input.isKeyPressed(Input.Keys.M)){
            dispose();
            fortyman.setScreen(new MapScreen(fortyman));
        }*/

    }

    public void update(float dt){
        handleInput();
        camera.update();
        formatcı();
        world.step(1, 6, 2);        // en soldaki elemanı küçülttükçe alengirli hesaplamalara giriyor küçülme fazla
        for (Piyade asker :askerler)
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
        Gdx.gl.glClearColor(128 / 255f, 187 / 255f, 96 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, camera.combined);
        fortyman.batch.setProjectionMatrix(camera.combined);

        fortyman.batch.begin();
        for (Piyade asker : askerler){
            asker.draw(fortyman.batch);
        }
        for (Bitki b : bitkiler)
            b.draw(fortyman.batch);
        fortyman.batch.end();
    }

    @Override
    public void dispose() {

        world.dispose();
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

    public World getWorld(){
        return world;
    }
}
