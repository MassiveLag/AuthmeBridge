package com.johanneshq.authmebridge.config;

import com.johanneshq.authmebridge.AuthMeBridge;
import nl.chimpgamer.networkmanager.api.utils.FileUtils;

public class SpigotConfig extends FileUtils {

    public SpigotConfig() {
        super(AuthMeBridge.get().getDataFolder().getPath(), "spigot.yml");
        setupFile(AuthMeBridge.get().getResource("spigot.yml"));
    }

    @Override
    public void reload() {
        super.reload();
    }

}
