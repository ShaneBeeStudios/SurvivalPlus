package com.shanebeestudios.survival.plugin.tasks;

import com.shanebeestudios.survival.api.data.Nutrient;
import com.shanebeestudios.survival.api.data.PlayerData;
import com.shanebeestudios.survival.api.util.Utils;
import com.shanebeestudios.survival.plugin.SurvivalPlugin;
import com.shanebeestudios.survival.plugin.config.Lang;
import com.shanebeestudios.survival.plugin.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

class NutrientsAlert extends BukkitRunnable {

	private final Lang lang;
	private final PlayerManager playerManager;

	NutrientsAlert(SurvivalPlugin plugin) {
		this.lang = plugin.getLang();
		final int ALERT_INTERVAL = plugin.getSurvivalConfig().MECHANICS_ALERT_INTERVAL;
		this.playerManager = plugin.getPlayerManager();
		this.runTaskTimer(plugin, -1, ALERT_INTERVAL * 20L);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				PlayerData playerData = playerManager.getPlayerData(player);

				if (playerData.getNutrient(Nutrient.CARBS) <= 480) {
                    Utils.sendColoredMini(player, "<dark_green>", this.lang.carbohydrates_lack);
				}

				if (playerData.getNutrient(Nutrient.VITAMINS) <= 180) {
                    Utils.sendColoredMini(player, "<blue>", this.lang.vitamins_lack);
				}

				if (playerData.getNutrient(Nutrient.PROTEIN) <= 120) {
                    Utils.sendColoredMini(player, "<dark_red>", this.lang.protein_lack);
				}
			}
		}
	}

}
