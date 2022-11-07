package pene.gc.importer;

import com.google.gson.Gson;
import emu.grasscutter.plugin.Plugin;
import pene.gc.importer.commands.Import;
import pene.gc.importer.objects.PluginConfig;

import java.io.*;
import java.util.stream.Collectors;

/**
 * The Grasscutter plugin template.
 * This is the main class for the plugin.
 */
public final class GenshinImporter extends Plugin {
    /* Turn the plugin into a singleton. */
    private static GenshinImporter instance;
    private static pene.gc.importer.objects.PluginConfig configuration;
    public static GenshinImporter getInstance() {
        return instance;
    }
    private static File configFile;

    public void reloadConfig(){
        try {
            File config = new File(this.getDataFolder(), "Settings.json");
            FileReader reader = new FileReader(config);
            configuration = new Gson().fromJson(reader, PluginConfig.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called immediately after the plugin is first loaded into system memory.
     */
    @Override public void onLoad() {
        // Set the plugin instance.
        instance = this;

        // Get the configuration file.
        configFile = new File(this.getDataFolder(), "Settings.json");

        // Get the extra data folder.
        File dataFolder = new File(this.getDataFolder(), "Data");

        // Create the extra data folder if it doesn't exist.
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        // Load the configuration.
        try {
            if(!configFile.exists()) {
                try (FileWriter writer = new FileWriter(configFile)) {
                    InputStream configStream = this.getResource("config.json");
                    if(configStream == null) {
                        this.getLogger().error("Failed to save default config file.");
                    } else {
                        writer.write(new BufferedReader(
                                new InputStreamReader(configStream)).lines().collect(Collectors.joining("\n"))
                        ); writer.close();

                        this.getLogger().info("Saved default config file.");
                    }
                }
            }


        } catch (IOException exception) {
            this.getLogger().error("Failed to create config file.", exception);
        }
    // Log a plugin status message.
        this.getLogger().info("The GenshinImporter plugin has been loaded.");
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
    // Github copilot automatically uses this even though it didn't exist, so I made it
    public static PluginConfig getPluginConfig() {
        try {
            configuration = new Gson().fromJson(new FileReader(configFile), pene.gc.importer.objects.PluginConfig.class);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return configuration;
    }
}

