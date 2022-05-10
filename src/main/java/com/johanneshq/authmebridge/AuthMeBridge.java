package com.johanneshq.authmebridge;

import com.johanneshq.authmebridge.proxy.BungeeBridge;
import com.johanneshq.authmebridge.spigot.SpigotBridge;
import com.johanneshq.authmebridge.hook.Hook;
import nl.chimpgamer.networkmanager.api.extensions.NMExtension;
import nl.chimpgamer.networkmanager.api.utils.PlatformType;
import org.bukkit.Bukkit;

public class AuthMeBridge extends NMExtension {

    private static AuthMeBridge authMeBridge;

    @Override
    protected void onConfigsReload() {
        if (getNetworkManager().getPlatformType().isProxy())
            BungeeBridge.get().getBungeeConfig().reload();
        else SpigotBridge.get().getSpigotConfig().reload();
    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected void onEnable() {
        authMeBridge = this;

        new Hook();

        if (networkManagerPlugin.getPlatformType().isProxy()) {
            new BungeeBridge();
        } else if (networkManagerPlugin.getPlatformType() == PlatformType.BUKKIT) {
            if (Bukkit.getPluginManager().getPlugin("AuthMe") == null) {
                AuthMeBridge.get().getLogger().severe("AuthMe is not installed! Shutting down for security reasons!");
                Bukkit.shutdown();
                return;
            }

            new SpigotBridge();
        }


    }

    public static AuthMeBridge get() {
        return authMeBridge;
    }
}
