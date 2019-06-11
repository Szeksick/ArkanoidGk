package pl.kgdev.arkanoidgk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import pl.kgdev.arkanoidgk.screens.Menuscreen;

public class ArkanoidGK extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 600;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new Menuscreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
