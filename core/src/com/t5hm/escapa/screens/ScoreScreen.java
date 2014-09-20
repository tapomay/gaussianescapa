package com.t5hm.escapa.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.t5hm.escapa.game.scores.GameScoreMode;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by tapomay on 20/9/14.
 */
public class ScoreScreen implements Screen {
    private GameScoreMode gameScoreMode;

    private Stage stage;

    private Skin skin;

    private Table table;
    private BitmapFont blackFont;

    private Sprite splashBg;
    private Texture bgTexture;

    public float w, h;

    public ScoreScreen(GameScoreMode gameScoreMode) {
        this.gameScoreMode = gameScoreMode;
    }

    @Override
    public void show() {
        stage = new Stage();
//        w = Gdx.graphics.getWidth();
//        h = Gdx.graphics.getHeight();
//        stage = new Stage(new StretchViewport(w, h));

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        blackFont = new BitmapFont(Gdx.files.internal("data/fonts/nfs-escapa.fnt"), false);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
//        table.setBounds(0, 0, stage.getWidth(), stage.getHeight());

//        table.debugTable();
        table.setFillParent(true);

        bgTexture = new Texture("data/splash-bg.png");
        splashBg = new Sprite(bgTexture);
        splashBg.setPosition(0, 0);
        splashBg.setSize(stage.getCamera().viewportWidth, stage.getCamera().viewportHeight);

        // creating heading
        Label.LabelStyle labelStyle = new Label.LabelStyle(blackFont, Color.BLACK);
        Label heading = new Label("SCORES", labelStyle);
        heading.setFontScale(2);

        TextButton buttonPlay = new TextButton("PLAY AGAIN", skin, "default");
        buttonPlay.setScale(0.5f);
        buttonPlay.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new EscapaGameScreen());
                    }
                })));
            }
        });
        buttonPlay.pad(10);

        TextButton buttonMenu = new TextButton("MAIN MENU", skin, "default");
        buttonMenu.setScale(0.5f);
        buttonMenu.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
                    }
                })));
            }
        });
        buttonMenu.pad(10);

        table.add(new Label("", skin));
        table.add(heading).spaceBottom(10).expandX();
        table.add(new Label("", skin));
        table.row();
        table.add(new Label("GAME TIME", skin));
        table.add(new Label(":", skin));
        table.add(new Label(gameScoreMode.gameTime(), skin)).pad(5);
        table.row();
        table.add(new Label("HITS", skin));
        table.add(new Label(":", skin));
        table.add(new Label(gameScoreMode.getDisplayHits(), skin)).pad(5);
        table.row();
        table.add(new Label("SCORE", skin));
        table.add(new Label(":", skin));
        table.add(new Label(gameScoreMode.score(), skin)).pad(5);
        table.row();
        table.add(new Label("RATING", skin));
        table.add(new Label(":", skin));
        table.add(new Label("COMING SOON", skin)).pad(5);
        table.row();
        table.add(buttonPlay);
        table.add(new Label("", skin));
        table.add(buttonMenu);
        table.row();

        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Batch batch = stage.getBatch();
        batch.begin();
        splashBg.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        table.invalidateHierarchy();
//        stage.getViewport().update(width, height, true);
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        stage.getViewport().setWorldSize(w, h);
    }


    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        blackFont.dispose();
        bgTexture.dispose();
    }
}
