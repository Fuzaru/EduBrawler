package com.edubrawlers.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("EduBrawlers");
		config.setWindowIcon("GameLogo.png", "GameLogo.png");
		// Set the window size
		config.setWindowedMode(1280, 720); // Set your desired window size here
		new Lwjgl3Application(new EduBrawlersGame(), config);
	}
}
