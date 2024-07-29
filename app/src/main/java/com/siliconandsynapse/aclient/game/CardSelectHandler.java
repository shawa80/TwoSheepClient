package com.siliconandsynapse.aclient.game;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.siliconandsynapse.aclient.gameModels.CardModel;
import com.siliconandsynapse.ixcpp.common.cards.Card;

public class CardSelectHandler implements OnClickListener {

	private final GameService service;

	private final String stackType;
	private final int pos;

	public CardSelectHandler(GameActivity act, GameService service, String stackType, int pos) {
		this.service = service;

		this.stackType = stackType;
		this.pos = pos;
	}


	@Override
	public void onClick(View v) {

		var card = service
				.getModel()
				.getPlayer(0)
				.get(stackType, pos)
				.getTop();

		if (card == null)
			return;

		var selectedCard = card.get();

		service.setCard(selectedCard);
		service.getCardBlock().sendNotice();
	}

}
