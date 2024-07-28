package com.siliconandsynapse.aclient;

import static com.siliconandsynapse.aclient.R.*;

import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.aclient.game.ThreeSheep.ThreeSheepFragment;
import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepFragment;
import com.siliconandsynapse.aclient.game.Euchre.EuchreFragment;
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

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;

import java.sql.Connection;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {


	private LocatorService loc;
	private IxcppServ localServer;
	private ServerConnection connectTo = null;
	private NetworkService networkService = null;

	private FragmentContainerView container;
	public MainActivity() {
		super(layout.main_fragment);
	}
	public boolean localStarted = false;

	private enum AppState {
		Login,
		GameList,
		Game
	}

	private AppState currentState = AppState.Login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		var intent = getIntent();
//		String action = intent.getAction();
//		var data = intent.getData();

		Images.loadCache(this);

		showlogin();

		getOnBackPressedDispatcher().addCallback(this,
				new OnBackPressedCallback(true) {
			@Override
			public void handleOnBackPressed() {
				switch (currentState) {
					case Game -> showGameList();
					case Login -> finish();
					case GameList -> { logoff(); finish();}
				}
			}
		});
	}

	public void showlogin() {
		setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);
		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.replace(id.fragment_container_view, LoginFragment.class, null)
				.commit();
	}

	public void login(String name, ServerConnection connectTo) {

		setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);
		this.connectTo = connectTo;
		localStarted = false;
		if ("localhost".equals(connectTo.address())) {
            loc = new LocatorService();
            loc.start(name);

            localServer = new com.siliconandsynapse.server.IxcppServ();
            localServer.start();
			localStarted = true;
        }

		networkService = new NetworkService(this, connectTo.address(), name);

		showGameList();

	}
	public void logoff()
	{
		networkService.stop();
		if (localStarted) {
			loc.stop();
			localServer.stop();
		}
	}

	public void startGame(GameInfo gi) {

		setTheme(android.R.style.Theme_NoTitleBar_Fullscreen);
		var bundle = new Bundle();
		bundle.putInt("GAME_ID", gi.getId());

		currentState = AppState.Game;
		Class<? extends Fragment> type = TwoSheepFragment.class;
		if ("Two Sheep".equals(gi.getName()))
			type = TwoSheepFragment.class;
		else if ("Euchre".equals(gi.getName()))
			type = EuchreFragment.class;
		else
			type = ThreeSheepFragment.class;

		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.replace(id.fragment_container_view, type, bundle)
				.commit();
	}

	public void showGameList() {
		setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);
		currentState = AppState.GameList;
		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.replace(id.fragment_container_view, MainFragment.class, null)
				.commit();
	}

	public NetworkService getNetworkService() {
		return networkService;
	}


	private Mutex logonBlock = new Mutex();;

}
