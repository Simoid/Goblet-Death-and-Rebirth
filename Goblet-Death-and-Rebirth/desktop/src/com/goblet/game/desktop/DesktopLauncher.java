package com.goblet.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.goblet.game.Engine;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Goblet: Death and Rebirth";
		config.backgroundFPS = 12;
		config.foregroundFPS = 60;
		config.fullscreen = true;
		new LwjglApplication(new Engine(), config);
	}
}
