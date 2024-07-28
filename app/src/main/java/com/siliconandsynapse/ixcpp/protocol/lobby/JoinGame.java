package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

import javax.xml.xpath.*;

public class JoinGame implements IxReceiver
{
	private final AcceptedAddresses events;

	public record JoinGameDto(int gameId, String type) {}

	public JoinGame(IxAddress baseAddr, GameController gameManager)
	{
		var addr = baseAddr.append("JoinGame");

		events = new AcceptedAddresses(addr);
	}


	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

        var gson = new Gson();
        var x = gson.fromJson(doc, JoinGameDto.class);

		//gameManager.startGame(returnTunnel, x.gameId(), x.type());

	}
	public String placeInThread()
	{

		//WE ARE NOT THREAD SAFE!!!!!!!
		return null;
		//We want to process this event before we
		//move on to the next

	}

	public AcceptedAddresses getEvents()
	{
		return events;
	}
}
