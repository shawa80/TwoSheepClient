package com.siliconandsynapse.aclient.lobbyModels;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.observerPool.ObserverPool;

public class DefaultRoomModel implements RoomModel {

	private ObserverPool<RoomModelListener> pool;
	private Hashtable<Integer, GameInfo> games;

	public DefaultRoomModel() {

		pool = new ObserverPool<RoomModelListener>(RoomModelListener.class);
		games = new Hashtable<Integer, GameInfo>();

	}

	public Iterator<GameInfo> iterator()
	{
		return games.values().iterator();
	}

	@Override
	public void addListener(RoomModelListener listener) {
		pool.add(listener);
	}

	@Override
	public void removeListener(RoomModelListener listener) {
		pool.add(listener);
	}

	@Override
	public void addGame(GameInfo game) {

		if (games.containsKey(game.getId()))
			return;

		var g = new Game(game);
		games.put(game.getId(), g);

		pool.getDispatcher().gameAdded(g);
	}

	@Override
	public void addPlayerToGame(int gameId, int seat, String player) {

		if (games.containsKey(gameId)) {

			var g = (Game)games.get(gameId);
			var p = new Player(seat, player);
			g.addPlayer(p);
			pool.getDispatcher().playerAdded(g, p);
		}
	}

	@Override
	public void removeGame(int id) {

		games.remove(id);
		pool.getDispatcher().gameRemoved(id);
	}

	@Override
	public void removePlayerFromGame(int gameId, int seat) {

		if (games.containsKey(gameId)) {

			var g = (Game)games.get(gameId);
			g.removePlayer(seat);
			pool.getDispatcher().playerRemoved(g, seat);
		}
	}

}
