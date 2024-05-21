//package com.siliconandsynapse.aclient.game.Euchre;
//
//import com.siliconandsynapse.aclient.game.CardSelectHandler;
//import com.siliconandsynapse.aclient.game.GameService;
//import com.siliconandsynapse.aclient.gameModels.models.ChoiceRequest;
//import com.siliconandsynapse.aclient.gameModels.models.DiscardRequest;
//import com.siliconandsynapse.ixcpp.common.Choice;
//import com.siliconandsynapse.ixcpp.common.Discard;
//
//public class EuchreUser implements DiscardRequest, ChoiceRequest {
//
//	private EuchreActivity act;
//	private EuchrePlayer player;
//
//	public EuchreUser(EuchreActivity act, EuchrePlayer player, GameService service) {
//		this.act = act;
//		this.player = player;
//
//
//		for (int i = 0; i < player.cards.length; i++) {  // take into account the extra card we might get as dealer
//			player.cards[i].setOnClickListener(new CardSelectHandler(act, service, "private", i));
//		}
//	}
//
//
//
//	@Override
//	public void displayChoiceDialog(final Choice c) {
//
//		act.runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				act.showChoice(c);
//			}
//		});
//
//	}
//
//	@Override
//	public void displayDiscardDialog(final Discard d) {
//
//		act.runOnUiThread(new Runnable() {
//
//			@Override
//			public void run() {
//				act.showDiscard(d);
//			}
//		});
//
//	}
//
//}
