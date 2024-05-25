//package com.siliconandsynapse.ixcpp.protocol.game;
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
//import javax.xml.xpath.*;
//
//
//public class GameChat implements IxReciever
//{
//	private AcceptedAddresses events;
//	private DocumentBuilder documentBuilder;
//
//	private IxAddress addr;
//	private IxAddress baseAddr;
//	private ITableDisplay table;
//	private IxManager tunnel;
//	private XPathFactory factory;
//
//	public GameChat(IxAddress baseAddr, IxManager tunnel)
//	{
//		super();
//
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//
//		}
//
//		this.table = null;
//		this.baseAddr = baseAddr;
//		this.tunnel = tunnel;
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("GameChat");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//	}
//
//	//////////////////////////////////////////////////
//	//IxReciever functions
//	//////////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		String message = "";
//
//		XPath path;
//		path = factory.newXPath();
//
//		try {
//			message = path.evaluate("/GameChat", doc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		table.writeNotice(message);
//	}
//	public String placeInThread()
//	{
//		return baseAddr.toString();	//Say we are thread safe
//	}
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//	}
//
//
//	//////////////////////////////////////////////////////
//	//  Sending functions
//	//////////////////////////////////////////////////////
//	public void registerTable(ITableDisplay table)
//	{
//		this.table = table;
//	}
//
//	public void sendMessage(String clientMessage)
//	{
//		Document doc;
//		Element message;
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("GameChat");
//		message.setAttribute("to", "unicast");
//		doc.appendChild(message);
//		message.setTextContent(clientMessage);
//
//		try {
//			tunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			return;
//		}
//	}
//}
