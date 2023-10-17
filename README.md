# <center> GenshinImporter </center>
 <center> Plugin that nobody asked for but many of us needed.  <br />
Allows for quickly importing hundreds of your artifacts from official server.</center>

## Usage

1. Get your genshin data from [InventoryKamera](https://github.com/Andrewthe13th/Inventory_Kamera/)
2. Download GenshinImporter from [releases](https://github.com/Penelopeep/GenshinImporter/releases/latest) or from [actions](https://github.com/Penelopeep/GenshinImporter/actions)
3. Launch you server with plugin installed
4. Now open `Grasscutter_Folder/Plugins/CharacterBuilder/Data` and paste your data (with .json extension) from InventoryKamera to this folder. <br>
 I recommend renaming it to something like `data.json` so you can easily type it later.
4. Use command `/import <file name> or !import <file name>` to get items (don't add .json extension in command)

## Settings
In `Grasscutter_Folder/Plugins/GenshinImporter/Settings.json` you can change some settings:
- `Artifacts/Weapons/Characters/Materials` - if you want to import artifacts/weapons/characters/materials set to true, otherwise false (currently only artifacts are supported)
- `(Artifacts/Weapons/Characters/Materials)Limit` - limit of items to import (if you have 1000 artifacts and you set limit to 100, only 100 artifacts will be imported) (currently not working)
- `(Artifacts/Weapons/Characters)Rarity` - list of rarities to import (if you have 5 star artifact and you set rarity to 4, it won't be imported) (currently not working)
- `(artifacts/weapons)Equip` - plugin will try to equip artifacts/weapons on character (currently only artifacts). If anything goes wrong, disable it.
- `rateLimit` - true/false, if true, plugin will wait each batch of items to be imported before importing next batch. If false, plugin will import all items at once.
- `rateLimitTime` - time in seconds to wait before importing next batch of items. <br>
  **Note: Don't set rateLimitTime to low value, otherwise you will get kicked from server. I recommend setting it to 10 seconds.**
- `rateLimitItems` - number of items to import in one batch.

## Helping
This plugin is built for InventoryKamera data, but if you have other data source, you can help me by sending me output from other inventory scanner like:<br>
- [AdeptiScanner](https://github.com/D1firehail/AdeptiScanner-GI) (I couldn't get it to work myself).
- [Artiscan](https://artiscan.ninjabay.org/#/) (Manual scanner that requires you to manually add video or images of your inventory).
- Any other scanner that outputs data in (preferably) json format.

## FAQ
1. Why some settings are missing?
   - If you update plugin, settings won't be updated. You have to delete `Grasscutter_Folder/Plugins/GenshinImporter/Settings.json` and plugin will generate new one with all settings.
1. Why would you upload plugin in this state?
   - I think that basic functionality is enough for most people, and I want to hear/see feedback on this.
1. Some 4* are fucked, don't ask for it.
1. How to install plugin?
    - Add the newest jar file from [here](https://github.com/Penelopeep/GenshinImporter/releases), and put it in **GrasscutterFolder/Plugins** (make this folder if you don't have)
2. I found a bug, where to report it?
    - Let me know on discord **<a href="https://discord.com/users/276265598508466176">Penelopeep#7963</a>** or make an issue on github
3. Stats aren't matching official server:
    - This plugin is using Snoo's GiveArtifact code, so it might be not 100% accurate, but it's close enough.
4. I have other question:
    - Ask me on discord **<a href="https://discord.com/users/276265598508466176">Penelopeep#7963</a>**
### Version
This plugin is WIP, I have plans to add more features.
Plugin is built for Grasscutter `1.7.2`. I don't know if it will work on other versions, but it should.
