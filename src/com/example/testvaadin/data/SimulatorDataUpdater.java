package com.example.testvaadin.data;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class SimulatorDataUpdater {
	protected DatabaseHelper dbHelp = new DatabaseHelper();
	private final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	public SimulatorDataUpdater() {
		SQLContainer simulatorContainer = dbHelp.getSimulatorContainer();
		final Runnable beeper = new Runnable() {
			@Override
			public void run() {
				System.out.println("Beep");
			}
		};
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(
				beeper, 0, 1, TimeUnit.SECONDS);
	}
}
