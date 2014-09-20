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
    private GamifiedWorld gamifiedWorld ;

    private OrthographicCamera camera;
    World world;

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
        rayHandler.dispose();
        world.dispose();
    }

    Box2DDebugRenderer debugRenderer;
    private RayHandler rayHandler;

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

        RayHandler.setGammaCorrection(true);
        RayHandler.useDiffuseLight(true);

        rayHandler = new RayHandler(world);
        MasterBuilder masterBuilder = new MasterBuilder();
        this.gamifiedWorld = masterBuilder.createWorld(world, rayHandler, worldSpec);
        camera.update(true);
        Gdx.input.setInputProcessor(new EscapaLightsInputAdapter(camera, gamifiedWorld));
	}

    @Override
	public void render () {
//        Gdx.app.log(NAME, "3PO: render()");
        camera.update();
		Gdx.gl.glClearColor(1, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
//        batch.draw(img, camera.viewportWidth / 2 - img.getWorldWidth()/2, camera.viewportHeight / 2 - img.getWorldHeight()/2);
        batch.draw(img, 0, 0, camera.viewportWidth, camera.viewportHeight);
		batch.end();
//        masterBuilder.update(batch, delta);
        debugRenderer.render(world, camera.combined);
        doPhysicsStep(Gdx.graphics.getDeltaTime());

        boolean stepped = fixedStep(Gdx.graphics.getDeltaTime());

        /** BOX2D LIGHT STUFF BEGIN */

        rayHandler.setCombinedMatrix(camera.combined, camera.position.x,
                camera.position.y, camera.viewportWidth * camera.zoom,
                camera.viewportHeight * camera.zoom);

        // rayHandler.setCombinedMatrix(camera.combined);
        if (stepped)
            rayHandler.update();
        rayHandler.render();

        /** BOX2D LIGHT STUFF END */

    }

    float physicsTimeLeft;
    private final static int MAX_FPS = 30;
    private final static int MIN_FPS = 15;
    public final static float TIME_STEP = 1f / MAX_FPS;
    private final static float MAX_STEPS = 1f + MAX_FPS / MIN_FPS;
    private final static float MAX_TIME_PER_FRAME = TIME_STEP * MAX_STEPS;
    private final static int VELOCITY_ITERS = 6;
    private final static int POSITION_ITERS = 2;
    private boolean fixedStep(float delta) {
        physicsTimeLeft += delta;
        if (physicsTimeLeft > MAX_TIME_PER_FRAME)
            physicsTimeLeft = MAX_TIME_PER_FRAME;

        boolean stepped = false;
        while (physicsTimeLeft >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERS, POSITION_ITERS);
            physicsTimeLeft -= TIME_STEP;
            stepped = true;
        }
        return stepped;
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