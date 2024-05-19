package com.siliconandsynapse.aclient.game;

import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACard;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.IxManager;

import android.util.Log;

public class GameServiceCard implements Runnable {

	private Thread t;
	
	private Mutex cardServerBlock;
	private Mutex cardUserBlock;
	private GameService service;
	private PlayerPickACard playerPickACard;
	private IxManager home;
	
	private volatile boolean keepRunning = true;
	
	public GameServiceCard(Mutex cardServerBlock, Mutex cardUserBlock, GameService service, 
			PlayerPickACard playerPickACard, IxManager home) {
		this.cardServerBlock = cardServerBlock;
		this.cardUserBlock = cardUserBlock;
		this.service = service;
		this.playerPickACard = playerPickACard;
		this.home = home;
		
		t = new Thread(this);
		t.setName("GameServiceCard");
		t.setDaemon(true);
		t.start();
	}

	public void stop() {

		keepRunning = false;
		t.interrupt();
		
	}
	
	public void join() {
		
		try {
			t.join();
		} catch (InterruptedException e) {

		}
	}
	
	@Override
	public void run() {
		
		while (keepRunning)
		{
			try {
				Log.d("DebugPrint", "Waiting for server request");
				
				cardServerBlock.waitForInterruptable();
	
				Log.d("DebugPrint", "Server requested Card");
				
				while (keepRunning) {
					service.setCard(null);	
					Log.d("DebugPrint", "Waiting for user card");
					
					cardUserBlock.waitForInterruptable();
					
					Log.d("DebugPrint", "User unlocked card");
					
					if (service.getCard() != null) {
						playerPickACard.sendPlayCard(home, service.getCard());
						break;
					}
	
			
				}
				
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	

}
