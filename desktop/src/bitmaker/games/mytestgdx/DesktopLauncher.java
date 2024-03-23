package bitmaker.games.mytestgdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(XXXBlackSoulRainGame.FPS);
		config.setWindowedMode(XXXBlackSoulRainGame.WINDOW_W, XXXBlackSoulRainGame.WINDOW_H);
	    config.useVsync(true);
		config.setTitle("XXXBlackSoulRainGame -  v0.1.alpha");
		new Lwjgl3Application(new XXXBlackSoulRainGame(), config);
	}
}
