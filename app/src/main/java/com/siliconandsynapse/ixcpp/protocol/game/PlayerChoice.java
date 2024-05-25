//package com.siliconandsynapse.ixcpp.protocol.game;
//
//import javax.xml.xpath.*;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.ui.ITableDisplay;
//import com.siliconandsynapse.ixcpp.util.Mutex;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//import com.siliconandsynapse.ixcpp.common.Answer;
//import com.siliconandsynapse.ixcpp.common.Choice;
//import com.siliconandsynapse.ixcpp.common.ChoiceResponse;
//
//import javax.xml.parsers.*;
//
//
//public class PlayerChoice implements IxReciever
//{
//	private DocumentBuilder documentBuilder;
//	private AcceptedAddresses events;
//	private ITableDisplay table;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//	private XPathFactory factory;
//	private Mutex block;
//
//	public PlayerChoice(IxAddress baseAddr, ITableDisplay table, Mutex block)
//	{
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//		}
//
//		this.table = table;
//		this.baseAddr = baseAddr;
//		this.block = block;
//
//		factory = XPathFactory.newInstance();
//
//		try {
//			addr = baseAddr.append("PlayerChoice");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//		events = new AcceptedAddresses(addr);
//	}
//
//	//////////////////////////////////////////////////////////////
//	// Receiving functions
//	/////////////////////////////////////////////////////////////
//
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		Choice c;
//		Answer a;
//
//		String choiceId = "";
//		String question = "";
//		int answerId = 0;
//		String value	= "";
//		Element answer;
//
//		NodeList nodes = null;
//
//		XPath path;
//		path = factory.newXPath();
//
//		try {
//			choiceId = path.evaluate("/choice/@id", doc);
//			question = path.evaluate("/choice/@question", doc);
//
//			c = new Choice(choiceId, question);
//
//			nodes = (NodeList)path.evaluate("/choice/answers/answer", doc, XPathConstants.NODESET);
//
//			if (nodes == null)
//				return;
//
//			for (int i = 0; i < nodes.getLength(); i++)
//			{
//				answer = (Element)nodes.item(i);
//
//				try {
//					answerId = Integer.parseInt(answer.getAttribute("id"));
//					value = answer.getAttribute("value");
//
//					a = new Answer(answerId, value);
//					c.add(a);
//				} catch (NumberFormatException ex) {
//
//				}
//			}
//
//			table.setChoice(c);
//			block.sendNotice();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
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
//	////////////////////////////////////////////////
//	/// sending functions
//	////////////////////////////////////////////////
//	public void answer(IxManager returnTunnel, ChoiceResponse c)
//	{
//		Document doc;
//
//		Element choice;
//		Element answer;
//		Answer a;
//
//		if (c == null)
//			return;
//
//		a = c.getAnswer();
//
//		if (a == null)
//			return;
//
//		doc = documentBuilder.newDocument();
//		choice = doc.createElement("choice");
//		//choice.setAttribute("id", c.getId());
//		//choice.setAttribute("question", c.getQuestion());
//		doc.appendChild(choice);
//
//		answer = doc.createElement("answer");
//		answer.setAttribute("id", "" + a.getId());
//		answer.setAttribute("value", a.getValue());
//		choice.appendChild(answer);
//
//		try {
//			returnTunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//}
