package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;

public class CreateGameCmd {

	private String type;
	public record CreateGameRequestObj(String type) {}

	public CreateGameCmd(String type) {
		this.type = type;
	}

	public void execute(IxAddress baseAddr, IxManager tunnel) {

        var addr = baseAddr.append("CreateGame");

        var gson = new Gson();
        var doc = gson.toJson(new CreateGameRequestObj(type));

		tunnel.sendDocument(addr, doc);

	}


}
