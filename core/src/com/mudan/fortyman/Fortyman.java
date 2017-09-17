package com.mudan.fortyman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mudan.fortyman.screens.MainMenu;

public class Fortyman extends Game {

	public  static int WITDH = 800;
	public  static int HEIGHT = 800;
	public  static final String TITLE = "FORTY";

	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
