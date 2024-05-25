//package com.siliconandsynapse.net.ixtunnel;
//
//import javax.xml.xpath.*;
//import java.io.*;
//import javax.xml.parsers.*;
//import org.xml.sax.*;
//import org.w3c.dom.*;
//import org.w3c.dom.ls.*;
//
//public class xpath
//{
//	public static void main(String args[])
//	{
//		XPath path = XPathFactory.newInstance().newXPath();
//		Document doc = null;
//
//		try {
//			doc = DocumentBuilderFactory
//				.newInstance()
//				.newDocumentBuilder()
//				.parse(new InputSource(new StringReader("<test><moo test='hello'/>hello</test>")));
//		DOMImplementationLS ls = (DOMImplementationLS)DocumentBuilderFactory
//			.newInstance()
//			.newDocumentBuilder()
//			.getDOMImplementation()
//			.getFeature("LS", "3.0");
//
//			System.out.println(ls.createLSSerializer().writeToString(doc));
//		} catch (Exception e) {
//
//		}
//
//		try {
////			System.out.print(path.evaluate("/*",
////				new InputSource(new StringReader("<test><moo test='hello'/>hellp</test>"))));
//			System.out.print(path.evaluate("/*", doc));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//
//}
