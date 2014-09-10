package com.t5hm.escapa.gaussian;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by tapomay on 6/9/14.
 */
public class Player extends MagSphere implements FerroMagnet{

    public Player(Arena arena, int radius, int initialVelocity) {

        float initialX = arena.getArenaWidth() / 2;
        float initialY = arena.getArenaHeight() / 2;
        int initialDirectionDeg = MathUtils.random(0, 360);

        this.setRadius(radius);
        this.setInitialX(initialX);
        this.setInitialY(initialY);
        this.setInitialVelocity(initialVelocity);
        this.setInitialDirectionDeg(initialDirectionDeg);

    }

    @Override
    public void magnetise(int xNewton, int yNewton) {

    }
}
