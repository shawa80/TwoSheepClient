package com.siliconandsynapse.net.ixtunnel;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import org.w3c.dom.*;


public class IxTunnel
{
//	private Socket connection;
//	private DataInputStream in;
//	private DataOutputStream out;
//	private StringXml serializer;

	private final LinkedBlockingQueue<KeyDocPair> toServer;
	private final LinkedBlockingQueue<KeyDocPair> toClient;

	public IxTunnel()
	{
		toServer = new LinkedBlockingQueue<>();
		toClient = new LinkedBlockingQueue<>();
//		connection = k;
//		serializer = new StringXml();
//
//		connection.setTcpNoDelay(true);
//		connection.setPerformancePreferences(0, 1, 0);
//
//		try {
//			in = new DataInputStream(connection.getInputStream());
// 			out = new DataOutputStream(connection.getOutputStream());
//		} catch (Exception e) {
//			throw new IOException();
//		}
	}


	public void sendDocumentFromServer(KeyDocPair doc) {
		toClient.offer((doc));
	}

	public void sendDocument(KeyDocPair doc)
	{

		toServer.offer(doc);

//		//DOMImplementationLS ls;
//		String xml = null;
//		byte buffer[] = new byte[0];
//
//		synchronized (out) {
//
//			try {
//
//				xml = serializer.createString(doc);
//			} catch (Exception e) {
//				e.printStackTrace();
//
//				return;
//			}
//
//			try {
//
//				buffer = xml.getBytes("UTF-8");
//			} catch (Exception e) {
//				//e.printStackTrace();
//				return;
//			}
//
//			out.writeInt(buffer.length);
//			out.write(buffer, 0, buffer.length);
//			out.flush();
//		}
	}

	public KeyDocPair receiveDocumentFromServer()  {
		try {
			return toServer.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public KeyDocPair recieveDocument()
	{
		try {
			return toClient.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
//		int size = -1;
//		byte buffer[] = new byte[0];
//		String xml = "";
//		Document doc;
//
//		int readIn = 0;
//		synchronized (in) {
//
//			try {
//				size = in.readInt();
//				buffer = new byte[size];
//
//					while (readIn < size)
//					{
//						readIn += in.read(buffer, readIn, size-readIn);
//					}
//			} catch (IOException e) {
//				throw e;
//			}
//
//			try {
//				xml = new String(buffer, "UTF-8");
//			} catch (Exception e) {
//				xml = "";
//			}
//
//			if (size == 0)
//			{
//				return null;
//			}
//			try {
//
//				doc = serializer.createDoc(xml);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return null;
//			}
//		}
//
//		return doc;
	}

	/*****************************************
	* Closes the socket and tunnel.
	******************************************/
	public void close()
	{
//		try {
//			connection.close();
//		} catch (Exception e) {
//
//		}
	}
}
