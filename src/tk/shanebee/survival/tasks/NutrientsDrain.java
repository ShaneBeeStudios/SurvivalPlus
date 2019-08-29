package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.StatusManager;

class NutrientsDrain extends BukkitRunnable {

	private Survival plugin;

	NutrientsDrain(Survival plugin) {
		this.plugin = plugin;
		this.runTaskTimer(plugin, -1, 1);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				if (player.getExhaustion() >= 4) {
					StatusManager.removeNutrients(player, StatusManager.Nutrients.CARBS, 8);
					StatusManager.removeNutrients(player, StatusManager.Nutrients.PROTEIN, 2);
					StatusManager.removeNutrients(player, StatusManager.Nutrients.SALTS, 3);
				}
			}
		}
	}

}
