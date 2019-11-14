package tk.shanebee.survival.tasks;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;

class FatigueRefresh extends BukkitRunnable {

	private Survival plugin;
	private Lang lang;
	private PlayerManager playerManager;

	FatigueRefresh(Survival plugin) {
		this.plugin = plugin;
		this.lang = plugin.getLang();
		this.playerManager = plugin.getPlayerManager();
		this.runTaskTimer(plugin, -1, 20 * plugin.getSurvivalConfig().MECHANICS_BED_FATIGUE_REFRESH_TIME);
	}

	@Override
	public void run() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				if (player.isSleeping()) {
					PlayerData playerData = playerManager.getPlayerData(player);
					int fatigue = playerData.getFatigue();

					if (fatigue >= 1) {
						playerData.setFatigue(fatigue - 1);
						Utils.sendColoredMsg(player, Utils.getColoredString(lang.energy_rising));
					}
				}
			}
		}
	}

}
