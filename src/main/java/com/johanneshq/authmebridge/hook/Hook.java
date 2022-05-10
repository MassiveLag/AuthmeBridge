package com.johanneshq.authmebridge.hook;

import com.johanneshq.authmebridge.AuthMeBridge;
import com.johanneshq.authmebridge.utils.Messages;
import nl.chimpgamer.networkmanager.api.NetworkManagerPlugin;
import nl.chimpgamer.networkmanager.api.cache.CacheManager;
import nl.chimpgamer.networkmanager.api.exceptions.LanguageMessageNotFoundException;
import nl.chimpgamer.networkmanager.api.models.player.Player;
import nl.chimpgamer.networkmanager.api.models.servers.Server;
import nl.chimpgamer.networkmanager.api.models.servers.ServerGroup;
import nl.chimpgamer.networkmanager.api.models.servers.balancer.BalanceMethodType;

import java.util.Optional;
import java.util.UUID;

public class Hook {
    public static Hook instance;

    private final NetworkManagerPlugin networkManager;

    public Hook() {
        networkManager = AuthMeBridge.get().getNetworkManager();

        for (Messages value : Messages.values()) {
            insertLanguageMessage(value.getKey(), value.getMessage(), value.getVersion(), value.getPluginName());
        }

        networkManager.getCacheManager().getCachedLanguages().reload();
        instance = this;
    }

    /**
     * Get the NetworkManager CacheManager.
     * @return The NetworkManager CacheManager.
     */
    public CacheManager getCacheManager() {
        return networkManager.getCacheManager();
    }

    /**
     * Get NetworkManager Player.
     * @param uuid UUID of the player.
     * @return an optional {@link Player} object
     */
    public Optional<Player> getPlayer(UUID uuid) {
        return getCacheManager().getCachedPlayers().getPlayerSafe(uuid);
    }

    public java.util.Map<UUID, Player> getAllCachedPlayers() {
        return getCacheManager().getCachedPlayers().getPlayers();
    }

    /**
     * Get an NetworkManager Cached player.
     * @param username The username of the player.
     * @return an optional {@link Player} object */
    public Optional<Player> getPlayer(String username) {
        return getCacheManager().getCachedPlayers().getPlayerSafe(username);
    }

    /**
     * @param player networkmanager player.
     * @param serverGroup the serverGroup you want to get a server from.
     * @param balanceMethodType the methode
     */
    public  Optional<Server> getServerFromServerGroup(Player player, ServerGroup serverGroup, BalanceMethodType balanceMethodType) {
        return Optional.ofNullable(networkManager.getCacheManager().getCachedServers().getServerFromGroup(player, serverGroup, balanceMethodType));
    }

    /**
     *
     * @param serverName the server name registed in NetworkManager
     * @return return the optional server.
     */
    public Optional<Server> getServer(String serverName) {
        return Optional.ofNullable(networkManager.getCacheManager().getCachedServers().getServer(serverName));
    }

    /**
     *
     * @param serverId the server id registed in networkManager
     * @return return the optional server.
     */
    public Optional<Server> getServer(int serverId) {
        return Optional.ofNullable(networkManager.getCacheManager().getCachedServers().getServer(serverId));
    }

    /**
     *
     * @param groupName get a group from the servers
     * @return return the group.
     */
    public Optional<ServerGroup> getServerGroup(String groupName) {
        return Optional.ofNullable(networkManager.getCacheManager().getCachedServers().getServerGroup(groupName));
    }

    public String getNMMessage(int languageId, nl.chimpgamer.networkmanager.api.values.Message message) {
        try {
            return networkManager.getCacheManager().getCachedLanguages().getMessage(1, message.getKey());
        } catch ( LanguageMessageNotFoundException ex) {
            return ex.getLocalizedMessage();
        }
    }

    public String getMessage(int languageId, Messages message) {
        try {
            return networkManager.getCacheManager().getCachedLanguages().getMessage(message.getPluginName(), languageId, message.getKey());
        } catch ( LanguageMessageNotFoundException ex) {
            return ex.getLocalizedMessage();
        }
    }

    /**
     *
     * @param key message key
     * @param message the message
     * @param version the plugin version
     * @param pluginName the plugin name
     */
    public void insertLanguageMessage(String key, String message, String version, String pluginName) {
        networkManager.getStorage().getDao().getLanguagesDao().insertLanguageMessage(key, message, version, pluginName);
    }

}
