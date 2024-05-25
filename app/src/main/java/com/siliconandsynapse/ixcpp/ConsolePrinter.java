//package com.siliconandsynapse.ixcpp;
//
//import org.w3c.dom.Document;
//
//import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
//import com.siliconandsynapse.net.ixtunnel.IxAddress;
//import com.siliconandsynapse.net.ixtunnel.IxManager;
//import com.siliconandsynapse.net.ixtunnel.IxReciever;
//import com.siliconandsynapse.net.ixtunnel.StringXml;
//
//public class ConsolePrinter implements IxReciever {
//
//	@Override
//	public void accept(IxAddress key, IxManager returnTunnel, Document doc) {
//
//		System.out.println(key.toString());
//
//		StringXml xml = new StringXml();
//		System.out.println(xml.createString(doc));
//	}
//
//	@Override
//	public String placeInThread() {
//
//		return null;
//	}
//
//	@Override
//	public AcceptedAddresses getEvents() {
//
//		return null;
//	}
//
//}
