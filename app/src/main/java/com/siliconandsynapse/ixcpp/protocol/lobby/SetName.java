package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;

public class SetName
{

    public SetName()
    {

    }


    public void execute(IxAddress baseAddr, IxManager tunnel, String name) {

        var addr = baseAddr.append("SetName");

        var gson = new Gson();
        var doc = gson.toJson(new SetNameObj(name));

        try {
            tunnel.sendDocument(addr, doc);
        } catch (Exception e) {
            return;
        }
    }
}

