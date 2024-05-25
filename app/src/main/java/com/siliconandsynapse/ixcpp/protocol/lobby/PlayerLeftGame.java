//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.ui.lobby.IGameGrid;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.xpath.*;
//
//public class PlayerLeftGame implements IxReciever
//{
//	private AcceptedAddresses events;
//
//	private IxAddress addr;
//	private IxAddress baseAddr;
//	private IGameGrid roomModel;
//
//	private XPathFactory factory;
//
//	public PlayerLeftGame(IxAddress baseAddr, IGameGrid roomModel)
//	{
//		super();
//
//		this.baseAddr = baseAddr;
//		this.roomModel = roomModel;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("PlayerLeftGame");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//	}
//
//	//////////////////////////////////////////////////
//	//IxReciever functions
//	//////////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		XPath path;
//		String game = "";
//		String playerName = "";
//
//		path = factory.newXPath();
//
//		try {
//			game = path.evaluate("/PlayerLeftGame/@gameID", doc);
//			playerName = path.evaluate("/PlayerLeftGame/@playerID", doc);
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		roomModel.removePlayerFromGame(game, playerName);
//
//	}
//	public String placeInThread()
//	{
//		return baseAddr.toString();	//Say we are thread safe
//	}
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//	}
//}
