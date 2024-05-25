//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.gameInteraction.RoomModel;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.xpath.*;
//
//public class DeleteGame implements IxReciever
//{
//	private AcceptedAddresses events;
//	private RoomModel model;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//
//	private XPathFactory factory;
//
//	public DeleteGame(IxAddress baseAddr, RoomModel model)
//	{
//		this.model = model;
//		this.baseAddr = baseAddr;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("DeleteGame");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//	}
//
//	//////////////////////////////////////////////
//	// Receiving functions
//	//////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//
//		XPath path;
//		String roomName = null;
//
//		path = factory.newXPath();
//
//		try {
//			roomName = path.evaluate("/GameDeleted/@name", doc);
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		model.removeGame(Integer.parseInt(roomName));
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
