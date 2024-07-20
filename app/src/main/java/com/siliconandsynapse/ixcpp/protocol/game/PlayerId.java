package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class PlayerId implements IxReceiver
{
	private AcceptedAddresses events;
	private ITableDisplay table;

	private IxAddress baseAddr;
	private IxAddress addr;

	public PlayerId(IxAddress baseAddr, ITableDisplay table)
	{
		this.table = table;
		this.baseAddr = baseAddr;

        addr = baseAddr.append("PlayerId");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{


        var gson = new Gson();
        var playerId = gson.fromJson(doc, PlayerIdObj.class);

		table.setMyPlayerId(playerId.id());
	}
	public String placeInThread()
	{
		return baseAddr.toString();
	}
	public AcceptedAddresses getEvents()
	{
		return events;
	}

//	public void requestPlayerID(IxManager returnTunnel)
//	{
//		Document doc;
//		Element message;
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("request");
//		message.setAttribute("type", "getMyID");
//		doc.appendChild(message);
//
//		try {
//			returnTunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}


}
