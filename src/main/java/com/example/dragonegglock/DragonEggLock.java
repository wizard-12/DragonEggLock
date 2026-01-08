package com.example.dragonegglock;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DragonEggLock extends JavaPlugin implements Listener {

    private boolean enabled = true;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("DragonEggLock enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("DragonEggLock disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("Usage: /dragonegglock <on|off|status>");
            return true;
        }

        if(args[0].equalsIgnoreCase("on")) {
            enabled = true;
            Bukkit.broadcastMessage("§aDragon Egg Lock ENABLED");
            return true;
        }

        if(args[0].equalsIgnoreCase("off")) {
            enabled = false;
            Bukkit.broadcastMessage("§cDragon Egg Lock DISABLED");
            return true;
        }

        if(args[0].equalsIgnoreCase("status")) {
            sender.sendMessage("Dragon Egg Lock is " + (enabled ? "ENABLED" : "DISABLED"));
            return true;
        }

        return false;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(!enabled) return;
        if(event.getBlockPlaced().getType() == Material.DRAGON_EGG) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cYou cannot place Dragon Eggs!");
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!enabled) return;
        ItemStack item = event.getCurrentItem();
        if(item != null && item.getType() == Material.DRAGON_EGG) {
            event.setCancelled(true);
            if(event.getWhoClicked() instanceof Player p) {
                p.sendMessage("§cYou cannot put Dragon Eggs in containers!");
            }
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if(!enabled) return;
        ItemStack item = event.getItem();
        if(item != null && item.getType() == Material.DRAGON_EGG) {
            event.setCancelled(true);
        }
    }
}
