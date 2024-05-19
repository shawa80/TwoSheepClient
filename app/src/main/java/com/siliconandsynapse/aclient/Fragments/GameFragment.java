package com.siliconandsynapse.aclient.Fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.siliconandsynapse.aclient.NetworkService;
import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.game.Euchre.EuchreActivity;
import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepActivity;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.protocol.lobby.CreateGameCmd;
import com.siliconandsynapse.net.ixtunnel.IxManager;

public class GameFragment extends Fragment {

	
	private RoomModel model;
	private GameListAdapter adapter;
	private Activity act;
	private NetworkService server;

	
	public GameFragment(Activity act, RoomModel model) {
		
		this.act = act;

		this.model = model;
		model.addListener(new RoomModel.RoomModelListener() {
			
			@Override
			public void playerRemoved(GameInfo game, String arg1) {}
			
			@Override
			public void playerAdded(GameInfo game, String arg1) {}
			
			@Override
			public void gameRemoved(GameInfo game) {

				removeGame(game);
			}
			
			@Override
			public void gameAdded(GameInfo game, IxManager arg2) {
				addGame(game);
			}
		});

	}
		

	public void setNetworkService(NetworkService server) {
		this.server = server;
	}
	
	
    @Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
			 
		
		Log.d("DebugPrint", "GameFragment start");

		
		adapter = new GameListAdapter(this.getActivity());
		        
        ListView list = (ListView)this.getActivity().findViewById(R.id.gameList);
        Button addGame = (Button)this.getActivity().findViewById(R.id.addGame);

        
        
        if (model != null)
        {
        	for (GameInfo game : model) {
        		
        		adapter.add(game);
        	}
        }
        
        list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				GameInfo game = adapter.getItem(position);

				Intent intent = null;
				
				if ("Two Sheep".equals(game.getName())) {
					intent = new Intent(GameFragment.this.getActivity(), TwoSheepActivity.class);
				} else {
					intent = new Intent(GameFragment.this.getActivity(), EuchreActivity.class);					
				}
				

				
				intent.putExtra("GAME_NAME", game.getId() + "");
				GameFragment.this.startActivity(intent);
			
				
			}
        	
        	
        });
        
        
        list.setAdapter(adapter);
        
        
        
        addGame.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
				//server.executeLobby(new CreateGameCmd("Two Sheep"));
	
				
				final ArrayList<String> names = new ArrayList<String>();
				names.clear();
				names.add("Two Sheep");
				names.add("Euchre");
				ArrayAdapter<String> namesAA = new ArrayAdapter<String> ( act, android.R.layout.simple_list_item_1, names );
				
				
				AlertDialog.Builder builder=new AlertDialog.Builder(act);
			    builder.setTitle("Dealers");
			    ListView list=new ListView(act);
			    list.setAdapter(namesAA);
			    builder.setView(list);
			    final AlertDialog dialog = builder.create();
			    
			    list.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
			    	@Override
			    	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			       
			           if(dialog.isShowing())
			               dialog.dismiss();
			           
			           String dealer = names.get(position);			           
			           server.executeLobby(new CreateGameCmd(dealer));
			        }
			    }); 
			    
			    dialog.show();
			}
        	
        });
	}
    
    

    
    
    
    private void addGame(final GameInfo game) {
    	    	
    	act.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (adapter != null)
					adapter.add(game);
			}
		});
    }
    
    private void removeGame(final GameInfo game) {
    	    	
    	act.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (adapter != null) {
										
					adapter.remove(game);
				}
			}
		});
    }
    

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.games, container, false);
        
        return rootView;
    }
	
	

	
	
	
}
