//package com.siliconandsynapse.ixcpp.protocol.lobby;
//
//
//import org.w3c.dom.*;
//
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.ParseError;
//
//public class Welcome implements IxReciever
//{
//	private AcceptedAddresses events;
//
//	private IxAddress baseAddr;
//	private IxAddress addr;
//
//	private ProtocalMonitor monitor;
//
//	public interface ProtocalMonitor
//	{
//		public void welcome();
//
//
//	}
//
//	public Welcome(IxAddress baseAddr, ProtocalMonitor monitor)
//	{
//
//
//		this.baseAddr = baseAddr;
//		this.monitor = monitor;
//
//
//		try {
//			addr = baseAddr.append("Welcome");
//		} catch (ParseError e) {
//			e.printStackTrace();
//		}
//
//		events = new AcceptedAddresses(addr);
//
//
//	}
//
//
//
//	//////////////////////////////////////////////////////////
//	// Receiving Functions
//	//////////////////////////////////////////////////////////
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc)
//	{
//		monitor.welcome();
//
//	}
//	public String placeInThread()
//	{
//		return baseAddr.toString();
//	}
//	public AcceptedAddresses getEvents()
//	{
//		return events;
//
//	}
//
//}
