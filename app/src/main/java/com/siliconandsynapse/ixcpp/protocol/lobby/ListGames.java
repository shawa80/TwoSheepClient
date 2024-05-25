//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
//import com.siliconandsynapse.ixcpp.gameInteraction.GameInfo;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.parsers.*;
//import javax.xml.xpath.*;
//
//public class ListGames implements IxReciever
//{
//	private AcceptedAddresses events;
//	private DocumentBuilder documentBuilder;
//	private RoomModel model;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//
//	private XPathFactory factory;
//
//	public ListGames(IxAddress baseAddr, RoomModel model)
//	{
//
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//		}
//
//		this.baseAddr = baseAddr;
//		this.model = model;
//
//		factory = XPathFactory.newInstance();
//
//
//		try {
//			addr = baseAddr.append("ListGames");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//	}
//
//
//	//////////////////////////////////////////////////////////
//	/// Sending functions
//	//////////////////////////////////////////////////////////
//	public void list(IxManager returnTunnel)
//	{
//		Element message;
//		Document doc;
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("ListGames");
//		doc.appendChild(message);
//
//		try {
//			returnTunnel.sendDocument(addr, doc);
//
//		} catch (Exception e) {
//			return;
//		}
//
//	}
//
//	//////////////////////////////////////////////////////////
//	// Receiving Functions
//	//////////////////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		NodeList games;
//		Element game;
//		Element listGames;
//		XPath path;
//		NodeList players = null;
//		Element player;
//		int id;
//
//		path = factory.newXPath();
//
//		listGames = doc.getDocumentElement();
//		games = listGames.getElementsByTagName("Game");
//
//		for (int i = 0; i < games.getLength(); i++)
//		{
//			game = (Element)games.item(i);
//			id = Integer.parseInt(game.getAttribute("id"));
//			model.addGame(new GameInfo(id, game.getAttribute("type")), returnTunnel);
//
//			try {
//				players = (NodeList)path.evaluate("/ListGames/Game[@id='" + id + "']/player",
//					doc, XPathConstants.NODESET);
//			} catch (XPathExpressionException e) {
//				e.printStackTrace();
//				return;
//			}
//
//			for (int j = 0; j < players.getLength(); j++)
//			{
//				player = (Element)players.item(j);
//
//				int gameId = Integer.parseInt(game.getAttribute("id"));
//
//				model.addPlayerToGame(new GameInfo(gameId, ""), player.getAttribute("id"));
//			}
//
//		}
//
//
//
//
//	}
//	public String placeInThread()
//	{
//		return baseAddr.toString();
//	}
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//
//	}
//
//}
