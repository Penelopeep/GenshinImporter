package pene.gc.importer.utils;

import ch.qos.logback.classic.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.avatar.AvatarSkillDepotData;
import emu.grasscutter.game.avatar.Avatar;
import emu.grasscutter.game.player.Player;
import pene.gc.importer.GenshinImporter;

public class CharacterConverter {
    public static void main(JsonArray charactersArray, Player targetPlayer) {
        int count = 0;
        Logger logger = Grasscutter.getLogger();
        // int rarity = 0;
        for (JsonElement character : charactersArray) {
            if (GenshinImporter.getPluginConfig().CharacterLimit != 0 && count == GenshinImporter.getPluginConfig().CharacterLimit) {
                break;
            }
            JsonObject avatarObject = character.getAsJsonObject();
            String avatarName = avatarObject.get("key").getAsString();
            logger.debug("Reading avatar " + avatarName);
            if (avatarName.startsWith("Traveler")) {
                logger.warn("Skipping traveler - " + avatarName);
                continue;
            }
            int avatarId = Datareader.getAvatarId(avatarName);
            if (targetPlayer.getAvatars().getAvatarById(avatarId) != null) {
                logger.warn("Avatar " + avatarName + " already exists");
                continue;
            }
            Avatar avatar = new Avatar(GameData.getAvatarDataMap().get(avatarId));
            avatar.setLevel(avatarObject.get("level").getAsInt());
            avatar.setPromoteLevel(avatarObject.get("ascension").getAsInt());
            avatar.forceConstellationLevel(avatarObject.get("constellation").getAsInt());

            JsonObject talents = avatarObject.get("talent").getAsJsonObject();
            int autoSL = talents.get("auto").getAsInt();
            int skillSL = talents.get("skill").getAsInt();
            int burstSL = talents.get("burst").getAsInt();

            AvatarSkillDepotData skillDepotData = avatar.getSkillDepot();
            assert skillDepotData != null;

            int[] skillArray = skillDepotData.getSkillsAndEnergySkill().toArray();
            int executions = 0;
            for (int skillId : skillArray) {
                executions += 1;
                if (executions == 1) {
                    avatar.setSkillLevel(skillId, autoSL);
                } else if (executions == 2) {
                    avatar.setSkillLevel(skillId, skillSL);
                } else if (executions == 3) {
                    avatar.setSkillLevel(skillId, burstSL);
                }
            }

            avatar.recalcStats();

            logger.debug("Adding avatar " + avatarName);
            targetPlayer.getAvatars().addAvatar(avatar);
            count++;
        }
    }
}