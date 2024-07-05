package com.siliconandsynapse.aclient.game;

import com.siliconandsynapse.ixcpp.common.Choice;

public interface GameActivity {

    public void runOnUiThread(Runnable run);
    public void showChoice(Choice c);

}
