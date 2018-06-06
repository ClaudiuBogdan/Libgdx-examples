package com.claudiubogdan.scene.prototype.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.claudiubogdan.scene.prototype.components.ModelComponent;

/**
 * Class that will iterate over all the object containing ModelComponent and draw these on the screen.
 */
public class RenderSystem extends EntitySystem {
    private ImmutableArray<Entity> entities; //This array will contain references to all the entities that we filter for.
    private ModelBatch batch;
    private Environment environment;

    /**
     * Constructor that create and initialize a RenderSystem object with specified ModelBatch and Environment objects.
     * @param batch The ModelBatch that will draw the object.
     * @param environment The environment for the object.
     */
    public RenderSystem(ModelBatch  batch, Environment environment){
        this.batch = batch;
        this.environment = environment;
    }

    /**
     * Method that is called once the system is added to the engine.
     * @param e The entity that handles components.
     */
    public void addedToEngine(Engine e){
        entities = e.getEntitiesFor(Family.all(ModelComponent.class).get());
    }

    /**
     * Method that is called every frame. Render all the models that implement ModelComponent.
     * @param delta
     */
    public void update(float delta){
        for(int i=0; i < entities.size(); i++){
            ModelComponent mod = entities.get(i).getComponent(ModelComponent.class);
            batch.render(mod.modelInstance, environment);

        }
    }
}
