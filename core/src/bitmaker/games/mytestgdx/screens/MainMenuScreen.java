package bitmaker.games.mytestgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import bitmaker.games.mytestgdx.XXXBlackSoulRainGame;

public class MainMenuScreen implements Screen {
	final XXXBlackSoulRainGame game;
	OrthographicCamera camera;
	
	private Texture backgroundImage;
	public MainMenuScreen(XXXBlackSoulRainGame game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, XXXBlackSoulRainGame.WINDOW_W, XXXBlackSoulRainGame.WINDOW_H);
		backgroundImage = new Texture(Gdx.files.internal("images/bgW.png"));
	}
	
	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		this.game.batch.draw(backgroundImage, 0,0);
		game.font.draw(game.batch, "Welcome to XXXBlackSoulRainGame!!! ", XXXBlackSoulRainGame.WINDOW_W/2-100, XXXBlackSoulRainGame.WINDOW_H/2-200);
		game.font.draw(game.batch, "Tap anywhere to begin!", XXXBlackSoulRainGame.WINDOW_W/2-50,  XXXBlackSoulRainGame.WINDOW_H/2-300);
		game.batch.end();

		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

}
