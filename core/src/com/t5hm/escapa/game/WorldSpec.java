package com.t5hm.escapa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.t5hm.escapa.gaussian.Arena;
import com.t5hm.escapa.gaussian.ControlMagnet;
import com.t5hm.escapa.gaussian.Enemy;
import com.t5hm.escapa.gaussian.MagSphere;
import com.t5hm.escapa.gaussian.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tapomay on 6/9/14.
 */
public class WorldSpec {

    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 100;

    private static final int INITIAL_VELOCITY = 100; //m/s
    private static final int MARGIN_PERCENT = 5;
    private static final int DEFAULT_RADIUS_PERCENT = 5;
    private static final int MIN_ENEMIES = 2;
    private static final int MAX_ENEMIES = 3;

    private Player player;
    private List<Enemy> enemyList;
    private Arena arena;
    private int width, height, defaultRadius;

    public static WorldSpec create() {
        WorldSpec ret = new WorldSpec();
        ret.width = Gdx.graphics.getWidth();
        ret.height = Gdx.graphics.getHeight();
        ret.defaultRadius = Math.min(ret.width, ret.height) * DEFAULT_RADIUS_PERCENT / 100;
        ret.defaultRadius = Math.max(ret.defaultRadius, 5);

        //CREATE ARENA WITH WALLS
        Arena arena = new Arena(ret.getWidth(), ret.getHeight(), MARGIN_PERCENT);
        ret.setArena(arena);

        //CREATE PLAYER
        Player player = new Player(arena, ret.defaultRadius, INITIAL_VELOCITY);
        ret.setPlayer(player);

        //CREATE ENEMIES
        List<Enemy> enemies = new ArrayList<Enemy>();
        int enemyCount = MathUtils.random(MIN_ENEMIES, MAX_ENEMIES);
        for (int i = 0; i < enemyCount; i++) {
            int enemyIndex = MathUtils.random(Enemy.QUADRANT_ARR.length - 1);
            Enemy.QUADRANT quadrant = Enemy.QUADRANT_ARR[enemyIndex];
            Enemy enemy = new Enemy(arena, ret.defaultRadius, INITIAL_VELOCITY, quadrant);
            enemies.add(enemy);
        }
        ret.setEnemyList(enemies);

        return ret;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(List<Enemy> enemyList) {
        this.enemyList = enemyList;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }
}
