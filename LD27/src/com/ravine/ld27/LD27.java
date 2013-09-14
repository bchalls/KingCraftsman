package com.ravine.ld27;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ravine.ld27.screen.EndScreen;
import com.ravine.ld27.screen.GameScreen;
import com.ravine.ld27.screen.MenuScreen;

public class LD27 extends Game {
    public static final String LOG = LD27.class.getSimpleName();

    public static final boolean DEV_MODE = true;

    public static SpriteBatch batch;

    protected FPSLogger fpsLogger;

    /*public SplashScreen getSplashScreen() {
        return new SplashScreen(this);
    } */

    @Override
    public void create() {
        Gdx.app.log(LOG, "Creating game on: " + Gdx.app.getType());

        fpsLogger = new FPSLogger();

        batch = new SpriteBatch();

        /*if(!DEV_MODE) {
            setScreen(getSplashScreen());
        } else {
            setScreen(new GameScreen(this));
        }*/
        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();

    }

    @Override
    public void render() {
        super.render();

        fpsLogger.log();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        Gdx.app.log(LOG, "Resizing screen to: " + width + " x " + height);

        /*if( getScreen() == null)
        {
            if(!DEV_MODE)
                setScreen(new SplashScreen(this));
            else
                setScreen(new MenuScreen(this));
        }*/
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
