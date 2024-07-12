package com.siliconandsynapse.aclient;

import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.aclient.lobbyModels.DefaultRoomModel;
import com.siliconandsynapse.aclient.lobbyModels.Game;
import com.siliconandsynapse.aclient.lobbyModels.Player;
import com.siliconandsynapse.aclient.lobbyModels.RoomModelListener;
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
import android.view.View;
import android.widget.AdapterView;
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
	private ArrayList<Game> gameList;

	private ListView dealers;
	private View dealerChoice;
	
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

		Bundle extras = getIntent().getExtras();
		var clientName = "";
		if (extras != null)
			clientName = extras.getString("user");

		Images.loadCache(this);
		setContentView(R.layout.games);

		dealerChoice = (View)findViewById(R.id.dealers);
		dealers = (ListView)findViewById(R.id.dealersOptions);
		var dealersList = new ArrayList<String>();
		dealersList.add("Two Sheep");
		dealersList.add("Three Sheep");
		dealersList.add("Euchre");

		var dealerAdapter = new ArrayAdapter<>(getApplicationContext(),
				android.R.layout.simple_list_item_1, dealersList);
		dealers.setAdapter(dealerAdapter);
		dealerChoice.setVisibility(View.GONE);

		dealers.setOnItemClickListener((adpt, view, pos, arg) -> {
			dealerChoice.setVisibility(View.GONE);

			var dealerName = dealerAdapter.getItem(pos);

			new Thread(() -> {
				//network on main thread exception!!!!!!!!!!!!!!!
				var tun = service.getTunnel();
				var cmd = new CreateGameCmd(dealerName);
				try {
					cmd.execute(IxAddress.parse("ixcpp.lobby"), tun);
				} catch (ParseError e) {
					throw new RuntimeException(e);
				}}).start();

		});


		createGame = (Button)findViewById(R.id.addGame);
		games = (ListView)findViewById(R.id.gameList);
		gameList = new ArrayList<Game>();
		var adapter = new ArrayAdapter<>(getApplicationContext(),
				android.R.layout.simple_list_item_1, gameList);

		games.setAdapter(adapter);



		var rm = new DefaultRoomModel();
		rm.addListener(new RoomModelListener() {
			@Override
			public void gameAdded(Game game) {
				MainActivity.this.runOnUiThread(() -> {
					adapter.add(game);
				});

			}

			@Override
			public void gameRemoved(int gameId) {
				MainActivity.this.runOnUiThread(() -> {

					var x = gameList.stream()
						.filter((g)-> g.getId() == gameId)
						.findFirst();

					x.ifPresent((game) -> adapter.remove(game));



				});
			}

			@Override
			public void playerAdded(Game game, Player player) {
				MainActivity.this.runOnUiThread(() -> {
					adapter.notifyDataSetChanged();
				});
			}

			@Override
			public void playerRemoved(Game game, int seat) {
				MainActivity.this.runOnUiThread(() -> {
					adapter.notifyDataSetChanged();
				});
			}
		});

		service = new NetworkService(this, rm, clientName);

		games.setOnItemClickListener((parent, view, pos, id)-> {
			new Thread(() -> {
				var gi = (GameInfo)parent.getItemAtPosition(pos);

				service.joinGame(gi);
			}).start();

		});

		createGame.setOnClickListener((view) -> {

			dealerChoice.setVisibility(View.VISIBLE);

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
