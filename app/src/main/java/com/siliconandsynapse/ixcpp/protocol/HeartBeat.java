package com.siliconandsynapse.ixcpp.protocol;

import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;
import com.siliconandsynapse.net.ixtunnel.ParseError;


public class HeartBeat implements IxReceiver
{
	private AcceptedAddresses events;
	private IxAddress addr;


	public HeartBeat()
	{
		super();


		try {
			addr = IxAddress.parse("__System.HeartBeat");
		} catch (ParseError e) {
		}

		events = new AcceptedAddresses(addr);

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
		try {
			returnTunnel.sendDocument(addr, doc);
		} catch (Exception e) {
			return;
		}

	}
	public String placeInThread()
	{
		return addr.toString();	//Say we are thread safe
	}
	public AcceptedAddresses getEvents()
	{
		return events;
	}

}
