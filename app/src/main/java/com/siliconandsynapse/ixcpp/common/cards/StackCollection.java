package com.siliconandsynapse.ixcpp.common.cards;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public class StackCollection {

	
	private Hashtable<String, CardStack> stacks;
	
	public StackCollection() {
		
		stacks = new Hashtable<String, CardStack>();
	}
	
	public void accept(StackCollectionVisitor visitor) {
		
		visitor.visit(this);
		
		for (String key : stacks.keySet()) {
			
			CardStack stack = stacks.get(key);
			visitor.visit(stack);
		}
		
	}
	
	public CardStack get(String addr) {
		return stacks.get(addr);
	}
	
	public Set<String> keys() {
		return stacks.keySet();
	}
	
	public void put(String addr, CardStack stack) {
		stacks.put(addr, stack);
	}
	
	public void remove(Card card) {
		
		CardStack cards;
		
		for (Enumeration<String> e = stacks.keys() ; e.hasMoreElements() ;)
		{
			cards = stacks.get(e.nextElement());
			if (cards == null)
				continue;
			cards.remove(card);
			
		}
	}
	
}
