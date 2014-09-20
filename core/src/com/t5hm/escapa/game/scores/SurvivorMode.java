package com.t5hm.escapa.game.scores;

import com.t5hm.escapa.game.WorldSpec;

/**
 * Created by tapomay on 14/9/14.
 */
public class SurvivorMode extends GameScoreBase {

    private static final double MODE_MAX_SCORE = 1000;
    public static final Integer DEFAULT_SURVIVOR_TIME = 10;

    private WorldSpec.DIFFICULTY difficulty;
    private Integer displayTime;
    public SurvivorMode(WorldSpec.DIFFICULTY difficulty, Integer totalTime) {
        this.difficulty = difficulty;
        this.displayTime = totalTime;
        this.timeElapsed = totalTime;
    }

    @Override
    public String getDisplayTime() {
        return String.valueOf(displayTime);
    }

    @Override
    public Boolean timeTick() {
        displayTime --;
        if (displayTime <= 0)
            return true;
        return false;
    }

    @Override
    public String score() {
        float penaltyMultiplier = (float)(100 - difficulty.penalty) / 100;
        double scoreMultiplier  = Math.pow(penaltyMultiplier, playerHits);
        Double score = MODE_MAX_SCORE * scoreMultiplier;
        return String.valueOf(score.intValue());
    }

}
