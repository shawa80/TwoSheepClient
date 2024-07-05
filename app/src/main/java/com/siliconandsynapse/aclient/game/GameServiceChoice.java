package com.siliconandsynapse.aclient.game;

import android.util.Log;

import com.siliconandsynapse.ixcpp.protocol.game.PlayerChoice;
import com.siliconandsynapse.ixcpp.util.Mutex;
import com.siliconandsynapse.net.ixtunnel.IxManager;

public class GameServiceChoice implements Runnable {

	private Thread t;

	private Mutex choiceServerBlock;
	private Mutex choiceUserBlock;
	private GameService service;
	private PlayerChoice playerPickAChoice;
	private IxManager home;

	private volatile boolean keepRunning = true;

	public GameServiceChoice(Mutex choiceServerBlock,  Mutex choiceUserBlock, GameService service,
			PlayerChoice playerPickAChoice, IxManager home) {

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
				Log.d("DebugPrint", "Waiting for server choice request");

				choiceServerBlock.waitForInterruptable();

				Log.d("DebugPrint", "Server requested choice");

				while (keepRunning) {
					service.setChoiceResponse(null);
					Log.d("DebugPrint", "Waiting for user choice");

					choiceUserBlock.waitForInterruptable();

					Log.d("DebugPrint", "User unlocked choice");

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