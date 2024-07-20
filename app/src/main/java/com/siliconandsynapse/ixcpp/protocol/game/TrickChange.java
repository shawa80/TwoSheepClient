package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
import com.siliconandsynapse.ixcpp.common.cards.types.PokerCard;
import com.siliconandsynapse.ixcpp.gameInteraction.TableCardEventHandler;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;

import java.lang.reflect.Type;
import java.util.List;

public class TrickChange implements IxReceiver
{

    private AcceptedAddresses events;
    private TableCardEventHandler table;
    private IxAddress baseAddr;
    private IxAddress addr;
    private CardFactory cache;

    public TrickChange(IxAddress baseAddr, TableCardEventHandler table, CardFactory cache)
    {
        this.table = table;
        this.baseAddr = baseAddr;
        this.cache = cache;

        addr = baseAddr.append("TrickChange");
        events = new AcceptedAddresses(addr);
    }


    public void accept(IxAddress key, IxManager returnTunnel, String doc)
    {
        table.invalidateAllCards();

        class CardCreator implements InstanceCreator<Card> {
            @Override
            public Card createInstance(Type type) {
                return new PokerCard(0, 0, 0);
            }
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Card.class,  new CardCreator());
        Gson gson = gsonBuilder.create();
        var t = new TypeToken<List<TrickChangeObj>>() {};
        var trickChanges = gson.fromJson(doc, t);

        trickChanges.forEach((change) -> {

            table.validateCard(
                    change.playerId(),
                    "trick",
                    0,
                    0,
                    cache.createCard(
                            change.card().getCode(),
                            change.card().getSuit(),
                            change.card().getType()
                    )
            );
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
