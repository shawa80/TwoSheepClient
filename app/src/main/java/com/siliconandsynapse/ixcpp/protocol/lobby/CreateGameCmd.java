//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
//import com.siliconandsynapse.ixcpp.Cmd;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//public class CreateGameCmd implements Cmd {
//
//	private String name;
//
//	private DocumentBuilder documentBuilder;
//
//	public CreateGameCmd(String name) {
//		this.name = name;
//
//	}
//
//
//	@Override
//	public void execute(IxAddress baseAddr, IxManager tunnel) {
//
//		Document doc;
//		Element message;
//
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//
//		}
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("CreateGame");
//		message.setAttribute("type", name);
//		doc.appendChild(message);
//
//		IxAddress addr = null;
//
//		try {
//			addr = baseAddr.append("CreateGame");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		try {
//			tunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			return;
//		}
//
//	}
//
//
//}
