package tk.shanebee.survival.tasks;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Lang;

class ThirstAlert extends BukkitRunnable {

	private Survival plugin;
	private Lang lang;

	ThirstAlert(Survival plugin) {
		this.plugin = plugin;
		this.lang = plugin.getLang();
		final int ALERT_INTERVAL = plugin.getSurvivalConfig().MECHANICS_ALERT_INTERVAL;
		this.runTaskTimer(plugin, -1, ALERT_INTERVAL * 20);
	}
	@Override
	public void run() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				int hunger = player.getFoodLevel();
				if (hunger <= 6) {
					player.sendMessage(ChatColor.GOLD + lang.starved_eat);
				}
				if (StatusManager.getThirst(player) <= 6) {
					player.sendMessage(ChatColor.AQUA + lang.dehydrated_drink);
				}
			}
		}
	}

}
