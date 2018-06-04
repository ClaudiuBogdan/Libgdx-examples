package com.claudiubogdan.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import drop.GameScreen;

public class MainMenuScreen implements Screen {
    final  SceneNavegator game;
    private Skin mySkin;
    private Stage stage;
    OrthographicCamera camera;

    public MainMenuScreen(final  SceneNavegator sceneNavegator){
        this.game = sceneNavegator;

        //Set the camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        stage = new Stage(game.screenPort);
        Gdx.input.setInputProcessor(stage);

        game.myAssetManager.queueAddSkin();
        game.myAssetManager.manager.finishLoading();
        mySkin = game.myAssetManager.manager.get(GameConstants.skin);
        mySkin = game.myAssetManager.manager.get(GameConstants.skin);
        Label gameTitle = new Label("GAME MENU",mySkin,"big");
        gameTitle.setSize(GameConstants.col_width*2,GameConstants.row_height*2);
        gameTitle.setPosition(GameConstants.centerX - gameTitle.getWidth()/2,GameConstants.centerY + GameConstants.row_height);
        gameTitle.setAlignment(Align.center);

        //Add button
        Button startBtn = new TextButton("START CUBE",mySkin,"small");
        startBtn.setSize(GameConstants.col_width*2,GameConstants.row_height);
        startBtn.setPosition(GameConstants.centerX - startBtn.getWidth()/2,GameConstants.centerY);
        startBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                game.gotoCubeScreen();
            }
        });

        Button settingsBtn = new TextButton("SETTINGS",mySkin,"small");
        settingsBtn.setSize(GameConstants.col_width*2,GameConstants.row_height);
        settingsBtn.setPosition(GameConstants.centerX - settingsBtn.getWidth()/2,startBtn.getY() - GameConstants.row_height -15);
        settingsBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //game.gotoSettingsScreen();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });

        stage.addActor(gameTitle);
        stage.addActor(startBtn);
        stage.addActor(settingsBtn);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.screenPort.update(width,height);
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
        mySkin.dispose();
        stage.dispose();
    }
}
