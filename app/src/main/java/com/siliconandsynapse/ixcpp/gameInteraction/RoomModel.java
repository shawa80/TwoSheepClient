package com.siliconandsynapse.ixcpp.gameInteraction;

import com.siliconandsynapse.net.ixtunnel.IxManager;

//TODO rename to gameModel...
public interface RoomModel extends Iterable<GameInfo>
{

	public interface RoomModelListener {

		public void gameAdded(GameInfo game, IxManager tunnel);
		public void gameRemoved(GameInfo game);
		public void playerAdded(GameInfo game, String player);
		public void playerRemoved(GameInfo game, String player);
	}

	public void addGame(GameInfo game, IxManager tunnel);
	public void removeGame(int id);

	public void addPlayerToGame(GameInfo game, String player);
	public void removePlayerFromGame(GameInfo game, String player);


	public void addListener(RoomModelListener listener);
	public void removeListener(RoomModelListener listener);
}
