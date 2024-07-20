package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

public class PlayerPickACard implements IxReceiver
{
    public static final String KEY = "PlayerPickACard";
	private AcceptedAddresses events;
	private Mutex block;
	private IxAddress baseAddr;
	private IxAddress addr;

	public PlayerPickACard(IxAddress baseAddr, Mutex block)
	{
		this.block = block;

		this.baseAddr = baseAddr;
        addr = baseAddr.append(KEY);

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

		block.sendNotice();
	}
	public String placeInThread()
	{
		return baseAddr.toString();
	}
	public AcceptedAddresses getEvents()
	{
		return events;
	}


	public void sendPlayCard(IxManager returnTunnel, Card pickedCard)
	{
		var gson = new Gson();
		var m = gson.toJson(new PlayerPickACardResponse(pickedCard));

        returnTunnel.sendDocument(addr, m);
	}
}
