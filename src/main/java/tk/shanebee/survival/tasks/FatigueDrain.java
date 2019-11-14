package tk.shanebee.survival.tasks;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.data.PlayerData;
import tk.shanebee.survival.events.FatigueLevelChangeEvent;
import tk.shanebee.survival.managers.PlayerManager;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Config;
import tk.shanebee.survival.util.Lang;
import tk.shanebee.survival.util.Utils;

import java.util.Random;

class FatigueDrain extends BukkitRunnable {

	private Config config;
	private Lang lang;
	private PlayerManager playerManager;

	FatigueDrain(Survival plugin) {
		this.config = plugin.getSurvivalConfig();
		this.lang = plugin.getLang();
		this.playerManager = plugin.getPlayerManager();
		this.runTaskTimer(plugin, -1, 100);
	}

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
				PlayerData playerData = playerManager.getPlayerData(player);
				int fatigue = playerData.getFatigue();

				if (fatigue == 1)
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0), true);
				else if (fatigue == 2) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 0), true);
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10, 0), true);
				} else if (fatigue == 3) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0), true);
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 120, 0), true);
				} else if (fatigue == 4) {
					player.damage(100);
				}
				World overworld = player.getWorld();

				int fatigueLevel = config.MECHANICS_BED_FATIGUE_INCREASE_CHANCE;
				int random = new Random().nextInt(100) + 1;

				if (overworld.getTime() >= 18000 && overworld.getTime() < 18150 && !player.isSleeping() &&
						player.getStatistic(Statistic.TIME_SINCE_REST) >= 5000 && random <= fatigueLevel &&
						Utils.getMinutesPlayed(player) >= 15) {
					FatigueLevelChangeEvent fatigueEvent = new FatigueLevelChangeEvent(player, 1, fatigue + 1);
					Bukkit.getPluginManager().callEvent(fatigueEvent);
					if (!fatigueEvent.isCancelled()) {
						playerData.setFatigue(fatigue + 1);
					}

					if (fatigue == 1)
						Utils.sendColoredMsg(player, lang.feeling_sleepy_1);
					else if (fatigue == 2)
						Utils.sendColoredMsg(player, lang.feeling_sleepy_2);
					else if (fatigue == 3)
						Utils.sendColoredMsg(player, lang.feeling_sleepy_3);
					else if (fatigue == 4)
						Utils.sendColoredMsg(player, lang.collapsed_2);
				}
			}
		}
	}

}
