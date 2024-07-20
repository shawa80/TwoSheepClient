package com.siliconandsynapse.net.ixtunnel;


public interface IxReceiver
{

	void accept(IxAddress key, IxManager returnTunnel, String doc);
    String placeInThread();
	AcceptedAddresses getEvents();

}


