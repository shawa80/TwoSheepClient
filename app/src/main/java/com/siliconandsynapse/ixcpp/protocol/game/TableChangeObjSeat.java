package com.siliconandsynapse.ixcpp.protocol.game;

import java.util.List;

public class TableChangeObjSeat {

    private final int playerId;
    private final List<TableChangeObjStack> stacks;

    public TableChangeObjSeat(int playerId, List<TableChangeObjStack> stacks) {
        this.playerId = playerId;
        this.stacks = stacks;
    }

    public int getPlayerId() {
        return playerId;
    }
    public List<TableChangeObjStack> getStacks() {
        return stacks;
    }

}
