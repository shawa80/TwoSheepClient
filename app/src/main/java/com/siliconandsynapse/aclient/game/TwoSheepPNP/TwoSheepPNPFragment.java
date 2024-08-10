package com.siliconandsynapse.aclient.game.TwoSheepPNP;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.siliconandsynapse.aclient.MainActivity;
import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.game.CardAddress;
import com.siliconandsynapse.aclient.game.GameActivity;
import com.siliconandsynapse.aclient.game.GameService;
import com.siliconandsynapse.aclient.game.TwoSheep.PlayerTurnUpdater;
import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepPlayer;
import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepUser;
import com.siliconandsynapse.aclient.game.UpdateCardsListener;
import com.siliconandsynapse.aclient.gameModels.PlayerModel;
import com.siliconandsynapse.aclient.gameModels.models.UpdateGame;
import com.siliconandsynapse.aclient.gameModels.models.UpdateUser;
import com.siliconandsynapse.ixcpp.common.Choice;
import com.siliconandsynapse.ixcpp.common.Discard;

import java.util.Arrays;
import java.util.Hashtable;

public class TwoSheepPNPFragment extends Fragment implements GameActivity {

    private GameService serviceA;
    private GameService serviceB;

    private ViewGroup table;

    private TextView myName;
    private TextView yourName;

    private TextView yourTurn;
    private TextView myTurn;


    private final int PRIVATE_TOTAL = 4;
    private final int PUBLIC_TOTAL = 6;

    private SurprisableImageView[] yourPrivate = new SurprisableImageView[PRIVATE_TOTAL];
    private ImageView[] yourPublic = new ImageView[PUBLIC_TOTAL];
    private SurprisableImageView[] myPrivate = new SurprisableImageView[PRIVATE_TOTAL];
    private ImageView[] myPublic = new ImageView[PUBLIC_TOTAL];
    private ImageView yourCard;
    private ImageView myCard;

    private TextView myCurrentMsg;
    private TextView yourCurrentMsg;

    private Hashtable<CardAddress, ImageView> yourCardsByAddress = new Hashtable<CardAddress, ImageView>();
    private Hashtable<CardAddress, ImageView> myCardsByAddress = new Hashtable<CardAddress, ImageView>();

    private int gameId;

    private UpdateGame updateGame;
    private UpdateCardsListener updateCardsListenerA;
    private UpdateCardsListener updateCardsListenerB;

    private TwoSheepPlayer updateUserNorth;
    private TwoSheepPlayer updateUserSouth;

    private PlayerTurnUpdater turnUpdaterA;
    private PlayerTurnUpdater turnUpdaterB;
    private Button showYourCards;
    private Button showMyCards;

    private MainActivity act;
    public TwoSheepPNPFragment() {
        super(R.layout.two_sheep_game_pnp);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        var tableColor = Color.rgb(0x30, 0x70, 0x21);
        act = (MainActivity) getActivity();
        try {

            var gameId = requireArguments().getInt("GAME_ID");

            table = (ViewGroup)act.findViewById(R.id.table);

            table.setBackgroundColor(tableColor);

            myName = (TextView)act.findViewById(R.id.myname);
            yourName = (TextView)act.findViewById(R.id.yourname);
            yourName.setRotation(180);

            myTurn = (TextView)act.findViewById(R.id.myTurn);
            yourTurn = (TextView)act.findViewById(R.id.yourTurn);

            showMyCards = (Button)act.findViewById(R.id.showMyCards);
            //showMyCards.setBackgroundColor(tableColor);
            showYourCards = (Button)act.findViewById(R.id.showYourCards);
            showYourCards.setRotation(180);
            //showYourCards.setBackgroundColor(tableColor);



            yourPrivate[0] = (SurprisableImageView)act.findViewById(R.id.yourPrivate1);
            yourPrivate[1] = (SurprisableImageView)act.findViewById(R.id.yourPrivate2);
            yourPrivate[2] = (SurprisableImageView)act.findViewById(R.id.yourPrivate3);
            yourPrivate[3] = (SurprisableImageView)act.findViewById(R.id.yourPrivate4);

            Arrays.stream(yourPrivate).forEach((x) -> {
                x.setRotation(180);
                x.setSupressed(true);
            });

            yourPublic[0] = (ImageView)act.findViewById(R.id.yourPublic1);
            yourPublic[1] = (ImageView)act.findViewById(R.id.yourPublic2);
            yourPublic[2] = (ImageView)act.findViewById(R.id.yourPublic3);
            yourPublic[3] = (ImageView)act.findViewById(R.id.yourPublic4);
            yourPublic[4] = (ImageView)act.findViewById(R.id.yourPublic5);
            yourPublic[5] = (ImageView)act.findViewById(R.id.yourPublic6);

            Arrays.stream(yourPublic).forEach((x) -> {
                x.setRotation(180);
            });

            myPrivate[0] = (SurprisableImageView)act.findViewById(R.id.myPrivate1);
            myPrivate[1] = (SurprisableImageView)act.findViewById(R.id.myPrivate2);
            myPrivate[2] = (SurprisableImageView)act.findViewById(R.id.myPrivate3);
            myPrivate[3] = (SurprisableImageView)act.findViewById(R.id.myPrivate4);

            Arrays.stream(myPrivate).forEach((x) -> {
                x.setSupressed(true);
            });

            myPublic[0] = (ImageView)act.findViewById(R.id.myPublic1);
            myPublic[1] = (ImageView)act.findViewById(R.id.myPublic2);
            myPublic[2] = (ImageView)act.findViewById(R.id.myPublic3);
            myPublic[3] = (ImageView)act.findViewById(R.id.myPublic4);
            myPublic[4] = (ImageView)act.findViewById(R.id.myPublic5);
            myPublic[5] = (ImageView)act.findViewById(R.id.myPublic6);

            yourCard = (ImageView)act.findViewById(R.id.YourCard);
            yourCard.setRotation(180);
            myCard = (ImageView)act.findViewById(R.id.MyCard);

            myCurrentMsg = (TextView)act.findViewById(R.id.myCurrentMsg);
            yourCurrentMsg = (TextView)act.findViewById(R.id.youCurrentMsg);
            yourCurrentMsg.setRotation(180);

            showMyCards.setOnTouchListener((v, event) -> {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Arrays.stream(myPrivate).forEach(c -> c.setSupressed(false));
                    table.invalidate();

                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Arrays.stream(myPrivate).forEach(c -> c.setSupressed(true));
                    table.invalidate();
                }

                return false;
            });

            showYourCards.setOnTouchListener((v, event) -> {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Arrays.stream(yourPrivate).forEach(c -> c.setSupressed(false));
                    table.invalidate();

                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    Arrays.stream(yourPrivate).forEach(c -> c.setSupressed(true));
                    table.invalidate();
                }

                return false;
            });

            mapCards();

            serviceA = GameService.getService(act, gameId, "A");
            serviceB = GameService.getService(act, gameId, "B");

            serviceA.getModel().addListener((msg) -> act.runOnUiThread(() -> {
                myCurrentMsg.setText(msg);
            }));
            serviceB.getModel().addListener((msg) -> act.runOnUiThread(() -> {
                yourCurrentMsg.setText(msg);
            }));

            updateCardsListenerB = new UpdateCardsListener(act, yourCardsByAddress, table);
            serviceB.getModel().addListener(updateCardsListenerB);

            updateCardsListenerA = new UpdateCardsListener(act, myCardsByAddress, table);
            serviceA.getModel().addListener(updateCardsListenerA);


            turnUpdaterA = new PlayerTurnUpdater(act);
            turnUpdaterB = new PlayerTurnUpdater(act);

            updateUserNorth = new TwoSheepPlayer(act, yourName, null,
                    yourPrivate, yourPublic, yourCard, turnUpdaterA, null);
            updateUserSouth = new TwoSheepPlayer(act, myName, null,
                    myPrivate, myPublic, myCard, turnUpdaterA, null);
            serviceB.getModel().getPlayer(1);
            serviceB.getModel().getPlayer(0).addListener(updateUserNorth);

            serviceA.getModel().getPlayer(1);
            serviceA.getModel().getPlayer(0).addListener(updateUserSouth);

            serviceA.getModel().getPlayer(0).addListener((TurnChangeEvent)(p)->{
                this.runOnUiThread(() -> {
                    Arrays.stream(myPublic).forEach(c->c.setRotation(0));
                    Arrays.stream(yourPublic).forEach(c->c.setRotation(0));
                    //Arrays.stream(yourPrivate).forEach(c -> c.setSupressed(true));
//                    showMyCards.setEnabled(true);
//                    showYourCards.setEnabled(false);
                    myCard.setRotation(0);
                    yourCard.setRotation(0);
                    table.invalidate();
                });
            });
            serviceB.getModel().getPlayer(0).addListener((TurnChangeEvent)(p)->{
                this.runOnUiThread(() -> {
                    Arrays.stream(myPublic).forEach(c->c.setRotation(180));
                    Arrays.stream(yourPublic).forEach(c->c.setRotation(180));
                    //Arrays.stream(myPrivate).forEach(c -> c.setSupressed(true));
//                    showMyCards.setEnabled(false);
//                    showYourCards.setEnabled(true);
                    myCard.setRotation(180);
                    yourCard.setRotation(180);
                    table.invalidate();
                });

            });


            turnUpdaterA.add(updateUserNorth, yourTurn);
            turnUpdaterA.add(updateUserSouth, myTurn);

            var yourUser = new TwoSheepUser(this, updateUserNorth, serviceB);
            var myUser = new TwoSheepUser(this, updateUserSouth, serviceA);

            serviceA.start();
            serviceB.start();

        } catch (Exception ex) {
            Toast.makeText(act, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        table.invalidate();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        new Thread(() -> {
            serviceA.stop();
            serviceB.stop();
        }).start();
    }


    private void mapCards() {

        myCardsByAddress.clear();
        yourCardsByAddress.clear();

        //trick order depends on who went first...
        //we need to register our trick cards for each order
        yourCardsByAddress.put(new CardAddress(0,"trick", 0, 0), yourCard);
        myCardsByAddress.put(new CardAddress(0, "trick", 0, 0), myCard);


        //player, stack type, stack number, level
        for (int i = 0; i < PUBLIC_TOTAL; i++) {
            yourCardsByAddress.put(new CardAddress(0, "public", i, 1),  yourPublic[i]);
            yourCardsByAddress.put(new CardAddress(0, "public", i, 0),  yourPublic[i]);

            myCardsByAddress.put(new CardAddress(0, "public", i, 1),  myPublic[i]);
            myCardsByAddress.put(new CardAddress(0, "public", i, 0),  myPublic[i]);
        }

        for (int i = 0; i < PRIVATE_TOTAL; i++) {
            yourCardsByAddress.put(new CardAddress(0, "private", i, 0),  yourPrivate[i]);
            myCardsByAddress.put(new CardAddress(0, "private", i, 0),  myPrivate[i]);
        }

    }


    @Override
    public void runOnUiThread(Runnable run) {
        act.runOnUiThread(run);
    }

    @Override
    public void showChoice(Choice c) {

    }

    @Override
    public void showDiscard(Discard d) {

    }

}
