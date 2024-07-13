package com.siliconandsynapse.aclient.game.TwoSheep;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.siliconandsynapse.aclient.R;
import com.siliconandsynapse.aclient.gameModels.PlayerModel;
import com.siliconandsynapse.aclient.gameModels.models.UpdateUser;

public class TwoSheepPlayer implements UpdateUser {

	private Activity act;

	private TextView scoreGui;
	private TextView nameGui;
	private TextView descGui;
	public ImageView[] privateCards;
	public ImageView[] publicCards;

	public ImageView trick;

	private PlayerTurnUpdater turn;

	public TwoSheepPlayer(Activity act, TextView nameGui, TextView scoreGui, ImageView[] privateCards,
						  ImageView[] publicCards, ImageView trick, PlayerTurnUpdater turn,
						  TextView descGui) {

		if (privateCards == null)
			privateCards = new ImageView[0];

		if (publicCards == null)
			publicCards = new ImageView[0];

		this.act = act;
		this.nameGui = nameGui;
		this.scoreGui = scoreGui;
		this.descGui = descGui;
		this.privateCards = privateCards;
		this.publicCards = publicCards;
		this.trick = trick;
		this.turn = turn;


		for (int i = 0; i < privateCards.length; i++) {
			init(privateCards[i]);
		}


		for (int i = 0; i < publicCards.length; i++) {
			init(publicCards[i]);
		}

		init(trick);
	}


	private void init(ImageView card)
	{
		Drawable d = card.getDrawable();
		LayerDrawable layer = (LayerDrawable)d;
		layer.setDrawableByLayerId(R.id.top, act.getResources().getDrawable(R.drawable.nocard2));
		layer.setDrawableByLayerId(R.id.bottom, act.getResources().getDrawable(R.drawable.nocard2));

	}


	@Override
	public void turnChanged(PlayerModel player) {

		turn.indicate(this);
	}

	@Override
	public void nameChanged(PlayerModel player, final String name) {

		act.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (nameGui != null)
					nameGui.setText(name);
			}
		});

	}

	@Override
	public void scoreChanged(PlayerModel player, final int score) {

		act.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (scoreGui != null)
					scoreGui.setText("" + score);
			}
		});


	}

	@Override
	public void descriptionChanged(PlayerModel player, final String description) {

		if (descGui == null)
			return;
		act.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (descGui != null)
					descGui.setText(description);
			}
		});

	}

}
