package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Lang;

class NutrientsAlert extends BukkitRunnable {

	private Lang lang;

	NutrientsAlert(Survival plugin) {
		this.lang = plugin.getLang();
		final int ALERT_INTERVAL = plugin.getSurvivalConfig().MECHANICS_ALERT_INTERVAL;
		this.runTaskTimer(plugin, -1, ALERT_INTERVAL * 20);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				if (StatusManager.getNutrients(player, StatusManager.Nutrients.CARBS) <= 480) {
					player.sendMessage(ChatColor.DARK_GREEN + lang.carbohydrates_lack);
				}

				if (StatusManager.getNutrients(player, StatusManager.Nutrients.SALTS) <= 180) {
					player.sendMessage(ChatColor.BLUE + lang.vitamins_lack);
				}

				if (StatusManager.getNutrients(player, StatusManager.Nutrients.PROTEIN) <= 120) {
					player.sendMessage(ChatColor.DARK_RED + lang.protein_lack);
				}
			}
		}
	}

}
