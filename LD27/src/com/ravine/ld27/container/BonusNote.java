package com.ravine.ld27.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/25/13
 * Time: 1:58 PM
 */
public class BonusNote extends Actor {
    private BitmapFont font;
    private float xOffset;
    private float amount;
    //1 - time, 2 - matching
    private int type;
    private Sound sound;

    public BonusNote(float amount, int type) {
        super();

        this.amount = amount;

        font = new BitmapFont(Gdx.files.internal("data/alagard.fnt"), false);
        font.setColor(0.98f,.054f,.0196f,1.0f);
        font.setScale(2.0f);

        this.type = type;

        if(type == 1) xOffset = font.getBounds("Time Bonus: " + String.valueOf(amount) + "x!").width/2;
        else if(type == 2) xOffset = font.getBounds("Matching Bonus: " + String.valueOf(amount) + "x!").width/2;

        setY(450);

        sound = Gdx.audio.newSound(Gdx.files.internal("data/bonus.wav"));
        sound.play();

    }

    @Override
    public void act(float delta) {
        if(font.getColor().a > 0.0) {
            font.setColor(0.98f,.054f,.0196f,font.getColor().a-(delta/2));
            translate(0, delta * 100);
        } else
            this.remove();
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        if(type == 1) font.draw(batch, "Time Bonus: " + String.valueOf(amount) + "x!", getStage().getWidth()/2-xOffset, getY() ) ;
        else if( type == 2)  font.draw(batch, "Matching Bonus: " + String.valueOf(amount) + "x!", getStage().getWidth()/2-xOffset, getY() ) ;
    }
}
