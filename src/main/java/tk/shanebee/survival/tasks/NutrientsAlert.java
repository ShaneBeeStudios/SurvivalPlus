package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.StatusManager;

class NutrientsAlert extends BukkitRunnable {

	NutrientsAlert(Survival plugin) {
		this.runTaskTimer(plugin, -1, plugin.getAlertInterval() * 20);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				if (StatusManager.getNutrients(player, StatusManager.Nutrients.CARBS) <= 480) {
					player.sendMessage(ChatColor.DARK_GREEN + Survival.lang.carbohydrates_lack);
				}

				if (StatusManager.getNutrients(player, StatusManager.Nutrients.SALTS) <= 180) {
					player.sendMessage(ChatColor.BLUE + Survival.lang.vitamins_lack);
				}

				if (StatusManager.getNutrients(player, StatusManager.Nutrients.PROTEIN) <= 120) {
					player.sendMessage(ChatColor.DARK_RED + Survival.lang.protein_lack);
				}
			}
		}
	}

}
