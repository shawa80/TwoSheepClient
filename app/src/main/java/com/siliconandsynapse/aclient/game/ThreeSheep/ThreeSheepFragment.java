package com.siliconandsynapse.aclient.game.ThreeSheep;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.siliconandsynapse.aclient.MainActivity;
import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.game.CardAddress;
import com.siliconandsynapse.aclient.game.GameActivity;
import com.siliconandsynapse.aclient.game.GameService;
import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.aclient.game.TwoSheep.PlayerTurnUpdater;
import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepPlayer;
import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepUser;
import com.siliconandsynapse.aclient.game.UpdateCardsListener;
import com.siliconandsynapse.aclient.gameModels.models.UpdateGame;
import com.siliconandsynapse.ixcpp.common.Choice;
import com.siliconandsynapse.ixcpp.common.ChoiceResponse;
import com.siliconandsynapse.ixcpp.common.Discard;
import com.siliconandsynapse.ixcpp.common.cards.Card;

import java.util.ArrayList;
import java.util.Hashtable;

public class ThreeSheepFragment extends Fragment implements GameActivity {

    private GameService service;

    private View choice;
    private ViewGroup table;

    private TextView eastName;
    private TextView westName;
    private TextView southName;


    private TextView eastTurn;
    private TextView westTurn;
    private TextView southTurn;

    private TextView eastDesc;
    private TextView westDesc;
    private TextView southDesc;


    private TextView eastScore;
    private TextView westScore;
    private TextView southScore;

    private TextView eastWealth;
    private TextView westWealth;
    private TextView southWealth;


    private final int PRIVATE_TOTAL = 10;

    private ImageView[] southPrivate = new ImageView[PRIVATE_TOTAL+2];
    private ImageView[] eastPrivate = new ImageView[PRIVATE_TOTAL];
    private ImageView[] westPrivate = new ImageView[PRIVATE_TOTAL];


    private ImageView trickEast;
    private ImageView trickWest;
    private ImageView trickSouth;

    private TextView currentMsg;

    private Hashtable<CardAddress, ImageView> cardsByAddress = new Hashtable<CardAddress, ImageView>();

    private int gameId;

    private UpdateGame updateGame;
    private UpdateCardsListener updateCardsListener;

    private TwoSheepPlayer updateUserEast;
    private TwoSheepPlayer updateUserWest;
    private TwoSheepPlayer updateUserSouth;

    private PlayerTurnUpdater turnUpdater;

    private MainActivity act;
    public ThreeSheepFragment() {
        super(R.layout.three_sheep_game);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        act = (MainActivity) getActivity();
        try {

            var gameId = requireArguments().getInt("GAME_ID");

            table = (ViewGroup)act.findViewById(R.id.table);

            table.setBackgroundColor(Color.rgb(0x30, 0x70, 0x21));

            southName = act.findViewById(R.id.southName);
            westName = act.findViewById(R.id.westName);
            eastName = act.findViewById(R.id.eastName);

            southTurn = act.findViewById(R.id.turnSouth);
            westTurn = act.findViewById(R.id.turnWest);
            eastTurn = act.findViewById(R.id.turnEast);

            southDesc = act.findViewById(R.id.descSouth);
            westDesc = act.findViewById(R.id.descWest);
            eastDesc = act.findViewById(R.id.descEast);

            southWealth = act.findViewById(R.id.wealthSouth);
            westWealth = act.findViewById(R.id.wealthWest);
            eastWealth = act.findViewById(R.id.wealthEast);

            eastPrivate[0] = (ImageView)act.findViewById(R.id.east1);
            eastPrivate[1] = (ImageView)act.findViewById(R.id.east2);
            eastPrivate[2] = (ImageView)act.findViewById(R.id.east3);
            eastPrivate[3] = (ImageView)act.findViewById(R.id.east4);
            eastPrivate[4] = (ImageView)act.findViewById(R.id.east5);
            eastPrivate[5] = (ImageView)act.findViewById(R.id.east6);
            eastPrivate[6] = (ImageView)act.findViewById(R.id.east7);
            eastPrivate[7] = (ImageView)act.findViewById(R.id.east8);
            eastPrivate[8] = (ImageView)act.findViewById(R.id.east9);
            eastPrivate[9] = (ImageView)act.findViewById(R.id.east10);

            westPrivate[0] = (ImageView)act.findViewById(R.id.west1);
            westPrivate[1] = (ImageView)act.findViewById(R.id.west2);
            westPrivate[2] = (ImageView)act.findViewById(R.id.west3);
            westPrivate[3] = (ImageView)act.findViewById(R.id.west4);
            westPrivate[4] = (ImageView)act.findViewById(R.id.west5);
            westPrivate[5] = (ImageView)act.findViewById(R.id.west6);
            westPrivate[6] = (ImageView)act.findViewById(R.id.west7);
            westPrivate[7] = (ImageView)act.findViewById(R.id.west8);
            westPrivate[8] = (ImageView)act.findViewById(R.id.west9);
            westPrivate[9] = (ImageView)act.findViewById(R.id.west10);

            southPrivate[0] = (ImageView)act.findViewById(R.id.south1);
            southPrivate[1] = (ImageView)act.findViewById(R.id.south2);
            southPrivate[2] = (ImageView)act.findViewById(R.id.south3);
            southPrivate[3] = (ImageView)act.findViewById(R.id.south4);
            southPrivate[4] = (ImageView)act.findViewById(R.id.south5);
            southPrivate[5] = (ImageView)act.findViewById(R.id.south6);
            southPrivate[6] = (ImageView)act.findViewById(R.id.south7);
            southPrivate[7] = (ImageView)act.findViewById(R.id.south8);
            southPrivate[8] = (ImageView)act.findViewById(R.id.south9);
            southPrivate[9] = (ImageView)act.findViewById(R.id.south10);

            southPrivate[10] = (ImageView)act.findViewById(R.id.south11);
            southPrivate[11] = (ImageView)act.findViewById(R.id.south12);


            trickEast = (ImageView)act.findViewById(R.id.trickEast);
            trickWest = (ImageView)act.findViewById(R.id.trickWest);
            trickSouth = (ImageView)act.findViewById(R.id.trickSouth);

            currentMsg = (TextView)act.findViewById(R.id.currentMsg);
            choice = (View)act.findViewById(R.id.choice);

            mapCards();

            service = GameService.getService(act, gameId);

            service.getModel().addListener((msg) -> {
                act.runOnUiThread(() -> {
                    currentMsg.setText(msg);
                });
            });

            updateCardsListener = new UpdateCardsListener(act, cardsByAddress, table);
            service.getModel().addListener(updateCardsListener);


            turnUpdater = new PlayerTurnUpdater(act);

            updateUserWest = new TwoSheepPlayer(act, westName, westScore,
                    westPrivate, null, trickWest, turnUpdater, westDesc, westWealth);

            updateUserEast = new TwoSheepPlayer(act, eastName, eastScore,
                    eastPrivate, null, trickEast, turnUpdater, eastDesc, eastWealth);

            updateUserSouth = new TwoSheepPlayer(act, southName, southScore,
                    southPrivate, null, trickSouth, turnUpdater, southDesc, southWealth);

            service.getModel().getPlayer(0).addListener(updateUserSouth);
            service.getModel().getPlayer(1).addListener(updateUserWest);
            service.getModel().getPlayer(2).addListener(updateUserEast);



            turnUpdater.add(updateUserWest, westTurn);
            turnUpdater.add(updateUserEast, eastTurn);
            turnUpdater.add(updateUserSouth, southTurn);

            var user = new TwoSheepUser(this, updateUserSouth, service);

            service.getModel().addChoiceListener(user);
            service.getModel().addDiscardListener(user);

            hideChoice();
            service.start();

        } catch (Exception ex) {
            Toast.makeText(act, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        table.invalidate();

    }


    private void mapCards() {

        cardsByAddress.clear();

        cardsByAddress.put(new CardAddress(0, "trick", 0, 0), trickSouth);
        cardsByAddress.put(new CardAddress(1,"trick", 0, 0), trickWest);
        cardsByAddress.put(new CardAddress(2,"trick", 0, 0), trickEast);

        for (int i = 0; i < PRIVATE_TOTAL; i++) {
            cardsByAddress.put(new CardAddress(0, "private", i, 0),  southPrivate[i]);
            cardsByAddress.put(new CardAddress(1, "private", i, 0),  westPrivate[i]);
            cardsByAddress.put(new CardAddress(2, "private", i, 0),  eastPrivate[i]);
        }
        cardsByAddress.put(new CardAddress(0, "private",
                PRIVATE_TOTAL, 0),  southPrivate[10]);
        cardsByAddress.put(new CardAddress(0, "private",
                PRIVATE_TOTAL+1, 0),  southPrivate[11]);

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.game, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        //int id = item.getItemId();
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void showChoice(Choice c) {

        TextView text = (TextView)act.findViewById(R.id.choiceMessage);

        text.setText(c.getQuestion());

        var names = new ArrayList<String>();
        names.clear();

        for (int i = 0; i < c.getCount(); i++) {
            names.add(c.getAnswerAt(i).getValue());
        }

        var namesAA = new ArrayAdapter<String>(act, android.R.layout.simple_list_item_1, names );
        var choiceList = (ListView)act.findViewById(R.id.choiceOptions);
        choiceList.setAdapter(namesAA);


        choiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                ThreeSheepFragment.this.hideChoice();

                service.setChoiceResponse(new ChoiceResponse(c.getAnswerAt(position)));
                service.getChoiceBlock().sendNotice();
            }
        });

        choice.setVisibility(View.VISIBLE);
        choice.bringToFront();

        table.invalidate();
    }

    private void hideChoice() {
        choice.setVisibility(View.GONE);
    }

    public void showDiscard(final Discard d) {

        TextView text = (TextView)act.findViewById(R.id.choiceMessage);

        text.setText(d.getMessage());

        final ArrayList<Card> names = new ArrayList<>();
        names.clear();

        for (int i = 0; i < d.getCount(); i++) {
            Card c = d.getCardAt(i);

            names.add(c);
        }

        var namesAA = new DiscardAdapter (act, names );
        var choiceList = (ListView)act.findViewById(R.id.choiceOptions);
        choiceList.setAdapter(namesAA);


        final int[] count = {0};
        final ArrayList<Card> pickedCards = new ArrayList<>();
        choiceList.setOnItemClickListener((arg0, arg1, position, arg3) -> {

            count[0]++;

            var card = namesAA.getItem(position);
            pickedCards.add(card);
            namesAA.remove(card);

            if (count[0] == 2)
            {
                this.hideChoice();

                Discard response = new Discard();
                response.addCard(pickedCards.get(0));
                response.addCard(pickedCards.get(1));
                service.setDiscardResponse(response);
                service.getDiscardBlock().sendNotice();
            }

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

    @Override
    public void runOnUiThread(Runnable run) {
        act.runOnUiThread(run);
    }
}


