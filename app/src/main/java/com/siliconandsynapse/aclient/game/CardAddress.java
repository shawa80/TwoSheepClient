package com.siliconandsynapse.aclient.game;

import com.siliconandsynapse.aclient.gameModels.models.CardUpdateEvent;

public class CardAddress {

	private int player;
	private String type;
	private int stack;
	private int layer;
	
	private String addr;
	
	public CardAddress(CardUpdateEvent event) {
		
		this.player = event.player;
		this.type = event.stackType;
		this.stack = event.stack;
		this.layer = event.position;
		
		addr = player + " " + type + " " + stack + " " + layer;
	}
	
	public CardAddress(int player, String type, int stack, int layer) {
		this.player = player;
		this.type = type;
		this.stack = stack;
		this.layer = layer;
		
		addr = player + " " + type + " " + stack + " " + layer;
	}
	
	@Override
	public boolean equals(Object o) {

		if (o instanceof CardAddress)
		{
			CardAddress ca = (CardAddress)o;
			
			return addr.equals(ca.addr);
		}
		
		return false;
	}

	@Override
	public int hashCode() {

		return addr.hashCode();
	}

}


