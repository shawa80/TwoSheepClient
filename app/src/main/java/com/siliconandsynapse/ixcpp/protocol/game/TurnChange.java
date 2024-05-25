package com.siliconandsynapse.ixcpp.protocol.game;

import javax.xml.xpath.*;

import org.w3c.dom.*;

import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.Message;
import com.siliconandsynapse.net.ixtunnel.ParseError;

public class TurnChange implements IxReciever
{
    public final static String KEY = "TurnChange";

	private final AcceptedAddresses events;
	private final ITableDisplay table;
	private final IxAddress baseAddr;


	public TurnChange(IxAddress baseAddr, ITableDisplay table)
	{
		this.table = table;
		this.baseAddr = baseAddr;

        IxAddress addr = baseAddr.append(KEY);

		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, Message doc)
	{
        TurnChangeObj x = (TurnChangeObj)doc.getDoc();

		table.indicatePlayer(x.getPlayerId());
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
