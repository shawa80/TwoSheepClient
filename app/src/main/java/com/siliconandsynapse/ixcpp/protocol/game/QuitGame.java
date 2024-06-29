package com.siliconandsynapse.ixcpp.protocol.game;

import org.w3c.dom.*;

import com.google.gson.Gson;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.ParseError;

public class QuitGame implements IxReciever
{
	private AcceptedAddresses events;
	private IxAddress baseAddr;
	private IxAddress addr;

	public QuitGame(IxAddress baseAddr)
	{

		this.baseAddr = baseAddr;
        addr = baseAddr.append("QuitGame");
		events = new AcceptedAddresses(addr);
	}

	public void sendQuitGame(IxManager tunnel, int gameId)
	{

        var gson = new Gson();
        var doc = gson.toJson(new QuitGameObj(gameId));

		try {
			tunnel.sendDocument(addr, doc);
		} catch (Exception e) {
			return;
		}

	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

	}
	public String placeInThread()
	{
		return baseAddr.toString(); //We want to process this event before we
				//move on to the next

	}

	public AcceptedAddresses getEvents()
	{
		return events;
	}
}
