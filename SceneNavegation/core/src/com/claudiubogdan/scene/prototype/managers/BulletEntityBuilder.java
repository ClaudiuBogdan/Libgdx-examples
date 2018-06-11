package com.claudiubogdan.scene.prototype.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;

public class BulletEntityBuilder extends BaseEntityBuilder {
    private btCollisionShape shape;

    public BulletEntityBuilder(){}

    public BulletEntityBuilder(Model model, btCollisionShape shape){
        super(model, Vector3.Zero);
        this.shape = shape;
    }


}
