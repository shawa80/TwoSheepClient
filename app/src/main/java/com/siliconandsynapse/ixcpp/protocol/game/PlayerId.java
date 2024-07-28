package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class PlayerId implements IxReceiver
{
	private final AcceptedAddresses events;
	private final ITableDisplay table;

	private final IxAddress baseAddr;

	public record PlayerIdDto(int id) {}

	public PlayerId(IxAddress baseAddr, ITableDisplay table)
	{
		this.table = table;
		this.baseAddr = baseAddr;

		IxAddress addr = baseAddr.append("PlayerId");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{


        var gson = new Gson();
        var playerId = gson.fromJson(doc, PlayerIdDto.class);

		table.setMyPlayerId(playerId.id());
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
