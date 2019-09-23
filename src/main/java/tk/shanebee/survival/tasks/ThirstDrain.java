package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.events.ThirstLevelChangeEvent;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Config;

import java.util.Random;

class ThirstDrain extends BukkitRunnable {

	private Survival plugin;
	private Config config;

	ThirstDrain(Survival plugin) {
		this.plugin = plugin;
		this.config = plugin.getSurvivalConfig();
		this.runTaskTimer(plugin, -1, 1);
	}

	@Override
	public void run() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				if (player.getExhaustion() >= 4) {
					Random rand = new Random();
					int change = rand.nextDouble() <= config.MECHANICS_THIRST_DRAIN_RATE ? 1 : 0;
					ThirstLevelChangeEvent event = new ThirstLevelChangeEvent(player, change, StatusManager.getThirst(player) - change);
					Bukkit.getPluginManager().callEvent(event);
					if (!event.isCancelled()) {
						StatusManager.removeThirst(player, change);
					}
				}
			}
		}
	}

}
