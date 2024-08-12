package com.siliconandsynapse.aclient;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.siliconandsynapse.aclient.Servers.ConfigurableServerConnection;
import com.siliconandsynapse.aclient.Servers.DynamicServerConnection;
import com.siliconandsynapse.aclient.Servers.ServerConnection;
import com.siliconandsynapse.aclient.Servers.StaticServerConnection;
import com.siliconandsynapse.aclient.util.SelectedListener;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    private MainActivity act;

    private ArrayAdapter<ServerConnection> servers;
    private ServerListener listener;

    public LoginFragment() {
        super(R.layout.login_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = (MainActivity)getActivity();

        var settings = act.getSharedPreferences("loginSettings", 0);
        var savedName = settings.getString("name", "");
        var savedServer = settings.getString("specifyServer", "");

        final var user = (EditText)act.findViewById(R.id.userInput);
        user.setText(savedName);
        final var serverList = (Spinner)act.findViewById(R.id.server);
        final var extraInput = (EditText)act.findViewById(R.id.extraInput);
        extraInput.setVisibility(View.GONE);
        extraInput.setText(savedServer);
        final var ipAddress = (TextView)act.findViewById(R.id.currentIp);

        final var s = new ArrayList<ServerConnection>();
        s.add(new StaticServerConnection("Internet Server", "twosheep.shawtonabbey.com"));
        s.add(new StaticServerConnection("Start Local Server", "localhost"));
        s.add(new ConfigurableServerConnection("Specify Server", extraInput));

        servers = new ArrayAdapter<>(act,
                android.R.layout.simple_list_item_1, s);
        serverList.setAdapter(servers);

        var loginBtn = (Button)act.findViewById(R.id.loginBtn);

        serverList.setOnItemSelectedListener((SelectedListener)(parent, v, position, id) -> {
            var conn = (ServerConnection)parent.getItemAtPosition(position);
            if (conn instanceof ConfigurableServerConnection ) {
                extraInput.setVisibility(View.VISIBLE);
            } else {
                extraInput.setVisibility(View.GONE);
            }
//            if ("localhost".equals(conn.address())) {
//                ipAddress.setText(ServerListener.getLocalAddresses());
//            } else {
//                ipAddress.setText("");
//            }

        });

        loginBtn.setOnClickListener((e) -> {
            var name = user.getText().toString();
            var specifyServer = extraInput.getText().toString();
            var connectTo = (ServerConnection)serverList.getSelectedItem();

            var editor = settings.edit();
            editor.putString("name", name);
            editor.putString("specifyServer", specifyServer);
            editor.apply();


            act.login(name, connectTo);
        });

        listener = new ServerListener();
        listener.listen(this::addServer, this::tickServer);

    }

    public void tickServer() {
        act.runOnUiThread(() -> {

            var toRemove = new ArrayList<ServerConnection>();
            for(var i = 0; i < servers.getCount(); i++)
            {
                var exConn = servers.getItem(i);
                if (exConn instanceof DynamicServerConnection dynConn) {

                    dynConn.increment();
                    if (dynConn.expired())
                        toRemove.add(dynConn);
                }

            }
            toRemove.forEach((c) -> servers.remove(c));
        });
    }

    public void addServer(String name, String address) {
        act.runOnUiThread(() -> {

            var alreadyExists = false;
            var conn = new DynamicServerConnection("Local:" + name, address, 0);

            for(var i = 0; i < servers.getCount(); i++)
            {
                var exConn = servers.getItem(i);
                if (exConn instanceof DynamicServerConnection dynConn) {
                    if (dynConn.address().equals(address)) {
                        alreadyExists = true;
                        dynConn.reset();
                        break;
                    }
                }
            }
            if (!alreadyExists)
                servers.add(conn);
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        listener.stop();
    }

}
