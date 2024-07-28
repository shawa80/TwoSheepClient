package com.siliconandsynapse.ixcpp.protocol.lobby;


import com.siliconandsynapse.aclient.MainActivity;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class Welcome implements IxReceiver
{
	private final AcceptedAddresses events;

	private final IxAddress baseAddr;


	public Welcome(IxAddress baseAddr, MainActivity monitor)
	{
		this.baseAddr = baseAddr;

		var addr = baseAddr.append("Welcome");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
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
