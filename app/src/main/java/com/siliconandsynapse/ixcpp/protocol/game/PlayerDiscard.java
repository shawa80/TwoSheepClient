//package com.siliconandsynapse.ixcpp.protocol.game;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
//import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
//import com.siliconandsynapse.ixcpp.util.Mutex;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//import com.siliconandsynapse.ixcpp.common.Discard;
//import com.siliconandsynapse.ixcpp.common.DiscardSerialize;
//
//public class PlayerDiscard implements IxReciever
//{
//
//	private AcceptedAddresses events;
//	private ITableDisplay table;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//	private CardFactory cache;
//	private Mutex block;
//
//	public PlayerDiscard(IxAddress baseAddr,
//		ITableDisplay table,
//		CardFactory cache,
//		Mutex block)
//	{
//
//		this.table = table;
//		this.baseAddr = baseAddr;
//		this.cache = cache;
//		this.block = block;
//
//
//		try {
//			addr = baseAddr.append("PlayerDiscard");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//		events = new AcceptedAddresses(addr);
//
//	}
//
//	//////////////////////////////////////////////////////////////
//	// Receiving functions
//	/////////////////////////////////////////////////////////////
//
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//
//		table.setDiscard(DiscardSerialize.decode(cache, doc));
//
//		block.sendNotice();
//	}
//	public String placeInThread()
//	{
//		return baseAddr.toString();
//	}
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//	}
//
//	////////////////////////////////////////////////
//	/// sending functions
//	////////////////////////////////////////////////
//	public void answer(IxManager returnTunnel, Discard d)
//	{
//
//		Document doc;
//
//		doc = DiscardSerialize.encode(d);
//
//		try {
//			returnTunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//}
