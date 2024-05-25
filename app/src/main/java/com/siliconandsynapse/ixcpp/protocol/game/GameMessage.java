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
//public class GameMessage implements IxReciever
//{
//
//	private AcceptedAddresses events;
//	private ITableDisplay table;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//	private XPathFactory factory;
//
//
//	public GameMessage(IxAddress baseAddr, ITableDisplay table)
//	{
//
//		this.table = table;
//		this.baseAddr = baseAddr;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("GameMessage");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
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
//		String notice = "";
//
//		XPath path;
//		path = factory.newXPath();
//		try {
//			notice = path.evaluate("/response/noticeMsg", doc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		table.writeNotice(notice);
//
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
