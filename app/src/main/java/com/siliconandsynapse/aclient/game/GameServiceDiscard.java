package com.siliconandsynapse.aclient.game;

import android.util.Log;

import com.siliconandsynapse.ixcpp.protocol.game.PlayerDiscard;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.IxManager;

public class GameServiceDiscard implements Runnable {

	private final Thread t;

	private final Mutex serverBlock;
	private final Mutex userBlock;
	private final GameService service;
	private final PlayerDiscard playerDiscard;
	private final IxManager home;

	private volatile boolean keepRunning = true;

	public GameServiceDiscard(Mutex serverBlock,
							  Mutex userBlock,
							  GameService service,
							  PlayerDiscard playerDiscard,
							  IxManager home) {

		this.userBlock = userBlock;
		this.serverBlock = serverBlock;
		this.service = service;
		this.playerDiscard = playerDiscard;
		this.home = home;

		t = new Thread(this);
		t.setName("GameServiceDiscard");
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
				serverBlock.waitForInterruptable();

				while (keepRunning) {
					service.setDiscardResponse(null);

					userBlock.waitForInterruptable();

					if (service.getDiscardResponse() != null) {
						playerDiscard.answer(home, service.getDiscardResponse());
						break;
					}


				}

			} catch (InterruptedException e) {
				break;
			}
		}
	}


}
