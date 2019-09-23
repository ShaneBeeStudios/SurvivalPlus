package tk.shanebee.survival.tasks;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Config;

class ThirstEffect extends BukkitRunnable {

	private Survival plugin;
	private Config config;

	ThirstEffect(Survival plugin) {
		this.plugin = plugin;
		this.config = plugin.getSurvivalConfig();
		this.runTaskTimer(plugin, -1, 320);
	}

	@Override
	public void run() {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				if (StatusManager.getThirst(player) <= 0) {
					switch (player.getWorld().getDifficulty()) {
						case EASY:
							if (player.getHealth() > 10)
								player.damage(1);
							break;
						case NORMAL:
							if (player.getHealth() > 1)
								player.damage(1);
							break;
						case HARD:
							player.damage(1);
							break;
						default:
					}
				}
			}
		}
	}

}
