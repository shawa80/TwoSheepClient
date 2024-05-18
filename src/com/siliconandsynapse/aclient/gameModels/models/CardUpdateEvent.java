package com.siliconandsynapse.aclient.gameModels.models;

import com.siliconandsynapse.ixcpp.common.cards.Card;

public class CardUpdateEvent {
	public int player;
	public String stackType;
	public int stack;
	public int position;
	public Card card;
}
