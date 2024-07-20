package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class PlayerLeftGame implements IxReceiver
{
	private AcceptedAddresses events;

	private IxAddress addr;
	private IxAddress baseAddr;
	private RoomModel roomModel;


	public PlayerLeftGame(IxAddress baseAddr, RoomModel roomModel)
	{
		super();

		this.baseAddr = baseAddr;
		this.roomModel = roomModel;

		addr = baseAddr.append("PlayerLeftGame");
		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
		var gson = new Gson();
		var left = gson.fromJson(doc, PlayerLeftGameObj.class);

		roomModel.removePlayerFromGame(left.gameId(), left.seat());

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
