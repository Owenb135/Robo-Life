package io.github.owenb135.robo_life;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen {

    private final Main game;

    private SpriteBatch batch;
    private Texture background;
    private BitmapFont font;

    private Stage stage;
    private Skin skin;

    public MainMenuScreen(Main game) {
        this.game = game;

        batch = new SpriteBatch();
        background = new Texture("background.png");
        font = new BitmapFont();

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton playButton = new TextButton("Play", skin);

        playButton.setSize(250, 60);

        playButton.setPosition(
            (Gdx.graphics.getWidth() - playButton.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - playButton.getHeight()) / 2f - 30
        );

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        stage.addActor(playButton);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);

        batch.begin();

        batch.draw(background, 0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        font.draw(batch, "MAIN MENU",
            (Gdx.graphics.getWidth() / 2f) - 60,
            (Gdx.graphics.getHeight() / 2f) + 120
        );

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override public void show() {}
    @Override public void resize(int w, int h) {
        stage.getViewport().update(w, h, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        font.dispose();
        stage.dispose();
        skin.dispose();
    }
}
