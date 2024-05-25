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
//public class QuitGame implements IxReciever
//{
//	private DocumentBuilder documentBuilder;
//	private AcceptedAddresses events;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//
//	public QuitGame(IxAddress baseAddr)
//	{
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//
//		}
//
//		this.baseAddr = baseAddr;
//
//		try {
//			addr = baseAddr.append("QuitGame");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//	}
//	//////////////////////////////////////////////
//	/// sending functions
//	//////////////////////////////////////////////
//	public void sendQuitGame(IxManager tunnel, String gameName)
//	{
//		Document doc;
//		Element message;
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("QuitGame");
//		message.setAttribute("gameId", gameName);
//		doc.appendChild(message);
//
//		try {
//			tunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			return;
//		}
//
//	}
//
//	//////////////////////////////////////////////
//	// Receiving functions
//	//////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//
//	}
//	public String placeInThread()
//	{
//		return baseAddr.toString(); //We want to process this event before we
//				//move on to the next
//
//	}
//
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//	}
//}
