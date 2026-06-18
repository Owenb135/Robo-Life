package io.github.owenb135.robo_life;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class SaveManager {

    private static final String FILE = "save.json";
    private static Json json = new Json();

    public static SaveData load() {
        FileHandle file = Gdx.files.local(FILE);

        if (!file.exists()) {
            return new SaveData();
        }

        try {
            return json.fromJson(SaveData.class, file.readString());
        } catch (Exception e) {
            return new SaveData();
        }
    }

    public static void save(SaveData data) {
        FileHandle file = Gdx.files.local(FILE);
        file.writeString(json.prettyPrint(data), false);
    }
}
