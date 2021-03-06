package com.johanneshq.authmebridge.spigot;

import com.johanneshq.authmebridge.AuthMeBridge;
import com.johanneshq.authmebridge.config.SpigotConfig;
import com.johanneshq.authmebridge.spigot.listeners.PlayerListener;
import com.johanneshq.authmebridge.spigot.listeners.PluginMessageListener;

public class SpigotBridge {

    private static SpigotBridge spigotBridge;

    private final PluginMessageListener pluginMessageListener;

    private final SpigotConfig spigotConfig;

    public SpigotBridge() {
        spigotBridge = this;
        pluginMessageListener = new PluginMessageListener();

        spigotConfig = new SpigotConfig();

        AuthMeBridge.get().getNetworkManager().registerListener(new PlayerListener());
    }

    public static SpigotBridge get() {
        return spigotBridge;
    }

    public PluginMessageListener getPluginMessageListener() {
        return pluginMessageListener;
    }

    public SpigotConfig getSpigotConfig() {
        return spigotConfig;
    }
}
