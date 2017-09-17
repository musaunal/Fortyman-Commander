package com.mudan.fortyman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mudan.fortyman.Fortyman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Fortyman.TITLE;
		config.width = Fortyman.WITDH;
		config.height = Fortyman.HEIGHT;
		new LwjglApplication(new Fortyman(), config);
	}
}
