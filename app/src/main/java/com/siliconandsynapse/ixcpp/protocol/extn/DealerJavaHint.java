//package com.siliconandsynapse.ixcpp.protocol.extn;
//
//import java.util.*;
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.ixcpp.util.Mutex;
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//import javax.xml.parsers.*;
//import javax.xml.xpath.*;
//
//public class DealerJavaHint implements IxReciever
//{
//	private AcceptedAddresses events;
//	private DocumentBuilder documentBuilder;
//	private IxAddress baseAddr;
//	private IxAddress addr;
//	private Hashtable<String, Mutex> wait;
//	private Hashtable<String, String> answers;
//
//
//	public DealerJavaHint(IxAddress baseAddr)
//	{
//		try {
//			documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		} catch (Exception e) {
//		}
//		this.baseAddr = baseAddr;
//
//		try {
//			addr = baseAddr.append("DealerJavaHint");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//		wait = new Hashtable<String, Mutex>();
//		answers = new Hashtable<String, String>();
//	}
//
//	//////////////////////////////////////////////////////////
//	// Receiving Functions
//	///////////////////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//
//		//Find dealer name //////////////////////////////////////////
//		XPath path;
//		String dealerId;
//		String classId;
//
//		try {
//			path = XPathFactory.newInstance().newXPath();
//			dealerId = path.evaluate("/JavaUIHint/table/@id", doc);
//			classId = path.evaluate("/JavaUIHint/table/@className", doc);
//
//			if (answers.containsKey(dealerId) == false)
//			{
//				answers.put(dealerId, classId);
//			}
//			wait.get(dealerId).sendNotice();
//
//			//TODO, check for dealerId, don't assume mutex exists
//
//		} catch (Exception e) {
//			return;
//		}
//
//	}
//
//	/************************************************
//	* Do not call from single thread, this function must be threaded.
//	*
//	************************************************/
//	public String getDealerHint(IxManager tunnel, String dealer)
//	{
//		Document doc;
//		Element message;
//		Mutex request;
//
//		//check if this dealer has been asked for
//		if (answers.containsKey(dealer) == true)
//			return answers.get(dealer);
//
//
//		//request the dealer information from the server
//		doc = documentBuilder.newDocument();
//		message = doc.createElement("JavaUIHint");
//		message.setAttribute("id", dealer);
//		doc.appendChild(message);
//
//		try {
//			tunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		//TODO, race condition... create mutex first, send request, then wait...
//
//		//Wait from the server
//		request = new Mutex();
//		wait.put(dealer, request);
//		request.waitFor();
//
//		//remove mutex from hashtable, else memory leak
//
//		return answers.get(dealer);  //return class for dealer.
//
//	}
//
//	public String getDealerHint(String dealer)
//	{
//		if (answers.containsKey(dealer) == true)
//			return answers.get(dealer);
//		else
//			return null;
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
//}
