package com.claudiubogdan.scene.prototype.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;

/**
 * Class that
 */
public abstract class EntityBuilder {
    protected Model model;
    protected Vector3 size;
    protected String rootNodeId;

    public abstract Entity create(float mass, Vector3 trans, Vector3 size);

}
