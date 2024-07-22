package com.siliconandsynapse.aclient.lobbyModels;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.protocol.lobby.ListGamesPlayersObj;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.observerPool.ObserverPool;

public class DefaultRoomModel implements RoomModel {

	public ObserverPool<PlayerAddedListener> playerAdded;
	public ObserverPool<PlayerRemovedListener> playerRemoved;
	public ObserverPool<GameAddedListener> gameAdded;
	public ObserverPool<GameRemovedListener> gameRemoved;

	private Hashtable<Integer, Game> games;

	public DefaultRoomModel() {

		playerAdded = new ObserverPool<>(PlayerAddedListener.class);
		playerRemoved = new ObserverPool<>(PlayerRemovedListener.class);
		gameAdded = new ObserverPool<>(GameAddedListener.class);
		gameRemoved = new ObserverPool<>(GameRemovedListener.class);
		games = new Hashtable<Integer, Game>();

	}

	public Iterator<Game> iterator()
	{
		return games.values().iterator();
	}


	@Override
	public void addGame(GameInfo game, List<ListGamesPlayersObj> players) {

		if (games.containsKey(game.getId()))
			return;

		var g = new Game(game);
		if (players != null)
			for (var p : players) {
				g.addPlayer(new Player(p.seat(), p.name()));
			}
		games.put(game.getId(), g);

		gameAdded.getDispatcher().gameAdded(g);
	}

	@Override
	public void addPlayerToGame(int gameId, int seat, String player) {

		if (games.containsKey(gameId)) {

			var g = (Game)games.get(gameId);
			var p = new Player(seat, player);
			g.addPlayer(p);
			playerAdded.getDispatcher().playerAdded(g, p);
		}
	}

	@Override
	public void removeGame(int id) {

		games.remove(id);
		gameRemoved.getDispatcher().gameRemoved(id);
	}

	@Override
	public void removePlayerFromGame(int gameId, int seat) {

		if (games.containsKey(gameId)) {

			var g = (Game)games.get(gameId);
			g.removePlayer(seat);
			playerRemoved.getDispatcher().playerRemoved(g, seat);
		}
	}

}
