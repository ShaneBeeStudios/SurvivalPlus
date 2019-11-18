package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;
import tk.shanebee.survival.data.Info;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.util.Utils;

public class OldHealthboard extends BukkitRunnable {

	private Survival plugin;
	private Player player;
	private Config config;
	private Scoreboard stats;
	private Objective status;

	public OldHealthboard(Survival plugin, Player player) {
		this.plugin = plugin;
		this.config = plugin.getSurvivalConfig();
		this.player = player;
		stats = Bukkit.getScoreboardManager().getNewScoreboard();
		player.setScoreboard(stats);
		this.status = stats.registerNewObjective("Status", "dummy", "Status");
		this.runTaskTimer(plugin, -1, 10);
	}

	@Override
	public void run() {
		status.unregister();
		status = stats.registerNewObjective("Status", "dummy", "Status");

		PlayerData playerData = plugin.getPlayerManager().getPlayerData(player);
		if (!player.isOnline()) return;

		GameMode mode = player.getGameMode();
		if (mode == GameMode.SURVIVAL || mode == GameMode.ADVENTURE) {
			status.setDisplaySlot(DisplaySlot.SIDEBAR);
			status.setDisplayName("Status");

			if (playerData.isInfoDisplayed(Info.HUNGER)) {
				Score hunger0 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(0));
				hunger0.setScore(10);
				Score hunger1 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(1));
				hunger1.setScore(9);
				Score hunger2 = status.getScore(plugin.getPlayerManager().ShowHunger(player).get(2));
				hunger2.setScore(8);
			}

			if (config.MECHANICS_THIRST_ENABLED && playerData.isInfoDisplayed(Info.THIRST)) {
				Score thirst0 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(0));
				thirst0.setScore(7);
				Score thirst1 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(1));
				thirst1.setScore(6);
				Score thirst2 = status.getScore(plugin.getPlayerManager().ShowThirst(player).get(2));
				thirst2.setScore(5);
			}

			if (config.MECHANICS_BED_FATIGUE_ENABLED && playerData.isInfoDisplayed(Info.FATIGUE)) {
				Score fatigue = status.getScore(plugin.getPlayerManager().ShowFatigue(player));
				fatigue.setScore(4);
			}

			if (config.MECHANICS_FOOD_DIVERSITY_ENABLED && playerData.isInfoDisplayed(Info.NUTRIENTS)) {
				Score carbon = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(0));
				carbon.setScore(3);
				Score protein = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(1));
				protein.setScore(2);
				Score salts = status.getScore(plugin.getPlayerManager().ShowNutrients(player).get(2));
				salts.setScore(1);
			}
		}
	}

}
