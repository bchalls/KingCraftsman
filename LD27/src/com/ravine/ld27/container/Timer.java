package com.ravine.ld27.container;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/24/13
 * Time: 1:17 PM
 */
public class Timer extends Actor {

    private TextureRegion timerRegion;
    private BitmapFont font;
    private float startingTime,curTime;
    private boolean running;
    private String text;

    public Timer() {
        super();

        timerRegion = new TextureRegion(new Texture(Gdx.files.internal("data/timer.png")));
        startingTime = 10.00f;
        curTime = startingTime;
        running = false;
        font = new BitmapFont(Gdx.files.internal("data/alagard.fnt"),false);
        //font.setScale(3.25f);
        font.setColor(Color.WHITE);
        text = String.valueOf(curTime).substring(0,4) + " s";

    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);
        if(running && curTime > 0) {
            curTime -= deltaTime;
            if(curTime < 0) {
                stopTimer();
                curTime = 0.00f;
                text = "0.00 s";
            }else {
                text = String.valueOf(curTime).substring(0, 4) + " s";
            }
        }

    }

    public boolean isTimeUp() { return curTime <= 0; }

    public void startTimer() { running = true; }
    public void stopTimer() { running = false; }
    public boolean isRunning() { return running; }

    public void resetTime(float time) {
        curTime = 0.00001f + time;
        text = String.valueOf(curTime).substring(0,4) + " s";
    }


    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(timerRegion, getX(), getY());
        font.draw(batch, text, getX()+24, getY()+44);
    }
}
