package com.siliconandsynapse.aclient.game;

import com.siliconandsynapse.ixcpp.protocol.game.PlayerPickACard;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.IxManager;

public class GameServiceCard implements Runnable {

	private final Thread t;

	private final Mutex cardServerBlock;
	private final Mutex cardUserBlock;
	private final GameService service;
	private final PlayerPickACard playerPickACard;
	private final IxManager home;

	private volatile boolean keepRunning = true;

	public GameServiceCard(Mutex cardServerBlock,
						   Mutex cardUserBlock,
						   GameService service,
						   PlayerPickACard playerPickACard,
						   IxManager home) {

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
		} catch (InterruptedException ignored) {

		}
	}

	@Override
	public void run() {

		while (keepRunning)
		{
			try {

				cardServerBlock.waitForInterruptable();

				while (keepRunning) {
					service.setCard(null);

					cardUserBlock.waitForInterruptable();

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
