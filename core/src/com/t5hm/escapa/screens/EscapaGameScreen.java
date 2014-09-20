package com.t5hm.escapa.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.t5hm.escapa.game.Constants;
import com.t5hm.escapa.game.EscapaLightsInputAdapter;
import com.t5hm.escapa.game.GaussianEscapaStart;
import com.t5hm.escapa.game.MasterBuilder;
import com.t5hm.escapa.game.WorldSpec;

import box2dLight.RayHandler;

/**
 * Created by tapomay on 14/9/14.
 */
public class EscapaGameScreen implements Screen {

    private float accumulator = 0;
    private SpriteBatch batch;
    private Texture img;
    private MasterBuilder masterBuilder;

    private OrthographicCamera camera;
    private World world;
    Box2DDebugRenderer debugRenderer;
    private RayHandler rayHandler;
    FPSLogger fpsLogger;
    Label scoreLabel;
    Label timerLabel;

    @Override
    public void show() {
//        world = new World(new Vector2(0, -10), true);
        world = new World(new Vector2(0, 0), true);
        fpsLogger = new FPSLogger();
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
        masterBuilder = new MasterBuilder(world, rayHandler);
        masterBuilder.createWorld(worldSpec);

        camera.update(true);
        Gdx.input.setInputProcessor(new EscapaLightsInputAdapter(camera, masterBuilder));
    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0, camera.viewportWidth, camera.viewportHeight);
        masterBuilder.update(batch, delta);
        batch.end();
        debugRenderer.render(world, camera.combined);
        doPhysicsStep(Gdx.graphics.getDeltaTime());

        boolean stepped = fixedStep(delta);

        /** BOX2D LIGHT STUFF BEGIN */

        rayHandler.setCombinedMatrix(camera.combined, camera.position.x,
                camera.position.y, camera.viewportWidth * camera.zoom,
                camera.viewportHeight * camera.zoom);

//        rayHandler.setCombinedMatrix(camera.combined);
        if (stepped)
            rayHandler.update();
        rayHandler.render();
//        fpsLogger.log();
        /** BOX2D LIGHT STUFF END */

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

    @Override
    public void resize(int width, int height) {

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
        batch.dispose();
        img.dispose();
        rayHandler.dispose();
        world.dispose();
    }
}
