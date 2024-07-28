package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class PlayerJoinedGame implements IxReceiver
{
	private final AcceptedAddresses events;
	private final IxAddress baseAddr;
	private final RoomModel roomModel;

	public record PlayerJoinedGameDto(int gameId, int seat, String name) {}

	public PlayerJoinedGame(IxAddress baseAddr, RoomModel roomModel)
	{
		super();

		this.baseAddr = baseAddr;
		this.roomModel = roomModel;

		IxAddress addr = baseAddr.append("PlayerJoinedGame");

		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

		var gson = new Gson();
		var joined = gson.fromJson(doc, PlayerJoinedGameDto.class);

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
