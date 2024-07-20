package com.siliconandsynapse.ixcpp.protocol.lobby;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class CreateGame implements IxReceiver
{
	private AcceptedAddresses events;
	private RoomModel model;
	private IxAddress baseAddr;
	private IxAddress addr;

	private GameController gameManager;

	public CreateGame(IxAddress baseAddr, RoomModel model, GameController gameManager)
	{

		this.model = model;
		this.baseAddr = baseAddr;
		this.gameManager = gameManager;

		addr = baseAddr.append("CreateGame");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

        var gson = new Gson();
        var x = gson.fromJson(doc, CreateGameObj.class);

		var id = x.gameId();
		var roomType = x.type();

		model.addGame(new GameInfo(id, roomType), null);

		//gameManager.startGame(returnTunnel, x.name(), x.type());
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
