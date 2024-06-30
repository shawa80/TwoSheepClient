package com.siliconandsynapse.aclient.lobbyModels;

import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.net.ixtunnel.IxManager;

public interface RoomModelListener {

    public void gameAdded(Game game);
    public void gameRemoved(int gameId);
    public void playerAdded(Game game, Player player);
    public void playerRemoved(Game game, int seat);
}
