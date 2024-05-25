package com.siliconandsynapse.net.ixtunnel;


import org.w3c.dom.*;

public interface IxReciever
{

	void accept(IxAddress key, IxManager returnTunnel, Message doc);
    String placeInThread();
	AcceptedAddresses getEvents();

}


