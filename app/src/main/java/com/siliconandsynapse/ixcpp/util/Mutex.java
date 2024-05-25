package com.siliconandsynapse.ixcpp.util;


public class Mutex
{
	private boolean ready;

	public Mutex()
	{
		ready = false;
	}
	public synchronized void sendNotice()
	{
		ready = true;
		notify();

	}
	public synchronized boolean isReady()
	{
		return ready;
	}

	public synchronized void waitFor()
	{
		try {
			while (ready == false)
			{
				wait();
			}
			ready = false;
		} catch (Exception e) {
		}
	}
	public synchronized void waitForInterruptable() throws InterruptedException
	{
		while (ready == false)
		{
			wait();
		}
		ready = false;
	}

}
