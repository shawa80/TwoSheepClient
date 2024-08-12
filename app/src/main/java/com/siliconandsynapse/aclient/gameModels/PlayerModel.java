package com.siliconandsynapse.aclient.gameModels;

import java.util.Hashtable;

import com.siliconandsynapse.aclient.gameModels.models.UpdateUser;
import com.siliconandsynapse.observerPool.ObserverPool;

public class PlayerModel implements TableVistable {

	
	private Hashtable<String, StackModel> stacks;
	private int playerId;
	
	
	private ObserverPool<UpdateUser> userUpdate;
	public void addListener(UpdateUser listener) {
		userUpdate.add(listener);
	}
	public void removeListener(UpdateUser listener) {
		userUpdate.remove(listener);
	}
	
	
	
	public PlayerModel(int playerId) {
		
		this.playerId = playerId;
		stacks = new Hashtable<String, StackModel>();
	
		userUpdate = new ObserverPool<UpdateUser>(UpdateUser.class);
		
	}
	
	public void setTurn() {
		this.userUpdate.getDispatcher().turnChanged(this);
	}
	
	
	private String description;
	public void setDescription(String description) {
		userUpdate.getDispatcher().descriptionChanged(this, description);
	}
	
	public String getDescription() {
		return description;
	}
	
	
	private String name;
	public void setName(String name) {
		this.name = name;
		userUpdate.getDispatcher().nameChanged(this, name);
	}
	
	public String getName() {
		return name;
	}
	
	private int score;
	public void setScore(int score) {
		this.score = score;
		userUpdate.getDispatcher().scoreChanged(this, score);
	}
	
	public int getScore() {
		return score;
	}
	
	private int wealth;
	public void setWealth(int wealth) {
		this.wealth = wealth;
		userUpdate.getDispatcher().wealthChanged(this, wealth);
	}
	public int getWealth() {return wealth;}


	public void setId(int playerId) {
		this.playerId = playerId;
	}
	
	public int getId() {
		return playerId;
	}
	
	
	
	
	
	
	public StackModel get(String name, int id) {
		
		if (stacks.containsKey(name + id) == false) {
			stacks.put(name + id, new StackModel(name, id));
		}
		
		return stacks.get(name + id);
	}

	@Override
	public void accept(TableVisitor visitor) {

		visitor.visit(this);
		
		for (String name : stacks.keySet()) {
			stacks.get(name).accept(visitor);
		}
		
	}
	
	
}
