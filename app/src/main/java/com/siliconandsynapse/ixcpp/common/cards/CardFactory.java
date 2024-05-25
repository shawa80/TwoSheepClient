package com.siliconandsynapse.ixcpp.common.cards;

import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.siliconandsynapse.ixcpp.common.cards.types.CorsorryCardFactory;
import com.siliconandsynapse.ixcpp.common.cards.types.EuchreCardFactory;
import com.siliconandsynapse.ixcpp.common.cards.types.PokerCardFactory;
import com.siliconandsynapse.ixcpp.common.cards.types.SheepsHeadFactory;

public abstract class CardFactory {

	public static final String POKER = "Poker";
	public static final String CORSORRY = "Corsorry";
	public static final String SHEEPSHEAD = "Sheeps Head";
	public static final String EUCHRE = "Euchre";
	
	
	public static CardFactory getInstance(String set) {
		
		if (POKER.equals(set)) {
			return new PokerCardFactory();
		} else if (CORSORRY.equals(set)) {
			return new CorsorryCardFactory();
		} else if (SHEEPSHEAD.equals(set)) {
			return new SheepsHeadFactory();
		} else if (EUCHRE.equals(set)) {
			return new EuchreCardFactory();
		}
		
		return null;
	}
	

	private Hashtable<Integer, Card> codes;

	protected CardFactory() {
		clearCache();
	}
	
	///////////////////////////////////////////////////
	//clearing cards
	///////////////////////////////////////////////////
	public synchronized void clearCache()
	{
		codes = new Hashtable<Integer, Card>();		
	}	
	
	//privates
	protected int generateCode()
	{

		int i = 0;
		Random r = new Random();

		do {
			i = r.nextInt(999);
		} while (codes.containsKey(i));

		return i;
	}
	
	protected synchronized void addCard(int secureCode, Card card)
	{
		codes.put(secureCode, card);

	}
	
	public abstract List<Card> getCards();
	public abstract Card createCard(int secureCode, String suite, String type);

	public synchronized Card getCard(int secureCode)
	{
		if (codes.containsKey(secureCode))
		{
			return codes.get(secureCode);
		}
		return null;
	}


	public Card getCardById(int secureCode) {
		
		if (codes.containsKey(secureCode))
		{
			return codes.get(secureCode);
		}
		return null;
	}
	
}
