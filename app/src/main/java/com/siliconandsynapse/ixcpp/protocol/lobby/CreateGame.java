package com.siliconandsynapse.ixcpp.protocol.lobby;

import org.w3c.dom.*;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.ParseError;

import javax.xml.xpath.*;

public class CreateGame implements IxReciever
{
	private AcceptedAddresses events;
	private RoomModel model;
	private IxAddress baseAddr;
	private IxAddress addr;

	private XPathFactory factory;

	public CreateGame(IxAddress baseAddr, RoomModel model)
	{

		this.model = model;
		this.baseAddr = baseAddr;

		factory = XPathFactory.newInstance();
		addr = baseAddr.append("CreateGame");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

        var gson = new Gson();
        var x = gson.fromJson(doc, CreateGameObj.class);

		var id = Integer.parseInt(x.name());
		var roomType = x.type();

		model.addGame(new GameInfo(id, roomType), returnTunnel);
		var jg = new JoinGameCmd(x.name());
		var gameKey = key.getParent();
		jg.execute(gameKey, returnTunnel);
	}
	public String placeInThread()
	{
		return baseAddr.toString();
	}

	public AcceptedAddresses getEvents()
	{
		return events;
	}
}
