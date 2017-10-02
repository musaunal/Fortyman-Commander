package com.mudan.fortyman;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mudan.fortyman.screens.MainMenu;

public class Fortyman extends Game {

	public  static int WITDH = 800;
	public  static int HEIGHT = 800;
	public  static final String TITLE = "FORTY";
	public SpriteBatch batch;

	public static final short NESNE = 2;
	public static final short PIYADE = 4;
	public static final short KUTU_ORDU = 8;
	public static final short KUTU_DUSMAN = 16;
	public static final short DUSMAN_PIYADE = 32;



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
