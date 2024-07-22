package com.siliconandsynapse.aclient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;


//import com.siliconandsynapse.aclient.lobbyModels.Credentials;
//import com.siliconandsynapse.aclient.lobbyModels.DefaultMessageModel;
import com.siliconandsynapse.aclient.lobbyModels.GameModel;
//import com.siliconandsynapse.ixcpp.Cmd;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
//import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
//import com.siliconandsynapse.ixcpp.protocol.HeartBeat;
//import com.siliconandsynapse.ixcpp.protocol.lobby.AccessControl;
//import com.siliconandsynapse.ixcpp.protocol.lobby.CreateGame;
//import com.siliconandsynapse.ixcpp.protocol.lobby.DeleteGame;
//import com.siliconandsynapse.ixcpp.protocol.lobby.JoinGame;
//import com.siliconandsynapse.ixcpp.protocol.lobby.ListGames;
//import com.siliconandsynapse.ixcpp.protocol.lobby.LobbyModel;
//import com.siliconandsynapse.ixcpp.protocol.lobby.MessageDisplay;
//import com.siliconandsynapse.ixcpp.protocol.lobby.UserList;
//import com.siliconandsynapse.ixcpp.protocol.lobby.Welcome;
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
import com.siliconandsynapse.ixcpp.ui.MessageReceiverModel;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

import com.siliconandsynapse.observerPool.ObserverPool;

public class NetworkService implements Runnable {

	private String server;
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
	private RoomModel roomModel;
	private final String clientName;

	public ObserverPool<OnConnectSuccessListener> onConnectSuccess;
	public ObserverPool<OnConnectFailureListener> onConnectFailure;

	public interface OnConnectFailureListener {
		public void failed(NetworkService service, String message);
	}
	public interface OnConnectSuccessListener {
		public void connected(NetworkService service);
	}

	private static NetworkService service;

	public static NetworkService getService() {
		return service;
	}


	public NetworkService(MainActivity act,
						  String server,
						  String clientName
    ) {

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

	}
	public void setRoomModel(RoomModel roomModel) {
		this.roomModel = roomModel;
	}

	public IxAddress getGameAddr() {

		return gamesAddr;
	}

	public void start() {

		t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	public void joinGame(GameInfo gi) {

		gameManager.startGame(getTunnel(), gi.getId(), gi.getName());
	}


	public IxManager getTunnel() {
		return tunnel;
	}

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

		Socket connection = null;
		try {
			connection = tryConnection(server);
		} catch (IOException e) {
			onConnectFailure.getDispatcher().failed(this, e.getMessage());
			return;
		}
		onConnectSuccess.getDispatcher().connected(this);

		try {

			tunnel = new IxManager(connection, bootStrap);
			tunnel.registerAllReciever(new Debug(act));

			var sn = new SetName();
			sn.execute(lobbyAddr, tunnel, clientName);

		} catch (Exception e) {

		}


	}

	private Socket tryConnection(String server) throws IOException {
		Socket connection = null;
		int times = 10;
		while (times > 0) {
			try {
				connection = new Socket(server, 1077);
				return connection;
			} catch (Exception e) {

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

		tunnel.close();
	}

}
