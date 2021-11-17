package me.TurtlesAreHot.KitSpawner;

import me.TurtlesAreHot.KitSpawner.commands.KitSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    private static File dataFolder;

    @Override
    public void onEnable() {
        dataFolder = getDataFolder();
        createCooldownFolder();
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();
        config.addDefault("cooldown", 2592000); // 1 month in seconds
        List<String> allowedEntities = new ArrayList<>();
        allowedEntities.add("ZOMBIE");
        allowedEntities.add("CREEPER");
        config.addDefault("entites", allowedEntities);
        config.options().copyDefaults(true);
        this.saveConfig();
        Config.reloadConfig();
        getCommand("kitspawner").setExecutor(new KitSpawner());
    }

    @Override
    public void onDisable() {

    }

    public static File getFolder() { return dataFolder; }

    public void createCooldownFolder() {
        File cooldownFolder = new File(getFolder(),  "/cooldown/");
        if(!(cooldownFolder.exists())) {
            cooldownFolder.mkdirs();
        }
    }
}
