package com.t5hm.escapa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.t5hm.escapa.game.scores.GameScoreMode;
import com.t5hm.escapa.game.scores.ProgressiveMode;
import com.t5hm.escapa.game.scores.SurvivorMode;
import com.t5hm.escapa.gaussian.Arena;
import com.t5hm.escapa.gaussian.ArenaWall;
import com.t5hm.escapa.gaussian.ControlMagnet;
import com.t5hm.escapa.gaussian.Enemy;
import com.t5hm.escapa.gaussian.MagSphere;
import com.t5hm.escapa.gaussian.Player;

import java.util.ArrayList;
import java.util.List;

import box2dLight.Light;
import box2dLight.PointLight;
import box2dLight.RayHandler;

/**
 * Created by tapomay on 6/9/14.
 */
public class MasterBuilder {

    private static final int RAYS_PER_BALL = 200;
    private static final float LIGHT_DISTANCE = 30f;
    public static final String TIME_TEMPLATE = "TIME: ${TIME}";
    public static final String HITS_TEMPLATE = "HITS: ${HITS}";
    public static final String SCORE_TEMPLATE = "TIME: ${TIME} \nHITS: ${HITS}";


    public MasterBuilder() {
    }

    public GamifiedWorld createWorld(World world, RayHandler rayHandler, WorldSpec worldSpec) {

        GamifiedWorld gamifiedWorld = new GamifiedWorld(worldSpec);
        Arena arena = worldSpec.getArena();

        List<ControlMagnet> magList = worldSpec.getMagList();
        List<Fixture> magFixtureList = new ArrayList<Fixture>(magList.size());
        materializeArena(world, arena, magList, magFixtureList);
        gamifiedWorld.setMagFixtureList(magFixtureList);

        Player player = worldSpec.getPlayer();
        Body playerBody = materializeMagSphere(world, player);
        gamifiedWorld.setPlayerBody(playerBody);

        List<Enemy> enemyList = worldSpec.getEnemyList();
        List<Body> enemyBodyList = new ArrayList<Body>(enemyList.size());
        for (Enemy enemy : enemyList) {
            Body enemyBody = materializeMagSphere(world, enemy);
            enemyBodyList.add(enemyBody);
        }

        gamifiedWorld.setEnemyBodyList(enemyBodyList);
        setupEffects(rayHandler, playerBody, enemyBodyList);

        GameScoreMode gameScoreMode = setupScoreMode(worldSpec.getGameMode(), worldSpec.getDifficulty());
        gamifiedWorld.setGameScoreMode(gameScoreMode);

        EscapaContactListener contactListener = new EscapaContactListener(gamifiedWorld);
        world.setContactListener(contactListener);

        return gamifiedWorld;
    }

    private GameScoreMode setupScoreMode(WorldSpec.GAME_MODE gameMode, WorldSpec.DIFFICULTY difficulty) {
        GameScoreMode gameScoreMode = null;
        switch (gameMode) {

            case SURVIVOR:
                gameScoreMode = new SurvivorMode(difficulty, SurvivorMode.DEFAULT_SURVIVOR_TIME);
                break;
            case PROGRESSIVE:
                gameScoreMode = new ProgressiveMode(difficulty);
                break;
        }
        return gameScoreMode;
    }

    private void setupEffects(RayHandler rayHandler, Body playerBody, List<Body> enemyBodyList) {
        MathUtils.random.setSeed(Long.MIN_VALUE);

        rayHandler.setAmbientLight(0.2f, 0.2f, 0.2f, 0.1f);
        rayHandler.setCulling(true);
        rayHandler.setBlurNum(1);

        Light light = new PointLight(rayHandler, RAYS_PER_BALL * 2);
        light.setDistance(LIGHT_DISTANCE * 2);
        light.attachToBody(playerBody, 0, 0.5f);
        light.setColor(0, 255, 255, 1f);

        for (Body enemyBody : enemyBodyList) {
            Light enemylight = new PointLight(rayHandler, RAYS_PER_BALL);
            enemylight.setDistance(LIGHT_DISTANCE);
            enemylight.attachToBody(enemyBody, 0, 0.5f);
            enemylight.setColor(MathUtils.random(), MathUtils.random(),
                    MathUtils.random(), 1f);
        }
    }

    private FixtureDef createCircleFixtureDef(int radius) {
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(radius);
        FixtureDef circleFixtureDef = new FixtureDef();
        circleFixtureDef.shape = circleShape ;
        circleFixtureDef.density = MagSphere.DENSITY; //kg/m^2
        circleFixtureDef.friction = MagSphere.FRICTION; // 0-1
        circleFixtureDef.restitution = MagSphere.RESTITUTION; // 0-1
        return circleFixtureDef;
    }

    private Body materializeMagSphere(World world, MagSphere sphere) {
        BodyDef circleBodyDef = new BodyDef();
        circleBodyDef.type = BodyDef.BodyType.DynamicBody;
        circleBodyDef.position.set(sphere.getInitialX(), sphere.getInitialY());
        Body body = world.createBody(circleBodyDef);
        Vector2 velocity = new Vector2(sphere.getInitialVelocity(), 0);
        velocity.rotate(sphere.getInitialDirectionDeg());
        body.setLinearVelocity(velocity);
        FixtureDef circleFixtureDef = createCircleFixtureDef(sphere.getRadius());
        Fixture fixture = body.createFixture(circleFixtureDef);
        fixture.setUserData(sphere);
        return body;
    }

    private Body materializeArena(World world, Arena arena, List<ControlMagnet> magList, List<Fixture> magFixtureList) {
        BodyDef arenaBodyDef = new BodyDef();
        arenaBodyDef.position.set(0, 0);
        arenaBodyDef.type = BodyDef.BodyType.StaticBody;
        Body arenaBody = world.createBody(arenaBodyDef);

        EdgeShape wallShape = new EdgeShape();
        FixtureDef wallFixtureDef = new FixtureDef();
        wallFixtureDef.restitution = 1f;
        wallFixtureDef.friction = 0f;
        wallFixtureDef.density = 1f;
        wallFixtureDef.shape = wallShape;

        ArenaWall bottomWall = arena.getBottomWall();
        wallShape.set(bottomWall.getX1(), bottomWall.getY1(), bottomWall.getX2(), bottomWall.getY2());
        Fixture bottomWallFixture = arenaBody.createFixture(wallFixtureDef);
        bottomWallFixture.setUserData(bottomWall);

        ArenaWall leftWall = arena.getLeftWall();
        wallShape.set(leftWall.getX1(), leftWall.getY1(), leftWall.getX2(), leftWall.getY2());
        Fixture leftWallFixture = arenaBody.createFixture(wallFixtureDef);
        leftWallFixture.setUserData(leftWall);

        ArenaWall topWall = arena.getTopWall();
        wallShape.set(topWall.getX1(), topWall.getY1(), topWall.getX2(), topWall.getY2());
        Fixture topWallFixture = arenaBody.createFixture(wallFixtureDef);
        topWallFixture.setUserData(topWall);

        ArenaWall rightWall = arena.getRightWall();
        wallShape.set(rightWall.getX1(), rightWall.getY1(), rightWall.getX2(), rightWall.getY2());
        Fixture rightWallFixture = arenaBody.createFixture(wallFixtureDef);
        rightWallFixture.setUserData(rightWall);

        PolygonShape polygonShape = new PolygonShape();

        for (ControlMagnet mag : magList) {
            System.out.println(mag);
            System.out.println(mag.getArenaWall());
            float xcenter = mag.getLeftBottomX() + mag.getWidth() / 2;
            float ycenter = mag.getLeftBottomY() + mag.getHeight() / 2;
            Vector2 center = new Vector2(xcenter, ycenter);
//            System.out.println(mag.getArenaWall().getSide() + " : " + center);
            polygonShape.setAsBox(mag.getWidth() / 2, mag.getHeight() / 2, center, 0f);
            Fixture fixture = arenaBody.createFixture(polygonShape, 0f);
            fixture.setUserData(mag);
            magFixtureList.add(fixture);
        }
        return arenaBody;
    }

}
