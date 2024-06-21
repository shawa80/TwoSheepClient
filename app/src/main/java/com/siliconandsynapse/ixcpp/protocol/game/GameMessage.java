package com.siliconandsynapse.ixcpp.protocol.game;

import javax.xml.xpath.*;

import org.w3c.dom.*;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.ParseError;

public class GameMessage implements IxReciever
{

	private AcceptedAddresses events;
	private ITableDisplay table;
	private IxAddress baseAddr;
	private IxAddress addr;


	public GameMessage(IxAddress baseAddr, ITableDisplay table)
	{

		this.table = table;
		this.baseAddr = baseAddr;
        addr = baseAddr.append("GameMessage");
		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
        var gson = new Gson();
        var message = gson.fromJson(doc, GameMessageObj.class);

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
