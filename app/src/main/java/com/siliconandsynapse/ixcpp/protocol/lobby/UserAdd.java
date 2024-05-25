//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.xpath.*;
//
//public class UserAdd implements IxReciever
//{
//	private AcceptedAddresses events;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//	private LobbyModel users;
//
//	private XPathFactory factory;
//
//	public UserAdd(IxAddress baseAddr, LobbyModel users)
//	{
//		super();
//
//		this.baseAddr = baseAddr;
//		this.users = users;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("UserAdd");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//
//	}
//	//////////////////////////////////////////////////////////
//	// Receiving Functions
//	///////////////////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		XPath path;
//		String user = "";
//
//		path = factory.newXPath();
//
//		try {
//			user = path.evaluate("/UserAdd/@name", doc);
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//		synchronized (users) {
//			users.addUser(user);
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
//}
