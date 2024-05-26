package com.siliconandsynapse.ixcpp.protocol.game;

import com.siliconandsynapse.ixcpp.common.cards.Card;


public record PlayerPickACardResponse(int code){
    public PlayerPickACardResponse(Card card) {
        this(card.getCode());
    }
}