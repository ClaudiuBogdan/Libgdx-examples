package com.claudiubogdan.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class BulletScreen implements Screen, InputProcessor {
    final SceneNavegator game;

    //Physics

    //The cube that will implements Bullet
    Model cube;
    ModelInstance instanceOfCube;
    Vector3 positionOfCube;
    //The platform that will hold the cube
    Model platform;
    ModelInstance instanceOfPlatform;

    ModelBuilder modelBuilder;
    ModelBatch modelBatch;

    Environment environment;
    Camera camera;

    public BulletScreen(SceneNavegator sceneNavegator){
        this.game = sceneNavegator;
        modelBatch = new ModelBatch();
        positionOfCube = new Vector3();
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
        Material material = new Material(ColorAttribute.createDiffuse(Color.RED));
        material.set(FloatAttribute.createShininess(32f));
        cube = modelBuilder.createBox(5,5,5, material,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal );

        instanceOfCube = new ModelInstance(cube);

        //Create the platform
        platform = modelBuilder.createBox(20,2,20, new Material(ColorAttribute.createDiffuse(Color.GOLD),FloatAttribute.createShininess(8f)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instanceOfPlatform = new ModelInstance(platform);
        instanceOfPlatform.transform.setTranslation(0,-5,0);

        //Create directional lights
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f,0.3f,0.3f,1));
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
        modelBatch.render(instanceOfPlatform,environment);
        modelBatch.end();

        movement();

        //Rotate the cube
        Vector3 rotationAxisY = new Vector3(0,1,0);
        float radiansPerFrame = -0.01f * 4;
        //instanceOfCube.transform.rotateRad(rotationAxisY, radiansPerFrame);

        //Escape button for PC. For Android, implement Input.Key.Back
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            // Go to the main menu
            game.gotoMenuScreen();
        }

    }
    private void movement() {
        instanceOfCube.transform.getTranslation(positionOfCube);
        int velocity = 4;
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            positionOfCube.x+=Gdx.graphics.getDeltaTime()*velocity;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            positionOfCube.z+=Gdx.graphics.getDeltaTime()*velocity;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            positionOfCube.z-=Gdx.graphics.getDeltaTime()*velocity;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            positionOfCube.x-=Gdx.graphics.getDeltaTime()*velocity;
        }
        instanceOfCube.transform.setTranslation(positionOfCube);
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

    }

    @Override
    public boolean keyDown(int keycode) {
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
