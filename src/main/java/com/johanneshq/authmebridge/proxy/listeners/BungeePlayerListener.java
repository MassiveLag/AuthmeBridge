package com.johanneshq.authmebridge.proxy.listeners;

import com.johanneshq.authmebridge.proxy.BungeeBridge;
import com.johanneshq.authmebridge.proxy.player.data.PlayerModel;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Optional;

public class BungeePlayerListener implements Listener {

    private final BungeeBridge bungeeBridge = BungeeBridge.get();

    @EventHandler
    public void onConnect(ServerConnectedEvent event) {
        ProxiedPlayer player = event.getPlayer();
        bungeeBridge.getPlayerHandler().add(player.getUniqueId());
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        bungeeBridge.getPlayerHandler().remove(player.getUniqueId());
    }

    @EventHandler
    public void onChat(ChatEvent event) {
        if (event.isCancelled())
            return;

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();


        String[] message = event.getMessage().split(" ");

        Optional<PlayerModel> playerModelOptional = bungeeBridge.getPlayerHandler().getModel(player.getUniqueId());
        if (playerModelOptional.isEmpty()) {
            player.disconnect(new TextComponent("An error occurred!"));
            return;
        }

        PlayerModel playerModel = playerModelOptional.get();

        //Check if player is in the login server
        if (bungeeBridge.isLogin(player.getServer().getInfo().getName())) {

            //Check if it's a command or not.
            if (!event.isCommand()) {
                event.setCancelled(bungeeBridge.getBungeeConfig().getBoolean("login.disableChat"));
            } else
                event.setCancelled(bungeeBridge.getBungeeConfig().getStringList("login.whitelistedCommands").stream().noneMatch(s -> s.startsWith(message[0])));
        } else if (!playerModel.isLoggedIn() && bungeeBridge.getBungeeConfig().getBoolean("auth.kickUnauthenticated"))
                player.disconnect(new TextComponent("You are not authenticated!"));
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if ( !event.getTag().equals("BungeeCord") ) return;

        ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(event.getReceiver().toString());
        if (proxiedPlayer == null)
            return;

        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(event.getData()));

        try {
            String action = dataInputStream.readUTF();
            String time = dataInputStream.readUTF().toLowerCase();

            if (action.equalsIgnoreCase("loggedIn")) {
                bungeeBridge.getPlayerHandler().setLoggedIn(proxiedPlayer.getUniqueId(), true);
            } else if (action.equalsIgnoreCase("logout")) {
                bungeeBridge.getPlayerHandler().setLoggedIn(proxiedPlayer.getUniqueId(), false);
            }

        } catch (IOException e) { /* ignored */}
    }

    @EventHandler
    public void onConnect(ServerConnectEvent event) {
        if (!bungeeBridge.getBungeeConfig().getBoolean("auth.blockServerSwitch"))
            return;

        if (bungeeBridge.isLogin(event.getTarget().getName()))
            return;

        event.setCancelled(true);
        event.getPlayer().sendMessage(new TextComponent("You can not move servers while unauthenticated!"));
    }
}
