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
//public class UserDel implements IxReciever
//{
//	private AcceptedAddresses events;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//	private LobbyModel users;
//
//	private XPathFactory factory;
//
//	public UserDel(IxAddress baseAddr, LobbyModel users)
//	{
//		super();
//
//
//		this.baseAddr = baseAddr;
//		this.users = users;
//
//		factory = XPathFactory.newInstance();
//
//
//		try {
//			addr = baseAddr.append("UserDel");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
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
//			user = path.evaluate("/UserDel/@name", doc);
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//		synchronized (users) {
//			users.removeUser(user);
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
