package com.t5hm.escapa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.t5hm.escapa.game.scores.GameScoreMode;
import com.t5hm.escapa.gaussian.ControlMagnet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import box2dLight.RayHandler;

/**
 * Created by tapomay on 20/9/14.
 */
public class GamifiedWorld {

    private WorldSpec worldSpec;
    private World world;
    private RayHandler rayHandler;
    private Body playerBody;
    private List<Body> enemyBodyList;
    private List<Fixture> magFixtureList;
    private Set<ControlMagnet> magnetizedMagnets;

    private Float timeElapsed = 0f;
    private Long timeDisplayed = 0l;
    private Long hits = 0l;

    private GameScoreMode gameScoreMode;

    private BitmapFont hitsFont, timeFont;


    public GamifiedWorld(WorldSpec worldSpec) {
        this.worldSpec = worldSpec;
        this.magnetizedMagnets = new HashSet<ControlMagnet>();
        setupScoreLabel();
    }

    private void setupScoreLabel() {
        hitsFont = new BitmapFont(Gdx.files.internal("data/fonts/lc-green.png.fnt"), false);
        timeFont = new BitmapFont(Gdx.files.internal("data/fonts/lc-green.png.fnt"), false);
        hitsFont.setScale(0.5f);
        timeFont.setScale(0.5f);
    }

    public void magnetise(ControlMagnet mag) {
        magnetizedMagnets.add(mag);
//        System.out.println("MAG_ENABLE: " + mag.getArenaWall().getSide());
    }

    public void deMagnetise() {
        magnetizedMagnets.clear();
//        System.out.println("DEMAG");
    }

    public void update(SpriteBatch batch, float delta) {
        for (ControlMagnet mag : magnetizedMagnets) {
//            System.out.println("MAG_APPLY: " + mag.getArenaWall().getSide());
            Vector2 force = mag.getForce();
            playerBody.applyForceToCenter(force.x, force.y, true);
        }

        timeElapsed += delta;
        if (timeElapsed - timeDisplayed > 1) {
            timeDisplayed = timeElapsed.longValue();
        }
        updateScoreLabel(batch);
    }

    private void updateScoreLabel(SpriteBatch batch) {
        String hitsStr = String.valueOf(hits);
        String timeStr = String.valueOf(timeDisplayed);
        hitsStr = "HITS : " + hitsStr;
        timeStr = "TIME : " + timeStr;
        timeFont.draw(batch, timeStr, 10, worldSpec.getWorldHeight() - 10);
        hitsFont.draw(batch, hitsStr, 12, worldSpec.getWorldHeight() - 15 - hitsFont.getLineHeight());
    }

    public WorldSpec getWorldSpec() {
        return worldSpec;
    }

    public void setWorldSpec(WorldSpec worldSpec) {
        this.worldSpec = worldSpec;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public RayHandler getRayHandler() {
        return rayHandler;
    }

    public void setRayHandler(RayHandler rayHandler) {
        this.rayHandler = rayHandler;
    }

    public Body getPlayerBody() {
        return playerBody;
    }

    public void setPlayerBody(Body playerBody) {
        this.playerBody = playerBody;
    }

    public List<Body> getEnemyBodyList() {
        return enemyBodyList;
    }

    public void setEnemyBodyList(List<Body> enemyBodyList) {
        this.enemyBodyList = enemyBodyList;
    }

    public List<Fixture> getMagFixtureList() {
        return magFixtureList;
    }

    public void setMagFixtureList(List<Fixture> magFixtureList) {
        this.magFixtureList = magFixtureList;
    }

    public Set<ControlMagnet> getMagnetizedMagnets() {
        return magnetizedMagnets;
    }

    public void setMagnetizedMagnets(Set<ControlMagnet> magnetizedMagnets) {
        this.magnetizedMagnets = magnetizedMagnets;
    }

    public float getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(float timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public Long getTimeDisplayed() {
        return timeDisplayed;
    }

    public void setTimeDisplayed(long timeDisplayed) {
        this.timeDisplayed = timeDisplayed;
    }

    public GameScoreMode getGameScoreMode() {
        return gameScoreMode;
    }

    public void setGameScoreMode(GameScoreMode gameScoreMode) {
        this.gameScoreMode = gameScoreMode;
    }

    public static void main(String... a) {
        String hitsStr = "0";
        String timeStr = "0";
        String display = MasterBuilder.SCORE_TEMPLATE.replace("${TIME}", timeStr);
        display = display.replace("${HITS}", hitsStr);
        System.out.println(display);
    }
}
