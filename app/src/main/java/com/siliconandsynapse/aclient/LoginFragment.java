package com.siliconandsynapse.aclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.siliconandsynapse.aclient.util.SelectedListener;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    private MainActivity act;

    private ArrayAdapter<ServerConnection> servers;
    private ServerListener listener;

    public LoginFragment() {
        super(R.layout.activity_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = (MainActivity)getActivity();
        final var user = (EditText)act.findViewById(R.id.userInput);
        final var serverList = (Spinner)act.findViewById(R.id.server);

        final var s = new ArrayList<ServerConnection>();
        s.add(new ServerConnection("Internet Server", "twosheep.shawtonabbey.com", -1));
        s.add(new ServerConnection("Local Server", "localhost",-1));
        servers = new ArrayAdapter<ServerConnection>(act,
                android.R.layout.simple_list_item_1, s);
        serverList.setAdapter(servers);

        var loginBtn = (Button)act.findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener((e) -> {
            var name = user.getText().toString();
            var connectTo = (ServerConnection)serverList.getSelectedItem();
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
                exConn.increment();
                if (exConn.expired())
                    toRemove.add(exConn);
            }
            toRemove.forEach((c) -> servers.remove(c));
        });
    }

    public void addServer(String name, String address) {
        act.runOnUiThread(() -> {

            var alreadyExists = false;
            var conn = new ServerConnection(name, address, 0);
            for(var i = 0; i < servers.getCount(); i++)
            {
                var exConn = servers.getItem(i);
                if (exConn.address().equals(address)) {
                    alreadyExists = true;
                    exConn.reset();
                    break;
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
