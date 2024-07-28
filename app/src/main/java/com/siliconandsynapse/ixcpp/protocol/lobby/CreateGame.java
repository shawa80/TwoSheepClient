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
	private final AcceptedAddresses events;
	private final RoomModel model;
	private final IxAddress baseAddr;

	public record CreateGameDto (int gameId, String type) { }

	public CreateGame(IxAddress baseAddr, RoomModel model, GameController gameManager)
	{

		this.model = model;
		this.baseAddr = baseAddr;

		IxAddress addr = baseAddr.append("CreateGame");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

        var gson = new Gson();
        var x = gson.fromJson(doc, CreateGameDto.class);

		var id = x.gameId();
		var roomType = x.type();

		model.addGame(new GameInfo(id, roomType), null);
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
