package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;

public class SetName
{

    public record SetNameDto(String name) {}

    public void execute(IxAddress baseAddr, IxManager tunnel, String name) {

        var addr = baseAddr.append("SetName");

        var gson = new Gson();
        var doc = gson.toJson(new SetNameDto(name));

        tunnel.sendDocument(addr, doc);

    }
}

