package com.siliconandsynapse.ixcpp.protocol.game;

public class PlayerInfoObj {

    private String name;
    private int id;

    public PlayerInfoObj(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
}
