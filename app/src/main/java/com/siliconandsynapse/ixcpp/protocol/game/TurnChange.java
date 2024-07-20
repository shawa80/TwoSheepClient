package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class TurnChange implements IxReceiver
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

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
		var gson = new Gson();
		var x = gson.fromJson(doc, TurnChangeObj.class);

		table.indicatePlayer(x.playerId());
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
