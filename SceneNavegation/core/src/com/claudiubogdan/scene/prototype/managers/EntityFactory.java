package com.claudiubogdan.scene.prototype.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.claudiubogdan.scene.prototype.components.BulletComponent;
import com.claudiubogdan.scene.prototype.components.ModelComponent;
import spaceglad.bullet.MotionState;

/**
 * Class that contains a method that  accepts a model and a position and returns an Ashley entity
 */
public class EntityFactory {
    public static Entity createStaticEntity(Model model, float x, float y,
                                            float z) {
        final BoundingBox boundingBox = new BoundingBox();
        model.calculateBoundingBox(boundingBox); //This calculate the rigid body shape for the object.
        Entity entity = new Entity();
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);
        Vector3 tmpV = new Vector3();
//        btCollisionShape col = new btBoxShape(tmpV.set(boundingBox.getWidth() * 0.5f,
//               boundingBox.getHeight() * 0.5f, boundingBox.getDepth() * 0.5f));

//        BulletComponent bulletComponent = new BulletComponent();
//        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null,
//                col, Vector3.Zero);
//        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
//        bulletComponent.body.userData = entity;
//        bulletComponent.motionState = new MotionState(modelComponent.modelInstance.transform);
//        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
//        entity.add(bulletComponent);
        return entity;
    }
}
