package com.t5hm.escapa.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.t5hm.escapa.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

/**
 * Created by tapomay on 14/9/14.
 */
public class T5HMSplash implements Screen {

    private int SCREEN_WIDTH = 640, SCREEN_HEIGHT=480; //FG dims

    private SpriteBatch batch;
    private Sprite splashBg, splashFg;
    private Texture fgTexture;
    private Texture bgTexture;
    private TweenManager tweenManager;

    private OrthographicCamera camera;

    @Override
    public void show() {
        // apply preferences
//        Gdx.graphics.setVSync(Settings.vSync());

        batch = new SpriteBatch();

        bgTexture = new Texture("data/splash-bg2.png");
        splashBg = new Sprite(bgTexture);

        fgTexture = new Texture("data/splash-fg.png");
        splashFg = new Sprite(fgTexture);


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float deviceAspect = h/w;

        //ASSUMING LANDSCAPE
        float viewPortHeight = SCREEN_HEIGHT;
        float viewPortWidth = SCREEN_WIDTH / deviceAspect;

        splashBg.setPosition(0, 0);
        splashBg.setSize(viewPortWidth, viewPortHeight);

        float fgTranslateX = 0, fgTranslateY = 0;
        //Try to center the splash fg
        if (viewPortWidth > SCREEN_WIDTH) {
            fgTranslateX = (viewPortWidth - SCREEN_WIDTH) / 2;
        }
        splashFg.setPosition(fgTranslateX, fgTranslateY);
        splashFg.setSize(SCREEN_WIDTH, viewPortHeight - 100);

        camera = new OrthographicCamera(viewPortWidth, viewPortHeight);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
//        camera.position.set(0, 0, 0);
        camera.update();

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        Tween.set(splashFg, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splashFg, SpriteAccessor.ALPHA, 1.5f).target(1).repeatYoyo(1, .5f).setCallback(new TweenCallback() {

            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        }).start(tweenManager);

        tweenManager.update(Float.MIN_VALUE); // update once avoid short flash of splash before animation
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//        batch.draw(splashBg.getTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        batch.draw(splashFg, 0, 0, 100, 100);
//        batch.draw(splashFg, 0, 0);

        splashBg.draw(batch);
        splashFg.draw(batch);
        batch.end();

        tweenManager.update(delta);

    }

    @Override
    public void resize(int width, int height) {
//        camera.viewportWidth = SCREEN_WIDTH;
//        camera.viewportHeight = SCREEN_HEIGHT * height/width;
        camera.update();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        if (bgTexture != null)
            bgTexture.dispose();
        if (fgTexture != null)
            fgTexture.dispose();
    }
}
