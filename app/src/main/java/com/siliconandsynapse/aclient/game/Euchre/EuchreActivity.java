package com.siliconandsynapse.aclient.game.Euchre;

import java.util.ArrayList;
import java.util.Hashtable;

import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.game.CardAddress;
import com.siliconandsynapse.aclient.game.GameActivity;
import com.siliconandsynapse.aclient.game.GameService;
import com.siliconandsynapse.aclient.game.UpdateCardsListener;
import com.siliconandsynapse.aclient.gameModels.models.UpdateGame;
import com.siliconandsynapse.ixcpp.common.Choice;
import com.siliconandsynapse.ixcpp.common.ChoiceResponse;
import com.siliconandsynapse.ixcpp.common.Discard;
import com.siliconandsynapse.ixcpp.common.cards.Card;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//public class EuchreActivity extends Activity implements GameActivity {
//
//	private GameService service;
//
//	private ViewGroup table;
//
//
//	public enum SeatLocation {
//		NORTH,
//		SOUTH,
//		EAST,
//		WEST
//	}
//
//	private final int PRIVATE_TOTAL = 6;
//
//	private ImageView[] north = new ImageView[PRIVATE_TOTAL];
//	private ImageView[] east = new ImageView[PRIVATE_TOTAL];
//	private ImageView[] west = new ImageView[PRIVATE_TOTAL];
//	private ImageView[] south = new ImageView[PRIVATE_TOTAL];
//
//	private ImageView trickNorth;
//	private ImageView trickEast;
//	private ImageView trickWest;
//	private ImageView trickSouth;
//
//	private TextView northName;
//	private TextView westName;
//	private TextView eastName;
//	private TextView southName;
//
//	private TextView score;
//	private PlayerScoreUpdater scoreUpdater;
//
//	private TextView northTurn;
//	private TextView westTurn;
//	private TextView eastTurn;
//	private TextView southTurn;
//
//	private TextView northDesc;
//	private TextView westDesc;
//	private TextView eastDesc;
//	private TextView southDesc;
//
//	private TextView currentMsg;
//
//	private Hashtable<CardAddress, ImageView> cardsByAddress = new Hashtable<CardAddress, ImageView>();
//
//	private View choice;
//
//	private int gameId;
//
//
//	private UpdateGame updateGame;
//	private UpdateCardsListener updateCardsListener;
//
//	private EuchrePlayer updateUserNorth;
//	private EuchrePlayer updateUserEast;
//	private EuchrePlayer updateUserWest;
//	private EuchrePlayer updateUserSouth;
//
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		try {
//
//			setContentView(R.layout.euchre_game2);
//
//			Log.d("DebugPrint", "Game activity create");
//
//			gameId = 1;
//
//			Bundle extras = getIntent().getExtras();
//			if (extras != null) {
//				gameId = extras.getInt("GAME_NAME");
//			}
//
//			table = (ViewGroup)this.findViewById(R.id.table);
//
//
//			table.setBackgroundColor(Color.rgb(0x30, 0x70, 0x21));
//
//			score = (TextView)this.findViewById(R.id.score);
//			scoreUpdater = new PlayerScoreUpdater(this, score);
//
//
//			northName = (TextView)this.findViewById(R.id.northName);
//			eastName = (TextView)this.findViewById(R.id.eastName);
//			westName = (TextView)this.findViewById(R.id.westName);
//			southName = (TextView)this.findViewById(R.id.southName);
//
//
//			northTurn = (TextView)this.findViewById(R.id.turnNorth);
//			eastTurn = (TextView)this.findViewById(R.id.turnEast);
//			westTurn = (TextView)this.findViewById(R.id.turnWest);
//			southTurn = (TextView)this.findViewById(R.id.turnSouth);
//
//			northDesc = (TextView)this.findViewById(R.id.descNorth);
//			eastDesc = (TextView)this.findViewById(R.id.descEast);
//			westDesc = (TextView)this.findViewById(R.id.descWest);
//			southDesc = (TextView)this.findViewById(R.id.descSouth);
//
//			south[0] = (ImageView)this.findViewById(R.id.south1);
//			south[1] = (ImageView)this.findViewById(R.id.south2);
//			south[2] = (ImageView)this.findViewById(R.id.south3);
//			south[3] = (ImageView)this.findViewById(R.id.south4);
//			south[4] = (ImageView)this.findViewById(R.id.south5);
//			south[5] = (ImageView)this.findViewById(R.id.south6);
//
//			north[0] = (ImageView)this.findViewById(R.id.north1);
//			north[1] = (ImageView)this.findViewById(R.id.north2);
//			north[2] = (ImageView)this.findViewById(R.id.north3);
//			north[3] = (ImageView)this.findViewById(R.id.north4);
//			north[4] = (ImageView)this.findViewById(R.id.north5);
//			north[5] = (ImageView)this.findViewById(R.id.north6);
//
//			east[0] = (ImageView)this.findViewById(R.id.east1);
//			east[1] = (ImageView)this.findViewById(R.id.east2);
//			east[2] = (ImageView)this.findViewById(R.id.east3);
//			east[3] = (ImageView)this.findViewById(R.id.east4);
//			east[4] = (ImageView)this.findViewById(R.id.east5);
//			east[5] = (ImageView)this.findViewById(R.id.east6);
//
//			west[0] = (ImageView)this.findViewById(R.id.west1);
//			west[1] = (ImageView)this.findViewById(R.id.west2);
//			west[2] = (ImageView)this.findViewById(R.id.west3);
//			west[3] = (ImageView)this.findViewById(R.id.west4);
//			west[4] = (ImageView)this.findViewById(R.id.west5);
//			west[5] = (ImageView)this.findViewById(R.id.west6);
//
//
//			trickSouth = (ImageView)this.findViewById(R.id.trickSouth);
//			trickNorth = (ImageView)this.findViewById(R.id.trickNorth);
//			trickEast = (ImageView)this.findViewById(R.id.trickEast);
//			trickWest = (ImageView)this.findViewById(R.id.trickWest);
//
//
//			currentMsg = (TextView)this.findViewById(R.id.currentMsg);
//			choice = (View)this.findViewById(R.id.choice);
//
//			mapCards();
//
//			service = GameService.getService(this, gameId);
//
//			updateGame = new UpdateGame() {
//
//				@Override
//				public void writeNotice(final String msg) {
//					EuchreActivity.this.runOnUiThread(new Runnable() {
//
//						@Override
//						public void run() {
//
//							currentMsg.setText(msg);
//							//gameChat.append(msg + "\n");
//						}
//
//					});
//				}
//			};
//			service.getModel().addListener(updateGame);
//
//
//			PlayerTurnUpdater turn = new PlayerTurnUpdater(this);
//
//
//			updateUserNorth = new EuchrePlayer(this, northName, northDesc, north, trickNorth, turn, scoreUpdater);
//			updateUserEast = new EuchrePlayer(this, eastName, eastDesc, east, trickEast, turn, scoreUpdater);
//			updateUserWest = new EuchrePlayer(this, westName, westDesc, west, trickWest, turn, scoreUpdater);
//			updateUserSouth = new EuchrePlayer(this, southName, southDesc, south, trickSouth, turn, scoreUpdater);
//
//			turn.add(updateUserNorth, northTurn);
//			turn.add(updateUserEast, eastTurn);
//			turn.add(updateUserSouth, southTurn);
//			turn.add(updateUserWest, westTurn);
//
//			service.getModel().getPlayer(0).addListener(updateUserSouth);
//			service.getModel().getPlayer(1).addListener(updateUserWest);
//			service.getModel().getPlayer(2).addListener(updateUserNorth);
//			service.getModel().getPlayer(3).addListener(updateUserEast);
//
//			updateCardsListener = new UpdateCardsListener(this, cardsByAddress, table,
//					service.getModel().getPlayer(1),
//					service.getModel().getPlayer(3));
//			service.getModel().addListener(updateCardsListener);
//
//
//			EuchreUser user = new EuchreUser(this, updateUserSouth, service);
//			service.getModel().addChoiceListener(user);
//			service.getModel().addDiscardListener(user);
//
//			service.start();
//
//		} catch (Exception ex) {
//			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
//		}
//
//		table.invalidate();
//
//	}
//
//
//	public SeatLocation getSeat(EuchrePlayer player) {
//
//		if (player == updateUserNorth)
//			return SeatLocation.NORTH;
//
//		if (player == updateUserSouth)
//			return SeatLocation.SOUTH;
//
//		if (player == updateUserEast)
//			return SeatLocation.EAST;
//
//		if (player == updateUserWest)
//			return SeatLocation.WEST;
//
//		return SeatLocation.NORTH;
//
//	}
//
//	@Override
//	protected void onStart() {
//		super.onStart();
//		hideChoice();
//	}
//
//
//	@Override
//	protected void onPause() {
//
//		super.onPause();
//		Log.d("DebugPrint", "GameActivity pause");
//
//	}
//
//
//
//
//
//	private void mapCards() {
//
//		cardsByAddress.clear();
//
//		//trick order depends on who went first...
//		//we need to register our trick cards for each order
//
//		cardsByAddress.put(new CardAddress(0, "trick", 0, 0), trickSouth);
//		cardsByAddress.put(new CardAddress(1, "trick", 0, 0), trickWest);
//		cardsByAddress.put(new CardAddress(2, "trick", 0, 0), trickNorth);
//		cardsByAddress.put(new CardAddress(3, "trick", 0, 0), trickEast);
//
//
//		//player, stack type, stack number, level
//		for (int i = 0; i < PRIVATE_TOTAL; i++) {
//
//			cardsByAddress.put(new CardAddress(0, "private", i, 0),  south[i]);
//			cardsByAddress.put(new CardAddress(1, "private", i, 0),  west[i]);
//			cardsByAddress.put(new CardAddress(2, "private", i, 0),  north[i]);
//			cardsByAddress.put(new CardAddress(3, "private", i, 0),  east[i]);
//
//		}
//
//	}
//
//
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.game, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		//int id = item.getItemId();
//
//		return super.onOptionsItemSelected(item);
//	}
//
//
//	private void hideChoice() {
//		choice.setVisibility(View.GONE);
//
//	}
//
//	public void showChoice(final Choice c) {
//
//		TextView text = (TextView)this.findViewById(R.id.choiceMessage);
//
//		text.setText(c.getQuestion());
//
//		final ArrayList<String> names = new ArrayList<String>();
//		names.clear();
//
//		for (int i = 0; i < c.getCount(); i++) {
//			names.add(c.getAnswerAt(i).getValue());
//		}
//
//		ArrayAdapter<String> namesAA = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, names );
//	    ListView choiceList = (ListView)this.findViewById(R.id.choiceOptions);
//	    choiceList.setAdapter(namesAA);
//
//
//	    choiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//	    	@Override
//	    	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//
//	    		EuchreActivity.this.hideChoice();
//
//
//	           service.setChoiceResponse(new ChoiceResponse(c.getAnswerAt(position)));
//	           service.getChoiceBlock().sendNotice();
//	        }
//	    });
//
//	    choice.setVisibility(View.VISIBLE);
//	    choice.bringToFront();
//
//		table.invalidate();
//	}
//
//
//	public void showDiscard(final Discard d) {
//
//		TextView text = (TextView)this.findViewById(R.id.choiceMessage);
//
//		text.setText(d.getMessage());
//
//		final ArrayList<String> names = new ArrayList<String>();
//		names.clear();
//
//		for (int i = 0; i < d.getCount(); i++) {
//			Card c = d.getCardAt(i);
//
//			names.add(c.getType() + " of " + c.getSuit());
//		}
//
//		ArrayAdapter<String> namesAA = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, names );
//	    ListView choiceList = (ListView)this.findViewById(R.id.choiceOptions);
//	    choiceList.setAdapter(namesAA);
//
//
//	    choiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//	    	@Override
//	    	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//
//	    		EuchreActivity.this.hideChoice();
//
//	    		Discard response = new Discard();
//	    		response.addCard(d.getCardAt(position));
//	           service.setDiscardResponse(response);
//	           service.getDiscardBlock().sendNotice();
//	        }
//	    });
//
//	    choice.setVisibility(View.VISIBLE);
//	    choice.bringToFront();
//
//		table.invalidate();
//	}
//
//	@Override
//	protected void onStop() {
//
//		super.onStop();
//
//		new Thread(() -> {
//			service.stop();
//		}).start();
//
//		this.finish();
//	}
//}
