package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.managers.PlayerManager;

class ThirstEffect extends BukkitRunnable {

	private PlayerManager playerManager;

	ThirstEffect(Survival plugin) {
		this.playerManager = plugin.getPlayerManager();
		this.runTaskTimer(plugin, -1, 320);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				PlayerData playerData = playerManager.getPlayerData(player);

				if (playerData.getThirst() <= 0) {
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
