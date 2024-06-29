package com.siliconandsynapse.aclient;

import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.aclient.lobbyModels.DefaultRoomModel;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.protocol.Debug;
import com.siliconandsynapse.ixcpp.protocol.lobby.CreateGameCmd;
import com.siliconandsynapse.ixcpp.ui.MessageReceiverModel;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.ParseError;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MainActivity extends Activity {

	//private TextView debug;

	private NetworkService service;
	private Button createGame;
	private ListView games;
	
	private RoomModel rooms;
	//private LobbyUserList lobbyModel;
	
	public MainActivity() {

	}

	public void log(String log) {
		//this.runOnUiThread(() -> {
		//	debug.append(log + "\n");
		//});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Images.loadCache(this);
		setContentView(R.layout.games);

		createGame = (Button)findViewById(R.id.addGame);
		games = (ListView)findViewById(R.id.gameList);
		var arrayList = new ArrayList<GameInfo>();
		var adapter = new ArrayAdapter<GameInfo>(getApplicationContext(),
				android.R.layout.simple_list_item_1, arrayList);

		games.setAdapter(adapter);



		var rm = new DefaultRoomModel();
		rm.addListener(new RoomModel.RoomModelListener() {
			@Override
			public void gameAdded(GameInfo game, IxManager tunnel) {
				MainActivity.this.runOnUiThread(() -> {
					adapter.add(game);
				});

			}

			@Override
			public void gameRemoved(GameInfo game) {

			}

			@Override
			public void playerAdded(GameInfo game, String player) {

			}

			@Override
			public void playerRemoved(GameInfo game, String player) {

			}
		});

		service = new NetworkService(this, rm);

		games.setOnItemClickListener((parent, view, pos, id)-> {
			new Thread(() -> {
				var gi = (GameInfo)parent.getItemAtPosition(pos);

				service.joinGame(gi);
			}).start();

		});

		createGame.setOnClickListener((view) -> {

			new Thread(() -> {
			//network on main thread exception!!!!!!!!!!!!!!!
			var tun = service.getTunnel();
			var cmd = new CreateGameCmd("Two Sheep");
			try {
				cmd.execute(IxAddress.parse("ixcpp.lobby"), tun);
			} catch (ParseError e) {
				throw new RuntimeException(e);
			}}).start();

		});


//		service.addOnConnectListener(new OnConnectListener() {
//
//			@Override
//			public void connected(NetworkService service) {
//				chatWindow.addMessage("Network", "Connected");
//				chatWindow.setNetworkService(service);
//				gameWindow.setNetworkService(service);
//			}
//
//			@Override
//			public void ModelsCreated(
//					MessageReceiverModel receiver) {
//
//				chatWindow.setReceiverModel(receiver);
//
//			}
//
//
//
//			@Override
//			public void failed(NetworkService service, String message) {
//				chatWindow.addMessage("Network", message);
//
//			}
//
//
//		});


		service.start();
	}


	@Override
	protected void onStart() {
		super.onStart();
		//service.start();
	}

	private Mutex logonBlock = new Mutex();;

}
