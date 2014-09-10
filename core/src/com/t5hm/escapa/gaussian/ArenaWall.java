package com.t5hm.escapa.gaussian;

/**
 * Created by tapomay on 9/9/14.
 */
public class ArenaWall {
    public static enum SIDE {
        BOTTOM, LEFT, TOP, RIGHT
    }
    private float x1, y1, x2, y2;

    public ArenaWall(int arenaWidth, int arenaHeight, int marginPercent, SIDE side) {
        float xMargin = (float)(arenaWidth * marginPercent) / 100;
        float yMargin = (float)(arenaHeight * marginPercent) / 100;

        switch (side) {
            case BOTTOM :
                x1 = xMargin; y1 = yMargin; x2 = arenaWidth - xMargin; y2 = yMargin;
                break;
            case LEFT:
                x1 = xMargin; y1 = yMargin; x2 = xMargin; y2 = arenaHeight - yMargin;
                break;
            case TOP:
                x1 = xMargin; y1 = arenaHeight - yMargin; x2 = arenaWidth - xMargin; y2 = arenaHeight - yMargin;
                break;
            case RIGHT:
                x1 = arenaWidth - xMargin; y1 = arenaHeight - yMargin; x2 = arenaWidth - xMargin; y2 = yMargin;
                break;
        }
    }

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }
}
