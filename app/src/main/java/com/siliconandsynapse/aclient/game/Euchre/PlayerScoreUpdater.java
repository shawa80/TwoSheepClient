package com.siliconandsynapse.aclient.game.Euchre;

import java.util.Hashtable;

import android.widget.TextView;

public class PlayerScoreUpdater {


	private EuchreFragment act;
	private TextView scoreText;

	private int teamNS;
	private int teamEW;

	public PlayerScoreUpdater(EuchreFragment act, TextView scoreText) {
		this.act = act;
		this.scoreText = scoreText;

	}

	public void setScore(EuchrePlayer player, final int score) {

		EuchreFragment.SeatLocation loc = act.getSeat(player);

		if (loc == EuchreFragment.SeatLocation.NORTH)
			teamNS = score;

		if (loc == EuchreFragment.SeatLocation.EAST)
			teamEW = score;



		act.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				scoreText.setText(teamNS + " to " + teamEW);
			}
		});

	}



}
