//package com.siliconandsynapse.ixcpp.protocol;
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
//
//public class Pause implements IxReciever
//{
//	private AcceptedAddresses events;
//
//	private IxAddress addr;
//	private IxAddress baseAddr;
//
//	public Pause(IxAddress baseAddr)
//	{
//		super();
//
//		this.baseAddr = baseAddr;
//
//		try {
//			addr = baseAddr.append("pause");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//	}
//
//	//////////////////////////////////////////////////
//	//IxReciever functions
//	//////////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		XPath path;
//		String time = "0";
//		int seconds = 0;
//
//		path = XPathFactory.newInstance().newXPath();
//
//		try {
//			time = path.evaluate("/pause/@sec", doc);
//			seconds = Integer.parseInt(time);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("Pause " + seconds);
//
//		try {
//			Thread.sleep(seconds * 1000);
//		} catch (InterruptedException e) {
//			System.out.println("here");
//		}
//
//
//
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
//}
