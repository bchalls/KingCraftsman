package com.ravine.ld27.item;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.ravine.ld27.container.MaterialContainer;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/24/13
 * Time: 1:01 AM
 */
public class Material extends Actor {
    private TextureRegion imageRegion;
    private TextureRegion highlightRegion;
    private String type;
    private BitmapFont font;
    private float textOffset;
    private boolean selected;
    private MaterialContainer container;
    private Material reference;
    private boolean inGrid;

    public Material(String type, boolean grid) {
        this.type = type;

        this.inGrid = grid;

        int imageOffset = 0;
        if(type.equals("Wood")) imageOffset = 0;
        else if(type.equals("Stone")) imageOffset = 1;
        else if(type.equals("Iron")) imageOffset = 2;
        else if(type.equals("Copper")) imageOffset = 3;
        else if(type.equals("Leather")) imageOffset = 4;
        else if(type.equals("Gold")) imageOffset = 5;
        else if(type.equals("Glass")) imageOffset = 6;
        else if(type.equals("Silver")) imageOffset = 7;

        imageRegion = new TextureRegion(new Texture(Gdx.files.internal("data/materials.png")),
                32* imageOffset, 0, 32, 32);
        highlightRegion = new TextureRegion(new Texture(Gdx.files.internal("data/mHighlight.png")));
        setWidth(imageRegion.getRegionWidth());
        setHeight(imageRegion.getRegionHeight());

        selected = false;

        font = new BitmapFont(Gdx.files.internal("data/alagard.fnt"), false);
        //font.setColor(Color.DARK_GRAY);
        font.setScale(0.60f);
        textOffset = (font.getBounds(type).width-getWidth())/2;

        //Need a pointer to this so that it can be accessed within the listener
        reference = this;

        addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                if(!inGrid){
                    if(!container.isMatSelected())
                    {
                        selected = true;
                        container.setMatSelected(true, reference);
                    } else if(container.isMatSelected() && !selected) {
                        selected = true;
                        container.setMatSelected(true, reference);
                    }
                }
            }

        });
        container = null;
    }

    public String getType() { return type; }

    public void setContainer( MaterialContainer cont) {
        container = cont;
    }

    public void setSelected(boolean tf) {
        selected = tf;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(imageRegion, getX(), getY());
        if(!inGrid) font.draw(batch, type, getX()-textOffset, getY()-8 );
        if(selected && !inGrid) batch.draw(highlightRegion, getX(), getY());
    }
}
