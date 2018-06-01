package com.claudiubogdan.game.basic3d;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class PhysicsImplementation extends ApplicationAdapter{
    //Create a model object to hold a cube
    private Model cube;
    private ModelBuilder modelBuilder;
    private ModelInstance instanceOfCube;
    private ModelBatch modelBatch;
    private Environment environment;
    private Camera camera;

    @Override
    public void create() {
        super.create();
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
    }

    @Override
    public void render() {
        super.render();
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
    }

    @Override
    public void dispose() {
        super.dispose();
        //Clear the memory
        modelBatch.dispose();
        cube.dispose();
    }
}
