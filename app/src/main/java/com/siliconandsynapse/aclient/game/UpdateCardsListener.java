package com.siliconandsynapse.aclient.game;

import java.util.Hashtable;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.widget.ImageView;

import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.game.TwoSheepPNP.SurprisableImageView;
import com.siliconandsynapse.aclient.gameModels.PlayerModel;
import com.siliconandsynapse.aclient.gameModels.models.CardUpdateEvent;
import com.siliconandsynapse.aclient.gameModels.models.UpdateCards;
import com.siliconandsynapse.ixcpp.common.cards.Card;

public class UpdateCardsListener implements UpdateCards {

	private Hashtable<CardAddress, ImageView> cardsByAddress;
	private Activity act;
	private View table;
	private PlayerModel[] horzPlayers;
	
	public UpdateCardsListener(Activity act,
							   Hashtable<CardAddress, ImageView> cardsByAddress,
							   View table,
								PlayerModel ... horizontal) {
		this.horzPlayers = horizontal;
		this.cardsByAddress = cardsByAddress;
		this.act = act;
		this.table = table;
	}
	
	public UpdateCardsListener(Activity act,
							   Hashtable<CardAddress, ImageView> cardsByAddress,
							   View table) {
		this.horzPlayers = null;
		this.cardsByAddress = cardsByAddress;
		this.act = act;
		this.table = table;
	}

	@Override
	public void cardChanged(CardUpdateEvent change) {

		var addr = new CardAddress(change);
		final var imageView = cardsByAddress.get(addr);
		
		final var level = change.position;
		final var card = change.card;  //copy the card out, else we have a race condition, since change is reused.
		final var playerId = change.player;
		
		if (imageView == null)
			return;
						
		act.runOnUiThread(() -> {

            var d = imageView.getDrawable();
            var layer = (LayerDrawable)d;

            var isHorz = isPlayerHorz(playerId);
            var image = Images.getPokerImage(act, card, isHorz);

            if (level == 1)
                layer.setDrawableByLayerId(R.id.top, image);
            else
                layer.setDrawableByLayerId(R.id.bottom, image);

        });

		
	}

	@Override
	public void cardRemoved(CardUpdateEvent change) {
		
		CardAddress addr = new CardAddress(change);
		final ImageView iv = cardsByAddress.get(addr);
		
		final int level = change.position;
		final int playerId = change.player;
		
		if (iv == null)
			return;
		
		
		act.runOnUiThread(() -> {

			var image = act.getResources().getDrawable(R.drawable.nocard2);

			if (iv instanceof SurprisableImageView siv) {
				siv.setSurpressedImage(Images.getCardBack(act));
				siv.setRemovedImage(level, image);

			} else {

				var d = iv.getDrawable();
				var layer = (LayerDrawable) d;

				if (level == 1)
					layer.setDrawableByLayerId(R.id.top, image);
				else {
					layer.setDrawableByLayerId(R.id.bottom, image);
				}
			}

        });

	
	}

	@Override
	public void cardAdded(final CardUpdateEvent change) {
		
		CardAddress addr = new CardAddress(change);
		final ImageView iv = cardsByAddress.get(addr);
		
		final Card card = change.card;  //copy the card out, else we have a race condition, since change is reused.
		final int level = change.position;
		final int playerId = change.player;
		
		if (iv == null)
			return;
						
		act.runOnUiThread(() -> {

            boolean isHorz = isPlayerHorz(playerId);
            Drawable image = Images.getPokerImage(act, card, isHorz);

			if (iv instanceof SurprisableImageView siv) {
				siv.setSurpressedImage(Images.getCardBack(act));
				siv.setImage(level, image);

			} else {

				Drawable d = iv.getDrawable();
				LayerDrawable layer = (LayerDrawable) d;

				if (level == 1)
					layer.setDrawableByLayerId(R.id.top, image);
				else
					layer.setDrawableByLayerId(R.id.bottom, image);
			}
        });
		
	}

	@Override
	public void changeFinished() {

		act.runOnUiThread(() -> table.invalidate());
	}
	
	private boolean isPlayerHorz(int playerId) {
	
		if (horzPlayers == null)
			return false;
		
		for (PlayerModel p : this.horzPlayers) {
			if (p.getId() == playerId)
				return true;
		}
		
		return false;
	}
}
