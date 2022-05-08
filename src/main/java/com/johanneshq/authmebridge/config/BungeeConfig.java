package com.johanneshq.authmebridge.config;

import com.johanneshq.authmebridge.AuthMeBridge;
import nl.chimpgamer.networkmanager.api.utils.FileUtils;

public class BungeeConfig extends FileUtils {

    public BungeeConfig() {
        super(AuthMeBridge.get().getDataFolder().getPath(), "bungee.yml");
        setupFile(AuthMeBridge.get().getResource("bungee.yml"));
    }

    public void load() {

    }

    @Override
    public void reload() {
        super.reload();

        load();
    }

}
