package com.t5hm.escapa.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.t5hm.escapa.game.GaussianEscapaStart;

public class MainMenuScreen implements Screen {

	private Stage stage;
//    private TextureAtlas atlas;

    private Skin skin;

	private Table table;
    private BitmapFont blackFont;

    private Sprite splashBg;
    private Texture bgTexture;

	@Override
	public void show() {
//        atlas = new TextureAtlas("data/uiskin.json");
//        skin = new Skin(atlas);

//        float w = Gdx.graphics.getWidth();
//        float h = Gdx.graphics.getHeight();
//        stage = new Stage(new StretchViewport(w, h));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        blackFont = new BitmapFont(Gdx.files.internal("data/fonts/nfs-escapa.fnt"), false);
//        Viewport viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        stage = new Stage(viewport);

		table = new Table();
//        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.setFillParent(true);

        bgTexture = new Texture("data/splash-bg2.png");
        splashBg = new Sprite(bgTexture);
        splashBg.setPosition(0, 0);
        splashBg.setSize(stage.getCamera().viewportWidth, stage.getCamera().viewportHeight);

        // creating heading
        Label.LabelStyle labelStyle = new Label.LabelStyle(blackFont, Color.WHITE);
		Label heading = new Label(GaussianEscapaStart.TITLE, labelStyle);
		heading.setFontScale(2);

		// creating buttons
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		TextButton buttonPlay = new TextButton("PLAY", skin, "default");
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

		TextButton buttonSettings = new TextButton("SETTINGS", skin, "default");
		buttonSettings.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new Settings());
					}
				})));
			}
		});
		buttonSettings.pad(5);

        TextButton buttonCredits = new TextButton("CREDITS", skin, "default");
        buttonCredits.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

                    @Override
                    public void run() {
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new CreditsScreen());
                    }
                })));
            }
        });
        buttonCredits.pad(5);

		TextButton buttonExit = new TextButton("EXIT", skin, "default");
		buttonExit.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
			}
		});
		buttonExit.pad(10);

		// putting stuff together
		table.add(heading).spaceBottom(10).row();
		table.add(buttonPlay).spaceBottom(5).row();
        table.add(buttonSettings).spaceBottom(5).row();
        table.add(buttonCredits).spaceBottom(5).row();
		table.add(buttonExit).spaceBottom(5).row();

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
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        stage.getViewport().setWorldSize(w, h);

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
        stage.dispose();
//        atlas.dispose();
        skin.dispose();
        blackFont.dispose();
        bgTexture.dispose();
	}

}
