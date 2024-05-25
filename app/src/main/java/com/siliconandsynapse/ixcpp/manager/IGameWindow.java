package com.siliconandsynapse.ixcpp.manager;

import com.siliconandsynapse.ixcpp.gameInteraction.GameInstance;

public interface IGameWindow extends GameInstance{
	
	public String getGameName();
	public int getPlayerId();
	public void setPlayerId(int id);

}
