package com.ravine.ld27.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ravine.ld27.LD27;

/**
 * Created with IntelliJ IDEA.
 * User: Brandon
 * Date: 8/25/13
 * Time: 5:58 PM
 */
public class EndScreen extends AbstractScreen {

    private Drawable[][] buttonDrawables;
    private Image bgImage;
    private Image htpImage;
    private Button[] buttons;
    private int score;
    private BitmapFont font;
    private String text;
    private float textXOffset;
    private Actor textBox;

    public EndScreen(LD27 game, int score) {
        super(game);

        this.score = score;
        font = new BitmapFont(Gdx.files.internal("data/alagard.fnt"),false);
        font.setScale(2.5f);
        font.setColor(Color.valueOf("363636"));
        text = String.valueOf(score) + " gold!";
        textXOffset = font.getBounds(text).width;

        textBox = new Actor(){
            @Override
            public void draw(SpriteBatch batch, float parentAlpha) {
                super.draw(batch, parentAlpha);
                font.draw(stage.getSpriteBatch(), text, stage.getWidth()/2 - textXOffset/2, 208);
            }
        };

        buttonDrawables = new Drawable[2][2];
        Texture temp = new Texture(Gdx.files.internal("data/buttons.png"));
        buttonDrawables[0][0] = new TextureRegionDrawable(new TextureRegion(temp, 0, 192, 256, 64));
        buttonDrawables[0][1] = new TextureRegionDrawable(new TextureRegion(temp, 256, 192, 256, 64));
        buttonDrawables[1][0] = new TextureRegionDrawable(new TextureRegion(temp, 0, 320, 256, 64));
        buttonDrawables[1][1] = new TextureRegionDrawable(new TextureRegion(temp, 256, 320, 256, 64));

        buttons = new Button[2];

        buttons[0] = new Button(buttonDrawables[0][0],buttonDrawables[0][1]);
        buttons[0].setPosition(stage.getWidth() - 256, 0);

        buttons[1] = new Button(buttonDrawables[1][0],buttonDrawables[1][1]);
        buttons[1].setPosition(0, 0);

        buttons[0].addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Button) event.getListenerActor()).toggle();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getStageX() <= event.getListenerActor().getX() + event.getListenerActor().getWidth() &&
                        event.getStageX() >= event.getListenerActor().getX() &&
                        event.getStageY() >= event.getListenerActor().getY() &&
                        event.getStageY() <= event.getListenerActor().getY() + event.getListenerActor().getHeight()) {
                    EndScreen.this.game.setScreen(new GameScreen(EndScreen.this.game));

                    ((Button) event.getListenerActor()).toggle();
                }
            }
        });

        buttons[1].addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Button) event.getListenerActor()).toggle();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (event.getStageX() <= event.getListenerActor().getX() + event.getListenerActor().getWidth() &&
                        event.getStageX() >= event.getListenerActor().getX() &&
                        event.getStageY() >= event.getListenerActor().getY() &&
                        event.getStageY() <= event.getListenerActor().getY() + event.getListenerActor().getHeight()) {
                    Gdx.app.exit();

                    ((Button) event.getListenerActor()).toggle();
                }
            }
        });

        bgImage = new Image(new Texture(Gdx.files.internal("data/bg.png")));
        htpImage = new Image(new Texture(Gdx.files.internal("data/end.png")));
        htpImage.setPosition(stage.getWidth()/2-350, stage.getHeight()/2-238);



    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void show() {
        super.show();
        stage.addActor(bgImage);
        stage.addActor(htpImage);
        stage.addActor(buttons[0]);
        stage.addActor(buttons[1]);
        stage.addActor(textBox);
    }
}
