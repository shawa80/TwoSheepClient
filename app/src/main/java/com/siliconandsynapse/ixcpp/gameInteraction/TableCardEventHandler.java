package com.siliconandsynapse.ixcpp.gameInteraction;

import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.protocol.game.TableChange;

import java.util.List;

public interface TableCardEventHandler
{

	public abstract void validateCard(int player, 
		String stackType, int stackId, int level, Card card);
	public abstract void invalidateAllCards();
	public abstract void removeNonvalidatedCards();

	public abstract List<TableChange.TableChangeObjStack> stackPreprocessor(List<TableChange.TableChangeObjStack> stack);
}
