package com.siliconandsynapse.aclient.gameModels;

import com.siliconandsynapse.ixcpp.common.cards.Card;

public class CardModel implements TableVistable {

	private int position;
	private Card card;
	private CardState state;
	
	public CardModel(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
	
	public void setState(CardState state) {
		this.state = state;
	}
	
	public CardState getState() {
		return state;
	}
	
	public void set(Card card) {
		this.card = card;
	}
	
	public Card get() {
		
		return card;
	}

	@Override
	public void accept(TableVisitor visitor) {

		visitor.visit(this);
		
	}
}
