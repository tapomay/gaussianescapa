package com.t5hm.escapa.gaussian;

/**
 * Created by tapomay on 6/9/14.
 */
public class MagSphere {

    public static final float DENSITY = 0.1f;//kg/m^2
    public static final float FRICTION = 0f;//0-1
    public static final float RESTITUTION = 1f;
    private int radius;
    private int initialVelocity;
    private int initialDirectionDeg;
    private Profile profile;
    private float initialX;
    private float initialY;

    protected MagSphere() {}

    public MagSphere(float initialX, float initialY, int initialVelocity, int initialDirectionDeg, int radius) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.initialVelocity = initialVelocity;
        this.initialDirectionDeg = initialDirectionDeg;
        this.radius = radius;
    }

    public int getInitialVelocity() {
        return initialVelocity;
    }

    public void setInitialVelocity(int initialVelocity) {
        this.initialVelocity = initialVelocity;
    }

    public int getInitialDirectionDeg() {
        return initialDirectionDeg;
    }

    public void setInitialDirectionDeg(int initialDirectionDeg) {
        this.initialDirectionDeg = initialDirectionDeg;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public float getInitialX() {
        return initialX;
    }

    public void setInitialX(float initialX) {
        this.initialX = initialX;
    }

    public float getInitialY() {
        return initialY;
    }

    public void setInitialY(float initialY) {
        this.initialY = initialY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

}
