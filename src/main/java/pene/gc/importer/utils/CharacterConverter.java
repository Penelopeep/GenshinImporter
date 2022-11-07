package pene.gc.importer.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import emu.grasscutter.data.GameData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;

public class CharacterConverter {
    public static void main(JsonArray artifactCodes, Player targetPlayer) {
        for (JsonElement artifactCode : artifactCodes){
            String avatarName = artifactCode.getAsJsonObject().get("location").getAsString();
            int avatarId = Datareader.getAvatarId(avatarName);
            if(targetPlayer.getAvatars().getAvatarById(avatarId)==null) {
                Avatar avatar = new Avatar(GameData.getAvatarDataMap().get(avatarId));
                //TODO: Maybe add a config option to set the level of the avatar?
                avatar.setLevel(1);
                avatar.setPromoteLevel(1);
                avatar.forceConstellationLevel(0);
                avatar.recalcStats();
                targetPlayer.getAvatars().addAvatar(avatar);
            } else {
                System.out.printf("Avatar %s already exists%n", avatarName);
            }
        }
    }
}
