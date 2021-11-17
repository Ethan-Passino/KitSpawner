package me.TurtlesAreHot.KitSpawner;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Cooldown {

    public static long checkCooldown(Player p) {
        // Checks if the player has a cooldown if they do it returns the number of seconds left.
        // If it doesn't it returns -1
        File cooldownFile = new File(Main.getFolder(), "/cooldown/" + p.getUniqueId().toString() + ".yml");
        if(!(cooldownFile.exists())) {
            return -1000;
        }
        FileConfiguration cdConfig = new YamlConfiguration();
        try {
            cdConfig.load(cooldownFile);
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return -1000;
        }
        // Returns the number of milliseconds passed since the cooldown started
        return System.currentTimeMillis() - cdConfig.getLong("cooldown");

    }

    public static void addCooldown(Player p, Long cooldown) {
        File cooldownFile = new File(Main.getFolder(), "/cooldown/" + p.getUniqueId().toString() + ".yml");
        if(cooldownFile.exists()) {
            Bukkit.getLogger().info("Some how the plugin tried creating a new cooldown file for a player that already has one.");
            return;
        }
        try {
            cooldownFile.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
        FileConfiguration fc = new YamlConfiguration();
        try {
            fc.load(cooldownFile);
        } catch(IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            return;
        }
        fc.addDefault("cooldown", cooldown);
        fc.options().copyDefaults(true);
        try {
            fc.save(cooldownFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void removeCooldown(Player p) {
        File cooldownFile = new File(Main.getFolder(), "/cooldown/" + p.getUniqueId().toString() + ".yml");
        if(!(cooldownFile.exists())) {
            return;
        }
        cooldownFile.delete();
    }



}
