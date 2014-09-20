package com.t5hm.escapa.game.scores;

import com.t5hm.escapa.game.WorldSpec;

/**
 * Created by tapomay on 14/9/14.
 */
public class ProgressiveMode extends GameScoreBase {

    private WorldSpec.DIFFICULTY difficulty;
    private long displayHits;

    public ProgressiveMode(WorldSpec.DIFFICULTY difficulty) {
        this.difficulty = difficulty;
        this.displayHits = difficulty.hits;
    }

    @Override
    public String getDisplayHits() {
        return String.valueOf(displayHits);
    }

    @Override
    public Boolean playerHit() {
        super.playerHit();
        displayHits--;
        if (playerHits >= difficulty.hits)
            return true;
        return false;
    }

    @Override
    public String score() {
        int tmp = timeElapsed * 10000;
        Double score = Math.log10(tmp) * 100;
        return String.valueOf(score.intValue());
    }

}
