//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.userInteraction.PasswordPrompt;
//import com.siliconandsynapse.ixcpp.userInteraction.UserPassword;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.parsers.*;
//
//public class AccessControl implements IxReciever
//{
//	private DocumentBuilder documentBuilder;
//	private AcceptedAddresses events;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//	private PasswordPrompt authWin;
//
//	public AccessControl(IxAddress baseAddr, PasswordPrompt authWin)
//	{
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//
//		}
//
//		this.authWin = authWin;
//		this.baseAddr = baseAddr;
//
//		try {
//			addr = baseAddr.append("AccessControl");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//	}
//	//////////////////////////////////////////////
//	// Receiving functions
//	//////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//
//		String user;
//		String pass;
//
//		UserPassword data;
//
//
//		//TODO this needs to not block the network thread
//		data = authWin.prompt();
//		if (data == null)
//			return;
//
//		user = data.getUser();
//		pass = data.getPass();
//
//		Document requestDoc;
//		Element message;
//		Element auth;
//
//		requestDoc = documentBuilder.newDocument();
//
//		message = requestDoc.createElement("AccessControl");
//		requestDoc.appendChild(message);
//
//		auth = requestDoc.createElement("Auth");
//		auth.setAttribute("User", user);
//		auth.setAttribute("Pass", pass);
//		message.appendChild(auth);
//
//		try {
//			returnTunnel.sendDocument(addr, requestDoc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
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
