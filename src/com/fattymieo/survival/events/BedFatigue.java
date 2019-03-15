package com.fattymieo.survival.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Objective;

import com.fattymieo.survival.Survival;

public class BedFatigue implements Listener {

	private Objective fatigue = Survival.mainBoard.getObjective("Fatigue");

	@EventHandler
	public void onBedLeave(PlayerBedLeaveEvent e) {
		long time = e.getBed().getWorld().getTime();
		if (time % 24000 == 0) {
			Player player = e.getPlayer();
			fatigue.getScore(player.getName()).setScore(0);
		}
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		fatigue.getScore(player.getName()).setScore(0);
	}

	@EventHandler
	public void onFirstJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (!player.hasPlayedBefore()) {
			fatigue.getScore(player.getName()).setScore(0);
		}
	}

}