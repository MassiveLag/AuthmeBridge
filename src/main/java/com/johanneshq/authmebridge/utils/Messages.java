package com.johanneshq.authmebridge.utils;

public enum Messages {

    ATTEMP_SENDING_LOBBY("authmebridge.attemp", "Trying to send you to a random lobby...", "0.1", "authmebridge");

    final String key;
    final String message;
    final String version;
    final String pluginName;

    Messages(String key, String message, String version, String pluginName) {
        this.key = key;
        this.message = message;
        this.version = version;
        this.pluginName = pluginName;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public String getVersion() {
        return version;
    }

    public String getPluginName() {
        return pluginName;
    }

}
