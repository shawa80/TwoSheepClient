package com.siliconandsynapse.ixcpp.protocol.game;

import org.w3c.dom.*;

import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.Message;
import com.siliconandsynapse.net.ixtunnel.ParseError;

import javax.xml.parsers.*;

public class PlayerPickACard implements IxReciever
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

	public void accept(IxAddress key, IxManager returnTunnel, Message doc)
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
        returnTunnel.sendDocument(addr, new Message(new PlayerPickACardResponse(pickedCard)));
	}
}
