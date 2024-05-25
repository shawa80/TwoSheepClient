//package com.siliconandsynapse.ixcpp.protocol.lobby;
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
//import javax.xml.xpath.*;
//
//public class CreateGame implements IxReciever
//{
//	private AcceptedAddresses events;
//	private RoomModel model;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//
//	private XPathFactory factory;
//
//	public CreateGame(IxAddress baseAddr, RoomModel model)
//	{
//
//		this.model = model;
//		this.baseAddr = baseAddr;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("CreateGame");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//
//	}
//	//////////////////////////////////////////////
//	/// sending functions
//	//////////////////////////////////////////////
//	/*public void createGame(IxManager tunnel, String type)
//	{
//		Document doc;
//		Element message;
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("CreateGame");
//		message.setAttribute("type", type);
//		doc.appendChild(message);
//		try {
//			tunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			return;
//		}
//	}*/
//
//	//////////////////////////////////////////////
//	// Receiving functions
//	//////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//
//		XPath path;
//		String roomName = null;
//		String roomType = null;
//
//		path = factory.newXPath();
//
//		try {
//			roomName = path.evaluate("/GameCreated/@name", doc);
//			roomType = path.evaluate("/GameCreated/@type", doc);
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		int id = Integer.parseInt(roomName);
//
//		model.addGame(new GameInfo(id, roomType), returnTunnel);
//
//	}
//	public String placeInThread()
//	{
//		return baseAddr.toString();
//	}
//
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//	}
//}
