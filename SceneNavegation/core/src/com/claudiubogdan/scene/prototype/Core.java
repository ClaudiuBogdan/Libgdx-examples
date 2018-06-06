package com.claudiubogdan.scene.prototype;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.claudiubogdan.scene.prototype.screen.GameScreen;

/**
 * Class that will contains constants and global settings(screens).
 */
public class Core extends ApplicationAdapter {
    public static final float VIRTUAL_WIDTH = 960; //Most common screen config for Android devices.
    public static final float VIRTUAL_HEIGHT = 540;
    private Screen screen;

    @Override
    public void create() {
        Gdx.input.setCatchBackKey(true); //Catch the back button from the OS
        setScreen(new GameScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |
                GL20.GL_DEPTH_BUFFER_BIT);
        screen.render(Gdx.graphics.getDeltaTime());
    }

    /**
     * Method that handle the screens of the games and displays them.
     */
    public void setScreen(Screen screen){
        if(this.screen != null){
            this.screen.hide();
            this.screen.dispose();
        }
        this.screen = screen;
        if(this.screen != null){
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(),
                    Gdx.graphics.getHeight());
        }
    }
}
