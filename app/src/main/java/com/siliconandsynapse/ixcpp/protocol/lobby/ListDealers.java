//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.manager.IGameList;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.parsers.*;
//import javax.xml.xpath.*;
//
//public class ListDealers implements IxReciever
//{
//	private AcceptedAddresses events;
//	private DocumentBuilder documentBuilder;
//	private IGameList gameList;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//
//	private XPathFactory factory;
//
//	public ListDealers(IxAddress baseAddr, IGameList gameList)
//	{
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//		}
//		this.gameList = gameList;
//		this.baseAddr = baseAddr;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("ListDealers");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//	}
//
//	//////////////////////////////////////////////////////////
//	// Receiving Functions
//	///////////////////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		NodeList dealers;
//		Element dealer;
//
//		XPath path;
//
//		path = factory.newXPath();
//
//		gameList.clear();
//
//		try {
//			dealers = (NodeList)path.evaluate("/ListDealers/Dealer", doc, XPathConstants.NODESET);
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//
//
//		for (int i = 0; i < dealers.getLength(); i++)
//		{
//			dealer = (Element)dealers.item(i);
//			gameList.addDealer(returnTunnel, dealer.getAttribute("id"));
//		}
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
//
//	////////////////////////////////////////
//	// room manager functions
//	////////////////////////////////////////
//	public void get(IxManager tunnel)
//	{
//		Document doc;
//		Element message;
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("ListDealers");
//		doc.appendChild(message);
//
//		try {
//			tunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			return;
//		}
//	}
//}
