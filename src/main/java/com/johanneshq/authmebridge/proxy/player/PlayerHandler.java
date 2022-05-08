package com.johanneshq.authmebridge.proxy.player;

import com.johanneshq.authmebridge.AuthMeBridge;
import com.johanneshq.authmebridge.proxy.BungeeBridge;
import com.johanneshq.authmebridge.proxy.player.data.PlayerModel;
import com.johanneshq.authmebridge.utils.ServerUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerHandler implements Runnable{

    private final PlayerHandler playerHandler;

    public PlayerHandler() {
        playerHandler = this;
        AuthMeBridge.get().getNetworkManager().getScheduler().runRepeating(this, 5, TimeUnit.SECONDS);
    }

    private final Map<UUID, PlayerModel> playerModelMap = new HashMap<>();

    public void add(UUID player) {
        playerModelMap.putIfAbsent(player, new PlayerModel(player, false));
    }

    public void remove(UUID player) {
        playerModelMap.remove(player);
    }

    public Optional<PlayerModel> getModel(UUID player) {
        return Optional.ofNullable(playerModelMap.get(player));
    }

    public void setLoggedIn(UUID player, boolean loggedIn) {
        getModel(player).ifPresent(playerModel -> playerModel.setLoggedIn(loggedIn));
    }

    public PlayerHandler get() {
        return playerHandler;
    }

    @Override
    public void run() {
        playerModelMap.forEach((uuid, playerModel) -> {
            if (!playerModel.isLoggedIn())
                return;

            ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
            if (player == null)
                return;

            if (BungeeBridge.get().isLogin(player.getServer().getInfo().getName()))
                return;

            ServerUtils.movePlayerToGroup(uuid, BungeeBridge.get().getBungeeConfig().getString("login.lobbyGroup"));
        });
    }
}
