package com.claudiubogdan.scene;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SceneNavegator extends Game {
	public SpriteBatch batch;
	public Texture img;
	public BitmapFont font;
	public MainMenuScreen mainMenuScreen;
	public Viewport screenPort;
	public MyAssetManager myAssetManager = new MyAssetManager();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		font = new BitmapFont();
		screenPort = new ScreenViewport();
		Gdx.input.setCatchBackKey(true);
		gotoMenuScreen();
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void gotoMenuScreen(){
		MainMenuScreen menuScreen = new MainMenuScreen(this);
		setScreen(menuScreen);
	}



	public void gotoCubeScreen(){
		CubeScreen gameScreen = new CubeScreen(this);
		setScreen(gameScreen);
	}

	public void gotoBulletScreen(){
		BulletScreen bulletScreen = new BulletScreen(this);
		setScreen(bulletScreen);
	}

}
