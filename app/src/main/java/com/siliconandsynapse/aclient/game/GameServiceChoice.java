package com.siliconandsynapse.aclient.game;

import android.util.Log;

import com.siliconandsynapse.ixcpp.protocol.game.PlayerChoice;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.IxManager;

public class GameServiceChoice implements Runnable {

	private final Thread t;

	private final Mutex choiceServerBlock;
	private final Mutex choiceUserBlock;
	private final GameService service;
	private final PlayerChoice playerPickAChoice;
	private final IxManager home;

	private volatile boolean keepRunning = true;

	public GameServiceChoice(Mutex choiceServerBlock,
							 Mutex choiceUserBlock,
							 GameService service,
							 PlayerChoice playerPickAChoice,
							 IxManager home) {

		this.choiceUserBlock = choiceUserBlock;
		this.choiceServerBlock = choiceServerBlock;
		this.service = service;
		this.playerPickAChoice = playerPickAChoice;
		this.home = home;

		t = new Thread(this);
		t.setName("GameServiceChoice");
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

				choiceServerBlock.waitForInterruptable();

				while (keepRunning) {
					service.setChoiceResponse(null);

					choiceUserBlock.waitForInterruptable();

					if (service.getChoiceResponse() != null) {
						playerPickAChoice.answer(home, service.getChoiceResponse());
						break;
					}


				}

			} catch (InterruptedException e) {
				break;
			}
		}
	}


}