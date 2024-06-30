package com.siliconandsynapse.ixcpp.protocol.lobby;

import org.w3c.dom.*;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.ParseError;

import javax.xml.xpath.*;

public class PlayerJoinedGame implements IxReciever
{
	private AcceptedAddresses events;

	private IxAddress addr;
	private IxAddress baseAddr;
	private RoomModel roomModel;


	public PlayerJoinedGame(IxAddress baseAddr, RoomModel roomModel)
	{
		super();

		this.baseAddr = baseAddr;
		this.roomModel = roomModel;

		addr = baseAddr.append("PlayerJoinedGame");

		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

		var gson = new Gson();
		var joined = gson.fromJson(doc, PlayerJoinedGameObj.class);

		roomModel.addPlayerToGame(joined.gameId(), joined.seat(), joined.name());

	}
	public String placeInThread()
	{
		return baseAddr.toString();	//Say we are thread safe
	}
	public AcceptedAddresses getEvents()
	{
		return events;
	}
}
