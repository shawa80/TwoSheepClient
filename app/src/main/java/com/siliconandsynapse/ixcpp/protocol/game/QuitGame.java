package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class QuitGame implements IxReceiver
{
	private final AcceptedAddresses events;
	private final IxAddress baseAddr;
	private final IxAddress addr;

	public record QuitGameDto(int gameId) { }
	public QuitGame(IxAddress baseAddr)
	{

		this.baseAddr = baseAddr;
        addr = baseAddr.append("QuitGame");
		events = new AcceptedAddresses(addr);
	}

	public void sendQuitGame(IxManager tunnel, int gameId)
	{

        var gson = new Gson();
        var doc = gson.toJson(new QuitGameDto(gameId));

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
