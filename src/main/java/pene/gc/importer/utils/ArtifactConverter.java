package pene.gc.importer.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.player.Player;
import pene.gc.importer.GenshinImporter;

import java.util.ArrayList;
import java.util.List;

import static pene.gc.importer.utils.Datareader.getArtifactCode;

public class ArtifactConverter {
    public static void main(JsonArray artifactsCodes, Player targetPlayer) {
        int rateLimitCounter = 0;
        int artifactLimitCounter = 0;
        for (JsonElement artifact : artifactsCodes) {
            int artifactCode = getArtifactCode(artifact.getAsJsonObject().get("setKey").getAsString());
            switch (artifact.getAsJsonObject().get("slotKey").getAsString()) {
                case "flower" -> artifactCode += 44;
                case "plume" -> artifactCode += 24;
                case "sands" -> artifactCode += 54;
                case "circlet" -> artifactCode += 34;
                case "goblet" -> artifactCode += 14;
            }
            if (GenshinImporter.getPluginConfig().ArtifactLimit != 0 && artifactLimitCounter == GenshinImporter.getPluginConfig().ArtifactLimit) {
                break;
            }
            int rarity = artifact.getAsJsonObject().get("rarity").getAsInt();
            if (!GenshinImporter.getPluginConfig().ArtifactRarity.contains(rarity)) {
                continue;
            }
            switch (rarity) {
                case 5 -> artifactCode += 500;
                case 4 -> artifactCode += 400;
                case 3 -> artifactCode += 300;
                case 2 -> artifactCode += 200;
                case 1 -> artifactCode += 100;
            }

            String mainStat = null;
            switch (artifact.getAsJsonObject().get("mainStatKey").getAsString()) {
                case "hp" -> mainStat = "hp";
                case "atk" -> mainStat = "atk";
                case "def" -> mainStat = "def";
                case "hp_" -> mainStat = "hp%";
                case "atk_" -> mainStat = "atk%";
                case "def_" -> mainStat = "def%";
                case "eleMas" -> mainStat = "em";
                case "enerRech_" -> mainStat = "er";
                case "critRate_" -> mainStat = "cr";
                case "critDMG_" -> mainStat = "cd";
                case "heal_" -> mainStat = "hb";
                case "hydro_dmg_" -> mainStat = "hydro";
                case "pyro_dmg_" -> mainStat = "pyro";
                case "cryo_dmg_" -> mainStat = "cryo";
                case "electro_dmg_" -> mainStat = "electro";
                case "anemo_dmg_" -> mainStat = "anemo";
                case "geo_dmg_" -> mainStat = "geo";
                case "physical_dmg_" -> mainStat = "phys";
                case "dendro_dmg_" -> mainStat = "dendro";
            }

            String subStatsString = null;
            for (JsonElement subStat : artifact.getAsJsonObject().get("substats").getAsJsonArray()) {
                try {
                    switch (subStat.getAsJsonObject().get("key").getAsString()) {
                        case "hp" -> subStatsString += " hp=";
                        case "atk" -> subStatsString += " atk=";
                        case "def" -> subStatsString += " def=";
                        case "hp_" -> subStatsString += " hp%=";
                        case "atk_" -> subStatsString += " atk%=";
                        case "def_" -> subStatsString += " def%=";
                        case "eleMas" -> subStatsString += " em=";
                        case "enerRech_" -> subStatsString += " er=";
                        case "critRate_" -> subStatsString += " cr=";
                        case "critDMG_" -> subStatsString += " cd=";
                        case "heal_" -> subStatsString += " hb=";
                    }
                    String value = subStat.getAsJsonObject().get("value").getAsString();
                    subStatsString += value;

                } catch (UnsupportedOperationException e) {
                    Exception ignored;
                }
            }
            int level = artifact.getAsJsonObject().get("level").getAsInt() + 1;

            List<String> args = new ArrayList<>();
            args.add(String.valueOf(artifactCode));
            args.add(mainStat);
            args.addAll(List.of(subStatsString.split(" ")));
            args.add(String.valueOf(level));

            if (GenshinImporter.getPluginConfig().rateLimit) {
                rateLimitCounter++;
                if (rateLimitCounter == GenshinImporter.getPluginConfig().rateLimitItems) {
                    try {
                        Thread.sleep(GenshinImporter.getPluginConfig().rateLimitTime* 1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rateLimitCounter = 0;
                }
            }
            ModifiedSnooGive meSnoo = new ModifiedSnooGive();
            GameItem newArtifact =  meSnoo.execute(null, targetPlayer, args);
            if(GenshinImporter.getPluginConfig().equipArtifact && !artifact.getAsJsonObject().get("location").getAsString().equals("")) {
                String avatarName = artifact.getAsJsonObject().get("location").getAsString();
                int avatarId = Datareader.getAvatarId(avatarName);
                targetPlayer.getInventory().getAvatarStorage().getAvatarById(avatarId).equipItem(newArtifact, true);
            }
            args.clear();
            artifactLimitCounter++;
        }
    }
}
