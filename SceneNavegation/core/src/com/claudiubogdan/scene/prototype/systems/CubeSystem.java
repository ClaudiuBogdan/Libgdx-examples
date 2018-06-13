package com.claudiubogdan.scene.prototype.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Quaternion;
import com.claudiubogdan.scene.prototype.GameWorld;
import com.claudiubogdan.scene.prototype.components.CubeComponent;
import com.claudiubogdan.scene.prototype.managers.EntityFactory;

import java.util.Random;

public class CubeSystem extends EntitySystem implements EntityListener {
    private ImmutableArray<Entity> entities;
    private Entity cube;
    private Quaternion quat = new Quaternion();
    private Engine engine;
    private GameWorld gameWorld;
    private static double count;
    private Random random;

    public CubeSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        count = 0;
        random = new Random();
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(CubeComponent.class).get());
        engine.addEntityListener(Family.all(CubeComponent.class).get(), this);
        this.engine = engine;
    }

    @Override
    public void update(float deltaTime) {
        count += deltaTime;
        if( count > 2.51){
//            engine.addEntity(EntityFactory.createCube( random.nextInt(40) - 20, 30 , random.nextInt(40) - 20));
            for(int i=-20; i<20; i = i + 5){
                for(int j=-20; j<20; j = j+5){
                    engine.addEntity(EntityFactory.createCube( j, 30 , i));
                }
            }

            count = 0;
        }
    }

    @Override
    public void entityAdded(Entity entity) {
        cube = entity;
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
