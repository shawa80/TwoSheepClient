package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class GameMessage implements IxReceiver
{

	private final AcceptedAddresses events;
	private final ITableDisplay table;
	private final IxAddress baseAddr;

	public record GameMessageDto(String msg) {}

	public GameMessage(IxAddress baseAddr, ITableDisplay table)
	{

		this.table = table;
		this.baseAddr = baseAddr;
		var addr = baseAddr.append("GameMessage");
		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
        var gson = new Gson();
        var message = gson.fromJson(doc, GameMessageDto.class);

		table.writeNotice(message.msg());

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
