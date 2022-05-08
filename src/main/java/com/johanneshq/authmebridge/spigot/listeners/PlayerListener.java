package com.johanneshq.authmebridge.spigot.listeners;

import com.johanneshq.authmebridge.spigot.SpigotBridge;
import fr.xephi.authme.events.LoginEvent;
import fr.xephi.authme.events.LogoutEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerListener implements Listener {

    private final Map<UUID, AtomicBoolean> players = new HashMap<>();

    @EventHandler
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();
        players.get(player.getUniqueId()).set(true);
        SpigotBridge.get().getPluginMessageListener().login(player);
    }

    @EventHandler
    public void onLogout(LogoutEvent event) {
        Player player = event.getPlayer();
        players.get(player.getUniqueId()).set(false);
        SpigotBridge.get().getPluginMessageListener().logout(player);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        players.put(event.getPlayer().getUniqueId(), new AtomicBoolean(false));

        if (!SpigotBridge.get().getSpigotConfig().getBoolean("server.joinMessage"))
            event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        players.remove(event.getPlayer().getUniqueId());

        if (!SpigotBridge.get().getSpigotConfig().getBoolean("server.quitMessage"))
            event.setQuitMessage(null);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.chat"));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.blockBreak"));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.blockPlace"));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onPlayerInteract"));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onEntityDamage"));
    }

    @EventHandler(ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        e.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onFoodLevelChange"));
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent e) {
        e.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onItemDrop"));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent e) {
        e.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onPlayerPickupItemEvent"));
    }

    @EventHandler(ignoreCancelled = true)
    public void onChangeWeather(WeatherChangeEvent event){
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onChangeWeather"));
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onBlockBurn"));
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onEntityExplode"));
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockExplodeEvent(BlockExplodeEvent event) {
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onBlockExplodeEvent"));
    }

    @EventHandler(ignoreCancelled = true)
    public void onExplosionPrimeEvent(ExplosionPrimeEvent event) {
        event.setCancelled(SpigotBridge.get().getSpigotConfig().getBoolean("protection.onExplosionPrimeEvent"));
    }

}
