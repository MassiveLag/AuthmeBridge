package com.johanneshq.authmebridge.spigot.listeners;

import com.johanneshq.authmebridge.AuthMeBridge;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.Messenger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Instant;

public class PluginMessageListener {

    private final Plugin plugin;

    public PluginMessageListener() {
        plugin = Bukkit.getPluginManager().getPlugin("NetworkManager");
        if (plugin == null) {
            AuthMeBridge.get().getLogger().warning("NetworkManager not found!");
            return;
        }

        registerChannel("BungeeCord");
    }

    public void registerChannel(String channel) {
        Messenger messenger = Bukkit.getMessenger();
        messenger.registerOutgoingPluginChannel(plugin, channel);
    }

    public void sendMessage(Player player, String channel, String... write) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteArray);

        try {
            for ( String s : write ) {
                out.writeUTF(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendPluginMessage(plugin, channel, byteArray.toByteArray());
    }

    public void login(Player player) {
        sendMessage(player, "BungeeCord", "loggedIn", Instant.now().toString());
    }

    public void logout(Player player) {
        sendMessage(player, "BungeeCord", "logout", Instant.now().toString());
    }
}
