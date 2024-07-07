package com.siliconandsynapse.aclient.game;

import com.siliconandsynapse.ixcpp.common.Choice;
import com.siliconandsynapse.ixcpp.common.Discard;

public interface GameActivity {

    public void runOnUiThread(Runnable run);
    public void showChoice(Choice c);
    public void showDiscard(Discard d);

}
