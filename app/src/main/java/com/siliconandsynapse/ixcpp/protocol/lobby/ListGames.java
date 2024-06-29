package com.siliconandsynapse.ixcpp.protocol.lobby;


import org.w3c.dom.*;

//import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
import com.siliconandsynapse.ixcpp.protocol.game.PlayerInfoObj;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.ParseError;

import java.util.List;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class ListGames implements IxReciever
{
	private AcceptedAddresses events;
	private RoomModel model;
	private IxAddress baseAddr;
	private IxAddress addr;
	private GameController gameManager;

	public ListGames(IxAddress baseAddr, RoomModel model, GameController gameManager)
	{
		this.baseAddr = baseAddr;
		this.gameManager = gameManager;
		this.model = model;

        addr = baseAddr.append("ListGames");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
        var gson = new Gson();
        var t = new TypeToken<List<ListGamesObj>>(){};
        var games = gson.fromJson(doc, t);

		for (var g: games) {
			var id = Integer.parseInt(g.gameId());
			model.addGame(new GameInfo(id, g.type()), returnTunnel);
		}

//		String gameId = null;
//		String type = null;
//		for (var g: games) {
//			if (g.freeSeats() > 0)
//			{
//				gameId = g.gameId();
//				type = g.type();
//				break;
//			}
//		}
//
//		if (gameId == null) {
//
//			var cmd = new CreateGameCmd("Two Sheep");
//			cmd.execute(key.getParent(), returnTunnel);
//		} else {
//			gameManager.startGame(returnTunnel, gameId, type);
//		}
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
