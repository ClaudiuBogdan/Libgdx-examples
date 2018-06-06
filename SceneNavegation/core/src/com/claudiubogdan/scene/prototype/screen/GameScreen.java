package com.claudiubogdan.scene.prototype.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.claudiubogdan.scene.prototype.Core;
import com.claudiubogdan.scene.prototype.GameWorld;

/**
 * This class will contains the objects to be drawn and UI elements.
 */
public class GameScreen implements Screen {
    Core game;
    GameWorld gameWorld;

    /**
     * Constructor that saves Core instance and GameWorld class.
     * @param game The reference to the Core instance.
     */
    public GameScreen(Core game){
        this.game = game;
        gameWorld = new GameWorld(); //Instance of the app.
        Gdx.input.setCursorCatched(true); //Disable the cursor icon.

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameWorld.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameWorld.resize(width, height);
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
        gameWorld.dispose();
    }
}
