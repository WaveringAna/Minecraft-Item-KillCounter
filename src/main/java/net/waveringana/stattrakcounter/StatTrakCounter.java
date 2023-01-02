package net.waveringana.stattrakcounter;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class StatTrakCounter implements ModInitializer  {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("staktrakcounter");
	public static final String MOD_ID = "staktrakcounter";

	@Override
	public void onInitialize() {
		LOGGER.info("StatTrak initialized");

		ServerLivingEntityEvents.AFTER_DEATH.register(new KillListener());
		PlayerBlockBreakEvents.AFTER.register(new ItemsMinedListener());
	}
}