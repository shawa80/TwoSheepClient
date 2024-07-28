package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class PlayerLeftGame implements IxReceiver
{
	private final AcceptedAddresses events;

	private final IxAddress baseAddr;
	private final RoomModel roomModel;

	public record PlayerLeftGameDto(int gameId, int seat, String name) {}

	public PlayerLeftGame(IxAddress baseAddr, RoomModel roomModel)
	{
		super();

		this.baseAddr = baseAddr;
		this.roomModel = roomModel;

		var addr = baseAddr.append("PlayerLeftGame");
		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
		var gson = new Gson();
		var left = gson.fromJson(doc, PlayerLeftGameDto.class);

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
