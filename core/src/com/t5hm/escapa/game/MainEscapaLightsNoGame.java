package com.t5hm.escapa.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

public class MainEscapaLightsNoGame extends ApplicationAdapter {
    public static final String NAME= "GaussianEscapa", VERSION = "0.0.1";
    private float accumulator = 0;
	SpriteBatch batch;
	Texture img;

    private OrthographicCamera camera;
    World world;
    Box2DDebugRenderer debugRenderer;

    RayHandler rayHandler;
    FPSLogger fpsLogger;
    Body circleBody;

    private int width, height;

    @Override
    public void dispose() {
        world.dispose();
    }

    @Override
	public void create () {
        Gdx.app.log(NAME, "3PO: create()");
		batch = new SpriteBatch();
		img = new Texture("data/badlogic.jpg");

        //START BOX 2D LIGHTS TUTORIAL - SI units - meters
        width = Gdx.graphics.getWidth() / 5;
        height = Gdx.graphics.getHeight() / 5;


        //SETUP CAMERA : point to center of screen
        camera = new OrthographicCamera(width, height);
        float x = width * 0.5f, y = height * 0.5f, z = 100f;
        camera.position.set(x, y, z);
        camera.update();

        //SETUP WORLD: gravity = earth
//        world = new World(new Vector2(0, -9.8f), true);
        world = new World(new Vector2(0, 0f), true);
        debugRenderer = new Box2DDebugRenderer();
        fpsLogger = new FPSLogger();

        BodyDef circleDef = new BodyDef();
        circleDef.type = BodyDef.BodyType.DynamicBody;
        circleDef.position.set(width / 2, height / 2);

        circleBody = world.createBody(circleDef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(9f); // ideal size is tennis ball, but this is larger and in meters

        FixtureDef circleFixture = new FixtureDef();
        ChainShape chainShape = new ChainShape();
        chainShape.createLoop(new Vector2[] { new Vector2(-22, 1),
                new Vector2(22, 1), new Vector2(22, 31), new Vector2(0, 20),
                new Vector2(-22, 31) });
        circleFixture.shape = chainShape ;
//        circleFixture.shape = circleShape;
        circleFixture.density = 0.4f; //kg/m^2
//        circleFixture.friction = 0.2f; // 0-1
        circleFixture.friction = 0f; // 0-1
//        circleFixture.restitution = 0.8f; // 0-1
        circleFixture.restitution = 1f; // 0-1

        circleBody.createFixture(circleFixture);
//        circleBody.applyForceToCenter(100f, -300f, false);
        circleBody.applyLinearImpulse(3000f, -3000f, circleBody.getPosition().x - 10, circleBody.getPosition().y, false);

        //SETTING UP THE GROUND
        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.position.set(0, 3);
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(camera.viewportWidth * 2, 3f);
        groundBody.createFixture(groundBox, 0f);

        BodyDef lWallBodyDef = new BodyDef();
        lWallBodyDef.position.set(0, 3);
        Body lWallBody = world.createBody(lWallBodyDef);
        PolygonShape lWallBox = new PolygonShape();
        lWallBox.setAsBox(3f, camera.viewportHeight * 2);
        lWallBody.createFixture(lWallBox, 0f);


        BodyDef roofBodyDef = new BodyDef();
        roofBodyDef.position.set(0, camera.viewportHeight - 3);
        Body roofBody = world.createBody(roofBodyDef);
        PolygonShape roofBox = new PolygonShape();
        roofBox.setAsBox(camera.viewportWidth * 2, 3f);
        roofBody.createFixture(roofBox, 0f);

        BodyDef rWallBodyDef = new BodyDef();
        rWallBodyDef.position.set(camera.viewportWidth - 3, 0);
        Body rWallBody = world.createBody(rWallBodyDef);
        PolygonShape rWallBox = new PolygonShape();
        rWallBox.setAsBox(3f, camera.viewportHeight * 2);
        rWallBody.createFixture(rWallBox, 0f);


        //LIGHTS STUFF
        RayHandler.setGammaCorrection(true);
//        RayHandler.useDiffuseLight(true);

        rayHandler = new RayHandler(world);
        rayHandler.setCombinedMatrix(camera.combined);
        rayHandler.setAmbientLight(0.2f, 0.2f, 0.2f, 0.1f);
        rayHandler.setCulling(true);
        rayHandler.setBlurNum(1);
        camera.update(true);

        int rays = 3000;
        Color color = Color.WHITE;
        float distance = 100;
        float rayX = (width / 2) - 50, rayY = (height / 2) + 15;

        PointLight pointLight = new PointLight(rayHandler, rays, color, distance, rayX, rayY);
        float directionDegree = -90f;
        float coneDegree = 60f;
        pointLight.attachToBody(circleBody, 0, 0.5f);
        new ConeLight(rayHandler, rays, Color.TEAL, distance, lWallBody.getPosition().x, camera.viewportHeight / 2, 0f, coneDegree);
        new ConeLight(rayHandler, rays, Color.OLIVE, distance, camera.viewportWidth / 2, groundBody.getPosition().y, 90f, coneDegree);
        new ConeLight(rayHandler, rays, Color.PINK, distance, rWallBody.getPosition().x, camera.viewportHeight / 2, 180, coneDegree);
        new ConeLight(rayHandler, rays, Color.NAVY, distance, camera.viewportWidth / 2, roofBody.getPosition().y, 270, coneDegree);


        //INPUT PROCESSOR
        Gdx.input.setInputProcessor(new EscapaLightsInputAdapter());
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, Constants.Game.WIDTH/2 - img.getWidth()/2, Constants.Game.HEIGHT/2 - img.getHeight()/2);
//		batch.end();
        debugRenderer.render(world, camera.combined);
        rayHandler.updateAndRender();
        doPhysicsStep(Gdx.graphics.getDeltaTime());
        fpsLogger.log();
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