package com.ravine.ld27.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.ravine.ld27.item.Material;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/24/13
 * Time: 1:01 AM
 */
public class MaterialContainer extends Container {

    private ArrayList<Material> materialArrayList;
    private boolean matSelected;
    private Material curMaterial;

    private TextureRegion[] toolBoxRegion;

    public MaterialContainer() {
        super();
        materialArrayList = new ArrayList<Material>();
        matSelected = false;
        curMaterial = null;

        toolBoxRegion = new TextureRegion[3];
        Texture toolBoxTexture = new Texture(Gdx.files.internal("data/toolbox.png"));
        toolBoxRegion[0] = new TextureRegion(toolBoxTexture,0,0,32,192);
        toolBoxRegion[1] = new TextureRegion(toolBoxTexture,32,0,64,192);
        toolBoxRegion[2] = new TextureRegion(toolBoxTexture,96,0,32,192);
        this.addActor(new Actor() {
            @Override
            public void draw(SpriteBatch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);
                batch.draw(toolBoxRegion[0], this.getX(), 0);
                for (int i = 0; i < materialArrayList.size() / 2 ; i++) {
                    batch.draw(toolBoxRegion[1], this.getX() + 32 + i * 64, 0);
                }
                batch.draw(toolBoxRegion[2], this.getX() + 32 + ((materialArrayList.size() / 2 ) * 64), 0);
            }
        });

        /*  No need to be able to scroll so few materials
        addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                setX(getX() + deltaX);
                getChildren().get(0).setX(stageToLocalCoordinates(new Vector2(0f,0f)).x);
            }
        }); */

    }

    public void addMaterial(Material mat) {
        materialArrayList.add(mat);
        mat.setContainer(this);
        if(matSelected == false) {
            setMatSelected(true, mat);
            mat.setSelected(true);
        }
        if((materialArrayList.size()-1)%2 == 0) {
            materialArrayList.get(materialArrayList.size()-1).translate((materialArrayList.size()-1)*32+40, 146);
        } else {
            materialArrayList.get(materialArrayList.size()-1).translate((materialArrayList.size()-2)*32+40, 88);
        }
        this.addActor(materialArrayList.get(materialArrayList.size()-1));
    }

    public boolean isMatSelected() { return  matSelected; }

    public Material getCurMaterial() { return curMaterial; }

    public void setMatSelected(boolean tf, Material mat) {
        if(curMaterial != null) curMaterial.setSelected(false);
        matSelected = tf;
        curMaterial = mat;
    }

}
