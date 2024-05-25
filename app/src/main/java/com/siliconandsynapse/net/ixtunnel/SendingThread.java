package com.siliconandsynapse.net.ixtunnel;


import java.util.*;
import java.util.concurrent.*;

class SendingThread implements Runnable
{
	private Hashtable<String,SendingThread> namedThread;
	private String name;
	private LinkedBlockingQueue<RecvProxy> q;
	private Thread thread;

	public SendingThread(String name, Hashtable<String,SendingThread> namedThread)
	{
		this.namedThread = namedThread;
		this.name = name;
		q = new LinkedBlockingQueue<RecvProxy>();
		thread = new Thread(this);
		thread.setName(name);
		thread.start();

	}
	public void add(RecvProxy proxy)
	{
		try {
			q.put(proxy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run()
	{
		RecvProxy proxy;

		while (true)
		{
			try {
				proxy = q.poll(600, TimeUnit.SECONDS);

				//Check for a time out
				if (proxy == null)
				{
					//check for a time out, remove threads that haven't serviced anything in 5 minutes.
					//TODO check thread safety here
					synchronized(namedThread) {
						//make sure no one added a request
						if (q.peek() == null)
						{
							namedThread.remove(name);
							break;
						}
						else
						{
							continue;
						}
					}
				}
				else
				{
					proxy.run();
				}
			} catch (InterruptedException ie) {
				break;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}

	}

}
