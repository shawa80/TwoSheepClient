package com.siliconandsynapse.ixcpp.gameInteraction;

import com.siliconandsynapse.aclient.lobbyModels.RoomModelListener;

//TODO rename to gameModel...
public interface RoomModel extends Iterable<GameInfo>
{
	public void addGame(GameInfo game);
	public void removeGame(int id);

	public void addPlayerToGame(int gameId, int seat, String player);
	public void removePlayerFromGame(int gameId, int seat);


	public void addListener(RoomModelListener listener);
	public void removeListener(RoomModelListener listener);
}
