package com.siliconandsynapse.ixcpp.protocol;

import com.google.gson.Gson;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;


public class Pause implements IxReceiver
{
	private AcceptedAddresses events;

	private IxAddress addr;
	private IxAddress baseAddr;

	public Pause(IxAddress baseAddr)
	{
		super();

		this.baseAddr = baseAddr;
        addr = baseAddr.append("pause");

		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

		var gson = new Gson();
		var x = gson.fromJson(doc, PauseObj.class);


		try {
			Thread.sleep(x.seconds() * 1000);
		} catch (InterruptedException ignored) {
			System.out.println("");
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
