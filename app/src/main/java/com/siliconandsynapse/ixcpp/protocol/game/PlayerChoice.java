package com.siliconandsynapse.ixcpp.protocol.game;

import com.google.gson.Gson;
import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReceiver;
import com.siliconandsynapse.ixcpp.common.Answer;
import com.siliconandsynapse.ixcpp.common.Choice;
import com.siliconandsynapse.ixcpp.common.ChoiceResponse;

import java.util.List;


public class PlayerChoice implements IxReceiver
{
	private final AcceptedAddresses events;
	private final ITableDisplay table;
	private final IxAddress baseAddr;
	private final IxAddress addr;
	private final Mutex block;

	public record PlayerChoiceRequestAnswer(int id, String answer) {}
	public record PlayerChoiceRequestObj(String id, String question,List<PlayerChoiceRequestAnswer> answers) {}
	public record PlayerChoiceResponseObj(int id, String value) {}

	public PlayerChoice(IxAddress baseAddr, ITableDisplay table, Mutex block)
	{

		this.table = table;
		this.baseAddr = baseAddr;
		this.block = block;

        addr = baseAddr.append("PlayerChoice");

		events = new AcceptedAddresses(addr);
	}

	public void accept(IxAddress key, IxManager returnTunnel, String doc)
	{
		Choice c;

        var gson = new Gson();
        var x = gson.fromJson(doc, PlayerChoiceRequestObj.class);

        c = new Choice(x.id(), x.question());

        x.answers().stream().forEach((a) -> {
            c.add(new Answer(a.id(), a.answer()));
        });

		table.setChoice(c);
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

	public void answer(IxManager returnTunnel, ChoiceResponse c)
	{
		Answer a;

		if (c == null)
			return;

		a = c.getAnswer();

		if (a == null)
			return;

        var gson = new Gson();
        var doc = gson.toJson(new PlayerChoiceResponseObj(a.getId(), a.getValue()));

		returnTunnel.sendDocument(addr, doc);

	}
}
