package com.siliconandsynapse.aclient.game.TwoSheep;

import java.util.Hashtable;

import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.game.CardAddress;
import com.siliconandsynapse.aclient.game.GameActivity;
import com.siliconandsynapse.aclient.game.GameService;
import com.siliconandsynapse.aclient.game.UpdateCardsListener;
import com.siliconandsynapse.aclient.gameModels.models.UpdateGame;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TwoSheepActivity extends Activity implements GameActivity {

	private GameService service;
	
	private ViewGroup table;
		
	private TextView myName;
	private TextView yourName;
		
	private TextView yourTurn;
	private TextView myTurn;

	private TextView myScore;
	private TextView yourScore;

	
	private final int PRIVATE_TOTAL = 4;
	private final int PUBLIC_TOTAL = 6;
	
	private ImageView[] yourPrivate = new ImageView[PRIVATE_TOTAL];	
	private ImageView[] yourPublic = new ImageView[PUBLIC_TOTAL];	
	private ImageView[] myPrivate = new ImageView[PRIVATE_TOTAL];
	private ImageView[] myPublic = new ImageView[PUBLIC_TOTAL];
	private ImageView yourCard;
	private ImageView myCard;
	
	private TextView currentMsg;
	
	private Hashtable<CardAddress, ImageView> cardsByAddress = new Hashtable<CardAddress, ImageView>();
	
	private String gameName;
	
	private UpdateGame updateGame;
	private UpdateCardsListener updateCardsListener;

	private TwoSheepPlayer updateUserNorth;
	private TwoSheepPlayer updateUserSouth;

	private PlayerTurnUpdater turnUpdater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
		
			//setContentView(R.layout.activity_game);
			setContentView(R.layout.activity_game2);
			
			Log.d("DebugPrint", "Game activity create");
			
			gameName = "1";
			
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
			    gameName = extras.getString("GAME_NAME");
			}
			
			table = (ViewGroup)this.findViewById(R.id.table);
			
			
			table.setBackgroundColor(Color.rgb(0x30, 0x70, 0x21));
			
			myName = (TextView)this.findViewById(R.id.myname);
			yourName = (TextView)this.findViewById(R.id.yourname);
			
			myTurn = (TextView)this.findViewById(R.id.myTurn);
			yourTurn = (TextView)this.findViewById(R.id.yourTurn);
	
			
			myScore = (TextView)this.findViewById(R.id.myScore);
			yourScore = (TextView)this.findViewById(R.id.yourScore);
		
			yourPrivate[0] = (ImageView)this.findViewById(R.id.yourPrivate1);
			yourPrivate[1] = (ImageView)this.findViewById(R.id.yourPrivate2);
			yourPrivate[2] = (ImageView)this.findViewById(R.id.yourPrivate3);
			yourPrivate[3] = (ImageView)this.findViewById(R.id.yourPrivate4);
			
			yourPublic[0] = (ImageView)this.findViewById(R.id.yourPublic1);
			yourPublic[1] = (ImageView)this.findViewById(R.id.yourPublic2);
			yourPublic[2] = (ImageView)this.findViewById(R.id.yourPublic3);
			yourPublic[3] = (ImageView)this.findViewById(R.id.yourPublic4);
			yourPublic[4] = (ImageView)this.findViewById(R.id.yourPublic5);
			yourPublic[5] = (ImageView)this.findViewById(R.id.yourPublic6);
			
			
			myPrivate[0] = (ImageView)this.findViewById(R.id.myPrivate1);
			myPrivate[1] = (ImageView)this.findViewById(R.id.myPrivate2);
			myPrivate[2] = (ImageView)this.findViewById(R.id.myPrivate3);
			myPrivate[3] = (ImageView)this.findViewById(R.id.myPrivate4);
			
			myPublic[0] = (ImageView)this.findViewById(R.id.myPublic1);
			myPublic[1] = (ImageView)this.findViewById(R.id.myPublic2);
			myPublic[2] = (ImageView)this.findViewById(R.id.myPublic3);
			myPublic[3] = (ImageView)this.findViewById(R.id.myPublic4);
			myPublic[4] = (ImageView)this.findViewById(R.id.myPublic5);
			myPublic[5] = (ImageView)this.findViewById(R.id.myPublic6);
			
			yourCard = (ImageView)this.findViewById(R.id.YourCard);
			myCard = (ImageView)this.findViewById(R.id.MyCard);
	
			
			currentMsg = (TextView)this.findViewById(R.id.currentMsg);
			

			
			mapCards();
			
			service = GameService.getService(gameName);
	
			updateGame = new UpdateGame() {
	
				@Override
				public void writeNotice(final String msg) {
					TwoSheepActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							currentMsg.setText(msg);
						}
						
					});	
				}
			};
			service.getModel().addListener(updateGame);
			
			
			updateCardsListener = new UpdateCardsListener(this, cardsByAddress, table);
			service.getModel().addListener(updateCardsListener);
			

			turnUpdater = new PlayerTurnUpdater(this);
			
			updateUserNorth = new TwoSheepPlayer(this, yourName, yourScore, yourPrivate, yourPublic, yourCard, turnUpdater);
			updateUserSouth = new TwoSheepPlayer(this, myName, myScore,  myPrivate, myPublic, myCard, turnUpdater);
			service.getModel().getPlayer(1).addListener(updateUserNorth);
			service.getModel().getPlayer(0).addListener(updateUserSouth);


			
			turnUpdater.add(updateUserNorth, yourTurn);
			turnUpdater.add(updateUserSouth, myTurn);
			
			TwoSheepUser user = new TwoSheepUser(this, updateUserSouth, service);
			
			service.start();
		
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		table.invalidate();
	}


	@Override
	protected void onPause() {
		
		super.onPause();
		Log.d("DebugPrint", "GameActivity pause");
		
	}
	
	@Override
	protected void onStop() {

		super.onStop();

		try {
		
			service.stop();
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		Log.d("DebugPrint", "GameActivity stop");

	}


	private void mapCards() {
		
		cardsByAddress.clear();
		
		//trick order depends on who went first...
		//we need to register our trick cards for each order		
		cardsByAddress.put(new CardAddress(1,"trick", 0, 0), yourCard);
		cardsByAddress.put(new CardAddress(0, "trick", 0, 0), myCard);
		
		
		//player, stack type, stack number, level
		for (int i = 0; i < PUBLIC_TOTAL; i++) {
			cardsByAddress.put(new CardAddress(1, "public", i, 1),  yourPublic[i]);	
			cardsByAddress.put(new CardAddress(1, "public", i, 0),  yourPublic[i]);
		
			cardsByAddress.put(new CardAddress(0, "public", i, 1),  myPublic[i]);
			cardsByAddress.put(new CardAddress(0, "public", i, 0),  myPublic[i]);
		}
		
		for (int i = 0; i < PRIVATE_TOTAL; i++) {
			cardsByAddress.put(new CardAddress(1, "private", i, 0),  yourPrivate[i]);	
			cardsByAddress.put(new CardAddress(0, "private", i, 0),  myPrivate[i]);
		}
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		
		return super.onOptionsItemSelected(item);
	}
	
		

}
