package com.t5hm.escapa.game.scores;

/**
 * Created by tapomay on 14/9/14.
 */
public interface GameScoreMode {
    String getDisplayTime();
    String getDisplayHits();
    Boolean playerHit();
    Boolean timeTick();
    void reset();
}