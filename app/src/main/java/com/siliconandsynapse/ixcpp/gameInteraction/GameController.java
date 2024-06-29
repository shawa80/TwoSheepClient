package com.siliconandsynapse.ixcpp.gameInteraction;

import com.siliconandsynapse.net.ixtunnel.IxManager;

public interface GameController
{
	public void startGame(IxManager returnTunnel, int gameId, String gameType);
	public void quitGame(GameInstance game);
}
