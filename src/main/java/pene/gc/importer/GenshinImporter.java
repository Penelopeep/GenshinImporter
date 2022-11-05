package pene.gc.importer;

import emu.grasscutter.plugin.Plugin;
import pene.gc.importer.commands.Import;

/**
 * The Grasscutter plugin template.
 * This is the main class for the plugin.
 */
public final class GenshinImporter extends Plugin {
    /* Turn the plugin into a singleton. */
    private static GenshinImporter instance;

    /**
     * Gets the plugin instance.
     * @return A plugin singleton.
     */
    public static GenshinImporter getInstance() {
        return instance;
    }
    
    /**
     * This method is called immediately after the plugin is first loaded into system memory.
     */
    @Override public void onLoad() {
        // Set the plugin instance.
        instance = this;
    }

    /**
     * This method is called before the servers are started, or when the plugin enables.
     */
    @Override public void onEnable() {
        // Register commands.
        this.getHandle().registerCommand(new Import());

        // Log a plugin status message.
        this.getLogger().info("The GenshinImporter plugin has been enabled.");
    }

    /**
     * This method is called when the plugin is disabled.
     */
    @Override public void onDisable() {
        // Log a plugin status message.
        this.getLogger().info("The GenshinImporter plugin has been disabled.");
    }
}
