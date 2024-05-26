package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
import com.siliconandsynapse.ixcpp.common.cards.types.PokerCard;
import com.siliconandsynapse.ixcpp.gameInteraction.TableCardEventHandler;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;

import java.lang.reflect.Type;
import java.util.List;

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


	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{

		class CardCreator implements InstanceCreator<Card> {
			@Override
			public Card createInstance(Type type) {
				return new PokerCard(0, 0, 0);
			}
		}

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Card.class,  new CardCreator());
		Gson gson = gsonBuilder.create();

		var x = gson.fromJson(doc, TableChangeObj.class);

		table.invalidateAllCards();

		x.seats().forEach( seat-> {
			seat.stacks().forEach(stack -> {
				stack.cards().forEach(card -> {
					table.validateCard(seat.playerId(),
						stack.stackType(), stack.id(),
						card.level(),
						cache.createCard(card.card().getCode(),
								card.card().getSuit(),
								card.card().getType()));
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
