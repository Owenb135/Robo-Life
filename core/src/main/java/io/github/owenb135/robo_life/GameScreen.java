package io.github.owenb135.robo_life;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    private final Main game;

    private final SpriteBatch batch;
    private final BitmapFont font;

    private final Stage stage;
    private final Skin skin;

    private final Texture background;
    private final Texture player;

    private final SaveData save;

    private enum State { INTRO, PLAYING }
    private State state;

    private final String[] story = {
        "You started off as a homeless robot...",
        "The city surrounds you...",
        "A stranger approaches...",
        "They give you $500.",
        "You now begin your life..."
    };

    private int storyIndex = 0;

    private float playerX;
    private float playerY;

    private int money;
    private float jobTimer = 0f;

    public GameScreen(Main game) {

        this.game = game;

        batch = new SpriteBatch();
        font = new BitmapFont();

        background = new Texture("background.png");
        player = new Texture("player.png");

        save = SaveManager.load();

        playerX = save.x;
        playerY = save.y;

        // 🔥 STORY DECISION LOGIC (IMPORTANT FIX)
        if (save.storyCompleted) {
            state = State.PLAYING;
            money = save.money;
        } else {
            state = State.INTRO;
            money = 500; // FIRST TIME REWARD
        }

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        createButtons();
    }

    private void createButtons() {

        TextButton saveBtn = new TextButton("Save", skin);
        saveBtn.setSize(150, 50);
        saveBtn.setPosition(20, 20);

        saveBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                SaveData data = new SaveData();
                data.money = money;
                data.x = playerX;
                data.y = playerY;
                data.storyCompleted = (state == State.PLAYING);

                SaveManager.save(data);

                System.out.println("Saved!");
            }
        });

        TextButton quitBtn = new TextButton("Quit", skin);
        quitBtn.setSize(150, 50);
        quitBtn.setPosition(200, 20);

        quitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                SaveData data = new SaveData();
                data.money = money;
                data.x = playerX;
                data.y = playerY;
                data.storyCompleted = (state == State.PLAYING);

                SaveManager.save(data);

                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(saveBtn);
        stage.addActor(quitBtn);
    }

    private void drawIntro() {

        ScreenUtils.clear(0, 0, 0, 1);

        font.setColor(1f, 0.84f, 0f, 1f);

        String text = story[storyIndex];

        GlyphLayout layout = new GlyphLayout(font, text);

        float x = (Gdx.graphics.getWidth() - layout.width) / 2f;
        float y = Gdx.graphics.getHeight() / 2f;

        font.draw(batch, layout, x, y);

        font.setColor(1f, 1f, 1f, 1f);

        if (Gdx.input.justTouched()) {

            storyIndex++;

            if (storyIndex >= story.length) {

                state = State.PLAYING;

                // 🚨 ensure reward only happens once
                save.storyCompleted = true;
            }
        }
    }

    private void drawGame(float delta) {

        batch.draw(background, 0, 0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        float speed = 200 * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) playerY += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) playerY -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) playerX -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) playerX += speed;

        batch.draw(player, playerX, playerY, 96, 96);

        jobTimer += delta;

        if (jobTimer > 5f) {
            money += 20;
            jobTimer = 0;
        }

        font.setColor(1, 1, 1, 1);
        font.draw(batch, "Money: $" + money, 20, Gdx.graphics.getHeight() - 80);
    }

    @Override
    public void render(float delta) {

        batch.begin();

        if (state == State.INTRO) {
            drawIntro();
        } else {
            ScreenUtils.clear(0, 0, 0, 1);
            drawGame(delta);
        }

        batch.end();

        if (state == State.PLAYING) {
            stage.act(delta);
            stage.draw();
        }
    }

    @Override public void show() {}
    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        background.dispose();
        player.dispose();
        stage.dispose();
        skin.dispose();
    }
}
