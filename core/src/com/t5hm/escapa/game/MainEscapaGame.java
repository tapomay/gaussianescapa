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

import box2dLight.RayHandler;

public class MainEscapaGame extends ApplicationAdapter {
    public static final String NAME= "GaussianEscapa", VERSION = "0.0.1";
    private float accumulator = 0;
	SpriteBatch batch;
	Texture img;
    private MasterBuilder masterBuilder;

    private OrthographicCamera camera;
    World world;

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
        world.dispose();
    }

    Box2DDebugRenderer debugRenderer;
    private RayHandler rayHandler;

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.update();
    }

    @Override
	public void create () {
        Gdx.app.log(NAME, "3PO: create()");
//        world = new World(new Vector2(0, -10), true);
        world = new World(new Vector2(0, 0), true);

        debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
		img = new Texture("data/badlogic.jpg");
        int worldWidth = Gdx.graphics.getWidth();
        int worldHeight = Gdx.graphics.getHeight();
        WorldSpec worldSpec = WorldSpec.create(worldWidth, worldHeight);
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        rayHandler = new RayHandler(world);
        masterBuilder = new MasterBuilder(world, rayHandler);
        masterBuilder.createWorld(worldSpec);
	}

    @Override
	public void render () {
//        Gdx.app.log(NAME, "3PO: render()");
		Gdx.gl.glClearColor(1, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//        batch.draw(img, camera.viewportWidth / 2 - img.getWorldWidth()/2, camera.viewportHeight / 2 - img.getWorldHeight()/2);
        batch.draw(img, 0, 0, camera.viewportWidth, camera.viewportHeight);
        masterBuilder.render(batch);
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