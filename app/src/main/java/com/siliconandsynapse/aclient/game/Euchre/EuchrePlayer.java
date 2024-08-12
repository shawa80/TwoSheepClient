package com.siliconandsynapse.aclient.game.Euchre;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.gameModels.PlayerModel;
import com.siliconandsynapse.aclient.gameModels.models.UpdateUser;

public class EuchrePlayer implements UpdateUser {

	private EuchreFragment act;

	private TextView nameGui;
	private TextView descGui;
	private PlayerTurnUpdater turnGui;
	public ImageView[] cards;

	public ImageView trick;

	private PlayerScoreUpdater scoreGui;

	public EuchrePlayer(EuchreFragment act, TextView nameGui, TextView descGui, ImageView[] cards,
			ImageView trick, PlayerTurnUpdater turnGui, PlayerScoreUpdater scoreGui) {
		this.act = act;
		this.nameGui = nameGui;
		this.descGui = descGui;
		this.cards = cards;
		this.trick = trick;
		this.turnGui = turnGui;
		this.scoreGui = scoreGui;

		for (int i = 0; i < cards.length; i++) {
			init(cards[i]);
		}

		init(trick);
	}


	private void init(ImageView card)
	{
		var d = card.getDrawable();
		var layer = (LayerDrawable)d;
		layer.setDrawableByLayerId(R.id.top, act.getResources().getDrawable(R.drawable.nocard2));
		layer.setDrawableByLayerId(R.id.bottom, act.getResources().getDrawable(R.drawable.nocard2));
	}


	@Override
	public void turnChanged(PlayerModel player) {

		turnGui.indicate(this);
	}

	@Override
	public void nameChanged(PlayerModel player, final String name) {

		act.runOnUiThread(() -> nameGui.setText(name));

	}

	@Override
	public void scoreChanged(PlayerModel player, int score) {

	}

	@Override
	public void wealthChanged(PlayerModel player, int wealth) {
		this.scoreGui.setScore(this, wealth);
	}

	@Override
	public void descriptionChanged(PlayerModel player, final String description) {

		act.runOnUiThread(() -> descGui.setText(description));

	}

}
