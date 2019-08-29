package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.StatusManager;

class NutrientsEffect extends BukkitRunnable {

	NutrientsEffect(Survival plugin) {
		this.runTaskTimer(plugin, -1, 320);
	}
	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {

				if (StatusManager.getNutrients(player, StatusManager.Nutrients.CARBS) <= 0) {
					switch (player.getWorld().getDifficulty()) {
						case EASY:
							player.setExhaustion(player.getExhaustion() + 2);
							break;
						case NORMAL:
							player.setExhaustion(player.getExhaustion() + 4);
							break;
						case HARD:
							player.setExhaustion(player.getExhaustion() + 8);
							break;
						default:
					}
				}

				if (StatusManager.getNutrients(player, StatusManager.Nutrients.SALTS) <= 0) {
					player.setExhaustion(player.getExhaustion() + 1);
					switch (player.getWorld().getDifficulty()) {
						case NORMAL:
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 0), true);
							break;
						case HARD:
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 1), true);
							break;
						default:
					}
				}

				if (StatusManager.getNutrients(player, StatusManager.Nutrients.PROTEIN) <= 0) {
					player.setExhaustion(player.getExhaustion() + 1);
					switch (player.getWorld().getDifficulty()) {
						case NORMAL:
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 0), true);
							break;
						case HARD:
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 400, 1), true);
							break;
						default:
					}
				}
			}
		}
	}

}
