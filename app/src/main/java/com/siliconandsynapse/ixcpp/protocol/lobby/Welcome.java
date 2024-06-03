package com.siliconandsynapse.ixcpp.protocol.lobby;


import org.w3c.dom.*;

import com.siliconandsynapse.aclient.MainActivity;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.ParseError;

public class Welcome implements IxReciever
{
	private AcceptedAddresses events;

	private IxAddress baseAddr;
	private IxAddress addr;

	private MainActivity monitor;


	public Welcome(IxAddress baseAddr, MainActivity monitor)
	{
		this.baseAddr = baseAddr;
		this.monitor = monitor;

        addr = baseAddr.append("Welcome");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
		monitor.log("got welcome");
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
