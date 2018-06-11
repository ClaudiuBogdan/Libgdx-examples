package com.claudiubogdan.scene.prototype.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * Class that represents a basic the model of objects from the game.
 */
public class ModelComponent implements Component {
    public Model model; //Model that will be drawn on the screen.
    public ModelInstance modelInstance; //Object that helps to manipulate model transformation.

    /**
     * Constructor that create and initialize the ModelComponent object to a specific position.
     * @param model The model to be created.
     * @param x The coordinate X of the model position.
     * @param y The coordinate Y of the model position.
     * @param z The coordinate Z of the model position.
     **/
    public ModelComponent(Model model, float x, float y, float z){
        this(model, new Vector3(x,y,z));
    }


    public ModelComponent(Model model, Vector3 pos){
        this.model = model;
        this.modelInstance = new ModelInstance(model, new Matrix4().setToTranslation(pos));

    }

}
