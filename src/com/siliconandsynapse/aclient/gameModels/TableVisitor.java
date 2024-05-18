package com.siliconandsynapse.aclient.gameModels;

public interface TableVisitor {

	public void visit(PlayerCollection players);
	public void visit(PlayerModel player);
	public void visit(StackModel stack);
	public void visit(CardModel card);
	
}
