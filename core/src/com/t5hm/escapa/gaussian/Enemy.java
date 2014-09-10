package com.t5hm.escapa.gaussian;

import com.badlogic.gdx.math.MathUtils;

/**
 * Created by tapomay on 6/9/14.
 */
public class Enemy extends MagSphere{

    public static enum QUADRANT {
        FIRST(1), SECOND(2), THIRD(3), FOURTH(4), FIFTH(5);//5=CENTER
        int quadrant;
        QUADRANT(int quad) {
            quadrant = quad;
        }
    }

    public static QUADRANT[] QUADRANT_ARR = {QUADRANT.FIRST,
            QUADRANT.SECOND, QUADRANT.THIRD, QUADRANT.FOURTH, QUADRANT.FIFTH};

    public Enemy(Arena arena, int radius, int initialVelocity, QUADRANT quadrant) {
        super();

        float initialX = 0, initialY = 0;
        int initialDirectionDeg = 0;
        float xMax, xMin, yMax, yMin;
        switch (quadrant) {
            case FIRST: {
                ArenaWall rightWall = arena.getRightWall();
                xMax = rightWall.getX1() - radius;
                xMin = arena.getArenaWidth() / 2;
                yMin = arena.getArenaHeight() / 2;
                yMax = rightWall.getY2() - radius;
                initialX = MathUtils.random(xMin, xMax);
                initialY = MathUtils.random(yMin, yMax);
                initialDirectionDeg = MathUtils.random(0, 90);
            }
            break;
            case SECOND: {
                ArenaWall leftWall = arena.getLeftWall();
                xMax = arena.getArenaWidth() / 2;
                xMin = leftWall.getX1() + radius;
                yMin = arena.getArenaHeight() / 2;
                yMax = leftWall.getY2() - radius;
                initialX = MathUtils.random(xMin, xMax);
                initialY = MathUtils.random(yMin, yMax);
                initialDirectionDeg = MathUtils.random(90, 180);
            }
            break;
            case THIRD: {
                ArenaWall leftWall = arena.getLeftWall();
                xMax = arena.getArenaWidth() / 2;
                xMin = leftWall.getX1() + radius;
                yMin = leftWall.getY1() + radius;
                yMax = arena.getArenaHeight() / 2;
                initialX = MathUtils.random(xMin, xMax);
                initialY = MathUtils.random(yMin, yMax);
                initialDirectionDeg = MathUtils.random(180, 270);
            }
            break;
            case FOURTH: {
                ArenaWall rightWall = arena.getRightWall();
                xMax = rightWall.getX1() - radius;
                xMin = arena.getArenaWidth() / 2;
                yMin = rightWall.getY1() + radius;
                yMax = arena.getArenaHeight() / 2;
                initialX = MathUtils.random(xMin, xMax);
                initialY = MathUtils.random(yMin, yMax);
                initialDirectionDeg = MathUtils.random(270, 360);
            }
            break;
            case FIFTH: {
                initialX = arena.getArenaWidth() / 2;
                initialY = arena.getArenaHeight() / 2;
                initialDirectionDeg = MathUtils.random(0, 360);
            }
            break;
        }
        this.setRadius(radius);
        this.setInitialX(initialX);
        this.setInitialY(initialY);
        this.setInitialVelocity(initialVelocity);
        this.setInitialDirectionDeg(initialDirectionDeg);
    }

}
