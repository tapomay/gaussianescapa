package com.t5hm.escapa.game.scores;

/**
 * Created by tapomay on 20/9/14.
 */
public abstract class GameScoreBase implements GameScoreMode{

    protected Integer timeElapsed = 0;
    protected Long playerHits = 0l;

    @Override
    public abstract String getDisplayTime();

    @Override
    public String getDisplayHits() {
        return String.valueOf(playerHits);
    }

    @Override
    public Boolean playerHit() {
        playerHits++;
        return false;
    }

    @Override
    public Boolean timeTick() {
        timeElapsed++;
        return false;
    }

    @Override
    public void reset() {
        timeElapsed = 0;
        playerHits = 0l;
    }
}
