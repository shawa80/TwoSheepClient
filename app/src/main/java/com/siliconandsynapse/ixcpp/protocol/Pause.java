package com.siliconandsynapse.ixcpp.protocol;

import com.google.gson.Gson;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;


public class Pause implements IxReceiver
{
	private final AcceptedAddresses events;
	private final IxAddress baseAddr;
	private record PauseDto (int seconds){};

	public Pause(IxAddress baseAddr)
	{
		super();

		this.baseAddr = baseAddr;
		var addr = baseAddr.append("pause");

		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

		var gson = new Gson();
		var x = gson.fromJson(doc, PauseDto.class);

		try {
			Thread.sleep(x.seconds() * 1000L);
		} catch (InterruptedException ignored) {
		}
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
