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
	public void addGame(GameInfo game, IxManager tunnel) {

		games.put(game.getId(), new Game(game, tunnel));

		pool.getDispatcher().gameAdded(game,  tunnel);
	}

	@Override
	public void addPlayerToGame(GameInfo game, String player) {

		if (games.containsKey(game.getId())) {

			Game g = (Game)games.get(game.getId());
			g.players.add(new Player(player));
		}
	}

	@Override
	public void removeGame(int id) {

		games.remove(id);
		pool.getDispatcher().gameRemoved(new GameInfo(id, ""));
	}

	@Override
	public void removePlayerFromGame(GameInfo game, String player) {


	}


	public class Game extends GameInfo {

		//private IxManager tunnel;

		public HashSet<Player> players;


		public Game(GameInfo game, IxManager tunnel) {

			super(game.getId(), game.getName());

			//this.tunnel = tunnel;

			players = new HashSet<Player>();

		}

		public Game(int id, String type, IxManager tunnel) {

			super(id, type);

			//this.tunnel = tunnel;

			players = new HashSet<Player>();

		}
	}

	private class Player {

		//private String name;

		public Player(String name) {

			//this.name = name;

		}

	}

}
