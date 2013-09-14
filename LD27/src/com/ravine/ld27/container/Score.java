package com.ravine.ld27.container;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/24/13
 * Time: 7:08 PM
 */
public class Score extends Actor {

    private TextureRegion scoreRegion;
    private BitmapFont font;
    private int score;
    private String text;
    private Actor[] coins;
    private Sound coinDing;

    public Score() {
        super();

        scoreRegion = new TextureRegion(new Texture(Gdx.files.internal("data/score.png")));
        score = 0;
        setSize(scoreRegion.getRegionWidth(), scoreRegion.getRegionHeight());
        font = new BitmapFont(Gdx.files.internal("data/alagard.fnt"),false);
        //font.setScale(3.25f);
        font.setColor(Color.BLACK);
        text = String.valueOf(score) + " gold";
        coins = new Actor[3];
        coinDing = Gdx.audio.newSound(Gdx.files.internal("data/gold.wav"));

    }

    @Override
    public void act(float deltaTime) {
        super.act(deltaTime);

    }

    public void createCoins() {
        for(int i = 0; i < coins.length; i++) {
            coins[i] = new Actor() {
                TextureRegion image1 = new TextureRegion(new Texture(Gdx.files.internal("data/coin.png")), 0,0,32,32);
                TextureRegion image2 = new TextureRegion(new Texture(Gdx.files.internal("data/coin.png")), 32,0,32,32);
                TextureRegion image3 = new TextureRegion(new Texture(Gdx.files.internal("data/coin.png")), 64,0,32,32);
                TextureRegion image4 = new TextureRegion(new Texture(Gdx.files.internal("data/coin.png")), 96,0,32,32);
                TextureRegion image5 = new TextureRegion(new Texture(Gdx.files.internal("data/coin.png")), 128,0,32,32);
                TextureRegion image6 = new TextureRegion(new Texture(Gdx.files.internal("data/coin.png")), 160,0,32,32);
                TextureRegion image7 = new TextureRegion(new Texture(Gdx.files.internal("data/coin.png")), 192,0,32,32);
                TextureRegion image8 = new TextureRegion(new Texture(Gdx.files.internal("data/coin.png")), 224,0,32,32);
                TextureRegion image9 = new TextureRegion(new Texture(Gdx.files.internal("data/coin.png")), 256,0,32,32);
                TextureRegion curImage = image1;
                int cur = 1;
                float time = 0.0f;

                @Override
                public void act(float delta) {
                    super.act(delta);
                    time += delta;
                    if(time >= 0.075f) {
                        time = 0;
                        cur++;
                        if(cur > 9) cur = 1;
                        switch (cur) {
                            case 1:
                                curImage = image1;
                                break;
                            case 2:
                                curImage = image2;
                                break;
                            case 3:
                                curImage = image3;
                                break;
                            case 4:
                                curImage = image4;
                                break;
                            case 5:
                                curImage = image5;
                                break;
                            case 6:
                                curImage = image6;
                                break;
                            case 7:
                                curImage = image7;
                                break;
                            case 8:
                                curImage = image8;
                                break;
                            case 9:
                                curImage = image9;
                                break;
                        }
                    }
                }

                @Override
                public void draw(SpriteBatch batch, float parentAlpha) {
                    batch.draw(curImage, getX(), getY());
                }
            };
            getStage().addActor(coins[i]);
            coins[i].setPosition(getX()+(getWidth()/2)-6, getY()+getHeight()-6);
            coins[i].setVisible(false);
        }
    }

    public void modifyScore(int amount) {
        score += amount;
        text = String.valueOf(score) + " gold";
        coinDing.play();
        coins[0].setVisible(true);
        coins[0].addAction(Actions.sequence(Actions.moveBy(-16, 28, 0.1f), Actions.delay(0.30f),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        getActor().setVisible(false);
                        return true;
                    }
                }, Actions.moveBy(16, -28)));
        coins[1].setVisible(true);
        coins[1].addAction(Actions.sequence(Actions.moveBy(0, 32, 0.1f), Actions.delay(0.30f),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        getActor().setVisible(false);
                        return true;
                    }
                }, Actions.moveBy(0, -32)));
        coins[2].setVisible(true);
        coins[2].addAction(Actions.sequence(Actions.moveBy(16, 28, 0.1f), Actions.delay(0.30f),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        getActor().setVisible(false);
                        return true;
                    }
                }, Actions.moveBy(-16, -28)));
    }

    public void resetScore() {
        score = 0;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(scoreRegion, getX(), getY());
        font.draw(batch, text, getX()+22, getY()+48);
    }

    public int getScore() {
        return score;
    }
}
