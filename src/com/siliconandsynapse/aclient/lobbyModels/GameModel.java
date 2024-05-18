package com.siliconandsynapse.aclient.lobbyModels;

import android.content.Intent;

import com.siliconandsynapse.aclient.MainActivity;
import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepActivity;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInstance;
import com.siliconandsynapse.net.ixtunnel.IxManager;

public class GameModel implements GameController {

	
	private MainActivity act;
	
	public GameModel(MainActivity act) {
		this.act = act;
	}
	
	
	@Override
	public void quitGame(GameInstance arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startGame(IxManager arg0, String gameName, String gametype) {
		
		//Intent intent = new Intent(act, TwoSheepActivity.class);
		//intent.putExtra("GAME_NAME", gameName);
		
		//act.startActivity(intent);
		
	}

}
