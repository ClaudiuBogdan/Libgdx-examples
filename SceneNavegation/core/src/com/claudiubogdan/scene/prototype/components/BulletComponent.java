package com.claudiubogdan.scene.prototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import spaceglad.bullet.MotionState;

/**
 * This class contains all the data that is required to function within a bullet environment.
 */
public class BulletComponent implements Component {
    public MotionState motionState; //MotionState holds information about the position and angle of the physical object.
    public btRigidBody.btRigidBodyConstructionInfo bodyInfo; //provides information to create a rigid body
    public btCollisionObject body;  // The object that can eventually be added to our bullet
                                    //    environment and is used to manage collision detection objects.

}
