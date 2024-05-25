package com.siliconandsynapse.ixcpp.protocol.game;

import javax.xml.xpath.*;


import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.Message;

import java.util.List;

public class PlayerInfo implements IxReciever
{

	public final static String KEY = "PlayerInfo";
	private final AcceptedAddresses events;
	private final ITableDisplay table;
	private final IxAddress baseAddr;

	private XPathFactory factory;

	public PlayerInfo(IxAddress baseAddr, ITableDisplay table)
	{

		this.table = table;
		this.baseAddr = baseAddr;
		IxAddress addr = baseAddr.append(KEY);

		events = new AcceptedAddresses(addr);

	}


	public void accept(IxAddress key, IxManager returnTunnel, Message doc)
	{
		List<PlayerInfoObj> pi = (List<PlayerInfoObj>)doc.getDoc();

		pi.forEach(p -> {
			table.updatePlayerName(p.getId(), p.getName());
		});

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
