package com.siliconandsynapse.aclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepActivity;
import com.siliconandsynapse.aclient.util.SelectedListener;

import java.util.ArrayList;

public class LoginActivity extends Activity {

	private ArrayAdapter<ServerConnection> servers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		final var user = (EditText)findViewById(R.id.userInput);
		final var serverList = (Spinner)findViewById(R.id.server);

		final String[] connectTo = {"twosheep.shawtonabbey.com"};

		final var s = new ArrayList<ServerConnection>();
		s.add(new ServerConnection("Internet Server", "twosheep.shawtonabbey.com"));
		s.add(new ServerConnection("Local Server", "localhost"));
		servers = new ArrayAdapter<ServerConnection>(this,
				android.R.layout.simple_list_item_1, s);
		serverList.setAdapter(servers);

		serverList.setOnItemSelectedListener((SelectedListener)(selector, view, pos, id) -> {
			var serverChoice = (ServerConnection)selector.getItemAtPosition(pos);
			connectTo[0] = serverChoice.address();
		});

		var loginBtn = (Button)findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener((e) -> {
				
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("user", user.getText().toString());
			intent.putExtra("server", connectTo[0]);

			this.startActivity(intent);
        });

		new Thread(() -> {

			try {
				var localServer = new com.siliconandsynapse.server.IxcppServ();
				localServer.start();

			} catch (Exception ex) {
				System.out.println("here");
			}
		}).start();

		new Thread(() -> {
			new ServerListener().listen(this);
		}).start();

	}
	public void addServer(String name, String address) {
		this.runOnUiThread(() -> {

			var conn = new ServerConnection(name, address);
			servers.remove(conn);
			servers.add(conn);
		});
	}

}
