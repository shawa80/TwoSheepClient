package com.siliconandsynapse.aclient;

import org.w3c.dom.Document;

import android.util.Log;

import com.siliconandsynapse.net.ixtunnel.AcceptedAddresses;
import com.siliconandsynapse.net.ixtunnel.IxAddress;
import com.siliconandsynapse.net.ixtunnel.IxManager;
import com.siliconandsynapse.net.ixtunnel.IxReciever;

public class DebugPrinter implements IxReciever {

	@Override
	public void accept(IxAddress key, IxManager returnTunnel, Document doc) {

		Log.d("DebugPrint", key.toString());
	}

	@Override
	public String placeInThread() {

		return null;
	}

	@Override
	public AcceptedAddresses getEvents() {

		return null;
	}

}
