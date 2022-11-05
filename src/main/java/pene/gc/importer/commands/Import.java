package pene.gc.importer.commands;

import emu.grasscutter.Grasscutter;
import emu.grasscutter.command.Command;
import emu.grasscutter.command.CommandHandler;
import emu.grasscutter.game.player.Player;
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

    @Override public void execute(Player sender, Player targetPlayer, List<String> args) {
        if (args.size()<1){
            sender.sendMessage(sender,"Usage: /import <filename>");
        } else if (args.size()>1) {
            if (sender==null){
                Grasscutter.getLogger().info("Wrong amount of arguments");
            } else {
                CommandHandler.sendMessage(targetPlayer,"Wrong amount of arguments");
            }
        } else {
            String filename = args.get(0);
            if (sender==null){
                Grasscutter.getLogger().info("Importing "+filename);
            } else {
                CommandHandler.sendMessage(targetPlayer,"Importing "+filename);
            }
           Datareader.artifacts(targetPlayer,filename);
        }
    }
}
