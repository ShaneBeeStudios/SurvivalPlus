package com.shanebeestudios.survival.tasks;

import com.shanebeestudios.survival.data.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.shanebeestudios.survival.SurvivalPlugin;
import com.shanebeestudios.survival.data.Nutrient;
import com.shanebeestudios.survival.data.PlayerData;
import com.shanebeestudios.survival.managers.PlayerManager;

class NutrientsDrain extends BukkitRunnable {

	private final PlayerManager playerManager;

	NutrientsDrain(SurvivalPlugin plugin) {
		this.playerManager = plugin.getPlayerManager();
		this.runTaskTimer(plugin, -1, 1);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
            if (Permissions.BYPASS_STAT_NUTRITION.has(player)) continue;

			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				if (player.getExhaustion() >= 4) {
					PlayerData playerData = playerManager.getPlayerData(player);

					playerData.increaseNutrient(Nutrient.CARBS, -8);
					playerData.increaseNutrient(Nutrient.PROTEIN, -2);
					playerData.increaseNutrient(Nutrient.VITAMINS, -3);
				}
			}
		}
	}

}
