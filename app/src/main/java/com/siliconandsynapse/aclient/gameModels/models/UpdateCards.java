package com.siliconandsynapse.aclient.gameModels.models;


import com.siliconandsynapse.ixcpp.protocol.game.TableChange;

import java.util.List;

public interface UpdateCards {
	public void cardChanged(CardUpdateEvent change);
	public void cardRemoved(CardUpdateEvent change);
	public void cardAdded(CardUpdateEvent change);

	List<TableChange.TableChangeObjStack> stackPreprocessor(List<TableChange.TableChangeObjStack> stack);
	public void changeFinished();
}
