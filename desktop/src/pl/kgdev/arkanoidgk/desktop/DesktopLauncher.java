package pl.kgdev.arkanoidgk.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import pl.kgdev.arkanoidgk.ArkanoidGK;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ArkanoidGK(), config);
		config.width = ArkanoidGK.WIDTH;
		config.height = ArkanoidGK.HEIGHT;
		config.resizable = false;
	}
}
