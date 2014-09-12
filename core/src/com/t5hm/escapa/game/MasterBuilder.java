package com.t5hm.escapa.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.t5hm.escapa.gaussian.Arena;
import com.t5hm.escapa.gaussian.ArenaWall;
import com.t5hm.escapa.gaussian.ControlMagnet;
import com.t5hm.escapa.gaussian.Enemy;
import com.t5hm.escapa.gaussian.MagSphere;
import com.t5hm.escapa.gaussian.Player;

import java.util.ArrayList;
import java.util.List;

import box2dLight.RayHandler;

/**
 * Created by tapomay on 6/9/14.
 */
public class MasterBuilder {

    private World world;
    private RayHandler rayHandler;
    private Body playerBody;
    private List<Body> enemyBodyList = new ArrayList<Body>();
    private List<Body> magBodyList = new ArrayList<Body>();

    public MasterBuilder(World world, RayHandler rayHandler) {
        this.world = world;
        this.rayHandler = rayHandler;
    }

    public void render(SpriteBatch spriteBatch) {

    }

    public World createWorld(WorldSpec worldSpec) {
        Arena arena = worldSpec.getArena();
        materializeArena(arena, worldSpec.getMagList());

        Player player = worldSpec.getPlayer();
        playerBody = materializeMagSphere(player);

        List<Enemy> enemyList = worldSpec.getEnemyList();
        for (Enemy enemy : enemyList) {
            Body enemyBody = materializeMagSphere(enemy);
            enemyBodyList.add(enemyBody);
        }
        return world;
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

    private Body materializeMagSphere(MagSphere sphere) {
        BodyDef circleBodyDef = new BodyDef();
        circleBodyDef.type = BodyDef.BodyType.DynamicBody;
        circleBodyDef.position.set(sphere.getInitialX(), sphere.getInitialY());
        Body body = world.createBody(circleBodyDef);
        Vector2 velocity = new Vector2(sphere.getInitialVelocity(), 0);
        velocity.rotate(sphere.getInitialDirectionDeg());
        body.setLinearVelocity(velocity);
        FixtureDef circleFixtureDef = createCircleFixtureDef(sphere.getRadius());
        body.createFixture(circleFixtureDef);
        body.setUserData(sphere);
        return body;
    }

    private Body materializeControlMagnet(ControlMagnet controlMagnet) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(controlMagnet.getLeftBottomX(), controlMagnet.getLeftBottomY());
        Body groundBody = world.createBody(bodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(controlMagnet.getWidth(), controlMagnet.getHeight());
        groundBody.createFixture(groundBox, ControlMagnet.DENSITY);
        return groundBody;
    }

    private Body materializeArena(Arena arena, List<ControlMagnet> magList) {
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
            System.out.println(mag.getArenaWall().getSide() + " : " + center);
            polygonShape.setAsBox(mag.getWidth() / 2, mag.getHeight() / 2, center, 0f);
            Fixture fixture = arenaBody.createFixture(polygonShape, 0f);
            fixture.setUserData(mag);
        }
        return arenaBody;
    }
}
