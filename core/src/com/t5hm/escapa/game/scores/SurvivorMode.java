package com.t5hm.escapa.game.scores;

import com.t5hm.escapa.game.WorldSpec;

/**
 * Created by tapomay on 14/9/14.
 */
public class SurvivorMode extends GameScoreBase {

    private WorldSpec.DIFFICULTY difficulty;
    private Integer displayTime;
    public SurvivorMode(WorldSpec.DIFFICULTY difficulty, Integer totalTime) {
        this.difficulty = difficulty;
        this.displayTime = totalTime;
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
}
