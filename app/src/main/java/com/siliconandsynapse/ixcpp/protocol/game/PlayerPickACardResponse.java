package com.siliconandsynapse.ixcpp.protocol.game;

import com.siliconandsynapse.ixcpp.common.cards.Card;

public class PlayerPickACardResponse {
    private int code;

    public PlayerPickACardResponse(Card card) {
        this.code = card.getCode();
    }

    public int getCode() {
        return code;
    }
}
