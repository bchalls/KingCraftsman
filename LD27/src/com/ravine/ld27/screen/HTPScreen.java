package com.ravine.ld27.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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
 * Time: 5:12 PM
 */
public class HTPScreen extends AbstractScreen {

    private Drawable[] buttonDrawables;
    private Image bgImage;
    private Image htpImage;
    private Button button;

    public HTPScreen(LD27 game) {
        super(game);

        buttonDrawables = new Drawable[2];
        Texture temp = new Texture(Gdx.files.internal("data/buttons.png"));
        buttonDrawables[0] = new TextureRegionDrawable(new TextureRegion(temp, 0, 192, 256, 64));
        buttonDrawables[1] = new TextureRegionDrawable(new TextureRegion(temp, 256, 192, 256, 64));

        button = new Button(buttonDrawables[0],buttonDrawables[1]);

        button. setPosition(stage.getWidth() / 2 - 128, 0);

        button.addListener(new InputListener() {
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
                    HTPScreen.this.game.setScreen(new GameScreen(HTPScreen.this.game));

                    ((Button) event.getListenerActor()).toggle();
                }
            }
        });

        bgImage = new Image(new Texture(Gdx.files.internal("data/bg.png")));
        htpImage = new Image(new Texture(Gdx.files.internal("data/htp.png")));
        htpImage.setPosition(stage.getWidth()/2-350, stage.getHeight()/2-261);



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
        stage.addActor(button);
    }
}
