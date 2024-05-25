package com.siliconandsynapse.ixcpp.common.cards;

import java.util.Stack;

public class CardStack {
	
	private Stack<Card> cards;
	
	public CardStack() {
		cards = new Stack<Card>();
	}
	
	public void accept(CardStackVisitor visitor) {
		
		visitor.visit(this);
		
		for (Card c : cards) {
			visitor.visit(c);
		}
	}

	
	public void push(Card card) {
		
		cards.push(card);
	}
	
	public Card peek() {
		return cards.peek();
	}
	
	public void remove(Card card) {
		cards.remove(card);
	}
	
	public int size() {
		return cards.size();
	}
	
	public Card elementAt(int i) {
		return cards.elementAt(i);
	}
}
