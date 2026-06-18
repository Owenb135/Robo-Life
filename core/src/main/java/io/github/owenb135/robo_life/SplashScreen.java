package io.github.owenb135.robo_life;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SplashScreen implements Screen {

    private final Main game;

    private SpriteBatch batch;
    private Texture logo;

    private float timer = 0f;

    public SplashScreen(Main game) {
        this.game = game;

        batch = new SpriteBatch();
        logo = new Texture("libgdx.png");
    }

    @Override
    public void render(float delta) {

        timer += delta;

        if (timer >= 3f) {
            game.setScreen(new MainMenuScreen(game));
        }

        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();

        float x = (Gdx.graphics.getWidth() - logo.getWidth()) / 2f;
        float y = (Gdx.graphics.getHeight() - logo.getHeight()) / 2f;

        batch.draw(logo, x, y);

        batch.end();
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        logo.dispose();
    }
}
