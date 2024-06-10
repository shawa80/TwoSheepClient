package com.siliconandsynapse.ixcpp.gameInteraction;

import com.siliconandsynapse.ixcpp.common.cards.Card;

public interface TableCardEventHandler
{

	public abstract void validateCard(int player, 
		String stackType, int stackId, int level, Card card);
	public abstract void invalidateAllCards();
	public abstract void removeNonvalidatedCards();
}
