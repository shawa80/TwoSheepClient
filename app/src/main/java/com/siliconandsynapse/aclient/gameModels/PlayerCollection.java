package com.siliconandsynapse.aclient.gameModels;

import java.util.Hashtable;

public class PlayerCollection implements TableVistable {

	private Hashtable<Integer, PlayerModel> players = new Hashtable<Integer, PlayerModel>();
	
	private int myPlayerId;
	
	public void setMyPlayerId(int id) {
		this.myPlayerId = id;
	}
	
	public int size() {
		
		return players.size();
	}
	
	private int translatePlayerIdServerToGui(int serverPlayerId) {
		
		int totalPlayers = players.size();
		int guiPlayerId = (serverPlayerId - myPlayerId);

		return (guiPlayerId + totalPlayers) % totalPlayers;
		
	}
	
	public PlayerModel getPlayerFromGui(int id) {
		
		if (players.containsKey(id) == false) {
			players.put(id, new PlayerModel(id));
		}
		
		return players.get(id);
	}
	
	public PlayerModel getPlayerFromServer(int id) {
		
		id = translatePlayerIdServerToGui(id);
		
		if (players.containsKey(id) == false) {
			players.put(id, new PlayerModel(id));
			//return null;
		}
		
		return players.get(id);
	}
	
	@Override
	public void accept(TableVisitor visitor) {
		
		visitor.visit(this);
		
		for (Integer playerId : players.keySet()) {
			players.get(playerId).accept(visitor);
		}
		
	}

}
