package com.ravine.ld27.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.ravine.ld27.LD27;
import com.ravine.ld27.item.Material;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/24/13
 * Time: 11:10 AM
 */
public class GridSlot extends Actor{
    private TextureRegion materialRegion;
    private String materialType;
    private Texture materialTexture;
    private boolean hasMaterial;
    private int slotPos;
    private boolean active;

    private Sound sound;

    private CraftTable table;

    public GridSlot(int pos, float x, float y, CraftTable parent) {
        super();
        materialType = null;
        hasMaterial = false;

        active = false;

        materialTexture = parent.screen.assMan.get("data/materials.png", Texture.class);
        materialRegion = new TextureRegion(materialTexture, 0, 0, 32, 32);

        table = parent;

        slotPos = pos;

        materialType = "BLANK";

        setPosition(x, y);
        setSize(32, 32);
        //setOrigin(16,16);

        sound = parent.screen.assMan.get("data/put.wav" ,Sound.class);

        addListener(new ActorGestureListener() {
           @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
               if(active) {
                   sound.play();
                   if(hasMaterial) {
                       clearMaterial();
                   } else {
                       setMaterialType(table.getSelectedMaterial().getType());
                       Gdx.app.log(LD27.LOG, "Material of type: " + table.getSelectedMaterial().getType() + " added.");
                   }
               }
           }
        });
    }

    public String getMaterialType() { return materialType; }

    public void setActive(boolean tf) {
        active = tf;
    }

    public void setMaterialType( String type ) {
        materialType = type;
        if(type.equals("Wood")) {
            materialRegion = new TextureRegion(materialTexture, 0, 0, 32, 32);
        } else if(type.equals("Stone")) {
            materialRegion = new TextureRegion(materialTexture, 32, 0, 32, 32);
        } else if(type.equals("Iron")) {
            materialRegion = new TextureRegion(materialTexture, 64, 0, 32, 32);
        } else if(type.equals("Copper")) {
            materialRegion = new TextureRegion(materialTexture, 96, 0, 32, 32);
        } else if(type.equals("Leather")) {
            materialRegion = new TextureRegion(materialTexture, 128, 0, 32, 32);
        } else if(type.equals("Gold")) {
            materialRegion = new TextureRegion(materialTexture, 160, 0, 32, 32);
        } else if(type.equals("Glass")) {
            materialRegion = new TextureRegion(materialTexture, 192, 0, 32, 32);
        } else if(type.equals("Silver")) {
            materialRegion = new TextureRegion(materialTexture, 224, 0, 32, 32);
        }

        if (materialType != null) hasMaterial = true;
    }

    public void clearMaterial() {
        hasMaterial = false;
        materialType = "BLANK";
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        if(hasMaterial) {
            batch.draw(materialRegion, getParent().getX() + ((getX()-16)*getScaleX()), getParent().getY() + ((getY()-10) *getScaleY()),
                    getOriginX(), getOriginY(), getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
        }
    }
}
