package com.siliconandsynapse.aclient.gameModels;

import java.util.Hashtable;

public class StackModel implements TableVistable {

	private String name;
	private int id;
	private Hashtable<Integer, CardModel> cards;
	
	private int maxPos = 0;
	
	public StackModel(String name, int id) {
		this.name = name;
		this.id = id;
		cards = new Hashtable<Integer, CardModel>();
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	//public void addCard(CardModel cardModel) {
	//	cards.add(cardModel);
	//}
	
	public CardModel getTop() {
		
		for (int i = maxPos; i >= 0; i--) {
			if (cards.containsKey(i)) {
				CardModel c = cards.get(i);
				
				if (c.get() != null) {
					return c;
				}
			}
		}
		
		return null;
		
	}
	
	public CardModel getCard(int position) {
		
		if (maxPos < position) {
			maxPos = position;
		}
		
		if (cards.containsKey(position) == false) {
			cards.put(position, new CardModel(position));
		}
		
		return cards.get(position);
	}

	@Override
	public void accept(TableVisitor visitor) {
		
		visitor.visit(this);
		
		for (Integer position : cards.keySet()) {
			
			cards.get(position).accept(visitor);
			
		}
		
	}
	
}
