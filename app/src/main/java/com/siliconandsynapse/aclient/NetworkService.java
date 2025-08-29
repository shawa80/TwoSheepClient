package com.siliconandsynapse.aclient;

import java.io.IOException;
import java.net.Socket;
import java.util.Vector;
import com.siliconandsynapse.aclient.lobbyModels.DefaultRoomModel;
import com.siliconandsynapse.aclient.lobbyModels.GameModel;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.ixcpp.protocol.Debug;
import com.siliconandsynapse.ixcpp.protocol.HeartBeat;
import com.siliconandsynapse.ixcpp.protocol.lobby.CreateGame;
import com.siliconandsynapse.ixcpp.protocol.lobby.DeleteGame;
import com.siliconandsynapse.ixcpp.protocol.lobby.JoinGame;
import com.siliconandsynapse.ixcpp.protocol.lobby.ListGames;
import com.siliconandsynapse.ixcpp.protocol.lobby.PlayerJoinedGame;
import com.siliconandsynapse.ixcpp.protocol.lobby.PlayerLeftGame;
import com.siliconandsynapse.ixcpp.protocol.lobby.SetName;
import com.siliconandsynapse.ixcpp.protocol.lobby.Welcome;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

import com.siliconandsynapse.observerPool.ObserverPool;

public class NetworkService {

	private final String server;
	private Thread t;

	private IxManager tunnel;
	private CreateGame createGame;
	private DeleteGame deleteGame;
	private JoinGame joinGame;
	private GameController gameManager;
	private ListGames listGames;
	private PlayerJoinedGame playerJoinedGame;
	private PlayerLeftGame playerLeftGame;
	private HeartBeat beat;
	private Welcome welcome;

	private final IxAddress rootAddr;
	private final IxAddress gamesAddr;
	private final IxAddress lobbyAddr;
	private final MainActivity act;
	private final DefaultRoomModel roomModel;
	private final String clientName;

	public ObserverPool<OnConnectSuccessListener> onConnectSuccess;
	public ObserverPool<OnConnectFailureListener> onConnectFailure;

	public interface OnConnectFailureListener {
		void failed(NetworkService service, String message);
	}
	public interface OnConnectSuccessListener {
		void connected(NetworkService service);
	}

	private static NetworkService service;
	private boolean isRunning;

	public static NetworkService getService() {
		return service;
	}


	public NetworkService(MainActivity act,
						  String server,
						  String clientName
    ) {

		roomModel = new DefaultRoomModel();
		this.server = server;
		service = this;
		this.clientName = clientName;

		this.act = act;
		onConnectSuccess = new ObserverPool<>(OnConnectSuccessListener.class);
		onConnectFailure = new ObserverPool<>(OnConnectFailureListener.class);

		gameManager = new GameModel(act);

        rootAddr = IxAddress.createRoot("ixcpp");
        lobbyAddr = rootAddr.append("lobby");
        gamesAddr = rootAddr.append("games");
		isRunning = false;

	}

	public IxAddress getGameAddr() {

		return gamesAddr;
	}

	private void start() {

		isRunning = true;
		t = new Thread(this::run);
		t.setDaemon(true);
		t.start();
	}

	public DefaultRoomModel getRoomModel()
	{
		return roomModel;
	}

	public void connect() {

		if (!isRunning)
			start();
	}

	public boolean isConnected() {
		return isRunning;
	}


	public IxManager getTunnel() {
		return tunnel;
	}
	private Socket connection = null;

	public void run() {

		var bootStrap = new Vector<IxReceiver>();

		gameManager = new GameModel(act);

		createGame = new CreateGame(lobbyAddr, roomModel, gameManager);
		deleteGame = new DeleteGame(lobbyAddr, roomModel);
		joinGame = new JoinGame(lobbyAddr, gameManager);
		listGames = new ListGames(lobbyAddr, roomModel, gameManager);
		playerJoinedGame = new PlayerJoinedGame(lobbyAddr, roomModel);
		playerLeftGame = new PlayerLeftGame(lobbyAddr, roomModel);
		welcome = new Welcome(lobbyAddr, act);

		beat = new HeartBeat();

		bootStrap.add(createGame);
		bootStrap.add(deleteGame);
		bootStrap.add(joinGame);
		bootStrap.add(listGames);
		bootStrap.add(playerJoinedGame);
		bootStrap.add(playerLeftGame);
		bootStrap.add(beat);
		bootStrap.add(welcome);

		try {
			connection = tryConnection(server);

		} catch (IOException e) {
			onConnectFailure.getDispatcher().failed(this, e.getMessage());
			return;
		}
		onConnectSuccess.getDispatcher().connected(this);

		try {

			tunnel = new IxManager(connection, bootStrap);
			tunnel.connectionClosedListner.add((t) -> {
				onConnectFailure.getDispatcher()
						.failed(this, "Disconnected");
				stop();
			});
			tunnel.registerAllReciever(new Debug(act));

			var sn = new SetName();
			sn.execute(lobbyAddr, tunnel, clientName);

		} catch (Exception ignored) {

		}


	}

	private Socket tryConnection(String server) throws IOException {
		Socket connection;
		int times = 3;
		while (times > 0) {
			try {
				connection = new Socket(server, 1077);
				return connection;
			} catch (Exception ignored) {

			}
			times--;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new IOException("Connection interrupted");
			}
		}
		throw new IOException("unable to connect to " + server);
	}


	public void stop() {

		onConnectSuccess.clear();
		onConnectFailure.clear();
		isRunning = false;
		try {
			connection.close();
		} catch (Exception ignored) {}
		try {
			tunnel.close();
		} catch (Exception ignored){}
	}

}
