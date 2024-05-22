package com.siliconandsynapse.ixcpp.protocol.game;

import com.siliconandsynapse.ixcpp.common.cards.Card;

public class TableChangeObjCard {

    private Card card;
    private int level;

    public TableChangeObjCard(int level, Card card) {

        this.card = card;
        this.level = level;
    }

    public Card getCard() {return card;}
    public int getLevel() {return level;}
}
