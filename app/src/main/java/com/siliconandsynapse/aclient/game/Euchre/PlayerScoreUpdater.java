package com.siliconandsynapse.aclient.game.Euchre;

import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.widget.TextView;

public class PlayerScoreUpdater {


	private final EuchreFragment act;
	private final TextView scoreText;

	private int teamNS;
	private int teamEW;

	public PlayerScoreUpdater(EuchreFragment act, TextView scoreText) {
		this.act = act;
		this.scoreText = scoreText;

	}

	@SuppressLint("SetTextI18n")
    public void setScore(EuchrePlayer player, final int score) {

		EuchreFragment.SeatLocation loc = act.getSeat(player);

		if (loc == EuchreFragment.SeatLocation.NORTH)
			teamNS = score;

		if (loc == EuchreFragment.SeatLocation.EAST)
			teamEW = score;

		act.runOnUiThread(() -> scoreText.setText("score: " + teamNS + " to " + teamEW));

	}



}
