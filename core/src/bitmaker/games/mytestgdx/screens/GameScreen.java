package bitmaker.games.mytestgdx.screens;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import bitmaker.games.mytestgdx.XXXBlackSoulRainGame;

public class GameScreen implements Screen {

	private Texture dropImage;
	private Texture bucketImage;
	private Texture backgroundImage;
	private Texture backgroundImageGO;
	private Sound dropSound;
	private Sound gameOverSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private Rectangle ground;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	private XXXBlackSoulRainGame game;
	private int points;
	private double multiplier;
	private double level = 0.0;
	private int xp = 10;
	private boolean gameover = false;
	private boolean gameoverSoundPlayed = false;

	public GameScreen(XXXBlackSoulRainGame game) {
		this.game = game;
		dropImage = new Texture(Gdx.files.internal("images/drop.png"));
		bucketImage = new Texture(Gdx.files.internal("images/bucket2.png"));
		backgroundImage = new Texture(Gdx.files.internal("images/bg.png"));
		backgroundImageGO = new Texture(Gdx.files.internal("images/Lilith.png"));
		dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/sound-of-a-drop-of-water-131023.mp3"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/soft-rain-ambient-111154.mp3"));
		gameOverSound =  Gdx.audio.newSound(Gdx.files.internal("sounds/ViKikko.wav"));
		rainMusic.setLooping(true);
		rainMusic.play();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, XXXBlackSoulRainGame.WINDOW_W, XXXBlackSoulRainGame.WINDOW_H);
		this.game.batch = new SpriteBatch();
		bucket = new Rectangle();
		bucket.x = XXXBlackSoulRainGame.WINDOW_W / 2 - 64 / 2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		ground = new Rectangle();
		ground.x = 0;
		ground.y = 0;
		ground.width = XXXBlackSoulRainGame.WINDOW_W;
		ground.height = 10;
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	@Override
	public void show() {
	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, XXXBlackSoulRainGame.WINDOW_W - 64);
		raindrop.y = XXXBlackSoulRainGame.WINDOW_H;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		this.game.batch.setProjectionMatrix(camera.combined);
		this.game.batch.begin();
		for (Rectangle raindrop : raindrops) {
			this.game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		this.game.batch.draw(backgroundImage, 0,0);
		this.game.batch.draw(bucketImage, bucket.x, bucket.y);
		this.game.font.draw(this.game.batch, "Level:" + level, 10, 20);
		this.game.font.draw(this.game.batch, "XP Points:" + points,  XXXBlackSoulRainGame.WINDOW_W - 150, 20);		
		play();
		gameOver();
		this.game.batch.end();
	}

	private void gameOver() {
		if(!gameover) return ;
		this.game.batch.draw(backgroundImageGO, 0,0);
		this.game.font.draw(this.game.batch, "GAME OVER!!!!!",  XXXBlackSoulRainGame.WINDOW_W / 2 +180, XXXBlackSoulRainGame.WINDOW_H/ 2);
		this.game.font.draw(this.game.batch, "Points: "+points,  XXXBlackSoulRainGame.WINDOW_W / 2 +180, XXXBlackSoulRainGame.WINDOW_H/ 2 - 50);
		if(!gameoverSoundPlayed) {
			rainMusic.stop();
			gameOverSound.play(200);
			gameoverSoundPlayed = true;
		}
		
	}

	private void play() {
		
		

		if (gameover)
			return;
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			bucket.x -= 300 * Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			bucket.x += 300 * Gdx.graphics.getDeltaTime();
		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > XXXBlackSoulRainGame.WINDOW_W - 64)
			bucket.x = XXXBlackSoulRainGame.WINDOW_W - 64;
		multiplier = 1.0 - (level / 10);
		if (TimeUtils.nanoTime() - lastDropTime > 1000000000 * multiplier)
			spawnRaindrop();
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();) {
			Rectangle raindrop = iter.next();
			if (raindrop.overlaps(ground)) {
				gameover = true;
			} else {
				raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
				if (raindrop.y + 64 < 0)
					iter.remove();
				if (raindrop.overlaps(bucket)) {
					dropSound.play();
					iter.remove();
					points = (int) (points + 1 + level);
					if (points >= xp) {
						level++;
						xp = xp + (int) (xp * 1.5);
					}
				}
			}

		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		this.game.batch.dispose();
		this.game.dispose();
	}

}
