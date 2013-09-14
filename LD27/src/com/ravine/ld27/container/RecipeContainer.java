package com.ravine.ld27.container;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ravine.ld27.item.Recipe;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/24/13
 * Time: 12:29 AM
 */
public class RecipeContainer extends Container {
    private ArrayList<Recipe> recipeArrayList;
    private boolean active;

    public RecipeContainer(ArrayList<Recipe> list1, boolean on)
    {
        super();
        recipeArrayList = new ArrayList<Recipe>();
        recipeArrayList.addAll(list1);
        for(int i = 0; i < recipeArrayList.size(); i++) {
            recipeArrayList.get(i).translate(i*6, 0);
            if(!on) recipeArrayList.get(i).setGreyed(true);
            this.addActor(recipeArrayList.get(i));
        }
        this.active = on;

        addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                if(active) { setX(getX() + deltaX); }
            }
        });

    }

    public void setActive(boolean tf) {
        if(active != tf) {
            active = tf;
            for(Recipe r : recipeArrayList) {
                r.setGreyed(!active);
            }
            if(active) {
                this.addAction(Actions.moveTo(getX()+64, getY()+140, 0.25f));
                this.toFront();
            } else {
                this.addAction(Actions.moveTo(getX()-64, getY()-140, 0.25f));
            }
        }
    }

    public boolean getActive() { return active; }

    @Override
    public void drawChildren(SpriteBatch batch, float parentAlpha) {
        super.drawChildren(batch, parentAlpha);


    }
}
