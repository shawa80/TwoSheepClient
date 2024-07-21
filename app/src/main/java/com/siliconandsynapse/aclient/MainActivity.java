package com.siliconandsynapse.aclient;

import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.aclient.lobbyModels.DefaultRoomModel;
import com.siliconandsynapse.aclient.lobbyModels.Game;
import com.siliconandsynapse.aclient.lobbyModels.Player;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.protocol.lobby.CreateGameCmd;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.ParseError;
import com.siliconandsynapse.server.IxcppServ;
import com.siliconandsynapse.server.locator.LocatorService;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity {

	private NetworkService service;
	private Button createGame;
	private ListView games;
	private ArrayList<Game> gameList;

	private ListView dealers;
	private View dealerChoice;
	
	private RoomModel rooms;
	//private LobbyUserList lobbyModel;

	private LocatorService loc;
	private IxcppServ localServer;
	private String connectTo = "";
	public MainActivity() {

	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		var clientName = "";
		if (extras != null)
			clientName = extras.getString("user");
		if (extras != null)
			connectTo = extras.getString("server");

		if ("localhost".equals(connectTo)) {
			loc = new LocatorService();
			loc.start(clientName);

			localServer = new com.siliconandsynapse.server.IxcppServ();
			localServer.start();
		}

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

				var tun = service.getTunnel();
				var cmd = new CreateGameCmd(dealerName);
				try {
					cmd.execute(IxAddress.parse("ixcpp.lobby"), tun);
				} catch (ParseError e) {
					throw new RuntimeException(e);
				}
			}).start();

		});


		createGame = (Button)findViewById(R.id.addGame);
		games = (ListView)findViewById(R.id.gameList);
		gameList = new ArrayList<Game>();
		var adapter = new ArrayAdapter<>(getApplicationContext(),
				android.R.layout.simple_list_item_1, gameList);

		games.setAdapter(adapter);

		var rm = new DefaultRoomModel();

		rm.gameAdded.add((game) -> {
			runOnUiThread(() -> {
				adapter.add(game);
			});
		});

		rm.gameRemoved.add((gameId) -> {
			runOnUiThread(() -> {

				var x = gameList.stream()
						.filter((g)-> g.getId() == gameId)
						.findFirst();

				x.ifPresent((game) -> adapter.remove(game));
			});
		});

		rm.playerAdded.add((game,  player) -> {
			runOnUiThread(() -> {
				adapter.notifyDataSetChanged();
			});
		});

		rm.playerRemoved.add((game, seat) -> {
			runOnUiThread(() -> {
				adapter.notifyDataSetChanged();
			});
		});

		service = new NetworkService(this, connectTo, rm, clientName);

		games.setOnItemClickListener((parent, view, pos, id)-> {
			new Thread(() -> {
				var gi = (GameInfo)parent.getItemAtPosition(pos);

				service.joinGame(gi);
			}).start();

		});

		createGame.setOnClickListener((view) -> {

			dealerChoice.setVisibility(View.VISIBLE);
		});

		service.start();
	}


	@Override
	protected void onStart() {
		super.onStart();
		//service.start();
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		service.stop();
		if ("localhost".equals(connectTo)) {
			loc.stop();
			localServer.stop();
		}
	}

	private Mutex logonBlock = new Mutex();;

}
