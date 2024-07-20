package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;
import com.siliconandsynapse.ixcpp.common.Discard;

import java.util.ArrayList;

public class PlayerDiscard implements IxReceiver
{

	private AcceptedAddresses events;
	private ITableDisplay table;
	private IxAddress baseAddr;
	private IxAddress addr;
	private CardFactory cache;
	private Mutex block;

	public PlayerDiscard(IxAddress baseAddr,
		ITableDisplay table,
		CardFactory cache,
		Mutex block)
	{

		this.table = table;
		this.baseAddr = baseAddr;
		this.cache = cache;
		this.block = block;

        addr = baseAddr.append("PlayerDiscard");

		events = new AcceptedAddresses(addr);

	}


	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

        var gson = new Gson();
        var d = gson.fromJson(doc, PlayerDiscardObj.class);

        var discard = new Discard();
        discard.setMax(d.max());
        discard.setMin(d.min());
        discard.setMessage(d.message());

        d.codes().forEach((code)-> {
			var card = cache.getCard(code);
            discard.addCard(card);
        });

		table.setDiscard(discard);

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

	public void answer(IxManager returnTunnel, Discard d)
	{

        var gson = new Gson();

        var cards = new ArrayList<Integer>();
        for (int i = 0; i < d.getCount(); i++)
            cards.add(d.getCardAt(i).getCode());

        var doc = gson.toJson(new PlayerDiscardResponseObj(cards));

		try {
			returnTunnel.sendDocument(addr, doc);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
