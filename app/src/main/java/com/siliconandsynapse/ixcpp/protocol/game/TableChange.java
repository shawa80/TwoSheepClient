package com.siliconandsynapse.ixcpp.protocol.game;

import org.w3c.dom.*;

import android.util.Log;

import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
import com.siliconandsynapse.ixcpp.gameInteraction.TableCardEventHandler;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;
import com.siliconandsynapse.net.ixtunnel.Message;
import com.siliconandsynapse.net.ixtunnel.ParseError;

public class TableChange implements IxReciever
{

    public final static String KEY = "TableChange";
	private final AcceptedAddresses events;
	private final TableCardEventHandler table;
	private final IxAddress baseAddr;

    private CardFactory cache;

	public TableChange(IxAddress baseAddr,
		TableCardEventHandler table,
		CardFactory cache)
	{

		this.baseAddr = baseAddr;
		this.table = table;
		this.cache = cache;
        IxAddress addr = baseAddr.append(KEY);

		events = new AcceptedAddresses(addr);

	}


	public void accept(IxAddress key, IxManager returnTunnel, Message doc)
	{

		table.invalidateAllCards();

		TableChangeObj x = (TableChangeObj) doc.getDoc();

		x.getSeats().forEach( seat-> {
			seat.getStacks().forEach(stack -> {
				stack.getCards().forEach(card -> {
					table.validateCard(seat.getPlayerId(),
						stack.getStackType(), stack.getId(),
						card.getLevel(),
						cache.createCard(card.getCard().getCode(),
								card.getCard().getSuit(),
								card.getCard().getType()));
				});
			});
		});

		table.removeNonvalidatedCards();
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
