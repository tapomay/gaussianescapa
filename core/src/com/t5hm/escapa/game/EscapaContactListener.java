package com.t5hm.escapa.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.t5hm.escapa.gaussian.Enemy;
import com.t5hm.escapa.gaussian.Player;

/**
 * Created by tapomay on 20/9/14.
 */
public class EscapaContactListener implements ContactListener {

    private GamifiedWorld gamifiedWorld;

    public EscapaContactListener(GamifiedWorld gamifiedWorld) {
        this.gamifiedWorld = gamifiedWorld;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object userDataA = fixtureA.getUserData();
        Object userDataB = fixtureB.getUserData();
        if (userDataA instanceof Player) {
            if (userDataB instanceof Enemy)
                gamifiedWorld.playerHit();
        } else if (userDataB instanceof Player) {
            if (userDataA instanceof Enemy)
                gamifiedWorld.playerHit();
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
