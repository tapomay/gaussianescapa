package com.t5hm.escapa.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.t5hm.escapa.game.GaussianEscapaStart;
import com.t5hm.escapa.game.MainEscapaGame;
import com.t5hm.escapa.game.MainEscapaLightsNoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "GaussianEscapa";
        config.width = 720;
        config.height = 360;
//        config.width = 1280;
//        config.height = 720;
        config.useGL30 = true;
//        new LwjglApplication(new MainEscapaGame(), config);
        new LwjglApplication(new GaussianEscapaStart(), config);
	}
}
