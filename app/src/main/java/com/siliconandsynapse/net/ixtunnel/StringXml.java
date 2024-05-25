//package com.siliconandsynapse.net.ixtunnel;
//
//import javax.xml.parsers.*;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.w3c.dom.*;
//import org.xml.sax.*;
//
//import java.io.*;
//
//public class StringXml
//{
//	//private DOMImplementationLS ls;
//	private DocumentBuilder parser;
//
//	public StringXml()
//	{
//		/*try {
//			ls = (DOMImplementationLS)DocumentBuilderFactory
//				.newInstance()
//				.newDocumentBuilder()
//				.getDOMImplementation()
//				.getFeature("LS", "3.0");
//
//
//			if (ls == null)
//				throw new Exception("ls is null");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//*/
//
//		try {
//			parser = DocumentBuilderFactory
//				.newInstance()
//				.newDocumentBuilder();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public String createString(Document doc)
//	{
//
//		try
//	    {
//	       DOMSource domSource = new DOMSource(doc);
//	       StringWriter writer = new StringWriter();
//	       StreamResult result = new StreamResult(writer);
//	       TransformerFactory tf = TransformerFactory.newInstance();
//	       Transformer transformer = tf.newTransformer();
//	       transformer.setOutputProperty("omit-xml-declaration", "yes");
//	       transformer.transform(domSource, result);
//
//
//	       return writer.toString();
//	    }
//	    catch(TransformerException ex)
//	    {
//	       ex.printStackTrace();
//	       return null;
//	    }
//
//
//
//		/*String xml = "";
//		try {
//			LSSerializer ser = ls.createLSSerializer();
//			xml = ser.writeToString(doc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return xml;*/
//	}
//	public Document createDoc(String xml)
//	{
//		try {
//
//			Document doc = parser.parse(new InputSource(new StringReader(xml)));
//
//
//			return doc;
//		} catch (Exception e) {
//			return null;
//		}
//	}
//}
