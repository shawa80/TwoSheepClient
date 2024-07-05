package com.siliconandsynapse.aclient.game.TwoSheep;

import com.siliconandsynapse.aclient.game.CardSelectHandler;
import com.siliconandsynapse.aclient.game.GameActivity;
import com.siliconandsynapse.aclient.game.GameService;
import com.siliconandsynapse.aclient.gameModels.PlayerModel;
import com.siliconandsynapse.aclient.gameModels.models.ChoiceRequest;
import com.siliconandsynapse.aclient.gameModels.models.UpdateUser;
import com.siliconandsynapse.ixcpp.common.Choice;

import java.util.Arrays;
//import com.siliconandsynapse.aclient.game.GameService;

public class TwoSheepUser implements ChoiceRequest {

	private GameActivity act;
	private TwoSheepPlayer player;

	public TwoSheepUser(GameActivity act, TwoSheepPlayer player, GameService service) {
		this.act = act;
		this.player = player;


		for (int i = 0; i < player.publicCards.length; i++) {
			player.publicCards[i].setOnClickListener(new CardSelectHandler(act, service, "public", i));
		}

		for (int i = 0; i < player.privateCards.length; i++) {
			player.privateCards[i].setOnClickListener(new CardSelectHandler(act, service, "private", i));
		}


	}


	@Override
	public void displayChoiceDialog(Choice c) {
		act.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				act.showChoice(c);
			}
		});
	}
}
