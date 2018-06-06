package com.claudiubogdan.scene.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.claudiubogdan.scene.prototype.*;

public class DesktopLauncher {
/*	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new SceneNavegator(), config);
	}*/
/*public static void main(String[] arg) {
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
	config.width = (int) Core.VIRTUAL_WIDTH;
	config.height = (int) Core.VIRTUAL_HEIGHT;
	new LwjglApplication(new Core(), config); *//**//*
}*/
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int) Core.VIRTUAL_WIDTH;
		config.height = (int) Core.VIRTUAL_HEIGHT;
		new LwjglApplication(new Core(), config);
	}
}
