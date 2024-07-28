package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class DeleteGame implements IxReceiver
{
	private final AcceptedAddresses events;
	private final RoomModel model;
	private final IxAddress baseAddr;

	public record DeleteGameDto(int gameId) { }

	public DeleteGame(IxAddress baseAddr, RoomModel model)
	{
		this.model = model;
		this.baseAddr = baseAddr;

		IxAddress addr = baseAddr.append("DeleteGame");

		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
        var gson = new Gson();
        var game = gson.fromJson(doc, DeleteGameDto.class);

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
