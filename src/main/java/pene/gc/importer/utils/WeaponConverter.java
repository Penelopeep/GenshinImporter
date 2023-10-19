package pene.gc.importer.utils;

import ch.qos.logback.classic.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import emu.grasscutter.Grasscutter;
import emu.grasscutter.data.GameData;
import emu.grasscutter.data.excels.ItemData;
import emu.grasscutter.game.inventory.GameItem;
import emu.grasscutter.game.inventory.ItemType;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.utils.lang.Language;
import pene.gc.importer.GenshinImporter;
import pene.gc.importer.objects.PluginConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static emu.grasscutter.GameConstants.ILLEGAL_WEAPONS;

public class WeaponConverter {
    static Logger logger = Grasscutter.getLogger();

    public static String fitNameToGOODStandard(String name) {
        logger.debug("Cleaning weapon name: " + name);
        name = name.replaceAll("-", " ");
        // Remove spaces and make each word capitalized
        String[] nameParts = name.split(" ");
        List<String> mutableStringArray = new ArrayList<>();
        for (String namePart : nameParts) {
            logger.debug("Part (before): " + namePart);
            String part = namePart;
            part = part.replaceAll("\"", "");
            part = part.substring(0, 1).toUpperCase() + part.substring(1).toLowerCase();
            logger.debug("Part (after): " + part);
            mutableStringArray.add(part);
        }
        String cleanedName = String.join("", mutableStringArray);
        logger.debug("After join (1): " + cleanedName);
        // Remove all non-alphanumeric characters
        String regexCleaned = cleanedName.replaceAll("[^a-zA-Z0-9]", "");
        logger.debug("After regex (2): " + regexCleaned);
        return regexCleaned;
    }

    public static HashMap<String, ItemData> buildWeaponMap() {
        HashMap<String, ItemData> weaponMap = new HashMap<>();

        for (ItemData itemdata : GameData.getItemDataMap().values()) {
            int id = itemdata.getId();
            if (id < 11100 || id > 16000) continue; // All extant weapons are within this range
            if (ILLEGAL_WEAPONS.contains(id)) continue;
            if (!itemdata.isEquip()) continue;
            if (itemdata.getItemType() != ItemType.ITEM_WEAPON) continue;

            long weaponNameHash = itemdata.getNameTextMapHash();
            Language.TextStrings possibleNames = Language.getTextMapKey(weaponNameHash);
            String baseWeaponName = possibleNames.get("EN");
            String weaponName = fitNameToGOODStandard(baseWeaponName);
            logger.debug("Found weapon: " + weaponName);

            weaponMap.put(weaponName, itemdata);
        }
        return weaponMap;
    }

    public static void main(JsonArray weaponsArray, Player targetPlayer) {
        PluginConfig config = GenshinImporter.getPluginConfig();
        HashMap<String, ItemData> weaponMap = buildWeaponMap();

        int count = 0;
        // int rarity = 0;
        for (JsonElement weapon : weaponsArray) {
            if (config.WeaponLimit != 0 && count == config.WeaponLimit) {
                break;
            }
            JsonObject weaponObject = weapon.getAsJsonObject();

            String weaponName = weaponObject.get("key").getAsString();
            logger.debug("Reading weapon " + weaponName);
            int weaponLevel = weaponObject.get("level").getAsInt();
            int weaponAscension = weaponObject.get("ascension").getAsInt();
            int weaponRefinement = weaponObject.get("refinement").getAsInt();
            String avatarName = weaponObject.get("location").getAsString();
            int avatarId = 0;
            if (avatarName != null && !avatarName.isEmpty()) {
                avatarId = Datareader.getAvatarId(avatarName);
            }

            boolean weaponLock = weaponObject.get("lock").getAsBoolean();

            logger.debug("Attempting to create weapon " + weaponName);
            ItemData weaponData = weaponMap.get(weaponName);
            if (weaponData == null) {
                logger.error("Failed to find weapon " + weaponName);
                continue;
            }

            GameItem item = new GameItem(weaponData);
            item.setLevel(weaponLevel);
            item.setPromoteLevel(weaponAscension);
            item.setRefinement(weaponRefinement - 1);
            item.setLocked(weaponLock);
            targetPlayer.getInventory().addItem(item);
            if (GenshinImporter.getPluginConfig().equipWeapon && avatarId != 0) {
                targetPlayer.getInventory().getAvatarStorage().getAvatarById(avatarId).equipItem(item, true);
            }
            logger.debug("Weapon added: " + weaponName);

            count++;
        }
    }
}