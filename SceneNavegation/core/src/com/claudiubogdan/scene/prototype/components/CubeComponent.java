package com.claudiubogdan.scene.prototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btConvexShape;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;

public class CubeComponent implements Component {
    public btConvexShape ghostShape;
    public btPairCachingGhostObject ghostObject;
    public btKinematicCharacterController characterController;
}

