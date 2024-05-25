//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.gameInteraction.GameController;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.xpath.*;
//
//public class JoinGame implements IxReciever
//{
//	private AcceptedAddresses events;
//	private GameController gameManager;
//	private IxAddress addr;
//
//	private XPathFactory factory;
//
//	public JoinGame(IxAddress baseAddr, GameController gameManager)
//	{
//
//
//		this.gameManager = gameManager;
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("JoinGame");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//	}
//
//
//	//////////////////////////////////////////////
//	// Receiving functions
//	//////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		XPath path;
//
//		String gameName = null;
//		String gameType = null;
//
//		path = factory.newXPath();
//
//		try {
//			gameName = path.evaluate("/JoinCreated/@gameId", doc);
//			gameType = path.evaluate("/JoinCreated/@type", doc);
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//		gameManager.startGame(returnTunnel, gameName, gameType);
//
//	}
//	public String placeInThread()
//	{
//
//		//WE ARE NOT THREAD SAFE!!!!!!!
//		return null;
//		//We want to process this event before we
//		//move on to the next
//
//	}
//
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//	}
//}
