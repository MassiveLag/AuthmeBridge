package com.johanneshq.authmebridge.proxy;

import com.johanneshq.authmebridge.AuthMeBridge;
import com.johanneshq.authmebridge.config.BungeeConfig;
import com.johanneshq.authmebridge.proxy.listeners.BungeePlayerListener;
import com.johanneshq.authmebridge.proxy.player.PlayerHandler;
import com.johanneshq.authmebridge.hook.Hook;
import nl.chimpgamer.networkmanager.api.models.servers.Server;
import nl.chimpgamer.networkmanager.api.models.servers.ServerGroup;

import java.util.Optional;

public class BungeeBridge {

    private static BungeeBridge bungeeBridge;

    private final PlayerHandler playerHandler;
    private final BungeeConfig bungeeConfig;

    public BungeeBridge() {
        bungeeBridge = this;

        bungeeConfig = new BungeeConfig();
        bungeeConfig.load();

        playerHandler = new PlayerHandler();

        AuthMeBridge.get().getNetworkManager().registerListener(new BungeePlayerListener());
    }

    public BungeeConfig getBungeeConfig() {
        return bungeeConfig;
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public static BungeeBridge get() {
        return bungeeBridge;
    }

    public boolean isLogin(String serverName) {
        Optional<Server> server = Hook.instance.getServer(serverName);

        if (server.isEmpty())
            return false;

        Optional<ServerGroup> authentication = server.get().getServerGroups().stream().filter(serverGroup -> serverGroup.getGroupName().equalsIgnoreCase(bungeeConfig.getString("login.loginGroup"))).findAny();
        return authentication.isPresent();
    }

    public boolean isGroup(String serverName, String serverGroupName) {
        Optional<Server> server = Hook.instance.getServer(serverName);

        if (server.isEmpty())
            return false;

        Optional<ServerGroup> serverGroupOptional = server.get().getServerGroups().stream().filter(serverGroup -> serverGroup.getGroupName().equalsIgnoreCase(serverGroupName)).findAny();

        return serverGroupOptional.isPresent();
    }
}
