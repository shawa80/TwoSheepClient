package com.siliconandsynapse.ixcpp.protocol;

import android.widget.TextView;

import com.siliconandsynapse.aclient.MainActivity;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;

public class Debug implements IxReciever {

    private AcceptedAddresses events;
    private MainActivity act;

    public Debug(MainActivity act) {
        super();
        this.act = act;
    }

    public void accept(IxAddress key, IxManager returnTunnel, String doc) {
        act.log(key.getFullPath());
        act.log(doc);
    }

    public String placeInThread() {
        return null;
    }

    public AcceptedAddresses getEvents() {
        return events;
    }
}
