//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//import javax.xml.xpath.*;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.ui.MessageReceiverModel;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//public class MessageDisplay implements IxReciever
//{
//	private MessageReceiverModel chat;
//	private AcceptedAddresses events;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//
//	private XPathFactory factory;
//
//	public MessageDisplay(IxAddress baseAddr, MessageReceiverModel chat)
//	{
//		this.baseAddr = baseAddr;
//		this.chat = chat;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("ChatMessage");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//		events = new AcceptedAddresses(addr);
//
//	}
//
//
//	//////////////////////////////////////////////////////////////
//	// Receiving functions
//	/////////////////////////////////////////////////////////////
//
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		String user;
//		String msg;
//		XPath path;
//
//		path = factory.newXPath();
//
//		try {
//			user = path.evaluate("/ChatMessage/@fromId", doc);
//			msg = path.evaluate("/ChatMessage", doc);
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//
//		chat.appendMessage(user, msg);
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
