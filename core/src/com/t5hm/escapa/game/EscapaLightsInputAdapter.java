package com.t5hm.escapa.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.t5hm.escapa.gaussian.ControlMagnet;

import java.util.List;

/**
 * Created by tapomay on 1/9/14.
 */
public class EscapaLightsInputAdapter extends InputAdapter {

    private OrthographicCamera camera;
    private GamifiedWorld gamifiedWorld;
    private Vector3 testPoint = new Vector3();

    public EscapaLightsInputAdapter(OrthographicCamera camera, GamifiedWorld gamifiedWorld) {
        this.camera = camera;
        this.gamifiedWorld = gamifiedWorld;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        testPoint.set(screenX, screenY, 0);
//        System.out.println("TOUCHDOWN-PRE: " + testPoint);
        camera.unproject(testPoint);
//        System.out.println("TOUCHDOWN: " + testPoint);
        List<Fixture> magFixtureList = gamifiedWorld.getMagFixtureList();
        for (Fixture magFixture : magFixtureList) {
//            System.out.println("Testing against: " + magFixture.getBody().getPosition());
            boolean b = magFixture.testPoint(testPoint.x, testPoint.y);
            if (b) {
                ControlMagnet mag = (ControlMagnet) magFixture.getUserData();
                gamifiedWorld.magnetise(mag);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        gamifiedWorld.deMagnetise();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }
}
