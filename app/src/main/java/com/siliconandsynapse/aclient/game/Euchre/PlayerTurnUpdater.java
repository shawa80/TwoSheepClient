package com.siliconandsynapse.aclient.game.Euchre;

import java.util.Hashtable;

import android.widget.TextView;

public class PlayerTurnUpdater {

	private EuchreFragment act;
	private Hashtable<EuchrePlayer, TextView> mapping = new Hashtable<EuchrePlayer, TextView>();

	public PlayerTurnUpdater(EuchreFragment act) {
		this.act = act;

	}


	public void add(EuchrePlayer player, TextView indicator) {

		if (!mapping.containsKey(player))
			mapping.put(player, indicator);
	}

	public void indicate(final EuchrePlayer player) {

		act.runOnUiThread(() -> {
            for (TextView views : mapping.values()) {
                views.setText("");
            }

            if (mapping.containsKey(player)) {
                mapping.get(player).setText("*");
            }
        });
	}

}
