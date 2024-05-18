package com.siliconandsynapse.aclient.gameModels;

/**
 * adds default implementations to TableVisitors so they don't have to
 * @author shawa
 *
 */
public class DefaultTableVisitor implements TableVisitor {

	@Override
	public void visit(PlayerCollection players) {}

	@Override
	public void visit(PlayerModel player) {}

	@Override
	public void visit(StackModel stack) {}

	@Override
	public void visit(CardModel card) {}

}
