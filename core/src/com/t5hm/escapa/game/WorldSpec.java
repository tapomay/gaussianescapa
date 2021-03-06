package com.t5hm.escapa.game;

import com.badlogic.gdx.math.MathUtils;
import com.t5hm.escapa.gaussian.Arena;
import com.t5hm.escapa.gaussian.ArenaWall;
import com.t5hm.escapa.gaussian.ControlMagnet;
import com.t5hm.escapa.gaussian.Enemy;
import com.t5hm.escapa.gaussian.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tapomay on 6/9/14.
 */
public class WorldSpec {

    private static final int INITIAL_VELOCITY_PERCENT = 40; //m/s
    private static final float WALL_MARGIN_RATIO = 0.1f;
    private static final float MAG_MARGIN_RATIO = 0.03f;
    private static final int DEFAULT_RADIUS_PERCENT = 5;
    private static final int MIN_ENEMIES = 5;
    private static final int MAX_ENEMIES = 5;
    private static final int MAG_AMPS = 15000;

    private Arena arena;

    private List<ControlMagnet> magList;
    private Player player;
    private List<Enemy> enemyList;
    private int worldWidth, worldHeight, defaultRadius, initialVelocity;

    public static enum GAME_MODE {
        SURVIVOR, PROGRESSIVE
    }

    public  static enum DIFFICULTY {
        VETERAN(20, 10), HUMANE(10, 20), NOBODY(5, 30);

        public final int penalty;
        public final int hits;
        DIFFICULTY(int i, int j) {
            this.penalty = i;
            this.hits = j;
        }
    }

    public DIFFICULTY getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DIFFICULTY difficulty) {
        this.difficulty = difficulty;
    }

    public GAME_MODE getGameMode() {
        return gameMode;
    }

    public void setGameMode(GAME_MODE gameMode) {
        this.gameMode = gameMode;
    }

    private GAME_MODE gameMode;
    private DIFFICULTY difficulty;

    public static WorldSpec create(int worldWidthArg, int worldHeightArg) {
        WorldSpec ret = new WorldSpec();
        ret.worldWidth = worldWidthArg;
        ret.worldHeight = worldHeightArg;
        ret.defaultRadius = Math.min(ret.worldWidth, ret.worldHeight) * DEFAULT_RADIUS_PERCENT / 100;
        ret.defaultRadius = Math.max(ret.defaultRadius, 5);
        ret.initialVelocity = Math.min(ret.worldWidth, ret.worldHeight) * INITIAL_VELOCITY_PERCENT / 100;
        ret.initialVelocity = Math.max(ret.initialVelocity, 5);

        //CREATE ARENA WITH WALLS
        Arena arena = new Arena(ret.getWorldWidth(), ret.getWorldHeight(), WALL_MARGIN_RATIO);
        ret.setArena(arena);

        //CREATE CONTROL MAGNETS
        List<ControlMagnet> magnets = new ArrayList<ControlMagnet>(5);
        magnets.add(createMagnet(arena.getBottomWall(), ret));
        magnets.add(createMagnet(arena.getLeftWall(), ret));
        magnets.add(createMagnet(arena.getTopWall(), ret));
        magnets.add(createMagnet(arena.getRightWall(), ret));
        ret.setMagList(magnets);

        //CREATE PLAYER
        Player player = new Player(arena, ret.defaultRadius, ret.initialVelocity);
        ret.setPlayer(player);

        //CREATE ENEMIES
        int enemyCount = MathUtils.random(MIN_ENEMIES, MAX_ENEMIES);
        List<Enemy> enemies = new ArrayList<Enemy>(enemyCount + 1);
        for (int i = 0; i < enemyCount; i++) {
            int enemyIndex = MathUtils.random(Enemy.QUADRANT_ARR.length - 1);
            Enemy.QUADRANT quadrant = Enemy.QUADRANT_ARR[enemyIndex];
            Enemy enemy = new Enemy(arena, ret.defaultRadius, ret.initialVelocity, quadrant);
            enemies.add(enemy);
        }
        ret.setEnemyList(enemies);
        ret.gameMode = GAME_MODE.PROGRESSIVE;
//        ret.gameMode = GAME_MODE.SURVIVOR;
        ret.difficulty = DIFFICULTY.HUMANE;
        return ret;
    }

    private static ControlMagnet createMagnet(ArenaWall arenaWall, WorldSpec worldSpec) {
        int currentAmps = MAG_AMPS;

        float xMargin = 0;
        float yMargin = 0;
        float magWidth = 0;
        float magHeight = 0;
        float leftBottomX = 0;
        float leftBottomY = 0;
        switch (arenaWall.getSide()) {

            case BOTTOM: {
                magWidth = arenaWall.getX2() - arenaWall.getX1();
                magHeight = arenaWall.getY2() - 0;
                xMargin = magWidth  * MAG_MARGIN_RATIO;
                yMargin = magHeight * MAG_MARGIN_RATIO;
                magWidth -= 2*xMargin;
                magHeight -= 2*yMargin;
                leftBottomX = arenaWall.getX1() + xMargin;
                leftBottomY = yMargin;
            }
            break;
            case LEFT: {
                magWidth = arenaWall.getX2() - 0;
                magHeight = arenaWall.getY2() - arenaWall.getY1();
                xMargin = magWidth * MAG_MARGIN_RATIO;
                yMargin = magHeight * MAG_MARGIN_RATIO;
                magWidth -= 2*xMargin;
                magHeight -= 2*yMargin;
                leftBottomX = xMargin;
                leftBottomY = arenaWall.getY1() + yMargin;
            }
            break;
            case TOP: {
                magWidth = arenaWall.getX2() - arenaWall.getX1();
                magHeight = worldSpec.getWorldHeight() - arenaWall.getY1();
                xMargin = magWidth  * MAG_MARGIN_RATIO;
                yMargin = magHeight * MAG_MARGIN_RATIO;
                magWidth -= 2*xMargin;
                magHeight -= 2*yMargin;
                leftBottomX = arenaWall.getX1() + xMargin;
                leftBottomY = arenaWall.getY1() + yMargin;
            }
            break;
            case RIGHT: {
                magWidth = worldSpec.getWorldWidth() - arenaWall.getX1();
                magHeight = arenaWall.getY2() - arenaWall.getY1();
                xMargin = magWidth * MAG_MARGIN_RATIO;
                yMargin = magHeight * MAG_MARGIN_RATIO;
                magWidth -= 2*xMargin;
                magHeight -= 2*yMargin;
                leftBottomX = arenaWall.getX1() + xMargin;
                leftBottomY = arenaWall.getY1() + yMargin;
            }
            break;
        }

        ControlMagnet ret = new ControlMagnet(currentAmps, leftBottomY, leftBottomX, magHeight, magWidth, arenaWall);
        return ret;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public void setWorldWidth(int worldWidth) {
        this.worldWidth = worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }

    public void setWorldHeight(int worldHeight) {
        this.worldHeight = worldHeight;
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

    public List<ControlMagnet> getMagList() {
        return magList;
    }

    public void setMagList(List<ControlMagnet> magList) {
        this.magList = magList;
    }

}
