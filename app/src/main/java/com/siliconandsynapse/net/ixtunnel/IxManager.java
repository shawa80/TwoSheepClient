package com.siliconandsynapse.net.ixtunnel;


import java.net.*;
import java.io.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import java.util.*;

import javax.xml.xpath.*;

import java.util.concurrent.*;



public class IxManager extends IxTunnel
{
	//private DocumentBuilder documentBuilder;
	//private IxManager self;

	//threads
	private Thread reciever;
	//private Thread sender;
	//private LinkedBlockingQueue<KeyDocPair> sendQ;

	//Shandlers
	private Hashtable<IxAddress, IxReciever> handlers;
	//private IxReciever defaultHandler;
	//private IxReciever allHandler;

	//object that want to be notified when the connection closes
	//private Vector<ConnectionNotifier> closed;

	//Session information
	//private Hashtable<String, Object> session;



	public IxManager()
	{
		this(new Vector<IxReciever>());
	}
	public IxManager(Vector<IxReciever> bootRecievers)
	{
		super();
		//self = this;

		handlers = new Hashtable<IxAddress, IxReciever>();
		//defaultHandler = null;
		//closed = new Vector<ConnectionNotifier>();

		//session = new Hashtable<String, Object>();

		IxReciever bootStrap;

		bootRecievers.forEach(this::registerReceiver);


		reciever = new Thread(new HandleEvents(this));
		reciever.setName("IxManager.reciever.");
		reciever.setDaemon(true);
		reciever.start();
//
//
//		sendQ = new LinkedBlockingQueue<KeyDocPair>();
//		sender = new Thread(new HandleSends(this));
//		sender.setName("IxManager.sender.");
//		sender.setDaemon(true);
//		sender.start();
	}


	////////////////////////////////////////////////////////////////////////////////
	/// Session
	///////////////////////////////////////////////////////////////////////////////
//	public void putSession(String key, Object value)
//	{
//		session.put(key, value);
//	}
//	public Object getSession(String key)
//	{
//		return session.get(key);
//	}

	//////////////////////////////////////////////////////////////////////////////////
	// prevent any one from directly reading or writing to the tunnel using the IxTunnel1 interface
	/////////////////////////////////////////////////////////////////////////////////
	public void sendDocument(String doc) throws IOException
	{
		throw new IOException("IxManager does not support direct tunnel writing");
	}

	public void sendDocument(IxAddress key, String sendDoc)
	{
		super.sendDocument(new KeyDocPair(key,sendDoc));
	}
	public void sendDocumentFromServer(IxAddress key, String sendDoc) {
		super.sendDocumentFromServer(new KeyDocPair(key, sendDoc));
	}


	//////////////////////////////////////////////////////////////////
	// Register closed connection
	//////////////////////////////////////////////////////////////////
//	public synchronized void registerConnectionNotifier(ConnectionNotifier c)
//	{
//		closed.add(c);
//	}
//	public synchronized void unregisterConnectionNotifier(ConnectionNotifier c)
//	{
//		closed.remove(c);
//	}

	///////////////////////////////////////////////////////////////////
	// Un/Register a handler to process data from tunnel
	///////////////////////////////////////////////////////////////////
	//runs only when a register event is not found.
//	public synchronized void registerDefaultReciever(IxReciever handle)
//	{
//		defaultHandler = handle;
//	}
//
//	//runs for every event
//	public synchronized void registerAllReciever(IxReciever handle)
//	{
//		allHandler = handle;
//	}
	//runs for registered event
	public synchronized void registerReceiver(IxReciever handle)
	{
		AcceptedAddresses keys;

		keys = handle.getEvents();

		for (IxAddress key : keys) {
			//add receiver to receiver table
			if (handlers.containsKey(key))
				continue;

			handlers.put(key, handle);
		}

	}
//	public synchronized void unregisterReciever(IxReciever handle)
//	{
//		//IxReciever recv;
//		AcceptedAddresses keys;
//
//		keys = handle.getEvents();
//
//		for (IxAddress key : keys) {
//
//			if (handlers.containsKey(key) == true)
//			{
//				handlers.remove(key);
//			}
//		}
//
//	}
//	public synchronized void unregisterReciever(IxAddress key)
//	{
//		handlers.remove(key);
//	}
//
//	private class HandleSends implements Runnable
//	{
//		IxManager connection;
//		public HandleSends(IxManager connection)
//		{
//			this.connection = connection;
//		}
//
//		public void run()
//		{
//			KeyDocPair pair;
//			IxAddress key;
//			Message sendDoc;
//
//			while (true)
//			{
//				pair = null;
//				try {
//					pair = sendQ.take();
//
//				} catch (InterruptedException ie) {
//					break;
//				} catch (Exception e) {
//					continue;
//				}
//				key = pair.getKey();
//				sendDoc = pair.getDoc();
//
//
//				try {
//					connection.sendDocumentSuper(sendDoc);
//				} catch (Exception e) {
//					break;
//				}
//			}
//		}
//
//	}

//
	private class HandleEvents implements Runnable
	{
		private IxManager tunnel;
		public HandleEvents(IxManager tunnel) {
			this.tunnel = tunnel;
		}
		public void run()
		{
			IxReciever recv;
			IxAddress key;

			String doc;

			while (true)
			{

				try {
				KeyDocPair pair = tunnel.recieveDocument();

				key = pair.getKey();
				doc = pair.getDoc();

				recv = handlers.get(key);
				if (recv == null)
					continue;

				recv.accept(key, tunnel, doc);
				} catch (Exception ex) {

					System.out.println();
				}
			}

		}
	}

}


