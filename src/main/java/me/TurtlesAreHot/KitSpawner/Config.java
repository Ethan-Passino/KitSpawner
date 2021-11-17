package me.TurtlesAreHot.KitSpawner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private static FileConfiguration config;

    public static void reloadConfig() { config = JavaPlugin.getPlugin(Main.class).getConfig(); }

    public static List<String> getEntitiesString() { return config.getStringList("entites"); }

    public static List<EntityType> getValidEntities() {
        List<String> strEntities = getEntitiesString();
        List<EntityType> entities = new ArrayList<>();
        for(String entity : strEntities) {
            if(EntityType.valueOf(entity) == null) {
                Bukkit.getLogger().info(ChatColor.RED + "[Error] " + ChatColor.WHITE + entity + " is not a valid entitytype.");
                continue;
            }
            entities.add(EntityType.valueOf(entity));
        }
        return entities;
    }

    public static int getCooldown() { return config.getInt("cooldown"); }
}
