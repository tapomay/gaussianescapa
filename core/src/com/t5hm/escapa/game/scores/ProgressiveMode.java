package com.t5hm.escapa.game.scores;

import com.t5hm.escapa.game.WorldSpec;

/**
 * Created by tapomay on 14/9/14.
 */
public class ProgressiveMode extends GameScoreBase {

    private WorldSpec.DIFFICULTY difficulty;

    public ProgressiveMode(WorldSpec.DIFFICULTY difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String getDisplayTime() {
        return String.valueOf(timeElapsed);
    }

    @Override
    public String score() {
        return String.valueOf(1000);
    }

}
