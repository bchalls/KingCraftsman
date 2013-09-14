package com.ravine.ld27.table;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.ravine.ld27.item.Material;
import com.ravine.ld27.item.Recipe;
import com.ravine.ld27.screen.GameScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/24/13
 * Time: 11:07 AM
 */
public class CraftTable extends Group {
    private Recipe curRecipe;
    private GameScreen screen;
    private GridSlot[] gridSlots;
    private TextureRegion tableRegion, gridRegion;
    private BitmapFont font;
    private float textXOffset;

    public CraftTable(GameScreen screen, int x, int y) {
        super();

        curRecipe = null;
        this.screen = screen;

        setPosition(x, y);

        gridSlots = new GridSlot[25];

        tableRegion = new TextureRegion(new Texture(Gdx.files.internal("data/grid2.png")));
        gridRegion = new TextureRegion(new Texture(Gdx.files.internal("data/grid.png")));
        setSize(tableRegion.getRegionWidth(),tableRegion.getRegionHeight());
        setOrigin(getWidth()/2, getHeight()/2);

        addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
            }
        });
        font = new BitmapFont(Gdx.files.internal("data/alagard.fnt"),false);
        //font.setColor(Color.BLACK);

    }

    public void addGridSlots() {
        for( int i = 0; i < gridSlots.length; i++) {
            gridSlots[i] = new GridSlot(i, 1+ ((i%5)*34), 131 - ((i/5)*34), this);
            this.addActor(gridSlots[i]);
            gridSlots[i].setScale(getScaleX(),getScaleY());
        }
    }

    public Material getSelectedMaterial() {
        return screen.getMaterialContainer().getCurMaterial();
    }

    public void setRecipe(Recipe r) {
        curRecipe = r;
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(tableRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
        batch.draw(gridRegion, getX(), getY(), gridRegion.getRegionWidth()/2, gridRegion.getRegionHeight()/2,
                gridRegion.getRegionWidth(), gridRegion.getRegionHeight(),getScaleX(),getScaleY(),getRotation());
        if(curRecipe != null && curRecipe.getName() != null) {
            textXOffset = font.getBounds(curRecipe.getName()).width/2;
            font.draw(batch,curRecipe.getName(), getStage().getWidth()/2-textXOffset, getStage().getHeight()-64);
        }

        for(int i = 0; i < gridSlots.length; i++)
        {
            gridSlots[i].draw(batch, parentAlpha);
        }

    }

    public void clearTable() {
        for( GridSlot gS : gridSlots) {
            gS.clearMaterial();
        }
    }

    public void setActive(boolean tf) {
        for(GridSlot gS : gridSlots) {
            gS.setActive(tf);
        }
    }

    public boolean checkForCorrect() {
        int nullCount = 0;
        for(int i = 0; i < 25; i++) {
            if(gridSlots[i].getMaterialType() != null) {
                if(!gridSlots[i].getMaterialType().equals(curRecipe.getMaterialArray()[i])) {
                    return false;
                }
            } else nullCount++;
        }
        if(nullCount >= 25) return false;
        return true;
    }

    @Override
    public void drawChildren(SpriteBatch batch, float parentAlpha) {
        super.drawChildren(batch, parentAlpha);



    }
}
