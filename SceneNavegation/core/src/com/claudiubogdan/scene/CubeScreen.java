package com.claudiubogdan.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.sun.prism.image.ViewPort;

public class CubeScreen implements Screen, InputProcessor {
    final SceneNavegator game;
    //Create a model object to hold a cube
    private Model cube;
    private ModelBuilder modelBuilder;
    private ModelInstance instanceOfCube;
    private ModelBatch modelBatch;
    private Environment environment;
    private Camera camera;

    public CubeScreen(final SceneNavegator sceneNavegator){
        this.game = sceneNavegator;
        modelBatch = new ModelBatch();
        int fieldOfView = 50;
        // Initiate the camera
        camera = new PerspectiveCamera(fieldOfView, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10,10,10);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 100f;
        camera.update();
        //Create the cube
        modelBuilder = new ModelBuilder();
        cube = modelBuilder.createBox(5,5,5, new Material(ColorAttribute.createDiffuse(Color.RED)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceOfCube = new ModelInstance(cube);

        //Create directional lights
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f,0.2f,0.2f,1));
        environment.add(new DirectionalLight().set(0.9f,0.9f,0.9f, -1f, -0.8f, -0.2f));

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        //Render the cube
        modelBatch.begin(camera);
        modelBatch.render(instanceOfCube,environment);
        modelBatch.end();

        //Rotate the cube
        Vector3 rotationAxisY = new Vector3(0,1,0);
        float radiansPerFrame = 0.01f * 4;
        instanceOfCube.transform.rotateRad(rotationAxisY, radiansPerFrame);

        //Escape button for PC. For Android, implement Input.Key.Back
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            // Go to the main menu
            game.gotoMenuScreen();
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
        //Clear the memory
        modelBatch.dispose();
        cube.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK){
            // Do something
            game.gotoMenuScreen();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
