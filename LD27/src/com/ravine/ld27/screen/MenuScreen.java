package com.ravine.ld27.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
 * Time: 3:02 PM
 */
public class MenuScreen extends AbstractScreen {

    private Drawable[][] buttonDrawables;
    private Image bgImage;
    private Image titleImage;
    private Button[] buttons;

    public MenuScreen(LD27 game) {
        super(game);

        assMan.load("data/buttons.png", Texture.class);
        assMan.load("data/title.png", Texture.class);
        assMan.load("data/bg.png", Texture.class);
        assMan.load("data/card.png", Texture.class);
        assMan.load("data/coin.png", Texture.class);
        assMan.load("data/end.png", Texture.class);
        assMan.load("data/greyOut.png", Texture.class);
        assMan.load("data/grid.png", Texture.class);
        assMan.load("data/grid2.png", Texture.class);
        assMan.load("data/htp.png", Texture.class);
        assMan.load("data/materials.png", Texture.class);
        assMan.load("data/mHighlight.png", Texture.class);
        assMan.load("data/score.png", Texture.class);
        assMan.load("data/timer.png", Texture.class);
        assMan.load("data/toolbox.png", Texture.class);
        assMan.load("data/bonus.wav", Sound.class);
        assMan.load("data/gold.wav", Sound.class);
        assMan.load("data/put.wav", Sound.class);
        assMan.load("data/bonus.wav", Sound.class);

        loadAssests();


        buttonDrawables = new Drawable[3][2];
        Texture temp = assMan.get("data/buttons.png", Texture.class);
        buttonDrawables[0][0] = new TextureRegionDrawable(new TextureRegion(temp, 0, 192, 256, 64));
        buttonDrawables[0][1] = new TextureRegionDrawable(new TextureRegion(temp, 256, 192, 256, 64));
        buttonDrawables[1][0] = new TextureRegionDrawable(new TextureRegion(temp, 0, 256, 256, 64));
        buttonDrawables[1][1] = new TextureRegionDrawable(new TextureRegion(temp, 256, 256, 256, 64));
        buttonDrawables[2][0] = new TextureRegionDrawable(new TextureRegion(temp, 0, 320, 256, 64));
        buttonDrawables[2][1] = new TextureRegionDrawable(new TextureRegion(temp, 256, 320, 256, 64));

        buttons = new Button[3];

        buttons[0] = new Button(buttonDrawables[0][0],buttonDrawables[0][1]);
        buttons[1] = new Button(buttonDrawables[1][0],buttonDrawables[1][1]);
        buttons[2] = new Button(buttonDrawables[2][0],buttonDrawables[2][1]);

        buttons[0]. setPosition(stage.getWidth()/2-128, 132);
        buttons[1]. setPosition(stage.getWidth() / 2 - 128, 66);
        buttons[2]. setPosition(stage.getWidth() / 2 - 128, 0);

        buttons[0].addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ((Button) event.getListenerActor()).toggle();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if(event.getStageX() <= event.getListenerActor().getX()+ event.getListenerActor().getWidth() &&
                        event.getStageX() >= event.getListenerActor().getX() &&
                        event.getStageY() >= event.getListenerActor().getY() &&
                        event.getStageY() <= event.getListenerActor().getY() + event.getListenerActor().getHeight()) {
                    MenuScreen.this.game.setScreen(new GameScreen(MenuScreen.this.game));

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
                    MenuScreen.this.game.setScreen(new HTPScreen(MenuScreen.this.game));

                    ((Button) event.getListenerActor()).toggle();
                }
            }
        });
        buttons[2].addListener(new InputListener() {
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

        bgImage = new Image(assMan.get("data/bg.png", Texture.class));
        titleImage = new Image(assMan.get("data/title.png", Texture.class));
        titleImage.setPosition(stage.getWidth()/2-320, stage.getHeight()-400);



    }

    private void loadAssests() {
        while(!assMan.update()) {

        }
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
        for(int i = 0; i < buttons.length; i++) {
            stage.addActor(buttons[i]);
        }
        stage.addActor(titleImage);
    }
}
