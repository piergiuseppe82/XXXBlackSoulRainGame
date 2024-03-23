package bitmaker.games.mytestgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import bitmaker.games.mytestgdx.screens.MainMenuScreen;

public class XXXBlackSoulRainGame  extends Game{
	public static final int WINDOW_H = 720;
	public static final int WINDOW_W = 1280;
	public static final int FPS = 60;

	public SpriteBatch batch;
	public BitmapFont font;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render(); 
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

}
