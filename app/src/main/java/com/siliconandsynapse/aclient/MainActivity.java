package com.siliconandsynapse.aclient;

import com.siliconandsynapse.aclient.game.Images;
import com.siliconandsynapse.ixcpp.ui.MessageReceiverModel;
import com.siliconandsynapse.ixcpp.util.Mutex;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.viewpager.widget.ViewPager;

public class MainActivity extends Activity {

	private ViewPager mViewPager;
    //private ScreenSlidePagerAdapter mTabsAdapter;
	
	//private NetworkService service;
	
	//private RoomModel rooms;
	//private LobbyUserList lobbyModel;
	
	public MainActivity() {

	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d("DebugPrint", "Starting activity");

		Images.loadCache(this);
		
		setContentView(R.layout.activity_main);

		//final ActionBar actionBar = getActionBar();

		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		

	    mViewPager = (ViewPager) findViewById(R.id.pager);
		//mTabsAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		
		// Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {

			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {}
	    };

	    mViewPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {
	                    getActionBar().setSelectedNavigationItem(position);
	                }
	            });
	    
	    //actionBar.addTab(actionBar.newTab().setText("Chat").setTabListener(tabListener));
	    //actionBar.addTab(actionBar.newTab().setText("Games").setTabListener(tabListener));
	    
		//DefaultRoomModel roomModel = new DefaultRoomModel();
		
		//final ChatFragment chatWindow = new ChatFragment(this);
        //final GameFragment gameWindow = new GameFragment(this, roomModel);
		
        //mTabsAdapter.addFragment(chatWindow);
        //mTabsAdapter.addFragment(gameWindow);
		
		//mViewPager.setAdapter(mTabsAdapter);


	
		
		//DefaultLobbyModel lobbyModel = new DefaultLobbyModel();
		//chatWindow.setLobbyModel(lobbyModel);
		
//		service = new NetworkService(this, lobbyModel, roomModel);
//		service.addOnConnectListener(new OnConnectListener() {
//
//			@Override
//			public void connected(NetworkService service) {
//				chatWindow.addMessage("Network", "Connected");
//				chatWindow.setNetworkService(service);
//				gameWindow.setNetworkService(service);
//			}
//
//			@Override
//			public void ModelsCreated(
//					MessageReceiverModel receiver) {
//
//				chatWindow.setReceiverModel(receiver);
//
//			}
//
//
//
//			@Override
//			public void failed(NetworkService service, String message) {
//				chatWindow.addMessage("Network", message);
//
//			}
//
//
//		});
//		service.start();
	}

	
	private Mutex logonBlock = new Mutex();;
	//private Credentials creds = null;
	
//	public Credentials getUserPass() {
//
//		Intent intent = new Intent(this, LoginActivity.class);
//		//intent.putExtra("GAME_NAME", gameName);
//		this.startActivityForResult(intent, 9);
//
//		logonBlock.waitFor();	//TODO make interruptable???
//
//		return creds;
//	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		

	    if (requestCode == 9) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {

	        	String user = data.getStringExtra("user");
	        	String pass = data.getStringExtra("pass");
	        
	        	//creds = new Credentials(user, pass);
	        }
	    }
	    
	    logonBlock.sendNotice();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
