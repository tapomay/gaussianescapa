package com.t5hm.escapa.gaussian;

/**
 * Created by tapomay on 6/9/14.
 */
public class ControlMagnet {
    public static final float DENSITY = 1f;
    private int currentAmps;
    private float leftBottomY;
    private float leftBottomX;
    private float height;
    private float width;
    private ArenaWall arenaWall;

    public ControlMagnet(int currentAmps, float leftBottomY, float leftBottomX, float height, float width, ArenaWall arenaWall) {
        this.currentAmps = currentAmps;
        this.leftBottomY = leftBottomY;
        this.leftBottomX = leftBottomX;
        this.height = height;
        this.width = width;
        this.arenaWall = arenaWall;
    }

    public ArenaWall getArenaWall() {
        return arenaWall;
    }

    public void setArenaWall(ArenaWall arenaWall) {
        this.arenaWall = arenaWall;
    }

    public int getCurrentAmps() {
        return currentAmps;
    }

    public void setCurrentAmps(int currentAmps) {
        this.currentAmps = currentAmps;
    }

    public float getLeftBottomY() {
        return leftBottomY;
    }

    public void setLeftBottomY(float leftBottomY) {
        this.leftBottomY = leftBottomY;
    }

    public float getLeftBottomX() {
        return leftBottomX;
    }

    public void setLeftBottomX(float leftBottomX) {
        this.leftBottomX = leftBottomX;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public String toString() {
        return "ControlMagnet{" +
                "currentAmps=" + currentAmps +
                ", leftBottomY=" + leftBottomY +
                ", leftBottomX=" + leftBottomX +
                ", height=" + height +
                ", width=" + width +
                ", side=" + arenaWall.getSide() +
                '}';
    }
}
