package com.siliconandsynapse.ixcpp.protocol.game;

import java.util.List;

public class TableChangeObjStack {

    private String stackType;
    private int id;

    private List<TableChangeObjCard> cards;

    public TableChangeObjStack(int id, String stackType, List<TableChangeObjCard> cards) {
        this.id = id;
        this.stackType  = stackType;
        this.cards = cards;
    }

    public int getId() {
        return id;
    }
    public String getStackType(){
        return stackType;
    }
    public List<TableChangeObjCard> getCards() {
        return cards;
    }
}
