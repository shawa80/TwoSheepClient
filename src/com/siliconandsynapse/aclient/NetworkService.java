package com.siliconandsynapse.aclient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;


import com.siliconandsynapse.aclient.lobbyModels.Credentials;
import com.siliconandsynapse.aclient.lobbyModels.DefaultMessageModel;
import com.siliconandsynapse.aclient.lobbyModels.GameModel;
import com.siliconandsynapse.ixcpp.Cmd;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.ixcpp.protocol.HeartBeat;
import com.siliconandsynapse.ixcpp.protocol.lobby.AccessControl;
import com.siliconandsynapse.ixcpp.protocol.lobby.CreateGame;
import com.siliconandsynapse.ixcpp.protocol.lobby.DeleteGame;
import com.siliconandsynapse.ixcpp.protocol.lobby.JoinGame;
import com.siliconandsynapse.ixcpp.protocol.lobby.ListGames;
import com.siliconandsynapse.ixcpp.protocol.lobby.LobbyModel;
import com.siliconandsynapse.ixcpp.protocol.lobby.MessageDisplay;
import com.siliconandsynapse.ixcpp.protocol.lobby.UserList;
import com.siliconandsynapse.ixcpp.protocol.lobby.Welcome;
import com.siliconandsynapse.ixcpp.ui.MessageReceiverModel;
import com.siliconandsynapse.ixcpp.userInteraction.PasswordPrompt;
import com.siliconandsynapse.ixcpp.userInteraction.UserPassword;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.ParseError;
import com.siliconandsynapse.net.ixtunnel.XMLException;
import com.siliconandsynapse.observerPool.ObserverPool;

public class NetworkService implements Runnable {
	
	private Thread t;
	
	private IxManager tunnel;
	private MessageDisplay messageDisplay;
	private CreateGame createGame; 
	private DeleteGame deleteGame;
	private JoinGame joinGame;
	private GameController gameManager;
	private ListGames listGames;
	private AccessControl accessControl;
	//private UserAdd userAdd;
	//private UserDel userDel;
	private UserList userList;
	//private PlayerJoinedGame playerJoinedGame;
	//private PlayerLeftGame playerLeftGame;
	private HeartBeat beat;
	//private ListDealers listDealer;
	private Welcome welcome;
	//private DealerJavaHint dealerJavaHint;
	
	private IxAddress rootAddr;
	private IxAddress gamesAddr;
	private IxAddress lobbyAddr;
	//private IxAddress extnAddr;
	
	private MainActivity act;
	
	//game objects
	private LobbyModel lobbyModel;
	private DefaultMessageModel lobbyMessageReceiver;
	private RoomModel roomModel;
	//private MessageSenderModel lobbyMessageSend;

	
	private ObserverPool<OnConnectListener> onConnect;
	
	public interface OnConnectListener {
		
		public void connected(NetworkService service);
		public void ModelsCreated(MessageReceiverModel message);
		public void failed(NetworkService service, String message);
	}
	
	public void addOnConnectListener(OnConnectListener listener) {
		onConnect.add(listener);
	}
	public void removeOnConnectListener(OnConnectListener listener) {
		onConnect.remove(listener);
	}
	
	
	private static NetworkService service;
	
	public static NetworkService getService() {
		return service;
	}
	

	public NetworkService(MainActivity act, 
			LobbyModel lobbyModel,
			RoomModel roomModel) {
				
		
		service = this;
		
		this.act = act;
		onConnect = new ObserverPool<OnConnectListener>(OnConnectListener.class);

		this.lobbyModel = lobbyModel;
		this.roomModel = roomModel;
		gameManager = new GameModel(act);
		
		try {
			rootAddr = IxAddress.createRoot("ixcpp");
			lobbyAddr = rootAddr.append("lobby");
			gamesAddr = rootAddr.append("games");
			//extnAddr = rootAddr.append("extn");
		} catch (ParseError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public IxAddress getGameAddr() {
		
		return gamesAddr;
	}
	
	public void start() {
		
		t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}
	

	public IxManager getTunnel() {
		return tunnel;
	}
	
	public void run() {
			
		Vector<IxReciever> bootStrap = new Vector<IxReciever>();

		//dealerJavaHint = new DealerJavaHint(extnAddr);

		//gameList = new GameList(dealerJavaHint);
		//gameManager = new GameManager(gamesAddr, messageAdder, gameList);

		accessControl = new AccessControl(lobbyAddr, new PasswordPrompt() {

			@Override
			public UserPassword prompt() {
				
				
				Credentials creds = act.getUserPass();
				
				return new UserPassword(creds.getUser(), creds.getPass());
			}
		});
		
		this.lobbyMessageReceiver = new DefaultMessageModel();
		
		messageDisplay = new MessageDisplay(lobbyAddr, lobbyMessageReceiver);		
		
		createGame = new CreateGame(lobbyAddr, roomModel);
		deleteGame = new DeleteGame(lobbyAddr, roomModel);
		joinGame = new JoinGame(lobbyAddr, gameManager);
		listGames = new ListGames(lobbyAddr, roomModel);
		//userAdd = new UserAdd(lobbyAddr, lobbyModel);
		//userDel = new UserDel(lobbyAddr, lobbyModel);
		userList = new UserList(lobbyAddr, lobbyModel);
		//playerJoinedGame = new PlayerJoinedGame(lobbyAddr, roomModel);
		//playerLeftGame = new PlayerLeftGame(lobbyAddr, roomModel);
		//listDealer = new ListDealers(lobbyAddr, gameList);
		welcome = new Welcome(lobbyAddr, new Welcome.ProtocalMonitor() {
			@Override
			public void welcome() {
				//TODO request Dealer list
				
			}
		});

		beat = new HeartBeat();

		bootStrap.add(accessControl);

		bootStrap.add(messageDisplay);
		bootStrap.add(createGame);
		bootStrap.add(deleteGame);
		bootStrap.add(joinGame);
		bootStrap.add(listGames);
		//bootStrap.add(userAdd);
		//bootStrap.add(userDel);
		bootStrap.add(userList);
		//bootStrap.add(playerJoinedGame);
		//bootStrap.add(playerLeftGame);
		bootStrap.add(beat);
		//bootStrap.add(listDealer);
		bootStrap.add(welcome);
		//bootStrap.add(dealerJavaHint);

		//////////////////////////////////////////////////////////////////////
		///	create our tunnels
		//////////////////////////////////////////////////////////////////////		
		
		
		Socket connection = null;
        try {
			connection = new Socket("pi.siliconandsynapse.com", 1077);

        } catch (UnknownHostException e) {
        	e.printStackTrace();
        	onConnect.getDispatcher().failed(this, e.getMessage());
        	return;
		} catch (IOException e) {
			e.printStackTrace();
			onConnect.getDispatcher().failed(this, e.getMessage());
			return;
		}
		
       
        
		try {
			
			tunnel = new IxManager(connection, bootStrap);
			tunnel.registerAllReciever(new DebugPrinter());
			
		} catch (XMLException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		onConnect.getDispatcher().connected(this);
		
		onConnect.getDispatcher().ModelsCreated(lobbyMessageReceiver);
	}
	
	
	public void executeLobby(Cmd cmd) {
		
		cmd.execute(lobbyAddr, tunnel);
		
	}
	
	public void stop() {
		
		tunnel.close();
	}
	
}
