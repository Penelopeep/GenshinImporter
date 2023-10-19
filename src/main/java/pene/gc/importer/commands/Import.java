package pene.gc.importer.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
import pene.gc.importer.GenshinImporter;
import pene.gc.importer.utils.Datareader;

import java.util.List;

/**
 * Commands are comprised of 3 things:
 * 1. The {@link Command} annotation.
 * 2. Implementing the {@link CommandHandler} interface.
 * 3. Implementing the {@link CommandHandler#execute(Player, Player, List)} method.
 *
 * The {@link Command} annotation should contain:
 * 1. A command label. ('example' in this case makes '/example' runnable in-game or on the console)
 * 2. A description of the command. (this is shown in the `/help` command description)
 * 3. A permission node. (this is used to check if the player has permission to use the command)
 * Other optional fields can be found in the {@link Command} annotation interface.
 */

@Command(label = "import", usage = "import <filename>")
public final class Import implements CommandHandler {
    String filename;
    @Override public void execute(Player sender, Player targetPlayer, List<String> args) {
        //Since plugin updates config on load, we need to get it again
        GenshinImporter.getInstance().reloadConfig();

        if (args.size()<1){
            CommandHandler.sendMessage(sender,"Usage: /import <filename>");
            return;
        } else if (args.size()>1) {
            if (sender==null){
                Grasscutter.getLogger().info("Wrong amount of arguments");
            } else {
                CommandHandler.sendMessage(targetPlayer,"Wrong amount of arguments");
            }
            return;
        } else {
            filename = args.get(0);
            if (sender==null){
                Grasscutter.getLogger().info("Importing "+filename);
            } else {
                CommandHandler.sendMessage(targetPlayer,"Importing "+filename);
            }
        }
        //Get character first, before artifacts because if equip is set to true, it will equip artifacts
        if (GenshinImporter.getPluginConfig().Characters){
            Datareader.characters(targetPlayer,filename);
        } else {
            if (sender==null){
                Grasscutter.getLogger().info("Characters import disabled");
            } else {
                CommandHandler.sendMessage(targetPlayer,"Characters import disabled");
            }
        }

        if(GenshinImporter.getPluginConfig().Artifacts){
            Datareader.artifacts(targetPlayer,filename);
        } else {
            if (sender==null){
                Grasscutter.getLogger().info("Artifacts import disabled");
            } else {
                CommandHandler.sendMessage(targetPlayer,"Artifacts import disabled");
            }
        }

        if (GenshinImporter.getPluginConfig().Weapons) {
            Datareader.weapons(targetPlayer, filename);
        } else {
            if (sender == null) {
                Grasscutter.getLogger().info("Weapons import disabled");
            } else {
                CommandHandler.sendMessage(targetPlayer, "Weapons import disabled");
            }
        }
        //Maybe someday I'll add more stuff
        //if (GenshinImporter.getInstance().getConfiguration().Materials){
        //    Datareader.materials(targetPlayer,filename);
        //}
    }
}
