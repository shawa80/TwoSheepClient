//package com.siliconandsynapse.ixcpp.protocol.game;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.common.cards.CardFactory;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//public class GameStart implements IxReciever
//{
//	private AcceptedAddresses events;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//	private CardFactory cache;
//
//	public GameStart(IxAddress baseAddr, CardFactory cache)
//	{
//		this.baseAddr = baseAddr;
//		this.cache = cache;
//
//		try {
//			addr = baseAddr.append("GameStart");
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
//		cache.clearCache();
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
//}
