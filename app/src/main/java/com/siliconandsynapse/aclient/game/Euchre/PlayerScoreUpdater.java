package com.siliconandsynapse.aclient.game.Euchre;

import java.util.Hashtable;

import android.widget.TextView;

public class PlayerScoreUpdater {


	private EuchreActivity act;
	private TextView scoreText;

	private int teamNS;
	private int teamEW;

	public PlayerScoreUpdater(EuchreActivity act, TextView scoreText) {
		this.act = act;
		this.scoreText = scoreText;

	}

	public void setScore(EuchrePlayer player, final int score) {

		EuchreActivity.SeatLocation loc = act.getSeat(player);

		if (loc == EuchreActivity.SeatLocation.NORTH)
			teamNS = score;

		if (loc == EuchreActivity.SeatLocation.EAST)
			teamEW = score;



		act.runOnUiThread(new Runnable() {

			@Override
			public void run() {

				scoreText.setText(teamNS + " to " + teamEW);
			}
		});

	}



}
