package com.siliconandsynapse.aclient;

import static com.siliconandsynapse.aclient.R.*;

import com.siliconandsynapse.aclient.Servers.ServerConnection;
import com.siliconandsynapse.aclient.game.GameActivity;
import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.aclient.game.ThreeSheep.ThreeSheepFragment;
import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepFragment;
import com.siliconandsynapse.aclient.game.Euchre.EuchreFragment;
import com.siliconandsynapse.aclient.game.TwoSheepPNP.TwoSheepPNPFragment;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.server.IxcppServ;
import com.siliconandsynapse.server.locator.LocatorService;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;

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

				var f = getSupportFragmentManager()
						.findFragmentById(id.fragment_container_view);
				if (f instanceof BackButtonHandler handler) {
					if (handler.handleBackPress()) return;
				}

				if (f instanceof GameActivity)
					showGameList();
				else if (f instanceof LoginFragment)
					finish();
				else if (f instanceof GamesFragment) {
					 logoff(); showlogin();
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

		Class<? extends Fragment> type = TwoSheepFragment.class;

		type = switch (gi.getName()) {
			case "Two Sheep" -> TwoSheepFragment.class;
			case "Two Sheep PNP" -> TwoSheepPNPFragment.class;
			case "Three Sheep" -> ThreeSheepFragment.class;
			case "Euchre" -> EuchreFragment.class;
			default -> null;
		};

		if (type == null)
			return;

		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.replace(id.fragment_container_view, type, bundle)
				.commit();
	}

	public void showGameList() {
		setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);

		getSupportFragmentManager().beginTransaction()
				.setReorderingAllowed(true)
				.replace(id.fragment_container_view, GamesFragment.class, null)
				.commit();
	}

	public NetworkService getNetworkService() {
		return networkService;
	}


	private Mutex logonBlock = new Mutex();;

}
