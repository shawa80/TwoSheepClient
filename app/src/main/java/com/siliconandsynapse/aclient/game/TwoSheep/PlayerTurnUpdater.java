package com.siliconandsynapse.aclient.game.TwoSheep;

import java.util.Hashtable;

import android.app.Activity;
import android.widget.TextView;

//import com.siliconandsynapse.aclient.game.Euchre.EuchrePlayer;

public class PlayerTurnUpdater {

	private Activity act;
	private Hashtable<TwoSheepPlayer, TextView> mapping = new Hashtable<TwoSheepPlayer, TextView>();

	public PlayerTurnUpdater(Activity act) {
		this.act = act;

	}


	public void add(TwoSheepPlayer player, TextView indicator) {

		if (!mapping.containsKey(player))
			mapping.put(player, indicator);
	}

	public void indicate(final TwoSheepPlayer player) {

		act.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				for (TextView views : mapping.values()) {
					views.setText("");
				}

				if (mapping.containsKey(player)) {
					mapping.get(player).setText("*");
				}
			}
		});
	}


}
