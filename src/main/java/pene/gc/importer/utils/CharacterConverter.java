package pene.gc.importer.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import pene.gc.importer.GenshinImporter;

public class CharacterConverter {
    public static void main(JsonArray artifactCodes, Player targetPlayer) {
        int count = 0;
        int rarity = 0;
        for (JsonElement artifactCode : artifactCodes){
            if (GenshinImporter.getPluginConfig().CharacterLimit != 0 && count == GenshinImporter.getPluginConfig().CharacterLimit) {
                break;
            }
            String avatarName = artifactCode.getAsJsonObject().get("location").getAsString();
            int avatarId = Datareader.getAvatarId(avatarName);
            if(targetPlayer.getAvatars().getAvatarById(avatarId)==null) {
                Avatar avatar = new Avatar(GameData.getAvatarDataMap().get(avatarId));
                //TODO: Maybe add a config option to set the level of the avatar?
                avatar.setLevel(1);
                avatar.setPromoteLevel(1);
                avatar.forceConstellationLevel(0);
                avatar.recalcStats();
                switch (avatar.getAvatarData().getQualityType()) {
                    case "QUALITY_PURPLE" -> rarity = 4;
                    case "QUALITY_ORANGE", "QUALITY_ORANGE_SP" -> rarity = 5;
                }
                if (GenshinImporter.getPluginConfig().CharacterRarity.contains(rarity)) {
                    targetPlayer.getAvatars().addAvatar(avatar);
                    count++;
                }
            } else {
                System.out.printf("Avatar %s already exists%n", avatarName);
            }
        }
    }
}
