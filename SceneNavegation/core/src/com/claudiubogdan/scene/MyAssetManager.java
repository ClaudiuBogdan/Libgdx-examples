package com.claudiubogdan.scene;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class MyAssetManager {
    public final AssetManager manager = new AssetManager();

    public void queueAddSkin(){
        SkinLoader.SkinParameter parameter = new SkinLoader.SkinParameter(GameConstants.skinAtlas);
        manager.load(GameConstants.skin,Skin.class,parameter);
    }
}