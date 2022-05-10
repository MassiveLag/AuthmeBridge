package com.johanneshq.authmebridge.utils;


import com.johanneshq.authmebridge.hook.Hook;
import com.johanneshq.authmebridge.proxy.BungeeBridge;
import nl.chimpgamer.networkmanager.api.models.player.Player;
import nl.chimpgamer.networkmanager.api.models.servers.Server;
import nl.chimpgamer.networkmanager.api.models.servers.ServerGroup;
import nl.chimpgamer.networkmanager.api.models.servers.balancer.BalanceMethodType;

import java.util.Optional;
import java.util.UUID;

public class ServerUtils {

    public static void movePlayerToGroup(UUID player, String group) {
        Optional<Player> playerOptional = Hook.instance.getPlayer(player);
        if (playerOptional.isEmpty())
            return;

        BalanceMethodType balanceMethodType = BalanceMethodType.RANDOM;

        if (BungeeBridge.get().getBungeeConfig().contains("balanceMethod"))
            balanceMethodType = BalanceMethodType.valueOf(BungeeBridge.get().getBungeeConfig().getString("balanceMethod"));

        Optional<ServerGroup> serverGroup = Hook.instance.getServerGroup(group);
        if (serverGroup.isEmpty()) {
            playerOptional.get().disconnect("No available group found!");
            return;
        }

        Optional<Server> randomServerFromServerGroup = Hook.instance.getServerFromServerGroup(playerOptional.get(), serverGroup.get(), balanceMethodType);
        randomServerFromServerGroup.ifPresent(server -> playerOptional.get().connect(server));
    }

}
