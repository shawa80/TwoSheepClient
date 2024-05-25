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
//public class UserList implements IxReciever
//{
//	private AcceptedAddresses events;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//	private LobbyModel users;
//
//	private XPathFactory factory;
//
//	public UserList(IxAddress baseAddr, LobbyModel users)
//	{
//		this.baseAddr = baseAddr;
//		this.users = users;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("UserList");
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
//		NodeList nodes = null;
//		String user;
//		Element u;
//
//		path = factory.newXPath();
//
//		try {
//			nodes = (NodeList)path.evaluate("/UserList/User", doc, XPathConstants.NODESET);
//		} catch (XPathExpressionException e) {
//			e.printStackTrace();
//			return;
//		}
//		if (nodes == null)
//			return;
//
//		synchronized (users) {
//			users.clear();
//			for (int i = 0; i < nodes.getLength(); i++)
//			{
//				u = (Element)nodes.item(i);
//				user = u.getAttribute("name");
//				users.addUser(user);
//			}
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
