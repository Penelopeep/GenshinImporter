package pene.gc.importer.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.game.player.Player;
import pene.gc.importer.GenshinImporter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class Datareader {
    public static int getArtifactCode(String setKey) {
        ClassLoader classLoader = GenshinImporter.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("artifacts.json");
             InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            return new JsonParser().parse(reader).getAsJsonObject().get(setKey).getAsInt();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
    public static void artifacts(Player targetPlayer, String filemane){
        String filepath = String.format("%s/GenshinImporter/%s.json",Grasscutter.getConfig().folderStructure.plugins, filemane);
        File file1 = new File(filepath);
            try (
                InputStream inputStream = new DataInputStream(new FileInputStream(file1));
                InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)) {
                JsonArray artifactsCodes = new JsonParser().parse(reader).getAsJsonObject().get("artifacts").getAsJsonArray();
                Converter.main(artifactsCodes, targetPlayer);
                reader.close();
                streamReader.close();
                inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
