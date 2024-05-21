//package com.siliconandsynapse.aclient.game;
//
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//
//import com.siliconandsynapse.aclient.gameModels.CardModel;
//import com.siliconandsynapse.ixcpp.common.cards.Card;
//
//public class CardSelectHandler implements OnClickListener {
//
//	private GameService service;
//	private GameActivity act;
//
//	private String stackType;
//	private int pos;
//
//	public CardSelectHandler(GameActivity act, GameService service, String stackType, int pos) {
//		this.service = service;
//
//
//		this.act = act;
//		this.stackType = stackType;
//		this.pos = pos;
//	}
//
//
//	@Override
//	public void onClick(View v) {
//
//		Log.d("DebugPrint", "Card Clicked");
//
//		CardModel card = service.getModel().getPlayer(0).get(stackType, pos).getTop();
//
//		if (card == null)
//			return;
//
//		Card selectedCard = card.get();
//
//		service.setCard(selectedCard);
//		service.getCardBlock().sendNotice();
//	}
//
//}
