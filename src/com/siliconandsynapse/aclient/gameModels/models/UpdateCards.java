package com.siliconandsynapse.aclient.gameModels.models;


public interface UpdateCards {
	public void cardChanged(CardUpdateEvent change);
	public void cardRemoved(CardUpdateEvent change);
	public void cardAdded(CardUpdateEvent change);
	
	public void changeFinished();
}
