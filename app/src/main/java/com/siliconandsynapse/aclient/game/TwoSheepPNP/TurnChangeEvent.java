package com.siliconandsynapse.aclient.game.TwoSheepPNP;

import com.siliconandsynapse.aclient.gameModels.PlayerModel;
import com.siliconandsynapse.aclient.gameModels.models.UpdateUser;

public  interface TurnChangeEvent extends UpdateUser {

    void turnChanged(PlayerModel player);
    default void nameChanged(PlayerModel player, String name) {}
    default void scoreChanged(PlayerModel player, int score){}
    default void wealthChanged(PlayerModel player, int wealth){}
    default void descriptionChanged(PlayerModel player, String description){}
}
