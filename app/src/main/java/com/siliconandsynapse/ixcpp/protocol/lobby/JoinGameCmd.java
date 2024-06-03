package com.siliconandsynapse.ixcpp.protocol.lobby;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.ParseError;

public class JoinGameCmd {

	private String gameId;

	private DocumentBuilder documentBuilder;

	public JoinGameCmd(String gameId) {

		this.gameId = gameId;
	}


	public void execute(IxAddress baseAddr, IxManager tunnel) {

		IxAddress addr = null;

        addr = baseAddr.append("JoinGame");

        var gson = new Gson();
        var doc = gson.toJson(new JoinGameRequest(gameId));

		try {
			tunnel.sendDocument(addr, doc);
		} catch (Exception e) {
			return;
		}

	}

}
