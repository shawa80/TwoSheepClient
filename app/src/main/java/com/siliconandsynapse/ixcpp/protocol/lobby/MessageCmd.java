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
//
//public class MessageCmd implements Cmd {
//
//	private String clientMessage;
//
//	private DocumentBuilder documentBuilder;
//
//	public MessageCmd(String message) {
//
//		this.clientMessage = message;
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
//
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("ChatMessage");
//		message.setAttribute("to", "broadcast");
//		doc.appendChild(message);
//		message.setTextContent(clientMessage);
//
//		IxAddress addr = null;
//
//		try {
//			addr = baseAddr.append("ChatMessage");
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
//}
