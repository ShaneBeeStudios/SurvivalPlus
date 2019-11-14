package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.Nutrient;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.Lang;

class NutrientsAlert extends BukkitRunnable {

	private Lang lang;
	private PlayerManager playerManager;

	NutrientsAlert(Survival plugin) {
		this.lang = plugin.getLang();
		final int ALERT_INTERVAL = plugin.getSurvivalConfig().MECHANICS_ALERT_INTERVAL;
		this.playerManager = plugin.getPlayerManager();
		this.runTaskTimer(plugin, -1, ALERT_INTERVAL * 20);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				PlayerData playerData = playerManager.getPlayerData(player);

				if (playerData.getNutrient(Nutrient.CARBS) <= 480) {
					player.sendMessage(ChatColor.DARK_GREEN + lang.carbohydrates_lack);
				}

				if (playerData.getNutrient(Nutrient.SALTS) <= 180) {
					player.sendMessage(ChatColor.BLUE + lang.vitamins_lack);
				}

				if (playerData.getNutrient(Nutrient.PROTEIN) <= 120) {
					player.sendMessage(ChatColor.DARK_RED + lang.protein_lack);
				}
			}
		}
	}

}
