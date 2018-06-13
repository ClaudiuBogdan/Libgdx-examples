package com.claudiubogdan.scene.prototype.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.claudiubogdan.scene.prototype.components.BulletComponent;
import com.claudiubogdan.scene.prototype.components.CharacterComponent;
import com.claudiubogdan.scene.prototype.components.CubeComponent;
import com.claudiubogdan.scene.prototype.components.ModelComponent;
import com.claudiubogdan.scene.prototype.systems.BulletSystem;
import spaceglad.bullet.MotionState;

/**
 * Class that contains a method that  accepts a model and a position and returns an Ashley entity
 */
public class EntityFactory {
    private static Model playerModel;
    private static ModelBuilder modelBuilder;
    static {
        modelBuilder = new ModelBuilder();
        playerModel = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.RED),
                ColorAttribute.createSpecular(Color.WHITE), FloatAttribute.createShininess(64f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }


    public static Entity createStaticEntity(Model model, float x, float y,
                                            float z) {
        final BoundingBox boundingBox = new BoundingBox();
        model.calculateBoundingBox(boundingBox); //This calculate the rigid body shape for the object.
        Entity entity = new Entity();
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);
        Vector3 tmpV = new Vector3();
        btCollisionShape col = new btBoxShape(tmpV.set(boundingBox.getWidth() * 0.5f,
               boundingBox.getHeight() * 0.5f, boundingBox.getDepth() * 0.5f));
        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null,
                col, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new BulletComponent.MotionState(modelComponent.modelInstance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
        entity.add(bulletComponent);
        return entity;
    }

    public static Entity createCubeEntity(BulletSystem bulletSystem, float x, float y, float z) {
        Entity entity = new Entity();
        ModelComponent modelComponent = new ModelComponent(playerModel, x, y, z);
        entity.add(modelComponent);
        CubeComponent characterComponent = new CubeComponent();
        characterComponent.ghostObject = new btPairCachingGhostObject();
        characterComponent.ghostObject.setWorldTransform(modelComponent.modelInstance.transform);
        characterComponent.ghostShape = new btCapsuleShape(3f, 2f);
        characterComponent.ghostObject.setCollisionShape(characterComponent.ghostShape);
        characterComponent.ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
        characterComponent.characterController = new btKinematicCharacterController(characterComponent.ghostObject, characterComponent.ghostShape, 0.35f);
        characterComponent.ghostObject.userData = entity;
        characterComponent.characterController.setGravity(new Vector3(0,20,0));
        entity.add(characterComponent);
        bulletSystem.collisionWorld.addCollisionObject(entity.getComponent(CubeComponent.class).ghostObject,
                (short) btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                (short) (btBroadphaseProxy.CollisionFilterGroups.AllFilter));
        bulletSystem.collisionWorld.addAction(entity.getComponent(CubeComponent.class).characterController);
        entity.add(new CubeComponent());
        return entity;
    }

    public static Entity createCube( float x, float y, float z){
        Model model = playerModel;
        final BoundingBox boundingBox = new BoundingBox();
        model.calculateBoundingBox(boundingBox); //This calculate the rigid body shape for the object.
        Entity entity = new Entity();
        ModelComponent modelComponent = new ModelComponent(model, x, y, z);
        entity.add(modelComponent);
        Vector3 tmpV = new Vector3();
        btCollisionShape col = new btBoxShape(tmpV.set(boundingBox.getWidth() * 0.5f,
                boundingBox.getHeight() * 0.5f, boundingBox.getDepth() * 0.5f));
        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(5, null,
                col, new Vector3( 2 , 2, 2));
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new BulletComponent.MotionState(modelComponent.modelInstance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
        entity.add(bulletComponent);
        return entity;
    }
}
