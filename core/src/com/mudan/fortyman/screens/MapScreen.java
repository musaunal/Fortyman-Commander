package com.mudan.fortyman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mudan.fortyman.CollisionHandler;
import com.mudan.fortyman.Elemanlar.MapElemanı.DusmanOrdu;
import com.mudan.fortyman.Elemanlar.MapElemanı.Ordu;
import com.mudan.fortyman.Fortyman;

/**
 * Created by musa on 1.10.2017.
 */

public class MapScreen implements Screen {

    private Fortyman fortyman;
    private OrthographicCamera camera;
    private Viewport viewport;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Ordu ordu;
    private Texture map;
    private DusmanOrdu dusman;

    public MapScreen(Fortyman fortyman) {
        this.fortyman = fortyman;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Fortyman.WITDH , Fortyman.HEIGHT , camera);
        camera.position.set(viewport.getWorldWidth()/2 , viewport.getWorldHeight()/2 ,0);
        camera.setToOrtho(false);

        world = new World(new Vector2(0,0), false);
        b2dr = new Box2DDebugRenderer();
        world.setContactListener(new CollisionHandler());

        ordu = new Ordu(this);
        ordu.setPosition(400,400);
        map = new Texture("map.jpg");
        dusman = new DusmanOrdu(this);
    }


    public void update(){
        ordu.update();
        dusman.update();
        camera.update();
        world.step(10, 6, 2);        // en soldaki elemanı küçülttükçe alengirli hesaplamalara giriyor küçülme fazla

        if ((int)camera.position.x != ordu.body.getPosition().x  )     // eşitse posi
            camera.position.x = ((int)camera.position.x - (int)ordu.body.getPosition().x ) <1 ? camera.position.x + 1 : camera.position.x - 1;
        if ((int)camera.position.y != (int) ordu.body.getPosition().y  )
            camera.position.y = ((int) camera.position.y - (int)ordu.body.getPosition().y ) <1 ? camera.position.y + 1 : camera.position.y - 1;

        if (ordu.getSavasBasladıMı()){
            dispose();
            fortyman.setScreen(new PlayScreen(fortyman, this ,ordu));
        }
    }

    @Override
    public void render(float delta) {
        update();
//        Gdx.gl.glClearColor(108 / 255f, 180 / 255f, 196 / 255f, 1);
        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fortyman.batch.begin();
        fortyman.batch.draw(map, -500,-400,1756,1530);
        ordu.draw(fortyman.batch);
        dusman.draw(fortyman.batch);
        fortyman.batch.end();
//        b2dr.render(world, camera.combined);
        fortyman.batch.setProjectionMatrix(camera.combined);
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

    @Override
    public void dispose() {
        b2dr.dispose();
        map.dispose();
        ordu.army.dispose();
        dusman.army.dispose();
        world.dispose();
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void show() {

    }
}
