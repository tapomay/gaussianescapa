package com.t5hm.escapa.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class MainEscapaGame extends ApplicationAdapter {
    public static final String NAME= "GaussianEscapa", VERSION = "0.0.1";
    private float accumulator = 0;
	SpriteBatch batch;
	Texture img;

    private OrthographicCamera camera;
    World world;
    Box2DDebugRenderer debugRenderer;

	@Override
	public void create () {
        Gdx.app.log(NAME, "3PO: create()");
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		img = new Texture("data/badlogic.jpg");
        camera = new OrthographicCamera();
	}

	@Override
	public void render () {
//        Gdx.app.log(NAME, "3PO: render()");
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, Constants.Game.WIDTH/2 - img.getWidth()/2, Constants.Game.HEIGHT/2 - img.getHeight()/2);
		batch.end();
        debugRenderer.render(world, camera.combined);
        doPhysicsStep(Gdx.graphics.getDeltaTime());
	}

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= Constants.TIME_STEP) {
            world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
            accumulator -= Constants.TIME_STEP;
        }
    }
}