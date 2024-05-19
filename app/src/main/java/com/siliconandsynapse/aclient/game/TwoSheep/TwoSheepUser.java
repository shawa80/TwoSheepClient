package com.siliconandsynapse.aclient.game.TwoSheep;

import com.siliconandsynapse.aclient.game.CardSelectHandler;
import com.siliconandsynapse.aclient.game.GameService;

public class TwoSheepUser {

	private TwoSheepActivity act;
	private TwoSheepPlayer player;
	
	public TwoSheepUser(TwoSheepActivity act, TwoSheepPlayer player, GameService service) {
		this.act = act;
		this.player = player;
		
		for (int i = 0; i < player.publicCards.length; i++) {
			player.publicCards[i].setOnClickListener(new CardSelectHandler(act, service, "public", i));			
		}
		
		for (int i = 0; i < player.privateCards.length; i++) {
			player.privateCards[i].setOnClickListener(new CardSelectHandler(act, service, "private", i));			
		}
		
	
	}
	


}
