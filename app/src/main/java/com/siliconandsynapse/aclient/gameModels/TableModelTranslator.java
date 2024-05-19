package com.siliconandsynapse.aclient.gameModels;

import com.siliconandsynapse.aclient.gameModels.models.CardUpdateEvent;
import com.siliconandsynapse.aclient.gameModels.models.UpdateCards;
import com.siliconandsynapse.ixcpp.common.cards.Card;
import com.siliconandsynapse.ixcpp.gameInteraction.TableCardEventHandler;
import com.siliconandsynapse.observerPool.ObserverPool;

public class TableModelTranslator implements TableCardEventHandler {

	private PlayerCollection collection;
	private ObserverPool<UpdateCards> cardUpdate;
	private boolean isTrickStack;
	
	public TableModelTranslator(PlayerCollection collection, ObserverPool<UpdateCards> cardUpdate, boolean isTrickStack) {
		
		this.cardUpdate = cardUpdate;
		this.collection = collection;
		this.isTrickStack = isTrickStack;
		
	}
	
	
	@Override
	public void invalidateAllCards() {
		
		collection.accept(new DefaultTableVisitor() {

			@Override
			public void visit(CardModel card) {
				
				if (card.get() == null)
					card.setState(CardState.NOOP);
				else
					card.setState(CardState.REMOVE);
			}
			
		});
		
	}
	
	@Override
	public void validateCard(int playerId, String stackType, int stackId, int level,
			Card card) {
		
		CardModel current;
		
		if (isTrickStack)
			current = collection.getPlayerFromServer(playerId).get("trick", stackId).getCard(0);
		else 
			current = collection.getPlayerFromServer(playerId).get(stackType, stackId).getCard(level);
		
		if (current.get() == null) {
			current.set(card);
			current.setState(CardState.ADD);
		} else {
		
			if (current.get().valuesEquals(card)) {
				current.setState(CardState.NOOP);
			} else {
	
				current.set(card);
				current.setState(CardState.CHANGED);
				
			}
		}
	}
	
	@Override
	public void removeNonvalidatedCards() {
		
		//notify clients
		collection.accept(new DefaultTableVisitor() {

			CardUpdateEvent change = new CardUpdateEvent();
			
			@Override
			public void visit(PlayerModel player) {
				change.player = player.getId();
			}

			@Override
			public void visit(StackModel stack) {
				change.stackType = stack.getName();
				change.stack = stack.getId();
			}

			@Override
			public void visit(CardModel card) {
				
				change.card = null;
				change.position = card.getPosition();
				
				if (card.getState() == CardState.ADD) {
					change.card = card.get();
					cardUpdate.getDispatcher().cardAdded(change);
				}
				if (card.getState() == CardState.CHANGED) {
					change.card = card.get();
					cardUpdate.getDispatcher().cardChanged(change);
				}
				if (card.getState() == CardState.REMOVE) {
					cardUpdate.getDispatcher().cardRemoved(change);
				}
			}
			
		});
		
		//reset table
		collection.accept(new DefaultTableVisitor() {

			@Override
			public void visit(CardModel card) {
				if (card.getState() == CardState.REMOVE)
					card.set(null);
				
				card.setState(CardState.NOOP);
			}
			
		});
		cardUpdate.getDispatcher().changeFinished();
		
	}
	
	
}
