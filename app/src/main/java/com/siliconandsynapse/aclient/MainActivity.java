package com.siliconandsynapse.aclient;

import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.ixcpp.protocol.Debug;
import com.siliconandsynapse.ixcpp.ui.MessageReceiverModel;
import com.siliconandsynapse.ixcpp.util.Mutex;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

public class MainActivity extends Activity {

	private TextView debug;

	private NetworkService service;
	
	//private RoomModel rooms;
	//private LobbyUserList lobbyModel;
	
	public MainActivity() {

	}

	public void log(String log) {
		this.runOnUiThread(() -> {
			debug.append(log + "\n");
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Images.loadCache(this);
		setContentView(R.layout.activity_main);
	    debug = (TextView) findViewById(R.id.debugLog);

		service = new NetworkService(this, new Debug(this));
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

	}

	@Override
	protected void onStart() {
		super.onStart();
		service.start();
	}

	private Mutex logonBlock = new Mutex();;

}
