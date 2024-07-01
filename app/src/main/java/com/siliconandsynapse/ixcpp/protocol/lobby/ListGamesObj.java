package com.siliconandsynapse.ixcpp.protocol.lobby;

import java.util.List;

public record ListGamesObj(int gameId, String type,
                           int freeSeats, List<ListGamesPlayersObj> players) {}
