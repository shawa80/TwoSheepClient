//package com.siliconandsynapse.net.ixtunnel;
//
//
//class RecvProxy implements Runnable
//{
//	private IxReceiver recv;
//	private IxManager ret;
//	private String doc;
//	private IxAddress key;
//
//	public RecvProxy(IxReceiver r, IxAddress k, IxManager re, String d)
//	{
//		recv = r;
//		ret = re;
//		doc = d;
//		key = k;
//	}
//	public void run()
//	{
//		recv.accept(key, ret, doc);
//	}
//
//	public String toString()
//	{
//		return key.toString();
//	}
//}
//
