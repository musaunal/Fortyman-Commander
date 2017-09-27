package com.mudan.fortyman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mudan.fortyman.Fortyman;

/**
 * Created by musa on 24.07.2017.
 */

public class MainMenu implements Screen {

    private Fortyman fortyman;
    protected OrthographicCamera camera;
    Stage stage;
    Viewport viewport;
    Image start;
    Label exit;
    boolean isStart;

    public MainMenu (Fortyman fortyman) {
        this.fortyman = fortyman;
        fortyman = new Fortyman();
        camera = new OrthographicCamera(400,400);
        viewport = new FitViewport(Fortyman.WITDH, Fortyman.HEIGHT ,camera);
        stage = new Stage(viewport, this.fortyman.batch);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle style = new Label.LabelStyle(new BitmapFont(), Color.CYAN);

        exit = new Label("EXIT", style);
        start = new Image(new Texture("Start.png"));
        exit.setFontScale(5);
//GUI CODES
        Table table = new Table();
        table.setFillParent(true);
        table.center().add(start).expandX().row();
        table.add(exit).padTop(220);
        stage.addActor(table);

        isStart = false;
    }

    public void handleInput(){
        start.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isStart= true;
                return true;
            }
        });
        exit.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
    }

    public void update(float dt){
        handleInput();
        if (isStart){
            dispose();
            fortyman.setScreen(new PlayState(fortyman));
        }
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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

    @Override
    public void dispose() {
       stage.dispose();
    }
}
