package com.siliconandsynapse.aclient.game.Euchre;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.siliconandsynapse.aclient.InsertHandler;
import com.siliconandsynapse.aclient.MainActivity;
import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.game.BaseGameFragment;
import com.siliconandsynapse.aclient.game.CardAddress;
import com.siliconandsynapse.aclient.game.GameActivity;
import com.siliconandsynapse.aclient.game.GameService;
import com.siliconandsynapse.aclient.game.UpdateCardsListener;
import com.siliconandsynapse.aclient.gameModels.models.UpdateGame;
import com.siliconandsynapse.ixcpp.common.Choice;
import com.siliconandsynapse.ixcpp.common.ChoiceResponse;
import com.siliconandsynapse.ixcpp.common.Discard;
import com.siliconandsynapse.ixcpp.common.cards.Card;

import java.util.ArrayList;
import java.util.Hashtable;

public class EuchreFragment  extends BaseGameFragment implements GameActivity {

    private GameService service;
    private ViewGroup table;

    public enum SeatLocation {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private final int PRIVATE_TOTAL = 6;

    private ImageView[] north = new ImageView[PRIVATE_TOTAL];
    private ImageView[] east = new ImageView[PRIVATE_TOTAL];
    private ImageView[] west = new ImageView[PRIVATE_TOTAL];
    private ImageView[] south = new ImageView[PRIVATE_TOTAL];

    private ImageView trickNorth;
    private ImageView trickEast;
    private ImageView trickWest;
    private ImageView trickSouth;

    private TextView northName;
    private TextView westName;
    private TextView eastName;
    private TextView southName;

    private TextView score;
    private PlayerScoreUpdater scoreUpdater;

    private TextView northTurn;
    private TextView westTurn;
    private TextView eastTurn;
    private TextView southTurn;

    private TextView northDesc;
    private TextView westDesc;
    private TextView eastDesc;
    private TextView southDesc;

    private TextView currentMsg;

    private Hashtable<CardAddress, ImageView> cardsByAddress = new Hashtable<CardAddress, ImageView>();

    private View choice;

    private int gameId;


    private UpdateGame updateGame;
    private UpdateCardsListener updateCardsListener;

    private EuchrePlayer updateUserNorth;
    private EuchrePlayer updateUserEast;
    private EuchrePlayer updateUserWest;
    private EuchrePlayer updateUserSouth;

    private MainActivity act;
    public EuchreFragment() {
        super(R.layout.euchre_game);
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        act = (MainActivity) getActivity();

        ViewCompat.setOnApplyWindowInsetsListener(view, new InsertHandler());
        try {

            var gameId = requireArguments().getInt("GAME_ID");

            table = (ViewGroup)act.findViewById(R.id.table);


            table.setBackgroundColor(Color.rgb(0x30, 0x70, 0x21));

            score = (TextView)act.findViewById(R.id.score);
            scoreUpdater = new PlayerScoreUpdater(this, score);


            northName = (TextView)act.findViewById(R.id.northName);
            eastName = (TextView)act.findViewById(R.id.eastName);
            westName = (TextView)act.findViewById(R.id.westName);
            southName = (TextView)act.findViewById(R.id.southName);


            northTurn = (TextView)act.findViewById(R.id.turnNorth);
            eastTurn = (TextView)act.findViewById(R.id.turnEast);
            westTurn = (TextView)act.findViewById(R.id.turnWest);
            southTurn = (TextView)act.findViewById(R.id.turnSouth);

            northDesc = (TextView)act.findViewById(R.id.descNorth);
            eastDesc = (TextView)act.findViewById(R.id.descEast);
            westDesc = (TextView)act.findViewById(R.id.descWest);
            southDesc = (TextView)act.findViewById(R.id.descSouth);

            south[0] = (ImageView)act.findViewById(R.id.south1);
            south[1] = (ImageView)act.findViewById(R.id.south2);
            south[2] = (ImageView)act.findViewById(R.id.south3);
            south[3] = (ImageView)act.findViewById(R.id.south4);
            south[4] = (ImageView)act.findViewById(R.id.south5);
            south[5] = (ImageView)act.findViewById(R.id.south6);

            north[0] = (ImageView)act.findViewById(R.id.north1);
            north[1] = (ImageView)act.findViewById(R.id.north2);
            north[2] = (ImageView)act.findViewById(R.id.north3);
            north[3] = (ImageView)act.findViewById(R.id.north4);
            north[4] = (ImageView)act.findViewById(R.id.north5);
            north[5] = (ImageView)act.findViewById(R.id.north6);

            east[0] = (ImageView)act.findViewById(R.id.east1);
            east[1] = (ImageView)act.findViewById(R.id.east2);
            east[2] = (ImageView)act.findViewById(R.id.east3);
            east[3] = (ImageView)act.findViewById(R.id.east4);
            east[4] = (ImageView)act.findViewById(R.id.east5);
            east[5] = (ImageView)act.findViewById(R.id.east6);

            west[0] = (ImageView)act.findViewById(R.id.west1);
            west[1] = (ImageView)act.findViewById(R.id.west2);
            west[2] = (ImageView)act.findViewById(R.id.west3);
            west[3] = (ImageView)act.findViewById(R.id.west4);
            west[4] = (ImageView)act.findViewById(R.id.west5);
            west[5] = (ImageView)act.findViewById(R.id.west6);


            trickSouth = (ImageView)act.findViewById(R.id.trickSouth);
            trickNorth = (ImageView)act.findViewById(R.id.trickNorth);
            trickEast = (ImageView)act.findViewById(R.id.trickEast);
            trickWest = (ImageView)act.findViewById(R.id.trickWest);


            currentMsg = (TextView)act.findViewById(R.id.currentMsg);
            choice = (View)act.findViewById(R.id.choice);

            mapCards();

            service = GameService.getService(act, gameId);

            updateGame = msg -> act.runOnUiThread(() -> currentMsg.setText(msg));
            service.getModel().addListener(updateGame);

            PlayerTurnUpdater turn = new PlayerTurnUpdater(this);

            updateUserNorth = new EuchrePlayer(this, northName, northDesc, north, trickNorth, turn, scoreUpdater);
            updateUserEast = new EuchrePlayer(this, eastName, eastDesc, east, trickEast, turn, scoreUpdater);
            updateUserWest = new EuchrePlayer(this, westName, westDesc, west, trickWest, turn, scoreUpdater);
            updateUserSouth = new EuchrePlayer(this, southName, southDesc, south, trickSouth, turn, scoreUpdater);

            turn.add(updateUserNorth, northTurn);
            turn.add(updateUserEast, eastTurn);
            turn.add(updateUserSouth, southTurn);
            turn.add(updateUserWest, westTurn);

            service.getModel().getPlayer(0).addListener(updateUserSouth);
            service.getModel().getPlayer(1).addListener(updateUserWest);
            service.getModel().getPlayer(2).addListener(updateUserNorth);
            service.getModel().getPlayer(3).addListener(updateUserEast);

            var cs = new CardSorter();
            updateCardsListener = new UpdateCardsListener(act, cardsByAddress, table,
                    cs,
                    service.getModel().getPlayer(1),
                    service.getModel().getPlayer(3));
            service.getModel().addListener(updateCardsListener);

            service.getModel().getPlayer(0).addListener(cs);
            service.getModel().getPlayer(1).addListener(cs);
            service.getModel().getPlayer(2).addListener(cs);
            service.getModel().getPlayer(3).addListener(cs);

            var user = new EuchreUser(this, updateUserSouth, service);
            service.getModel().addChoiceListener(user);
            service.getModel().addDiscardListener(user);

            hideChoice();
            service.start();

        } catch (Exception ex) {
            Toast.makeText(act, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        table.invalidate();

    }


    public EuchreFragment.SeatLocation getSeat(EuchrePlayer player) {

        if (player == updateUserNorth)
            return EuchreFragment.SeatLocation.NORTH;

        if (player == updateUserSouth)
            return EuchreFragment.SeatLocation.SOUTH;

        if (player == updateUserEast)
            return EuchreFragment.SeatLocation.EAST;

        if (player == updateUserWest)
            return EuchreFragment.SeatLocation.WEST;

        return EuchreFragment.SeatLocation.NORTH;

    }


    private void mapCards() {

        cardsByAddress.clear();

        cardsByAddress.put(new CardAddress(0, "trick", 0, 0), trickSouth);
        cardsByAddress.put(new CardAddress(1, "trick", 0, 0), trickWest);
        cardsByAddress.put(new CardAddress(2, "trick", 0, 0), trickNorth);
        cardsByAddress.put(new CardAddress(3, "trick", 0, 0), trickEast);

        for (int i = 0; i < PRIVATE_TOTAL; i++) {

            cardsByAddress.put(new CardAddress(0, "private", i, 0),  south[i]);
            cardsByAddress.put(new CardAddress(1, "private", i, 0),  west[i]);
            cardsByAddress.put(new CardAddress(2, "private", i, 0),  north[i]);
            cardsByAddress.put(new CardAddress(3, "private", i, 0),  east[i]);

        }

    }

    private void hideChoice() {
        choice.setVisibility(View.GONE);
    }

    @Override
    public void runOnUiThread(Runnable run) {
        act.runOnUiThread(run);
    }

    public void showChoice(final Choice c) {

        var text = (TextView)act.findViewById(R.id.choiceMessage);

        text.setText(c.getQuestion());

        final ArrayList<String> names = new ArrayList<String>();
        names.clear();

        for (int i = 0; i < c.getCount(); i++) {
            names.add(c.getAnswerAt(i).getValue());
        }

        ArrayAdapter<String> namesAA = new ArrayAdapter<String> (act, android.R.layout.simple_list_item_1, names );
        var choiceList = (ListView)act.findViewById(R.id.choiceOptions);
        choiceList.setAdapter(namesAA);


        choiceList.setOnItemClickListener((arg0, arg1, position, arg3) -> {

            EuchreFragment.this.hideChoice();

            service.setChoiceResponse(new ChoiceResponse(c.getAnswerAt(position)));
            service.getChoiceBlock().sendNotice();
        });

        choice.setVisibility(View.VISIBLE);
        choice.bringToFront();

        table.invalidate();
    }


    public void showDiscard(final Discard d) {

        TextView text = (TextView)act.findViewById(R.id.choiceMessage);

        text.setText(d.getMessage());

        final var names = new ArrayList<Card>();
        names.clear();

        for (int i = 0; i < d.getCount(); i++) {
            Card c = d.getCardAt(i);

            names.add(c);
        }

        var namesAA = new DiscardAdapter(act, names );
        var choiceList = (ListView)act.findViewById(R.id.choiceOptions);
        choiceList.setAdapter(namesAA);

        choiceList.setOnItemClickListener((arg0, arg1, position, arg3) -> {

            EuchreFragment.this.hideChoice();

            Discard response = new Discard();
            response.addCard(d.getCardAt(position));
            service.setDiscardResponse(response);
            service.getDiscardBlock().sendNotice();
        });

        choice.setVisibility(View.VISIBLE);
        choice.bringToFront();

        table.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        new Thread(() -> {
            service.stop();
        }).start();
    }
}
