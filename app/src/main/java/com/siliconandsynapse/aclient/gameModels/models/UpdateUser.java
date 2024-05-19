package com.siliconandsynapse.aclient.gameModels.models;

import com.siliconandsynapse.aclient.gameModels.PlayerModel;

public interface UpdateUser {
	
	public void turnChanged(PlayerModel player);
	public void nameChanged(PlayerModel player, String name);
	public void scoreChanged(PlayerModel player, int score);
	public void descriptionChanged(PlayerModel player, String description);
}
