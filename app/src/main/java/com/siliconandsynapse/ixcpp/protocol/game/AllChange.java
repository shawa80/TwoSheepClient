//package com.siliconandsynapse.ixcpp.protocol.game;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.parsers.*;
//
//public class AllChange implements IxReciever
//{
//	private DocumentBuilder documentBuilder;
//	private AcceptedAddresses events;
//
//	private IxAddress baseAddr;
//	private IxAddress addr;
//
//	public AllChange(IxAddress baseAddr)
//	{
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//		}
//		this.baseAddr = baseAddr;
//
//		try {
//			addr = baseAddr.append("AllChange");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//	}
//
//	//////////////////////////////////////////////////////////////
//	// Receiving functions
//	/////////////////////////////////////////////////////////////
//
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		requestAll(returnTunnel);
//	}
//
//	public String placeInThread()
//	{
//		return baseAddr.toString();
//	}
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//	}
//
//	/////////////////////////////////////////////////////////////
//	/// Sending Functions
//	/////////////////////////////////////////////////////////////
//
//	public void requestAll(IxManager returnTunnel)
//	{
//		Document doc;
//		Element message;
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("request");
//		message.setAttribute("type", "getAll");
//		doc.appendChild(message);
//
//		try {
//			returnTunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
