package com.siliconandsynapse.aclient;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.siliconandsynapse.aclient.lobbyModels.Game;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.ixcpp.protocol.lobby.CreateGameCmd;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.ParseError;

import java.util.ArrayList;

public class GamesFragment extends Fragment implements BackButtonHandler {

    private NetworkService service;
    private Button createGame;
    private ListView games;
    private ArrayList<Game> gameList;

    private ListView dealers;
    private View dealerChoice;

    private RoomModel rooms;
    //private LobbyUserList lobbyModel;

    private NetworkService.OnConnectSuccessListener onConnect;
    private NetworkService.OnConnectFailureListener onFailure;
    private MainActivity act;

    public GamesFragment() {
        super(R.layout.games);
    }

    public boolean handleBackPress() {

        if(dealerChoice.getVisibility() == View.VISIBLE)
        {
            dealerChoice.setVisibility(View.GONE);
            return true;
        }
        return false;
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = (MainActivity) getActivity();

        ProgressBar progressBar = act.findViewById(R.id.progressBar);
        dealerChoice = act.findViewById(R.id.dealers);
        dealers = act.findViewById(R.id.dealersOptions);
        var dealersList = new ArrayList<String>();
        dealersList.add("Two Sheep");
        //dealersList.add("Three Sheep");
        //dealersList.add("Euchre");
        dealersList.add("Two Sheep PNP");

        var dealerAdapter = new ArrayAdapter<>(act.getApplicationContext(),
                android.R.layout.simple_list_item_1, dealersList);
        dealers.setAdapter(dealerAdapter);
        dealerChoice.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);


        dealers.setOnItemClickListener((adpt, viewx, pos, arg) -> {
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


        createGame = (Button)act.findViewById(R.id.addGame);
        createGame.setEnabled(false);
        games = (ListView)act.findViewById(R.id.gameList);
        gameList = new ArrayList<Game>();
        var adapter = new ArrayAdapter<>(act.getApplicationContext(),
                android.R.layout.simple_list_item_1, gameList);

        games.setAdapter(adapter);

        var rm = act.getNetworkService().getRoomModel();

        for (var g : rm) {
            act.runOnUiThread(() -> {
                adapter.add(g);
            });
        }
        //TODO is a remove needed?
        rm.gameAdded.add((game) -> {
            act.runOnUiThread(() -> {
                adapter.add(game);
            });
        });

        rm.gameRemoved.add((gameId) -> {
            act.runOnUiThread(() -> {

                var x = gameList.stream()
                        .filter((g)-> g.getId() == gameId)
                        .findFirst();

                x.ifPresent((game) -> adapter.remove(game));
            });
        });

        rm.playerAdded.add((game,  player) -> {
            act.runOnUiThread(adapter::notifyDataSetChanged);
        });

        rm.playerRemoved.add((game, seat) -> {
            act.runOnUiThread(adapter::notifyDataSetChanged);
        });

        service = NetworkService.getService();

        if (!service.isConnected())
            act.runOnUiThread(() -> {
                progressBar.setVisibility(View.VISIBLE);
            });
        if (service.isConnected()) {
            progressBar.setVisibility(View.GONE);
            createGame.setEnabled(true);
        }
        onConnect = (service) -> act.runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            createGame.setEnabled(true);
        });
        service.onConnectSuccess.add(onConnect);

        onFailure = (service, message) -> act.runOnUiThread(() -> {
            var toast = Toast.makeText(act , message, Toast.LENGTH_SHORT);
            toast.show();
            act.showlogin();
        });
        service.onConnectFailure.add(onFailure);

        games.setOnItemClickListener((parent, viewx, pos, id)-> {
            new Thread(() -> {
                var gi = (GameInfo)parent.getItemAtPosition(pos);

                act.startGame(gi);

            }).start();

        });

        createGame.setOnClickListener((viewx) -> {

            dealerChoice.setVisibility(View.VISIBLE);
        });

        act.getNetworkService().connect();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        service.onConnectSuccess.remove(onConnect);
        service.onConnectFailure.remove(onFailure);
    }


}

