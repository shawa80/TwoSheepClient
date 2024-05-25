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
//
//public class HeartBeat implements IxReciever
//{
//	private AcceptedAddresses events;
//	private IxAddress addr;
//
//
//	public HeartBeat()
//	{
//		super();
//
//
//		try {
//			addr = IxAddress.parse("__System.HeartBeat");
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
//		try {
//			returnTunnel.sendDocument(addr, doc);
//		} catch (Exception e) {
//			return;
//		}
//
//	}
//	public String placeInThread()
//	{
//		return addr.toString();	//Say we are thread safe
//	}
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//	}
//
//}
