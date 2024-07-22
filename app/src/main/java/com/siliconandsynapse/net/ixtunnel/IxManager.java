package com.siliconandsynapse.net.ixtunnel;


import java.net.*;
import java.io.*;

import java.util.*;


public class IxManager extends IxTunnel
{
	private Thread reciever;
	private Hashtable<IxAddress, IxReceiver> handlers;
	private IxReceiver allHandler;

	public IxManager(Socket client) throws IOException
	{
		this(client, new Vector<IxReceiver>());
	}
	public IxManager(Socket client, Vector<IxReceiver> bootRecievers) throws IOException
	{
		super(client);

		handlers = new Hashtable<IxAddress, IxReceiver>();

		IxReceiver bootStrap;

		bootRecievers.forEach(this::registerReceiver);

		reciever = new Thread(new HandleEvents(this));
		reciever.setName("IxManager.reciever.");
		reciever.setDaemon(true);
		reciever.start();

	}

	public void sendDocument(String doc) throws IOException
	{
		throw new IOException("IxManager does not support direct tunnel writing");
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
					KeyDocPair pair = tunnel.recieveDocument();

					key = pair.getKey();
					doc = pair.getDoc();

					if (allHandler != null)
						allHandler.accept(key, tunnel, doc);

					recv = handlers.get(key);
					if (recv == null)
						continue;

					recv.accept(key, tunnel, doc);
				} catch (Exception ex) {
					System.out.println(key.getFullPath());
				}
			}

		}
	}

}


