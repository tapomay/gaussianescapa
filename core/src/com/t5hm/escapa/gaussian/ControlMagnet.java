package com.t5hm.escapa.gaussian;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by tapomay on 6/9/14.
 */
public class ControlMagnet {
    private int currentAmps;
    private float leftBottomY;
    private float leftBottomX;
    private float height;
    private float width;
    private ArenaWall arenaWall;
    private Vector2 force;

    public ControlMagnet(int currentAmps, float leftBottomY, float leftBottomX, float height, float width, ArenaWall arenaWall) {
        this.currentAmps = currentAmps;
        this.leftBottomY = leftBottomY;
        this.leftBottomX = leftBottomX;
        this.height = height;
        this.width = width;
        this.arenaWall = arenaWall;
        this.force = computeForceVector(arenaWall);
    }

    private Vector2 computeForceVector(ArenaWall arenaWall) {
        Vector2 ret = null;
        switch (arenaWall.getSide()) {
            case BOTTOM:
                ret = new Vector2(0, -this.currentAmps);
                break;
            case LEFT:
                ret = new Vector2(-this.currentAmps, 0);
                break;
            case TOP:
                ret = new Vector2(0, this.currentAmps);
                break;
            case RIGHT:
                ret = new Vector2(this.currentAmps, 0);
                break;
        }
        return ret;
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

    public Vector2 getForce() {
        return force;
    }

    public void setForce(Vector2 force) {
        this.force = force;
    }
}
