package com.johanneshq.authmebridge.proxy.player.data;

import java.time.Instant;
import java.util.UUID;

public class PlayerModel {

    private final UUID player;
    private boolean loggedIn;
    private Instant loggedInAt;


    public PlayerModel(UUID player, boolean loggedIn) {
        this.player = player;
        this.loggedIn = loggedIn;
    }

    public UUID getPlayer() {
        return player;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public Instant getLoggedInAt() {
        return loggedInAt;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void setLoggedInAt(Instant loggedInAt) {
        this.loggedInAt = loggedInAt;
    }

}
