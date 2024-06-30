package com.siliconandsynapse.aclient.lobbyModels;

import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;

import java.util.HashSet;
import java.util.Hashtable;

public class Game extends GameInfo {

    public Hashtable<Integer, Player> players;

    public Game(GameInfo game) {

        super(game.getId(), game.getName());
        players = new Hashtable<>();

    }

    public void addPlayer(Player player) {
        players.remove(player.seat());

        players.put(player.seat(), player);
    }

    public void removePlayer(int seat) {
        players.remove(seat);
    }

    @Override
    public String toString() {

        var names = players.values().stream().map((p) -> p.name()).toList();

        var n = String.join(",", names);

        return super.toString() + " - " + n;
    }
}
