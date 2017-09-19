package com.mudan.fortyman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
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
    private boolean kameraOdakMı;
    private float timer;

    private Body bt;
    private World world;
    private Box2DDebugRenderer renderer;

    public PlayState(Fortyman fortyman) {
        this.fortyman = fortyman;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Fortyman.WITDH , Fortyman.HEIGHT , camera);
        camera.position.set(viewport.getWorldWidth()/2 , viewport.getWorldHeight()/2 ,0);
        camera.setToOrtho(false);

        world = new World(new Vector2(0,0),false);
        askerler = new Array<Players>();
        createSoldiers(40);
        vaziyet = DEFAULT;
        formatDegisMi = false;
        kameraOdakMı = true;
        timer = 0;
/*
        BodyDef b= new BodyDef();
        b.position.set(400,720);
        b.type = BodyDef.BodyType.StaticBody;
        bt = world.createBody(b);

        FixtureDef f = new FixtureDef();
        CircleShape s = new CircleShape();
        s.setRadius(6);
        f.shape = s;
        bt.createFixture(f);
*/
        renderer = new Box2DDebugRenderer();
    }

    public void createSoldiers(int miktar){
        for (int i=0; i<miktar; i++){
            askerler.add(new Players(i+1, this));
            askerler.get(i).setPosition(0 ,0 );
        }
        askerler.get(0).setState(Players.Vaziyet.MEFTA);
        askerler.get(1).setState(Players.Vaziyet.MEFTA);
        int sayac =0;
        float hizaciX = 760, hizaciY = 280;
        float temp = hizaciX;
        for (int i=0; i<4; i++){
            for (int j =0; j<10; j++){
                askerler.get(sayac).setPosition(hizaciX,hizaciY);
                hizaciX -= 32;
                sayac++;
            }
            hizaciX = temp ;
            hizaciY-= 32;
        }
    }

    public boolean defaultFormasyon(){             // dikdörtgen dizilim
        int sayac =0;
        float hizaciX = askerler.get(0).getX(),
            hizaciY = askerler.get(0).getY(),
            temp = hizaciX;
        for (int i=0; i<4; i++){
            for (int j =0; j<10; j++){
                askerler.get(sayac).hizayaSok(hizaciX, hizaciY);
                hizaciX -= 32;
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
                Gdx.app.log("sayı: ", "" + asker.getX() + " " + asker.getY());
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
        world.step(1/60f, 6, 2); // bunları oyna
        if (kameraOdakMı) {
            camera.position.x = askerler.get(0).getX() -144;
            camera.position.y = askerler.get(0).getY() +200;
            camera.update();
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

        renderer.render(world, camera.combined);
        fortyman.batch.setProjectionMatrix(camera.combined);

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

    public World getWorld(){
        return world;
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
