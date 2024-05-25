//package com.siliconandsynapse.ixcpp.protocol.game;
//
//import javax.xml.xpath.*;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.parsers.*;
//
//public class PlayerId implements IxReciever
//{
//	private DocumentBuilder documentBuilder;
//	private AcceptedAddresses events;
//	private ITableDisplay table;
//
//	private IxAddress baseAddr;
//	private IxAddress addr;
//
//	private XPathFactory factory;
//
//	public PlayerId(IxAddress baseAddr, ITableDisplay table)
//	{
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//		}
//
//		this.table = table;
//		this.baseAddr = baseAddr;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("PlayerId");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
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
//		String playerIdStr = "";
//		XPath path;
//
//		path = factory.newXPath();
//
//		int playerId = 0;
//
//		try {
//			playerIdStr = path.evaluate("/response/player/@id", doc);
//
//			playerId = Integer.parseInt(playerIdStr);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		table.setMyPlayerId(playerId);
//	}
//	public String placeInThread()
//	{
//		return baseAddr.toString();
//	}
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//	}
//	////////////////////////////////////////////////////////////
//	/// sending functions
//	////////////////////////////////////////////////////////////
//
//	public void requestPlayerID(IxManager returnTunnel)
//	{
//		Document doc;
//		Element message;
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("request");
//		message.setAttribute("type", "getMyID");
//		doc.appendChild(message);
//
//		try {
//			returnTunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//
//}
