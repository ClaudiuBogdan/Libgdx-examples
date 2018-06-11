package com.claudiubogdan.scene.prototype;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.claudiubogdan.scene.prototype.components.CharacterComponent;
import com.claudiubogdan.scene.prototype.components.ModelComponent;
import com.claudiubogdan.scene.prototype.managers.EntityFactory;
import com.claudiubogdan.scene.prototype.systems.BulletSystem;
import com.claudiubogdan.scene.prototype.systems.CubeSystem;
import com.claudiubogdan.scene.prototype.systems.RenderSystem;


/**
 * Class that will hold the objects to be rendered, camera, environment.
 */
public class GameWorld {
    private static final float FOV = 67; //Angle of camera aperture.
    private ModelBatch batch; //Render objects into the screen.
    private Environment environment; //Add light to scene and objects.
    private PerspectiveCamera camera; //The camera that will focus the scene.
    private Engine engine; //The ashley engine that handles the objects.

    public BulletSystem bulletSystem;
    public ModelBuilder modelBuilder = new ModelBuilder();
    float wallHeight = 10;
    Model wallHorizontal = modelBuilder.createBox(40, wallHeight, 1,
            new Material(ColorAttribute.createDiffuse(Color.WHITE),
                    ColorAttribute.createSpecular(Color.RED), FloatAttribute
                    .createShininess(16f)), VertexAttributes.Usage.Position
                    | VertexAttributes.Usage.Normal);
    Model wallVertical = modelBuilder.createBox(1, wallHeight, 40,
            new Material(ColorAttribute.createDiffuse(Color.GREEN),
                    ColorAttribute.createSpecular(Color.WHITE),
                    FloatAttribute.createShininess(16f)),
            VertexAttributes.Usage.Position |
                    VertexAttributes.Usage.Normal);
    Model groundModel = modelBuilder.createBox(40, 1, 40,
            new Material(ColorAttribute.createDiffuse(Color.YELLOW),
                    ColorAttribute.createSpecular(Color.BLUE),
                    FloatAttribute.createShininess(16f)),
            VertexAttributes.Usage.Position
                    | VertexAttributes.Usage.Normal);

    /**
     * Constructor that initialize a GameWorld object.
     */
    public GameWorld(){
        initPersCamera();
        initEnvironment();
        initModelBatch();
        addSystems();
        addEntities();
    }

    private void addEntities() {

        createGround();
    }

    private void createGround() {
        float high_pos = wallHeight/2 - 1;
        engine.addEntity(EntityFactory.createStaticEntity(groundModel,0, 0, 0));
        engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, high_pos, -20));
        engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, high_pos, 20));
        engine.addEntity(EntityFactory.createStaticEntity(wallVertical, 20, high_pos, 0));
        engine.addEntity(EntityFactory.createStaticEntity(wallVertical, -20, high_pos, 0));
    }


    private void addSystems() {
        engine = new Engine();
        bulletSystem = new BulletSystem();
        engine.addSystem(bulletSystem);
        engine.addSystem(new RenderSystem(batch, environment));
        engine.addSystem(new CubeSystem(this));
    }

    /**
     * Method that configure the camera of the game.
     */
    private void initPersCamera() {
        this.camera = new PerspectiveCamera(FOV,
                Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        camera.position.set(30f, 40f, 30f);
        camera.lookAt(0f,0f,0f); //Look at the center of the world.
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
    }

    /**
     * Method that configure the environment that determines how this will be illuminated.
     */
    private void initEnvironment() {
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1f));
        environment.add(new DirectionalLight().set(0.9f,0.9f,0.9f, -1f, -0.8f, -0.2f));
    }

    /**
     * Method that initialize the ModelBatch class that will render the objects on the screen.
     */
    private void initModelBatch() {
        batch = new ModelBatch();
    }

    /**
     * Method that handle the model batch cleaning.
     */
    public void dispose(){
        batch.dispose();
        wallHorizontal.dispose();
        wallVertical.dispose();
        groundModel.dispose();
        bulletSystem.dispose();

        bulletSystem = null;
    }

    /**
     * Method that configure the camera for the new screen configuration.
     */
    public void resize(int width, int height){
        camera.viewportHeight = height;
        camera.viewportWidth = width;
    }

    /**
     * Method that will render the objects.
     */
    public void render(float delta){
        batch.begin(camera);
        engine.update(delta);
        batch.end();
    }

    public void remove(Entity entity) {
        engine.removeEntity(entity);
        bulletSystem.removeBody(entity);
    }
}
