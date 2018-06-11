package com.claudiubogdan.scene.prototype.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.claudiubogdan.scene.prototype.components.ModelComponent;

public class BaseEntityBuilder extends EntityBuilder {
    protected BaseEntityBuilder(){}

    public BaseEntityBuilder(Model model, Vector3 size){
        this.model = model;
        this.size = size;
    }

    @Override
    public Entity create(float mass, Vector3 trans, Vector3 size) {
        return new Entity();
    }

    public static Entity load(Model model, String rootNodeId, Vector3 size, Vector3 translation){
        Entity e = new Entity();
        e.add(new ModelComponent(model, translation));
        return e;
    }
}
