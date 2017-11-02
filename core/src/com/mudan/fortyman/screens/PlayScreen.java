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
import com.mudan.fortyman.Elemanlar.MapElemanı.DusmanOrdu;
import com.mudan.fortyman.Elemanlar.MapElemanı.Ordu;
import com.mudan.fortyman.Elemanlar.Piyade;
import com.mudan.fortyman.Elemanlar.düşmanlar.DusmanPiyade;
import com.mudan.fortyman.Fortyman;

/**
 * Created by musa on 25.07.2017.
 */

public class PlayScreen implements Screen{
    private OrthographicCamera camera;
    private Viewport viewport;
    private Fortyman fortyman;
    private Array<Bitki> bitkiler;
    private Ordu ordu;
    private DusmanOrdu dusman;
    private boolean kameraOdakMı;

    private World world;
    private Box2DDebugRenderer b2dr;

    // to start play screen there need to be a battle betwwen to armies so that we take these parameters
    public PlayScreen(Fortyman fortyman, MapScreen map , Ordu ordu , DusmanOrdu dusman) {
        this.fortyman = fortyman;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Fortyman.WITDH , Fortyman.HEIGHT , camera);
        camera.position.set(viewport.getWorldWidth()/2 , viewport.getWorldHeight()/2 ,0);
        camera.setToOrtho(false);

        world = new World(new Vector2(0,0), false);
        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new CollisionHandler());
        kameraOdakMı = true;

        this.ordu = ordu;
        this.dusman = dusman;
        generateArmy(40);
        generateDusman(40);
        generateBitki();

    }

    public void generateBitki(){
        bitkiler = new Array<Bitki>();
        float y=800;
        for (int i=0; i<40; i++){
            if (i%10 == 0)
                y -= 80;
            bitkiler.add(new Bitki(this,(i%10)*90,y));
      }
    }

    public void generateArmy(int miktar){       // cavusa göre düzelt
        float hizaciX = 760, hizaciY = 312;
        ordu.getCavus().cavusUret(600,332,this);
        for (int i=0; i<miktar; i++){
            if (i%10 == 0)
                hizaciY -= 32;
            ordu.getAskerler().add(new Piyade(i+"ordu" ,this, hizaciX - i%10*32 , hizaciY ));
        }
    }

    public void generateDusman(int miktar){
        float hizaciX = 760, hizaciY = 1012;
        for (int i=0; i<miktar; i++){
            if (i%10 == 0)
                hizaciY -= 32;
            dusman.getAskerler().add(new DusmanPiyade(i+"dusman",this, hizaciX - i%10*32 , hizaciY ));
        }
    }

    public void handleInput(){
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
        }if (Gdx.input.isKeyPressed(Input.Keys.E)){
            Gdx.app.log("came" , ""+camera.position.x+ " "+camera.position.y);
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
        world.step(1, 6, 2);        // en soldaki elemanı küçülttükçe alengirli hesaplamalara giriyor küçülme fazla
        ordu.update(dt);
        dusman.update(dt);

        if (kameraOdakMı) {
            camera.update();
            if ((int)camera.position.x != (int)ordu.getAskerler().get(0).getX() -144 )     // eşitse posi
                camera.position.x = ((int)camera.position.x - (int)ordu.getAskerler().get(0).getX() +144) <1 ? camera.position.x + 1 : camera.position.x - 1;
            if ((int)camera.position.y != (int) ordu.getAskerler().get(0).getY() +150 )
                camera.position.y = ((int) camera.position.y - (int) ordu.getAskerler().get(0).getY() -150) <1 ? camera.position.y + 1 : camera.position.y - 1;
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(128 / 255f, 187 / 255f, 96 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        b2dr.render(world, camera.combined);
        fortyman.batch.setProjectionMatrix(camera.combined);

        fortyman.batch.begin();
        ordu.draw(fortyman.batch);
        dusman.draw(fortyman.batch);
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
