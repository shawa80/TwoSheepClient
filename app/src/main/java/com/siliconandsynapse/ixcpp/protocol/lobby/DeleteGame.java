package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class DeleteGame implements IxReceiver
{
	private AcceptedAddresses events;
	private RoomModel model;
	private IxAddress baseAddr;
	private IxAddress addr;


	public DeleteGame(IxAddress baseAddr, RoomModel model)
	{
		this.model = model;
		this.baseAddr = baseAddr;

        addr = baseAddr.append("DeleteGame");

		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
        var gson = new Gson();
        var game = gson.fromJson(doc, DeleteGameObj.class);

		model.removeGame(game.gameId());
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
