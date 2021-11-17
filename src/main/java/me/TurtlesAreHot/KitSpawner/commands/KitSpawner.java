package me.TurtlesAreHot.KitSpawner.commands;

import me.TurtlesAreHot.KitSpawner.Config;
import me.TurtlesAreHot.KitSpawner.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class KitSpawner implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getLogger().info("Only players can run this command.");
            return false;
        }

        if(label.equalsIgnoreCase("kitspawner")) {
            Player p = (Player) sender;
            if(!(p.hasPermission("kitspawner.use"))) {
                p.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
                return false;
            }
            if(args.length != 1) {
                p.sendMessage(ChatColor.RED + "You must provide 1 spawner type to run this command. Valid spawner types: " + Config.getEntitiesString().toString());
                return false;
            }
            // Check the cooldown to make sure they don't have a cooldown.
            long cooldown = (long) Cooldown.checkCooldown(p)/1000;
            // cooldown is the number of seconds that has passed since the cooldown started
            if(cooldown < Config.getCooldown() && cooldown != -1) {
                // This case is if the player is still on cooldown.
                p.sendMessage(ChatColor.RED + "You are still on cooldown. You can run this command in: " + (Config.getCooldown() - cooldown) + " seconds.");
                return false;
            }
            if(cooldown >= Config.getCooldown()) {
                Cooldown.removeCooldown(p);
            }
            List<EntityType> validEntities = Config.getValidEntities();
            List<String> validTypes = Config.getEntitiesString();
            EntityType chosen = null;
            for(String entity : args) {
                if(!(validTypes.contains(entity.toUpperCase()))) {
                    p.sendMessage(ChatColor.RED + "You have provided an invalid spawner type. Please try again. Valid spawner types: " + validTypes.toString());
                    return false;
                }
                chosen = EntityType.valueOf(entity.toUpperCase());
            }
            ItemStack item = getSpawner(chosen);
            p.getInventory().addItem(item);
            Cooldown.addCooldown(p, System.currentTimeMillis());
            p.sendMessage(ChatColor.RED + "We have given you the spawner that you have requested!");
        }
        return false;
    }

    private ItemStack getSpawner(EntityType entity) {
        ItemStack spawner = new ItemStack(Material.SPAWNER, 1);
        ItemMeta meta = spawner.getItemMeta();
        BlockStateMeta bsm = (BlockStateMeta) meta;
        CreatureSpawner cs = (CreatureSpawner) bsm.getBlockState();
        cs.setSpawnedType(entity);
        bsm.setBlockState(cs);
        meta.setDisplayName(entity.name() + " spawner");
        spawner.setItemMeta(meta);
        return spawner;
    }
}
