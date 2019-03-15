package com.fattymieo.survival.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.fattymieo.survival.Survival;

public class ScoreboardStats implements Listener {

	private Objective boardHunger = Survival.mainBoard.getObjective("BoardHunger");
	private Objective boardThirst = Survival.mainBoard.getObjective("BoardThirst");
	private Objective boardFatigue = Survival.mainBoard.getObjective("BoardFatigue");
	private Objective boardNutrients = Survival.mainBoard.getObjective("BoardNutrients");

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if (!player.hasPlayedBefore()) {
			boardHunger.getScore(player.getName()).setScore(0);
			boardThirst.getScore(player.getName()).setScore(0);
			boardFatigue.getScore(player.getName()).setScore(1);
			boardNutrients.getScore(player.getName()).setScore(1);
		}
		SetupScorebard(player);
	}

	public static void SetupScorebard(Player p) {
		final Player player = p;
		final Scoreboard stats = Survival.manager.getNewScoreboard();
		player.setScoreboard(stats);

		Runnable run = new Runnable() {
			Objective status = stats.registerNewObjective("Status", "dummy");

			Objective boardHunger = Survival.mainBoard.getObjective("BoardHunger");
			Objective boardThirst = Survival.mainBoard.getObjective("BoardThirst");
			Objective boardFatigue = Survival.mainBoard.getObjective("BoardFatigue");
			Objective boardNutrients = Survival.mainBoard.getObjective("BoardNutrients");

			public void run() {
				status.unregister();
				status = stats.registerNewObjective("Status", "dummy");
				status.setDisplaySlot(DisplaySlot.SIDEBAR);
				status.setDisplayName("Status");

				if (boardHunger.getScore(player.getName()).getScore() <= 0) {
					Score hunger0 = status.getScore(Survival.ShowHunger(player).get(0));
					hunger0.setScore(10);
					Score hunger1 = status.getScore(Survival.ShowHunger(player).get(1));
					hunger1.setScore(9);
					Score hunger2 = status.getScore(Survival.ShowHunger(player).get(2));
					hunger2.setScore(8);
				}

				if (Survival.settings.getBoolean("Mechanics.Thirst.Enabled") && boardThirst.getScore(player.getName()).getScore() <= 0) {
					Score thirst0 = status.getScore(Survival.ShowThirst(player).get(0));
					thirst0.setScore(7);
					Score thirst1 = status.getScore(Survival.ShowThirst(player).get(1));
					thirst1.setScore(6);
					Score thirst2 = status.getScore(Survival.ShowThirst(player).get(2));
					thirst2.setScore(5);
				}

				if (Survival.settings.getBoolean("Mechanics.BedFatigueLevel") && boardFatigue.getScore(player.getName()).getScore() <= 0) {
					Score fatigue = status.getScore(Survival.ShowFatigue(player));
					fatigue.setScore(4);
				}

				if (Survival.settings.getBoolean("Mechanics.FoodDiversity") && boardNutrients.getScore(player.getName()).getScore() <= 0) {
					Score carbon = status.getScore(Survival.ShowNutrients(player).get(0));
					carbon.setScore(3);
					Score protein = status.getScore(Survival.ShowNutrients(player).get(1));
					protein.setScore(2);
					Score salts = status.getScore(Survival.ShowNutrients(player).get(2));
					salts.setScore(1);
				}

				if (player.isOnline())
					Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, this, 10L);
			}
		};

		Bukkit.getScheduler().scheduleSyncDelayedTask(Survival.instance, run, -1);
	}

}
