package com.t5hm.escapa.game.scores;

/**
 * Created by tapomay on 20/9/14.
 */
public class DisplayScoreMode implements GameScoreMode {

    @Override
    public String getDisplayTime() {
        return "DISPLAYTIME";
    }

    @Override
    public String getDisplayHits() {
        return "DISPLAY HITS";
    }

    @Override
    public Boolean playerHit() {
        return null;
    }

    @Override
    public Boolean timeTick() {
        return null;
    }

    @Override
    public void reset() {

    }

    @Override
    public String score() {
        return "SCORE";
    }

    @Override
    public String gameTime() {
        return "GAME TIME";
    }

}
