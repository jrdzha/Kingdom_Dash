package com.jahrud.kingdomdash.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jahrud.kingdomdash.GameEngine;

public class DesktopLauncher{


	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Kingdom Dash";
		config.useGL30 = false;
		config.width = 750 / 2;
		config.height = 1334 / 2;
		config.fullscreen = false;

		new LwjglApplication(new GameEngine(), config);
	}
}
