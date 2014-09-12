package com.t5hm.escapa.gaussian;

/**
 * Created by tapomay on 9/9/14.
 */
public class Arena {
    private ArenaWall bottomWall;
    private ArenaWall leftWall;
    private ArenaWall topWall;
    private ArenaWall rightWall;
    private int arenaWidth;
    private int arenaHeight;

    public Arena(int arenaWidth, int arenaHeight, float marginRatio) {
        this.arenaWidth = arenaWidth;
        this.arenaHeight = arenaHeight;
        this.bottomWall = new ArenaWall(arenaWidth, arenaHeight, marginRatio, ArenaWall.SIDE.BOTTOM);
        this.leftWall = new ArenaWall(arenaWidth, arenaHeight, marginRatio, ArenaWall.SIDE.LEFT);
        this.topWall = new ArenaWall(arenaWidth, arenaHeight, marginRatio, ArenaWall.SIDE.TOP);
        this.rightWall = new ArenaWall(arenaWidth, arenaHeight, marginRatio, ArenaWall.SIDE.RIGHT);
    }

    public int getArenaWidth() {
        return arenaWidth;
    }

    public void setArenaWidth(int arenaWidth) {
        this.arenaWidth = arenaWidth;
    }

    public int getArenaHeight() {
        return arenaHeight;
    }

    public void setArenaHeight(int arenaHeight) {
        this.arenaHeight = arenaHeight;
    }

    public ArenaWall getBottomWall() {
        return bottomWall;
    }

    public void setBottomWall(ArenaWall bottomWall) {
        this.bottomWall = bottomWall;
    }

    public ArenaWall getLeftWall() {
        return leftWall;
    }

    public void setLeftWall(ArenaWall leftWall) {
        this.leftWall = leftWall;
    }

    public ArenaWall getTopWall() {
        return topWall;
    }

    public void setTopWall(ArenaWall topWall) {
        this.topWall = topWall;
    }

    public ArenaWall getRightWall() {
        return rightWall;
    }

    public void setRightWall(ArenaWall rightWall) {
        this.rightWall = rightWall;
    }
}
