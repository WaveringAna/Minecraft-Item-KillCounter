package net.waveringana.killtracker;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;

public class KillTracker implements ModInitializer  {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("killtracker");
	public static final String MOD_ID = "killtracker";

	@Override
	public void onInitialize() {
		LOGGER.info("Killtracker initialized");

		ServerLivingEntityEvents.AFTER_DEATH.register(new KillListener());
	}
}