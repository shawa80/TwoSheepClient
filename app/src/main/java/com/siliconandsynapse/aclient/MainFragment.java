package com.siliconandsynapse.aclient;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.aclient.lobbyModels.DefaultRoomModel;
import com.siliconandsynapse.aclient.lobbyModels.Game;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.ixcpp.protocol.lobby.CreateGameCmd;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.ParseError;
import com.siliconandsynapse.server.IxcppServ;
import com.siliconandsynapse.server.locator.LocatorService;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private NetworkService service;
    private Button createGame;
    private ListView games;
    private ArrayList<Game> gameList;

    private ListView dealers;
    private View dealerChoice;

    private RoomModel rooms;
    //private LobbyUserList lobbyModel;



    private MainActivity act;

    public MainFragment() {
        super(R.layout.games);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = (MainActivity) getActivity();

        dealerChoice = (View)act.findViewById(R.id.dealers);
        dealers = (ListView)act.findViewById(R.id.dealersOptions);
        var dealersList = new ArrayList<String>();
        dealersList.add("Two Sheep");
        dealersList.add("Three Sheep");
        dealersList.add("Euchre");

        var dealerAdapter = new ArrayAdapter<>(act.getApplicationContext(),
                android.R.layout.simple_list_item_1, dealersList);
        dealers.setAdapter(dealerAdapter);
        dealerChoice.setVisibility(View.GONE);

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
        games = (ListView)act.findViewById(R.id.gameList);
        gameList = new ArrayList<Game>();
        var adapter = new ArrayAdapter<>(act.getApplicationContext(),
                android.R.layout.simple_list_item_1, gameList);

        games.setAdapter(adapter);

        var rm = new DefaultRoomModel();

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
            act.runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
            });
        });

        rm.playerRemoved.add((game, seat) -> {
            act.runOnUiThread(() -> {
                adapter.notifyDataSetChanged();
            });
        });

        service = NetworkService.getService();

        games.setOnItemClickListener((parent, viewx, pos, id)-> {
            new Thread(() -> {
                var gi = (GameInfo)parent.getItemAtPosition(pos);

                act.startGame(gi);

            }).start();

        });

        createGame.setOnClickListener((viewx) -> {

            dealerChoice.setVisibility(View.VISIBLE);
        });

        act.getNetworkService().setRoomModel(rm);

        act.getNetworkService().start();
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        //service.start();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        service.stop();
//        if ("localhost".equals(connectTo)) {
//            loc.stop();
//            localServer.stop();
//        }
//    }

    //private Mutex logonBlock = new Mutex();;

}

