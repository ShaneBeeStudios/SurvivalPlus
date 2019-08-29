package tk.shanebee.survival.tasks;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Utils;

class FatigueRefresh extends BukkitRunnable {

	private Survival plugin;

	FatigueRefresh(Survival plugin) {
		this.plugin = plugin;
		this.runTaskTimer(plugin, -1, 20 * plugin.getSurvivalConfig().MECHANICS_FATIGUE_REFRESH_TIME);
	}

	@Override
	public void run() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				if (player.isSleeping()) {
					if (StatusManager.getFatigue(player) >= 1) {
						StatusManager.setFatigue(player, StatusManager.getFatigue(player) - 1);
						Utils.sendColoredMsg(player, Utils.getColoredString(Survival.lang.energy_rising));
					}
				}
			}
		}
	}

}
