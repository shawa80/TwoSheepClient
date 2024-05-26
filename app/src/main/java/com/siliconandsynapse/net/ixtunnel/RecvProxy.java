package com.siliconandsynapse.net.ixtunnel;


import org.w3c.dom.*;

class RecvProxy implements Runnable
{
	private IxReciever recv;
	private IxManager ret;
	private String doc;
	private IxAddress key;

	public RecvProxy(IxReciever r, IxAddress k, IxManager re, String d)
	{
		recv = r;
		ret = re;
		doc = d;
		key = k;
	}
	public void run()
	{
		recv.accept(key, ret, doc);
	}

	public String toString()
	{
		return key.toString();
	}
}

