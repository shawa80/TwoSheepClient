package com.siliconandsynapse.net.ixtunnel;


import com.siliconandsynapse.observerPool.ObserverPool;

import java.net.*;
import java.io.*;

import java.util.*;


public class IxManager extends IxTunnel
{
	private Thread reciever;
	private Hashtable<IxAddress, IxReceiver> handlers;
	private IxReceiver allHandler;

	public interface ConnectionCloseEvent { void closed(IxManager mng);}

	public ObserverPool<ConnectionCloseEvent> connectionClosedListner
			= new ObserverPool(ConnectionCloseEvent.class);

	public IxManager(Socket client) throws IOException
	{
		this(client, new Vector<IxReceiver>());
	}
	public IxManager(Socket client, Vector<IxReceiver> bootReceivers) throws IOException
	{
		super(client);

		handlers = new Hashtable<IxAddress, IxReceiver>();

		IxReceiver bootStrap;

		bootReceivers.forEach(this::registerReceiver);

		reciever = new Thread(new HandleEvents(this));
		reciever.setName("IxManager.reciever.");
		reciever.setDaemon(true);
		reciever.start();

	}

	public void sendDocument(IxAddress key, String sendDoc)
	{
		super.sendDocument(new KeyDocPair(key,sendDoc));
	}



	public synchronized void registerAllReciever(IxReceiver handle)
	{
		allHandler = handle;
	}

	public synchronized void registerReceiver(IxReceiver handle)
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
	public synchronized void unregisterReciever(IxReceiver handle)
	{
		AcceptedAddresses keys;

		keys = handle.getEvents();

		for (IxAddress key : keys) {

			if (handlers.containsKey(key) == true)
			{
				handlers.remove(key);
			}
		}

	}


	private class HandleEvents implements Runnable
	{
		private IxManager tunnel;
		public HandleEvents(IxManager tunnel) {
			this.tunnel = tunnel;
		}
		public void run()
		{
			IxReceiver recv;
			IxAddress key = null;

			String doc;

			while (true)
			{

				try {
					KeyDocPair pair = tunnel.receiveDocument();

					key = pair.getKey();
					doc = pair.getDoc();

					if (allHandler != null)
						allHandler.accept(key, tunnel, doc);

					recv = handlers.get(key);
					if (recv == null)
						continue;

					recv.accept(key, tunnel, doc);
				} catch (Exception ex) {
					break;
				}
			}
			connectionClosedListner.getDispatcher().closed(tunnel);
		}
	}

}


