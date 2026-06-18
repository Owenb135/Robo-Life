package io.github.owenb135.robo_life;

import com.badlogic.gdx.Game;

public class Main extends Game {

    @Override
    public void create() {
        setScreen(new SplashScreen(this));
    }
}
