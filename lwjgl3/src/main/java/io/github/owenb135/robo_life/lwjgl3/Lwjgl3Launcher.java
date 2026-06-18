package io.github.owenb135.robo_life.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.github.owenb135.robo_life.Main;

public class Lwjgl3Launcher {

    public static void main(String[] args) {
        new Lwjgl3Launcher().run();
    }

    private void run() {
        new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        config.setTitle("Robo Life");

        // fixed window size
        config.setWindowedMode(1280, 720);

        // prevent resizing
        config.setResizable(false);

        return config;
    }
}
