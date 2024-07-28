package com.siliconandsynapse.ixcpp.gameInteraction;

import com.siliconandsynapse.aclient.lobbyModels.Game;
import com.siliconandsynapse.ixcpp.protocol.lobby.ListGames;

import java.util.List;

//TODO rename to gameModel...
public interface RoomModel extends Iterable<Game>
{
	public void addGame(GameInfo game, List<ListGames.ListGamesPlayersObj> players);
	public void removeGame(int id);

	public void addPlayerToGame(int gameId, int seat, String player);
	public void removePlayerFromGame(int gameId, int seat);

}
