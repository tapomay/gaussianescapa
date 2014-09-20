package com.t5hm.escapa.game;

import com.badlogic.gdx.Game;
import com.t5hm.escapa.game.scores.DisplayScoreMode;
import com.t5hm.escapa.game.scores.GameScoreMode;
import com.t5hm.escapa.screens.EscapaGameScreen;
import com.t5hm.escapa.screens.MainMenuScreen;
import com.t5hm.escapa.screens.ScoreScreen;
import com.t5hm.escapa.screens.Settings;
import com.t5hm.escapa.screens.T5HMSplash;

/**
 * Created by tapomay on 14/9/14.
 */
public class GaussianEscapaStart extends Game {

    public static final String TITLE = "Gaussian Escapa 2", VERSION = "2.0";

    @Override
    public void create() {
//        setScreen(new T5HMSplash());
//        setScreen(new EscapaGameScreen());
        setScreen(new MainMenuScreen());
//        setScreen(new ScoreScreen(new DisplayScoreMode()));
//        setScreen(new Settings());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
