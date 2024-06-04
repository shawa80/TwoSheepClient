package com.siliconandsynapse.ixcpp.protocol.lobby;

import org.w3c.dom.*;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.ParseError;

import javax.xml.xpath.*;

public class JoinGame implements IxReciever
{
	private AcceptedAddresses events;
	private GameController gameManager;
	private IxAddress addr;

	private XPathFactory factory;

	public JoinGame(IxAddress baseAddr, GameController gameManager)
	{
		this.gameManager = gameManager;
		factory = XPathFactory.newInstance();

        addr = baseAddr.append("JoinGame");

		events = new AcceptedAddresses(addr);

	}


	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

        var gson = new Gson();
        var x = gson.fromJson(doc, JoinGameObj.class);

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
