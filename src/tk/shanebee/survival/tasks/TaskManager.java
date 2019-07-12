package tk.shanebee.survival.tasks;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import tk.shanebee.survival.Survival;
import tk.shanebee.survival.managers.StatusManager;
import tk.shanebee.survival.util.Utils;

import java.util.Objects;
import java.util.Random;

public class TaskManager {

	private Survival plugin;
	private Scoreboard mainBoard;

	public TaskManager(Survival plugin) {
		this.plugin = plugin;
		this.mainBoard = Survival.mainBoard;
	}

	public void daysNoSleep() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {

					if (StatusManager.getFatigue(player) == 1)
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0), true);
					else if (StatusManager.getFatigue(player) == 2) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 0), true);
						player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10, 0), true);
					} else if (StatusManager.getFatigue(player) == 3) {
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 120, 0), true);
						player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 120, 0), true);
					} else if (StatusManager.getFatigue(player) == 4) {
						player.damage(100);
					}
					World overworld = player.getWorld();

					int fatigueLevel = Survival.settings.getInt("Mechanics.FatigueLevelIncreaseChance");
					int random = new Random().nextInt(100) + 1;

					if (overworld.getTime() >= 18000 && overworld.getTime() < 18100 && !player.isSleeping() &&
							player.getStatistic(Statistic.TIME_SINCE_REST) >= 5000 && random <= fatigueLevel &&
							Utils.getMinutesPlayed(player) >= 15) {
						StatusManager.increaseFatigue(player);

						if (StatusManager.getFatigue(player) == 1)
							Utils.sendColoredMsg(player, Survival.lang.feeling_sleepy_1);
						else if (StatusManager.getFatigue(player) == 2)
							Utils.sendColoredMsg(player, Survival.lang.feeling_sleepy_2);
						else if (StatusManager.getFatigue(player) == 3)
							Utils.sendColoredMsg(player, Survival.lang.feeling_sleepy_3);
						else if (StatusManager.getFatigue(player) == 4)
							Utils.sendColoredMsg(player, Survival.lang.collapsed_2);
					}
				}
			}
		}, -1, 100);

		int refreshTime = Survival.settings.getInt("Mechanics.BedFatigueRefreshTime");

		if (refreshTime >= 1) {
			Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
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
			}, -1, 20 * refreshTime);
		}
	}

	public void playerStatus() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
					if (player.getExhaustion() >= 4) {
						Random rand = new Random();
						double chance = rand.nextDouble();
						StatusManager.removeThirst(player, chance <= Survival.settings.getDouble("Mechanics.Thirst.DrainRate") ? 1 : 0);
					}
				}
			}
		}, -1L, 1L);

		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
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
		}, -1L, 320L);

		if (!Survival.settings.getBoolean("Mechanics.StatusScoreboard") && plugin.getAlertInterval() > 0) {
			plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
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
			}, -1L, plugin.getAlertInterval() * 20);
		}
	}

	public void foodDiversity() {
		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
					for (Player player : plugin.getServer().getOnlinePlayers()) {
						if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
							Score carbon = Objects.requireNonNull(mainBoard.getObjective("Carbs")).getScore(player.getName());
							Score protein = Objects.requireNonNull(mainBoard.getObjective("Protein")).getScore(player.getName());
							Score salts = Objects.requireNonNull(mainBoard.getObjective("Salts")).getScore(player.getName());
							if (player.getExhaustion() >= 4) {
								carbon.setScore(carbon.getScore() - 8);
								if (carbon.getScore() < 0)
									carbon.setScore(0);

								protein.setScore(protein.getScore() - 2);
								if (protein.getScore() < 0)
									protein.setScore(0);

								salts.setScore(salts.getScore() - 3);
								if (salts.getScore() < 0)
									salts.setScore(0);
							}
						}
					}
				}, -1L, 1L);

		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
					for (Player player : plugin.getServer().getOnlinePlayers()) {
						if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
							Score carbon = Objects.requireNonNull(mainBoard.getObjective("Carbs")).getScore(player.getName());
							Score protein = Objects.requireNonNull(mainBoard.getObjective("Protein")).getScore(player.getName());
							Score salts = Objects.requireNonNull(mainBoard.getObjective("Salts")).getScore(player.getName());

							if (carbon.getScore() <= 0) {
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

							if (salts.getScore() <= 0) {
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

							if (protein.getScore() <= 0) {
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
				}, -1L, 320L);

		if (!Survival.settings.getBoolean("Mechanics.StatusScoreboard") && plugin.getAlertInterval() > 0) {
			plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
						for (Player player : plugin.getServer().getOnlinePlayers()) {
							if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
								Score carbon = Objects.requireNonNull(mainBoard.getObjective("Carbs")).getScore(player.getName());
								Score protein = Objects.requireNonNull(mainBoard.getObjective("Protein")).getScore(player.getName());
								Score salts = Objects.requireNonNull(mainBoard.getObjective("Salts")).getScore(player.getName());

								if (carbon.getScore() <= 480) {
									player.sendMessage(ChatColor.DARK_GREEN + Survival.lang.carbohydrates_lack);
								}

								if (salts.getScore() <= 180) {
									player.sendMessage(ChatColor.BLUE + Survival.lang.vitamins_lack);
								}

								if (protein.getScore() <= 120) {
									player.sendMessage(ChatColor.DARK_RED + Survival.lang.protein_lack);
								}
							}
						}
					}, -1L, plugin.getAlertInterval() * 20);
		}
	}

}
