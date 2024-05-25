package com.siliconandsynapse.net.ixtunnel;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class AcceptedAddresses implements Iterable<IxAddress> {

	private final ArrayList<IxAddress> addresses = new ArrayList<IxAddress>();

	public AcceptedAddresses(IxAddress... addresses) {

        Collections.addAll(this.addresses, addresses);
	}

	@NonNull
	@Override
	public Iterator<IxAddress> iterator() {
		return addresses.iterator();
	}


}
