package tk.shanebee.survival.tasks;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.StatusManager;

class ThirstAlert extends BukkitRunnable {

	private Survival plugin;

	ThirstAlert(Survival plugin) {
		this.plugin = plugin;
		this.runTaskTimer(plugin, -1, plugin.getAlertInterval() * 20);
	}
	@Override
	public void run() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				int hunger = player.getFoodLevel();
				if (hunger <= 6) {
					player.sendMessage(ChatColor.GOLD + Survival.lang.starved_eat);
				}
				if (StatusManager.getThirst(player) <= 6) {
					player.sendMessage(ChatColor.AQUA + Survival.lang.dehydrated_drink);
				}
			}
		}
	}

}
