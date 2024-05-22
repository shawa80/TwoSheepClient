package com.siliconandsynapse.ixcpp.protocol.game;

public class TurnChangeObj {

    private final int playerId;
    public TurnChangeObj(int playerId) {
        this.playerId = playerId;
    }
    public int getPlayerId() {
        return playerId;
    }
}
